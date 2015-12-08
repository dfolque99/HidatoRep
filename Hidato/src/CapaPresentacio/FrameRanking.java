/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacio;

import CapaDomini.Partida.Difficulty;
import CapaDomini.Rank.RankingController;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Guillem
 */
public class FrameRanking extends javax.swing.JFrame {
    
    /**
     * Creates new form FrameRanking
     */
    public FrameRanking() {
        initComponents();
        inicialitza();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selectorDificultat = new javax.swing.JComboBox();
        labelDificultat = new javax.swing.JLabel();
        rankingPanel = new javax.swing.JPanel();
        posPanel = new javax.swing.JPanel();
        tagPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ranking");
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        selectorDificultat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fàcil", "Mitjà", "Difícil" }));
        selectorDificultat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectorDificultatActionPerformed(evt);
            }
        });

        labelDificultat.setText("Selecciona la dificultat:");

        rankingPanel.setMaximumSize(new java.awt.Dimension(650, 530));
        rankingPanel.setMinimumSize(new java.awt.Dimension(650, 530));
        rankingPanel.setName("Ranking"); // NOI18N
        rankingPanel.setPreferredSize(new java.awt.Dimension(650, 530));

        javax.swing.GroupLayout rankingPanelLayout = new javax.swing.GroupLayout(rankingPanel);
        rankingPanel.setLayout(rankingPanelLayout);
        rankingPanelLayout.setHorizontalGroup(
            rankingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );
        rankingPanelLayout.setVerticalGroup(
            rankingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        posPanel.setMaximumSize(new java.awt.Dimension(20, 530));
        posPanel.setMinimumSize(new java.awt.Dimension(20, 530));
        posPanel.setPreferredSize(new java.awt.Dimension(20, 530));

        javax.swing.GroupLayout posPanelLayout = new javax.swing.GroupLayout(posPanel);
        posPanel.setLayout(posPanelLayout);
        posPanelLayout.setHorizontalGroup(
            posPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        posPanelLayout.setVerticalGroup(
            posPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        tagPanel.setMaximumSize(new java.awt.Dimension(32767, 20));
        tagPanel.setMinimumSize(new java.awt.Dimension(100, 20));
        tagPanel.setPreferredSize(new java.awt.Dimension(100, 20));

        javax.swing.GroupLayout tagPanelLayout = new javax.swing.GroupLayout(tagPanel);
        tagPanel.setLayout(tagPanelLayout);
        tagPanelLayout.setHorizontalGroup(
            tagPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        tagPanelLayout.setVerticalGroup(
            tagPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDificultat)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(selectorDificultat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addComponent(posPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tagPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                    .addComponent(rankingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(labelDificultat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectorDificultat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tagPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(posPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rankingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        labelDificultat.getAccessibleContext().setAccessibleName("jLabel1");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectorDificultatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectorDificultatActionPerformed
        String selection = selectorDificultat.getSelectedItem().toString();
        setVisibleRanking(string2diff(selection));
    }//GEN-LAST:event_selectorDificultatActionPerformed
 
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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameRanking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameRanking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameRanking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameRanking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameRanking().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelDificultat;
    private javax.swing.JPanel posPanel;
    private javax.swing.JPanel rankingPanel;
    private javax.swing.JComboBox selectorDificultat;
    private javax.swing.JPanel tagPanel;
    // End of variables declaration//GEN-END:variables

    
    
    
    
    //===============================MI CODIGO==================================
    
    
    //NO SE USA (SE PUEDE BORRAR)
    private void msgError(String text) {
        JOptionPane.showMessageDialog(this,text,"On vas flipat!",JOptionPane.ERROR_MESSAGE);
    }
    //HASTA AQUI
    
    private RankingController rc;
    private final int N = RankingController.getRankingMaxSize();
    //Numero de informacion proporcionada por cada entrada del ranking
    //Es decir, numero de columnas del gridlayout
    private final static int M = 3; 
    
    public void setRc(RankingController rc) {
        this.rc = rc;
    }
    
    //Funcio per inicialitzar el frame
    private void inicialitza() {
        //ESTE CODIGO ES SOLO PARA DEBUGAR (LUEGO HAY QUE BORRARLO)
        rc = new RankingController();
        rc.init();
        //HASTA AQUI
        
        
        //HACE FALTA TENER LA IMAGEN "icon.png" (DESCOMENTAR MAS TARDE)
        //setIconImage((new ImageIcon("icon.png")).getImage());
        
        posPanel.setLayout(new GridLayout(N,1));
        for (int i = 1; i <= N; ++i) {
            posPanel.add(new JLabel(Integer.toString(i) + ". "));
        }
        
        tagPanel.setLayout(new GridLayout(1,M));
        tagPanel.add(new JLabel("Nom usuari",SwingConstants.CENTER));
        tagPanel.add(new JLabel("Data",SwingConstants.CENTER));
        tagPanel.add(new JLabel("Puntuació",SwingConstants.CENTER));
        
        rankingPanel.setLayout(new GridLayout(N,M));
        setVisibleRanking(Difficulty.EASY);
    }
    
    //Mostra el ranking de dificultat dif
    private void setVisibleRanking(Difficulty dif) {
        rankingPanel.removeAll();
        ArrayList <String> ranking = rc.getRankingInfo(dif);
        for (int i = 0; i < ranking.size(); ++i) {
            String[] infoEntrada = ranking.get(i).split("/");
            for(int j = 0; j < M; ++j) {
                rankingPanel.add(new JLabel(infoEntrada[perm(j)],SwingConstants.CENTER));
            }
        }
        for (int i = ranking.size(); i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                rankingPanel.add(new JLabel(""));
            }
        }
        rankingPanel.updateUI();
    }
    
    //Serveix per reordenar l'ordre de les columnes del ranking (canviar l'ordre
    //en que es dona l'informacio de cada entrada)
    private static int perm(int i) {
        switch(i) {
            case 0: return 1;
            case 1: return 0;
            case 2: return 2;
            default: return 0;
        }
    }
    
    //Converteix una dificultat donada com un string en una del tipus Difficulty
    private static Difficulty string2diff(String s) {
        switch(s) {
            case "Fàcil": return Difficulty.EASY;
            case "Mitjà": return Difficulty.MEDIUM;
            case "Difícil": return Difficulty.HARD;
            default: return Difficulty.EASY;
        }
    }
    

}
