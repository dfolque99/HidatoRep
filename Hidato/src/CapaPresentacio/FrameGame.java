/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacio;

import CapaDomini.Domini;
import CapaDomini.Misc.Colors;
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
import CapaDomini.Tauler.HidatoSet;
import CapaDomini.Tauler.SolverController;
import CapaDomini.Usuari.HidatoUserController;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.Timer;


//https://www.youtube.com/watch?v=FKJxPlWQp9Y

/**
 * TO DO:
 * canviar en GameManagerController la dificultat per defecte, quan estigui el difficulty controller
 * canviar mouse listeners per action listeners (actionperformed)
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
    Help help;
    GeneratorController hidatoGenerator;
    Domini parent;
        
    /**
     * Creates new form FrameGame
     */
    
    
    private void msgError(String text) {
        JOptionPane.showMessageDialog(this,text,"Error",JOptionPane.ERROR_MESSAGE);
    }
    
    private void msg(String text, String title) {
        JOptionPane.showMessageDialog(this,text,title,JOptionPane.PLAIN_MESSAGE);
    }
    
    private void inicialitzaParametres(Domini parent, RankingController rc, HidatoUserController uc, GameManagerController gmc, Help h, String gameName){
        this.parent = parent;
        
        if (rc == null) {
            ctrRanking = new RankingController();
            ctrRanking.init();
        }else ctrRanking = rc;
        
        if (uc == null) {
            hidatoUserController = new HidatoUserController();
            hidatoUserController.login("hola", "adeu");
        }else hidatoUserController = uc;
        
        if (gmc == null) ctrGameManager = new GameManagerController(ctrRanking, hidatoUserController);
        else ctrGameManager = gmc;
        
        if (h == null) help = Help.LOW;
        else help = h;
        
        hidatoGenerator = new GeneratorController();
        
        String name;
        if (gameName == null) name = "Nou joc";
        else name = gameName;
        currentGameCtr = ctrGameManager.createGame(name, hidatoGenerator.generateHidato(6,6), help);
    }
    
    private void acabaPartida(){
        msg("Felicitats, hidato completat!","Yeeeeeeee!!");
        currentGameCtr.finishGame();
        this.setVisible(false);
        FrameRanking fr = new FrameRanking();
        fr.setVisible(true);
    }
    
    private int nextNumber(int ini){
        Hidato hidato = currentGameCtr.getGame().getHidato();
        HidatoController hc = new HidatoController(hidato);
        for (int i = ini; i <= hc.countValidCells(); i++){
            if (hc.getCellPositionFromValue(i, 1) == -1){
                return i;
            }
        }
        return -1;
    }
    
    public FrameGame(Domini parent, RankingController rc, HidatoUserController uc, GameManagerController gmc, Help h, String gameName) {
        initComponents();
        inicialitzaParametres(parent,rc,uc,gmc,h,gameName);
        
        
        //Hidato hidato = game.getHidato();
        
        /*Game game = new Game();
        SolverController solver = new Solver();
        HidatoUserController hidatoUserController = new HidatoUserController();
        currentGame = newCurrentGameController(game,null,solver,null,hidatoUserController);
        */
        
        int x = currentGameCtr.getSizeX(), y = currentGameCtr.getSizeY();
        int maxim, x1, x2, y1, y2;
        maxim = Math.max(x,y);
        x1 = (maxim-x)/2; 
        x2 = x + x1;
        y1 = (maxim-y)/2;
        y2 = y + y1; //aixi el hidato queda centrat en el panel
        boardPanel.setLayout(new GridLayout(maxim, maxim));
        //int cellsize = 80;
        //boardPanel.setPreferredSize(new Dimension(cellsize*x, cellsize*y));

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
        
        Timer timer = new Timer(1000,taskPerformer);
        timer.start();
        
        isGamePaused = false;
        timeSincePause = 0;
        
        this.setFocusable(true);
        /*KeyListener kl = new KeyListener(){
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt){
                System.out.println("Pressed key "+evt.getKeyCode());
            }
            
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt){
                System.out.println("Released key "+evt.getKeyCode());
            }

            @Override
            public void keyTyped(KeyEvent evt) {
                System.out.println("Typed key "+evt.getKeyCode());
            }
        };*/
        
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
                    //p.setPreferredSize(new Dimension(cellsize,cellsize));
                    //p.setSize(new Dimension(cellsize,cellsize));
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
        jPanel3 = new javax.swing.JPanel();
        pauseButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        restartButton = new javax.swing.JButton();
        timeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));

        boardPanel.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
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
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
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
        checkButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                checkButtonMouseReleased(evt);
            }
        });

        hintButton.setText("Pista");
        hintButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                hintButtonMouseReleased(evt);
            }
        });

        solveButton.setText("Resol");
        solveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                solveButtonMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hintButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(solveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hintButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(solveButton)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Opcions de partida"));

        pauseButton.setText("Pausa (P)");
        pauseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pauseButtonMouseReleased(evt);
            }
        });

        saveButton.setText("Guarda");
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                saveButtonMouseReleased(evt);
            }
        });

        restartButton.setText("Reinicia");
        restartButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                restartButtonMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pauseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(restartButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pauseButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(restartButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        timeLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timeLabel.setText("Temps: ");

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timeLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(timeLabel)
                .addGap(0, 93, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkButtonMouseReleased
        //falta fer que no es cliqui si el ratoli no hi es a sobre
        if(currentGameCtr.check()){
            msg("El hidato encara te solucio","");
        }else{
            msgError("El hidato no te solucio");
        }
    }//GEN-LAST:event_checkButtonMouseReleased

    private void hintButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hintButtonMouseReleased
        //falta fer que no es cliqui si el ratoli no hi es a sobre
        ArrayList<Integer> hint = currentGameCtr.requestHint();
        if (hint == null){
            msgError("El hidato no te solucio");
            return;
        }
        int x = hint.get(0);
        int y = hint.get(1);
        int value = hint.get(2);
        panels.get(x).get(y).changeVal(value);
        if(nextNumber(1) == -1){
            acabaPartida();
        }
    }//GEN-LAST:event_hintButtonMouseReleased

    private void solveButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_solveButtonMouseReleased
        // TODO add your handling code here:
        currentGameCtr.solve();
        for(int i = 0; i < currentGameCtr.getSizeX(); i++){
            for(int j = 0; j < currentGameCtr.getSizeY(); j++){
                int value = currentGameCtr.getCellVal(i, j);
                panels.get(i).get(j).changeVal(value);
            }
        }
    }//GEN-LAST:event_solveButtonMouseReleased

    private void pauseButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pauseButtonMouseReleased
        // TODO add your handling code here:
        currentGameCtr.pause();
        isGamePaused = true;
        timeSincePause = 0;          
        msg("Joc pausat. Prem OK per continuar","Pausa");
        currentGameCtr.unpause();
        isGamePaused = false;      
    }//GEN-LAST:event_pauseButtonMouseReleased

    private void saveButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveButtonMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_saveButtonMouseReleased

    private void restartButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_restartButtonMouseReleased
        // TODO add your handling code here:
        currentGameCtr.restartGame();
        timeSincePause = 0;
        for(int i = 0; i < currentGameCtr.getSizeX(); i++){
            for(int j = 0; j < currentGameCtr.getSizeY(); j++){
                int value = currentGameCtr.getCellVal(i, j);
                panels.get(i).get(j).changeVal(value);
            }
        }
        newValue.setValue(nextNumber(1));
    }//GEN-LAST:event_restartButtonMouseReleased

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
                new FrameGame(null, null, null, null, null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel boardPanel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton checkButton;
    private javax.swing.JButton hintButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner newValue;
    private javax.swing.JButton pauseButton;
    private javax.swing.JButton restartButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton solveButton;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
}
