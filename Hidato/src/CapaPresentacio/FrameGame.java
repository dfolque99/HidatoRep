package CapaPresentacio;

import CapaDomini.Misc.Colors;
import CapaDomini.Misc.Fonts;
import CapaDomini.Partida.CurrentGameController;
import CapaDomini.Partida.Help;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Tauler.SolverControllerStop;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Vista de la partida.
 * @author Pau
 */
public class FrameGame extends javax.swing.JFrame {

    /**
     * Classe que representa una accio de l'usuari. Conte les coordenades de la 
     * casella on l'usuari ha fet l'accio, i el valor que tenia abans que la fes.
     */
    private class Accio{
        public int x,y,val;
        Accio(int x0, int y0, int val0){
            x = x0;
            y = y0;
            val = val0;
        }
    }
    
    /**
     * Matriu de cells que formen el tauler de la partida.
     */
    private final ArrayList<ArrayList<SquareCell>> panels;

    /**
     * Colors que s'utilitzaran pels diferents tipus de cells.
     */
    private Color blankColor;
    private Color hintColor;
    private Color voidColor;
    private Color clickColor;
    
    /**
     * Controlador de partida amb qui es comunicara la vista.
     */
    private CurrentGameController currentGameCtr;
    
    /**
     * Boolea que indica si la partida esta pausada.
     */
    private Boolean isGamePaused;
    
    /**
     * Variabla que guarda el temps que ha passat des de l'ultima pausa.
     */
    private int timeSincePause;
    
    /**
     * Controlador del conjunt de hidatos amb qui es comunicara la vista.
     */
    private HidatoManagerController hidatoManagerController;
    
    /**
     * Objecte que ha creat aquest.
     */
    private AdminVistes parent;
    
    /**
     * Temporitzador usat per calcular el temps de partida en temps real.
     */
    private final Timer timer;
    
    /**
     * Guarda totes les accions que ha fet l'usuari, per poder-les desfer si aquest ho demana.
     */
    private Stack<Accio> historial;
    
    /**
     * Instancia de la classe, utilitzada pels threads.
     */
    private final FrameGame dis = this;
    
    /**
     * Variables per compartir entre threads
     */
    private int v_compartit;
    private SquareCell p_compartit;
    
    /**
     * Barra de progres.
     */
    private DialogProgressBar dialog;
    
