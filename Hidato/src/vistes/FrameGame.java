/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistes;

import domini.Misc.Colors;
import domini.Partida.CurrentGameController;
import domini.Partida.Difficulty;
import domini.Partida.Game;
import domini.Partida.GameDBController;
import domini.Partida.GameManagerController;
import domini.Partida.Help;
import domini.Rank.RankingController;
import domini.Tauler.Cell;
import domini.Tauler.GeneratorController;
import domini.Tauler.Hidato;
import domini.Tauler.HidatoController;
import domini.Tauler.HidatoSet;
import domini.Tauler.SolverController;
import domini.Usuari.HidatoUserController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;


//https://www.youtube.com/watch?v=FKJxPlWQp9Y

/**
 * canvis en squarecell:
 * posar el color que toca ja a la creadora? (no posar el blank per defecte)
 * si ho canviem, ho haig de treure d'aqui
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
    /**
     * Creates new form FrameGame
     */
    
    
    private void msgError(String text) {
        JOptionPane.showMessageDialog(this,text,"Error",JOptionPane.ERROR_MESSAGE);
    }
    
    private void msgFi(String text) {
        JOptionPane.showMessageDialog(this,text,"Ueeeeeee!",JOptionPane.PLAIN_MESSAGE);
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
    
    public FrameGame() {
        initComponents();
        
        HidatoSet hidatoSet = new HidatoSet();       
        GameDBController ctrDBGame = new GameDBController();
        SolverController solver = new SolverController();
        RankingController ctrRanking = new RankingController();
        HidatoUserController hidatoUserController = new HidatoUserController();
        GameManagerController ctrGameManager = new GameManagerController(hidatoSet, ctrDBGame, solver, ctrRanking, hidatoUserController);
        Help help = Help.MEDIUM;
        GeneratorController hidatoGenerator = new GeneratorController();
        Hidato solvedHidato = hidatoGenerator.generateHidato(6,6);
        currentGameCtr = ctrGameManager.createGame("Nou joc", solvedHidato, help);
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

        panels = new ArrayList<>();
        for (int i0 = 0; i0 < maxim; ++i0) {
            panels.add(new ArrayList<>());
            for (int j0 = 0; j0 < maxim; ++j0) {
                if (x1 <= i0 && i0 < x2 && y1 <= j0 && j0 < y2) {
                    int i = i0 - x1, j = j0-y1; //i, j van de 0 a x-1 i de 0 a y-1 respectivament
                    int valor = currentGameCtr.getCellVal(i, j);
                    domini.Tauler.Type type = currentGameCtr.getCellType(i, j);
                    int fontSize = 250/maxim;
                    
                    voidColor = Colors.c(0);
                    if (valor == 1 || valor == currentGameCtr.getValidCells()){
                        hintColor = Colors.c(3);
                    }else hintColor = Colors.c(1);
                    blankColor = Colors.c(2);
                    clickColor = Colors.c(4);
                    
                    SquareCell p = new SquareCell(i,j, valor, type, blankColor, clickColor, hintColor, voidColor, fontSize, type == domini.Tauler.Type.BLANK);
                   
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
                                int v;
                                if (evt.isControlDown()) v = 0;
                                else v = (Integer) newValue.getValue();
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
                                        msgFi("Felicitats, hidato completat!");
                                    }else{
                                        if((int) newValue.getValue() != 0){
                                            newValue.setValue(nextNumber(v));
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

        boardPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        newValue = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(740, 580));

        boardPanel.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
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
                        .addComponent(newValue, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 438, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                new FrameGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel boardPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner newValue;
    // End of variables declaration//GEN-END:variables
}
