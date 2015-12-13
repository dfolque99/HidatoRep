/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacio;

import CapaDomini.Domini;
import CapaDomini.Misc.Colors;
import CapaDomini.Misc.Fonts;
import CapaDomini.Partida.CurrentGameController;
import CapaDomini.Partida.Difficulty;
import CapaDomini.Partida.Game;
import CapaPersistencia.GameDBController;
import CapaDomini.Partida.GameManagerController;
import CapaDomini.Partida.Help;
import CapaDomini.Rank.RankingController;
import CapaDomini.Tauler.Cell;
import CapaDomini.Tauler.GeneratorController;
import CapaDomini.Tauler.Hidato;
import CapaDomini.Tauler.HidatoController;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Tauler.HidatoSet;
import CapaDomini.Tauler.SolverController;
import CapaDomini.Usuari.HidatoUserController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.time.Duration;
import java.util.ArrayList;
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.Timer;


//https://www.youtube.com/watch?v=FKJxPlWQp9Y

/**
 * TO DO:
 */
/**
 *
 * @author Pau
 */
public class FrameGame extends javax.swing.JFrame {

    ArrayList<ArrayList<SquareCell>> panels;
    Color blankColor;
    Color hintColor;
    Color voidColor;
    Color startColor;
    Color clickColor;
    CurrentGameController currentGameCtr;
    Boolean isGamePaused;
    int timeSincePause;
    RankingController ctrRanking;
    HidatoUserController hidatoUserController;
    GameManagerController ctrGameManager;
    HidatoManagerController hidatoManagerController;
    HidatoSet hidatoSet;
    Help help;
    GeneratorController hidatoGenerator;
    Domini parent;
    Timer timer;
    
    
    private void msgError(String text) {
        JOptionPane.showMessageDialog(this,text,"Error",JOptionPane.ERROR_MESSAGE);
    }
    
    private void msg(String text, String title) {
        JOptionPane.showMessageDialog(this,text,title,JOptionPane.PLAIN_MESSAGE);
    }
    
    private void inicialitzaParametres(Domini parent, CurrentGameController cgc, HidatoManagerController hmc){
        this.parent = parent;
        hidatoGenerator = new GeneratorController();
        
        hidatoManagerController = hmc;
        currentGameCtr = cgc;
        
        help = currentGameCtr.getHelp();
        
        helpLabel.setText("Nivell d'ajuda: "+currentGameCtr.getHelp());
        diffLabel.setText("Dificultat: "+currentGameCtr.getDifficulty());
        if (currentGameCtr.getName() == null){
            titleLabel.setText("Nova partida");
        }else titleLabel.setText("Partida: "+currentGameCtr.getName());
    }
    
    private void acabaPartida(){
        currentGameCtr.finishGame();
        timer.stop();
        msg("Felicitats, hidato completat!\n Puntuacio: "+currentGameCtr.calculateScore(),"Yeeeeeeee!!");
        if (currentGameCtr.isVolatile()){
            String newName = JOptionPane.showInputDialog(this, "Vols guardar el hidato? Escriu el nom:");
            Boolean aux = hidatoManagerController.usedName(newName);
            System.out.println("el hidato amb nom "+newName+" esta utilitzat? "+aux);
            while (newName != null && aux){
                newName = JOptionPane.showInputDialog(this, "Ja hi ha un hidato amb aquest nom. Tria'n un altre:");
                aux = hidatoManagerController.usedName(newName);
                System.out.println("el hidato amb nom "+newName+" esta utilitzat? "+aux);
            }
            if (newName != null){
                currentGameCtr.setBoardName(newName);
                hidatoManagerController.saveTempHidato(newName);
            }
        }
        this.setVisible(false);
        parent.obrirMenu(this);
    }
    
    private int nextNumber(int ini){
        HidatoController hc = new HidatoController(currentGameCtr.getHidato());
        for (int i = ini+1; i <= hc.countValidCells(); i++){
            if (hc.getCellPositionFromValue(i, 1) == -1){
                return i;
            }
        }
        if (ini != 1){
            return nextNumber(1);
        }
        return -1;
    }
    
    private int prevNumber(int ini){
        HidatoController hc = new HidatoController(currentGameCtr.getHidato());
        for (int i = ini-1; i >1 ; i--){
            if (hc.getCellPositionFromValue(i, 1) == -1){
                return i;
            }
        }
        if (ini != hc.countValidCells()){
            return prevNumber(hc.countValidCells());
        }
        return -1;
    }
    