    /**
     * Crea un JOptionPane per indicar-li un error a l'usuari
     * @param text Text del missatge d'error
     */
    private void msgError(String text) {
        JOptionPane.showMessageDialog(this,text,"Error",JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Crea un JOptionPane per informar a l'usuari d'alguna cosa
     * @param text Text del missatge
     * @param title Titol del missatge
     */
    private void msg(String text, String title) {
        JOptionPane.showMessageDialog(this,text,title,JOptionPane.PLAIN_MESSAGE);
    }
    
    /**
     * Funcio que es crida quan es crea una instancia de FrameGame. 
     * S'ocupa d'inicialitzar tots els parametres.
     * @param parent instancia que ha creat aquest objecte
     * @param cgc controlador de la partida 
     * @param hmc controlador de hidatos
     */
    private void inicialitzaParametres(AdminVistes parent, CurrentGameController cgc, HidatoManagerController hmc){
        this.parent = parent;
        hidatoManagerController = hmc;
        currentGameCtr = cgc;
        helpLabel.setText("Nivell d'ajuda: "+currentGameCtr.getHelp());
        diffLabel.setText("Dificultat: "+currentGameCtr.getDifficulty());
        if (currentGameCtr.getName() == null){
            titleLabel.setText("Nova partida");
        }else titleLabel.setText("Partida: "+currentGameCtr.getName());
        historial = new Stack<>();
        undoButton.setEnabled(false);
    }
    
    /**
     * Funcio que s'executa quan l'usuari acaba una partida. Actualitza totes 
     * les dades corresponents, i obre el menu principal. Tambe pregunta a l'usuari
     * si vol guardar el hidato de la partida al repositori.
     */
    private void acabaPartida(){
        currentGameCtr.finishGame();
        timer.stop();
        msg("Hidato completat!\n Puntuacio: "+currentGameCtr.calculateScore(),"Felicitats!");
        if (currentGameCtr.isVolatile() && currentGameCtr.getUsername() != null){
            String newName = JOptionPane.showInputDialog(this, "Vols guardar el hidato? Escriu el nom:");
            Boolean aux = hidatoManagerController.usedName(newName);
            while (newName != null && aux){
                newName = JOptionPane.showInputDialog(this, "Ja hi ha un hidato amb aquest nom. Tria'n un altre:");
                aux = hidatoManagerController.usedName(newName);
            }
            if (newName != null){
                currentGameCtr.setBoardName(newName);
                hidatoManagerController.saveTempHidato(newName);
            }
        }
        this.setVisible(false);
        parent.obrirMenu(this);
    }
    
    /**
     * Calcula el seguent nombre a partir d'un donat que no esta colocat al hidato.
     * Si arriba al final, torna a començar.
     * @param ini Nombre donat
     * @return Minim nombre mes gran que ini que no esta al hidato. Retorna -1
     * si no n'ha trobat cap.
     */
    private int nextNumber(int ini){
        return currentGameCtr.nextNumber(ini);
    }
    
    /**
     * Creadora, i funcio principal de la classe. Crea la matriu de panels que 
     * representara al hidato, i afegeix els listeners a les celes que detecten
     * diferents events, com quan es clica o quan el ratoli hi passa per sobre
     * @param parent objecte que ha creat el frameGame
     * @param cgc controlador de partida (comunicacio vista-controlador)
     * @param hmc controlador del conjunt de hidatos
     */
    public FrameGame(AdminVistes parent, CurrentGameController cgc, HidatoManagerController hmc) {
        initComponents();
        inicialitzaParametres(parent, cgc, hmc);
        
        setIconImage((new ImageIcon("icon.png")).getImage());

        int x = currentGameCtr.getSizeX(), y = currentGameCtr.getSizeY();
        int maxim, x1, x2, y1, y2;
        maxim = Math.max(x,y);
        x1 = (maxim-x)/2; 
        x2 = x + x1;
        y1 = (maxim-y)/2;
        y2 = y + y1; //aixi el hidato queda centrat en el panel
        boardPanel.setLayout(new GridLayout(maxim, maxim));

        if (currentGameCtr.getHelp() == Help.HIGH){
            checkButton.setEnabled(false);
        }
        
        ActionListener taskPerformer = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                int duration;
                if (!isGamePaused) timeSincePause++;
                duration = (int)currentGameCtr.getDuration().toMillis()/1000+timeSincePause;
                timeLabel.setText("Temps: "+duration+"s");
            }
        };
        
        timer = new Timer(1000,taskPerformer);
        timer.start();
        
        isGamePaused = false;
        timeSincePause = 0;
        
        this.setFocusable(true);

        
        newValue.setValue(nextNumber(1));
        
        panels = new ArrayList<>();
        for (int i0 = 0; i0 < maxim; ++i0) {
            panels.add(new ArrayList<>());
            for (int j0 = 0; j0 < maxim; ++j0) {
                if (x1 <= i0 && i0 < x2 && y1 <= j0 && j0 < y2) {
                    int i = i0 - x1, j = j0-y1; //i, j van de 0 a x-1 i de 0 a y-1 respectivament
                    int valor = currentGameCtr.getCellVal(i, j);
                    CapaDomini.Tauler.Type type = currentGameCtr.getCellType(i, j);
                    int fontSize = 250/maxim;
                    
                    voidColor = Colors.c(0);
                    if (valor == 1 || valor == currentGameCtr.getValidCells()){
                        hintColor = Colors.c(3);
                    }else hintColor = Colors.c(1);
                    blankColor = Colors.c(2);
                    clickColor = Colors.c(4);
                    
                    SquareCell p = new SquareCell(i,j, valor, type, blankColor, clickColor, hintColor, voidColor, fontSize, type == CapaDomini.Tauler.Type.BLANK);
                   
                    panels.get(i).add(p);
                    boardPanel.add(p,i0*maxim+j0);
                    p.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mousePressed(java.awt.event.MouseEvent evt){
                            p.setLight(true);
                        }
                        @Override
                        public void mouseReleased(java.awt.event.MouseEvent evt){
                            if (p.getLight()){
                                p.setLight(false);
                                int v;
                                v = (Integer) newValue.getValue();
                                if (evt.isControlDown()) v = 0;
                                
                                SolverControllerStop.allow();
                                dis.setEnabled(false);
                                v_compartit = v;
                                p_compartit = p;
                                Thread t = new Thread(new Runnable() {
                                    public void run() {
                                        posarValor(p_compartit, v_compartit);
                                    }
                                });
                                if (currentGameCtr.getHelp() == Help.HIGH) dialog = obrirProgressBar("Comprovant valor...", t);
                                t.start();
                            }
                        }
                    });
                }else{
                    JPanel p = new JPanel();
                    boardPanel.add(p,i0*maxim+j0);
                }
            }
        }
    }
    
    /**
     * Coloca un valor en un panell de la vista
     * @param p panell que volem
     * @param v valor a colocar
     */
    private void posarValor(SquareCell p, int v) {
        int errCode = currentGameCtr.putValue(v, p.getA(), p.getB());
        if (!SolverControllerStop.isStopped()) {
            dis.setEnabled(true);
            if (dialog != null) dialog.dispose();
            if (errCode == -1){
                msgError("No s'ha colocat el numero perque el hidato ja no te solucio");
            }else if (errCode == -2){
                msgError("No s'ha colocat el numero perque hi ha dos nombres consecutius separats");
            }else if (errCode == -3){
                msgError("El valor esta fora del rang!");
            }else if (errCode == -4){
                msgError("Ja hi ha una cell amb aquest valor!");
            }else if (errCode == -5){
                msgError("La posicio no es valida!");
            }else{
                int oldVal = p.getVal();
                historial.add(new Accio(p.getA(), p.getB(), oldVal));
                undoButton.setEnabled(true);
                p.changeVal(v);
                if (nextNumber(1) == -1){
                    if (currentGameCtr.isSolved()) acabaPartida();
                }else{
                    if((int) newValue.getValue() != 0){
                        newValue.setValue(nextNumber(v));
                        if ((int) newValue.getValue() == -1){
                            newValue.setValue(nextNumber(1));
                        }
                    } 
                }
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boardPanel = new javax.swing.JPanel();
        buttonsPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        newValue = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        checkButton = new javax.swing.JButton();
        hintButton = new javax.swing.JButton();
        solveButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        restartButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        undoButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        timeLabel = new javax.swing.JLabel();
        helpLabel = new javax.swing.JLabel();
        diffLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Partida");
        setPreferredSize(new java.awt.Dimension(800, 600));
        setSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        boardPanel.setPreferredSize(new java.awt.Dimension(450, 450));

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Modificar valors"));

        jLabel2.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)
        );
        jLabel2.setText("Ctrl + Click esborra el número");

        jLabel1.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)
        );
        jLabel1.setText("Nombre a colocar:");

        newValue.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newValue, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(newValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Accions"));

        checkButton.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)
        );
        checkButton.setText("Comprova");
        checkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkButtonActionPerformed(evt);
            }
        });

        hintButton.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)
        );
        hintButton.setText("Pista");
        hintButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hintButtonActionPerformed(evt);
            }
        });

        solveButton.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)
        );
        solveButton.setText("Resol");
        solveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solveButtonActionPerformed(evt);
            }
        });

        pauseButton.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)
        );
        pauseButton.setText("Pausa");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        restartButton.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)
        );
        restartButton.setText("Reinicia");
        restartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartButtonActionPerformed(evt);
            }
        });

        exitButton.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)
        );
        exitButton.setText("Surt");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        undoButton.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)
        );
        undoButton.setText("Desfés");
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(checkButton, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(hintButton, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(solveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pauseButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(undoButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(restartButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkButton)
                    .addComponent(pauseButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hintButton)
                    .addComponent(undoButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(solveButton)
                    .addComponent(restartButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitButton))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        timeLabel.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)
        );
        timeLabel.setText("Temps: ");

        helpLabel.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)
        );
        helpLabel.setText("Nivell d'ajuda:");

        diffLabel.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)
        );
        diffLabel.setText("Dificultat:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(timeLabel)
                    .addComponent(helpLabel)
                    .addComponent(diffLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(helpLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diffLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        titleLabel.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 36));
        titleLabel.setText("Hidato:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                        .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Obre un dialeg amb una barra de progres.
     * @param titol Titol del dialeg obert
     * @param t Thread que se n'ocupa
     * @return El dialeg creat
     */
    private DialogProgressBar obrirProgressBar(String titol, Thread t) {
        DialogProgressBar dialog = new DialogProgressBar(this,false,new Runnable() {
            @Override
            public void run() {
                SolverControllerStop.stop();
                dis.setEnabled(true);
            }
        });
        int x = dis.getLocation().x+(dis.getSize().width-dialog.getSize().width)/2;
        int y = dis.getLocation().y+(dis.getSize().height-dialog.getSize().height)/2;
        dialog.setTitle(titol);
        dialog.setLocation(new Point(x,y));
        dialog.setVisible(true);
        return dialog;
    }
    
    /**
     * Quan es prem el boto de comprovar, es busca una solucio al hidato i s'indica
     * a l'usuari si s'ha trobat o no. L'usuari pot cancelar la busca en qualsevol moment.
     * @param evt event de clicar en el boto
     */
    private void checkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkButtonActionPerformed
        dis.setEnabled(false);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SolverControllerStop.allow();
                boolean result = currentGameCtr.check();
                if (!SolverControllerStop.isStopped()) {
                        dis.setEnabled(true);
                        dialog.dispose();
                    if(result){
                        msg("El hidato encara te solucio","");
                    }else{
                        msgError("El hidato no te solucio");
                    }
                }
            }
        });
        dialog = obrirProgressBar("Buscant una solució...", t);
        t.start();
    }//GEN-LAST:event_checkButtonActionPerformed

    /**
     * Quan es prem el boto de pista, la vista demana una pista al controlador.
     * Si es troba, es coloca en el panel la pista retornada. Si el hidato no te
     * solucio, aleshores no es retorna cap pista i s'avisa a l'usuari.
     * @param evt event de clicar en el boto
     */
    private void hintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hintButtonActionPerformed
        dis.setEnabled(false);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SolverControllerStop.allow();
                ArrayList<Integer> hint = currentGameCtr.requestHint();
                if (!SolverControllerStop.isStopped()) {
                    dis.setEnabled(true);
                    if (dialog != null) dialog.dispose();
                    if (hint == null){
                        msgError("El hidato no te solucio");
                        return;
                    }
                    int x = hint.get(0);
                    int y = hint.get(1);
                    int value = hint.get(2);
                    int oldVal = panels.get(x).get(y).getVal();
                    historial.add(new Accio(x,y,oldVal));
                    undoButton.setEnabled(true);
                    panels.get(x).get(y).changeVal(value);
                    newValue.setValue(nextNumber((int)newValue.getValue()-1));
                    if(nextNumber(1) == -1){
                        acabaPartida();
                    }
                }
            }
        });
        dialog = obrirProgressBar("Buscant una pista...", t);
        t.start();
    }//GEN-LAST:event_hintButtonActionPerformed

    /**
     * Quan es prem el boto de resoldre, el controlador resol el hidato, i la vista
     * l'actualitza tot. Prement aquest boto, l'usuari sempre tindra una puntuacio de 0.
     * @param evt event de clicar en el boto
     */
    private void solveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solveButtonActionPerformed
        dis.setEnabled(false);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SolverControllerStop.allow();
                currentGameCtr.solve();
                if (!SolverControllerStop.isStopped()) {
                    dis.setEnabled(true);
                    if (dialog != null) dialog.dispose();
                    for(int i = 0; i < currentGameCtr.getSizeX(); i++){
                        for(int j = 0; j < currentGameCtr.getSizeY(); j++){
                            int value = currentGameCtr.getCellVal(i, j);
                            panels.get(i).get(j).changeVal(value);
                        }
                    }
                    acabaPartida();
                }
            }
        });
        dialog = obrirProgressBar("Resolent el hidato...", t);
        t.start();
    }//GEN-LAST:event_solveButtonActionPerformed

    /**
     * Quan es prem el boto de pausa, es pausa el temps, i s'amaga el tauler 
     * (per evitar que l'usuari resolgui el joc amb menys temps del real). 
     * Apareix una nova finestra amb un boto que quan es clica, torna a la partida.
     * @param evt event de clicar en el boto
     */
    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        currentGameCtr.pause();
        isGamePaused = true;
        boardPanel.setVisible(false);
        timeSincePause = 0;          
        msg("Joc pausat. Prem OK per continuar","Pausa");
        currentGameCtr.unpause();
        isGamePaused = false; 
        boardPanel.setVisible(true);
    }//GEN-LAST:event_pauseButtonActionPerformed

    /**
     * Quan es prem el boto de reiniciar, la partida torna a l'estat inicial. 
     * Totes les estadistiques de la partida tambe es reinicien.
     * @param evt event de clicar en el boto
     */
    private void restartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartButtonActionPerformed
        currentGameCtr.restartGame();
        timeSincePause = 0;
        for(int i = 0; i < currentGameCtr.getSizeX(); i++){
            for(int j = 0; j < currentGameCtr.getSizeY(); j++){
                int value = currentGameCtr.getCellVal(i, j);
                panels.get(i).get(j).changeVal(value);
                historial = new Stack<>();
                undoButton.setEnabled(false);
            }
        }
        newValue.setValue(nextNumber(1));
    }//GEN-LAST:event_restartButtonActionPerformed

    /**
     * Quan es tanca la finestra, es guarden les llistes de hidatos i partides
     * en fitxers, per poder-les recuperar quan es torni a jugar.
     * @param evt event de clicar en el boto
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        parent.saveBeforeClose();
    }//GEN-LAST:event_formWindowClosing

    /**
     * Quan es prem el boto de sortir, el joc demanara si es vol guardar la partida, 
     * i en cas afirmatiu, amb quin nom es vol guardar. En cas que la partida ja 
     * hagi estat guardada anteriorment, no es demanara nom, nomes s'actualitzara.
     * @param evt event de clicar en el boto
     */
    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        currentGameCtr.pause();
        timeSincePause = 0;       
        isGamePaused = true;
        boardPanel.setVisible(false);
        if (currentGameCtr.getUsername() == null){
            String[] opcions = new String[] {"Si", "No"};
            int response = JOptionPane.showOptionDialog(this, "Vols sortir de la partida sense guardar?", "Sortir a menu",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, opcions, opcions[0]);
            if (response == 0){
                parent.obrirMenu(this);
            }else{
                currentGameCtr.unpause();
                isGamePaused = false;
                boardPanel.setVisible(true);
            }
        }else{        
            String[] opcions = new String[] {"Si", "No", "Cancel·la"};
            int response = JOptionPane.showOptionDialog(this, "Vols guardar la partida?", "Sortir a menu",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, opcions, opcions[0]);
            if (response == 0 || response == 1){
                if (response == 0){
                    if (currentGameCtr.getName() == null) {
                        String newName = JOptionPane.showInputDialog(this, "Escriu el nom de la partida: ");
                        if (newName == null) {
                            boardPanel.setVisible(true);
                            currentGameCtr.unpause();
                            isGamePaused = false;
                            return;
                        }
                        if (newName.equals("")){
                            msgError("Introdueix algun nom");
                            boardPanel.setVisible(true);
                            currentGameCtr.unpause();
                            isGamePaused = false;
                            return;
                        }
                        if (currentGameCtr.existsGame(newName)){
                            msgError("Ja existeix una partida amb aquest nom");
                            boardPanel.setVisible(true);
                            currentGameCtr.unpause();
                            isGamePaused = false;
                            return;
                        }
                        currentGameCtr.setName(newName);
                    }
                    currentGameCtr.unpause();
                    isGamePaused = false;
                    currentGameCtr.saveGame();
                }
                parent.obrirMenu(this);
            }
            currentGameCtr.unpause();
            isGamePaused = false;
            boardPanel.setVisible(true);
        }
    }//GEN-LAST:event_exitButtonActionPerformed

    /**
     * Quan es prem el boto de desfer, el joc desfara l'ultima accio que l'usuari
     * hagi realitzat. Les accions de l'usuari s'enregistren, de manera que es pot
     * premer el boto tants cops com es vulgui fins a tornar a l'estat inicial de 
     * la partida.
     * @param evt event de clicar en el boto
     */
    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonActionPerformed
        if (historial.isEmpty()) return;
        Accio a = historial.pop();
        int x = a.x;
        int y = a.y;
        int val = a.val;
        currentGameCtr.putValue(val, x, y);
        panels.get(x).get(y).changeVal(val);
        if (historial.size() == 0) undoButton.setEnabled(false);
    }//GEN-LAST:event_undoButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel boardPanel;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton checkButton;
    private javax.swing.JLabel diffLabel;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel helpLabel;
    private javax.swing.JButton hintButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner newValue;
    private javax.swing.JButton pauseButton;
    private javax.swing.JButton restartButton;
    private javax.swing.JButton solveButton;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton undoButton;
    // End of variables declaration//GEN-END:variables
}