    public FrameGame(Domini parent, CurrentGameController cgc, HidatoManagerController hmc) {
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

        if (help == Help.HIGH){
            checkButton.setVisible(false);
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
                                int errCode = currentGameCtr.putValue(v, p.getA(), p.getB());
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
                    });
                }else{
                    JPanel p = new JPanel();
                    boardPanel.add(p,i0*maxim+j0);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
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
        jPanel3 = new javax.swing.JPanel();
        timeLabel = new javax.swing.JLabel();
        helpLabel = new javax.swing.JLabel();
        diffLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        boardPanel.setPreferredSize(new java.awt.Dimension(400, 400));

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 469, Short.MAX_VALUE)
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Modificar valors"));

        jLabel2.setText("Ctrl + Click esborra el número");

        jLabel1.setText("Nombre a colocar:");

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

        checkButton.setText("Comprova");
        checkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkButtonActionPerformed(evt);
            }
        });

        hintButton.setText("Pista");
        hintButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hintButtonActionPerformed(evt);
            }
        });

        solveButton.setText("Resol");
        solveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solveButtonActionPerformed(evt);
            }
        });

        pauseButton.setText("Pausa");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        restartButton.setText("Reinicia");
        restartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartButtonActionPerformed(evt);
            }
        });

        exitButton.setText("Surt");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(checkButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hintButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(pauseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(restartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(solveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(pauseButton)
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(restartButton)
                            .addComponent(exitButton)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(checkButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hintButton)
                            .addComponent(solveButton))
                        .addGap(29, 29, 29)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        timeLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timeLabel.setText("Temps: ");

        helpLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        helpLabel.setText("Nivell d'ajuda:");

        diffLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 143, Short.MAX_VALUE))
        );

        titleLabel.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 36));
        titleLabel.setText("Hidato:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(55, 55, 55))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkButtonActionPerformed
        if(currentGameCtr.check()){
            msg("El hidato encara te solucio","");
        }else{
            msgError("El hidato no te solucio");
        }
    }//GEN-LAST:event_checkButtonActionPerformed

    private void hintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hintButtonActionPerformed
        ArrayList<Integer> hint = currentGameCtr.requestHint();
        if (hint == null){
            msgError("El hidato no te solucio");
            return;
        }
        int x = hint.get(0);
        int y = hint.get(1);
        int value = hint.get(2);
        panels.get(x).get(y).changeVal(value);
        newValue.setValue(nextNumber((int)newValue.getValue()-1));
        if(nextNumber(1) == -1){
            acabaPartida();
        }
    }//GEN-LAST:event_hintButtonActionPerformed

    private void solveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solveButtonActionPerformed
        currentGameCtr.solve();
        for(int i = 0; i < currentGameCtr.getSizeX(); i++){
            for(int j = 0; j < currentGameCtr.getSizeY(); j++){
                int value = currentGameCtr.getCellVal(i, j);
                panels.get(i).get(j).changeVal(value);
            }
        }
        acabaPartida();
    }//GEN-LAST:event_solveButtonActionPerformed

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

    private void restartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartButtonActionPerformed
        currentGameCtr.restartGame();
        timeSincePause = 0;
        for(int i = 0; i < currentGameCtr.getSizeX(); i++){
            for(int j = 0; j < currentGameCtr.getSizeY(); j++){
                int value = currentGameCtr.getCellVal(i, j);
                panels.get(i).get(j).changeVal(value);
            }
        }
        newValue.setValue(nextNumber(1));
    }//GEN-LAST:event_restartButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        parent.saveBeforeClose();
    }//GEN-LAST:event_formWindowClosing

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        String[] opcions = new String[] {"Si", "No", "Cancel·la"};
        int response = JOptionPane.showOptionDialog(this, "Vols guardar la partida?", "Sortir a menu",
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, opcions, opcions[0]);
        if (response == 0 || response == 1){
            if (response == 0){
                if (currentGameCtr.getName() == null) {
                    String newName = JOptionPane.showInputDialog(this, "Escriu el nom de la partida: ");
                    System.out.println("Nom de partida:"+newName+","+newName+","+newName+","+newName+","+newName+",");
                    if (newName == null) return;
                    if (newName.equals("")){
                        msgError("Introdueix algun nom");
                        return;
                    }
                    if (currentGameCtr.existsGame(newName)){
                        msgError("Ja existeix una partida amb aquest nom");
                        return;
                    }
                    currentGameCtr.setName(newName);
                }
                currentGameCtr.saveGame();
            }
            parent.obrirMenu(this);
        }
    }//GEN-LAST:event_exitButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FrameGame(new Domini(), null, null, null, null, null, null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel boardPanel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
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
    // End of variables declaration//GEN-END:variables
}
