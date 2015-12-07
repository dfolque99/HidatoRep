/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacio;


import CapaDomini.Domini;
import CapaDomini.Misc.Colors;
import CapaDomini.Misc.Fonts;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Tauler.HidatoSet;
import CapaDomini.Usuari.HidatoUserController;
import java.awt.Color;
import java.awt.Container;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author David
 */
public class FrameEditor extends javax.swing.JFrame {

    /**
     * Creates new form FrameEditor
     */
    public FrameEditor(Domini parent, HidatoManagerController hmc) {
        initComponents();
        inici(parent, hmc);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        b_canvi_num = new javax.swing.JRadioButton();
        b_canvi_type = new javax.swing.JRadioButton();
        b_canvi_prop = new javax.swing.JRadioButton();
        panel_type = new javax.swing.JPanel();
        b_blank = new javax.swing.JRadioButton();
        b_given = new javax.swing.JRadioButton();
        b_void = new javax.swing.JRadioButton();
        b_esborrar_tipus = new javax.swing.JButton();
        panel_num = new javax.swing.JPanel();
        b_esborrar_num = new javax.swing.JRadioButton();
        b_preguntar_num = new javax.swing.JRadioButton();
        b_seq_num = new javax.swing.JRadioButton();
        spinner_num_actual = new javax.swing.JSpinner();
        l_num_actual = new javax.swing.JLabel();
        b_esborrar_numeros = new javax.swing.JButton();
        panel_nom = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        b_autocompletar = new javax.swing.JButton();
        b_validar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        b_sortir = new javax.swing.JButton();
        b_desar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        label_nom = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 800, 600));
        setSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Opcions d'edició"));

        b_canvi_num.setText("Canviar número");

        b_canvi_type.setText("Canviar tipus de cel·la");

        b_canvi_prop.setText("Canviar propietats");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b_canvi_num)
                    .addComponent(b_canvi_type)
                    .addComponent(b_canvi_prop))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(b_canvi_num)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_canvi_type)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_canvi_prop))
        );

        panel_type.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipus de cel·la"));

        b_blank.setText("Cel·la blanca");

        b_given.setText("Cel·la donada");

        b_void.setText("Cel·la no vàlida");

        b_esborrar_tipus.setText("Esborrar tipus");

        javax.swing.GroupLayout panel_typeLayout = new javax.swing.GroupLayout(panel_type);
        panel_type.setLayout(panel_typeLayout);
        panel_typeLayout.setHorizontalGroup(
            panel_typeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_typeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_typeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b_esborrar_tipus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_typeLayout.createSequentialGroup()
                        .addGroup(panel_typeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(b_blank)
                            .addComponent(b_given)
                            .addComponent(b_void))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_typeLayout.setVerticalGroup(
            panel_typeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_typeLayout.createSequentialGroup()
                .addComponent(b_blank)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_given)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_void)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_esborrar_tipus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_num.setBorder(javax.swing.BorderFactory.createTitledBorder("Acció:"));

        b_esborrar_num.setText("Esborrar número");

        b_preguntar_num.setText("Preguntar número");

        b_seq_num.setText("Seqüència de números");

        l_num_actual.setText("Número actual:");

        b_esborrar_numeros.setText("Esborrar números");

        javax.swing.GroupLayout panel_numLayout = new javax.swing.GroupLayout(panel_num);
        panel_num.setLayout(panel_numLayout);
        panel_numLayout.setHorizontalGroup(
            panel_numLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_numLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_numLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b_esborrar_numeros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_numLayout.createSequentialGroup()
                        .addGroup(panel_numLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_numLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(l_num_actual)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinner_num_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(b_esborrar_num)
                            .addComponent(b_preguntar_num)
                            .addComponent(b_seq_num))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_numLayout.setVerticalGroup(
            panel_numLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_numLayout.createSequentialGroup()
                .addComponent(b_esborrar_num)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_preguntar_num)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_seq_num)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_numLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_num_actual)
                    .addComponent(spinner_num_actual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_esborrar_numeros)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_nom.setBorder(javax.swing.BorderFactory.createTitledBorder("Canvi de nom"));

        javax.swing.GroupLayout panel_nomLayout = new javax.swing.GroupLayout(panel_nom);
        panel_nom.setLayout(panel_nomLayout);
        panel_nomLayout.setHorizontalGroup(
            panel_nomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
        );
        panel_nomLayout.setVerticalGroup(
            panel_nomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 71, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_num, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_nom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_type, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_type, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_num, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_nom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        b_autocompletar.setText("Autocompletar");

        b_validar.setText("Validar");

        jPanel5.setLayout(new java.awt.GridLayout(1, 0, 2, 0));

        b_sortir.setText("Sortir");
        jPanel5.add(b_sortir);

        b_desar.setText("Desar");
        jPanel5.add(b_desar);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b_autocompletar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b_validar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(b_autocompletar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_validar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        label_nom.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        label_nom.setText("Hidato:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_nom, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_nom, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 36, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(21, 21, 21))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        hmc.saveAll();
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(FrameEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                HidatoUserController uc = new HidatoUserController();
                //uc.createUser("Usuari", "password");
                uc.login("david", "david");
                HidatoManagerController hmc = new HidatoManagerController(new HidatoSet(), null, uc);
                hmc.loadAll();
                hmc.createRandom(8,12);
                new FrameEditor(null, hmc).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_autocompletar;
    private javax.swing.JRadioButton b_blank;
    private javax.swing.JRadioButton b_canvi_num;
    private javax.swing.JRadioButton b_canvi_prop;
    private javax.swing.JRadioButton b_canvi_type;
    private javax.swing.JButton b_desar;
    private javax.swing.JRadioButton b_esborrar_num;
    private javax.swing.JButton b_esborrar_numeros;
    private javax.swing.JButton b_esborrar_tipus;
    private javax.swing.JRadioButton b_given;
    private javax.swing.JRadioButton b_preguntar_num;
    private javax.swing.JRadioButton b_seq_num;
    private javax.swing.JButton b_sortir;
    private javax.swing.JButton b_validar;
    private javax.swing.JRadioButton b_void;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel l_num_actual;
    private javax.swing.JLabel label_nom;
    private javax.swing.JPanel panel_nom;
    private javax.swing.JPanel panel_num;
    private javax.swing.JPanel panel_type;
    private javax.swing.JSpinner spinner_num_actual;
    // End of variables declaration//GEN-END:variables

    // =============================AQUI LO MEU=================================

    
    
    private ArrayList<ArrayList<SquareCell>> panels;
    private int N, M;
    private HidatoManagerController hmc;
    private Domini parent;
    private ButtonGroup g1, g2, g3;
    private String nomHidato;
    
    
    
    public void inici(Domini parent, HidatoManagerController hmc) {
        this.N = hmc.getTempSizeX();
        this.M = hmc.getTempSizeY();
        this.hmc = hmc;
        this.parent = parent;
        b_desar.setEnabled(false);
        nomHidato = hmc.getTempBoardName();
        if (nomHidato == null) label_nom.setText("Hidato no guardat");
        else label_nom.setText("Hidato: "+nomHidato);
        label_nom.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 48));
        panels = new ArrayList<>();
        
        int maxim, N1, N2, M1, M2;
        maxim = Math.max(N,M);
        N1 = (maxim-N)/2;
        N2 = N + N1;
        M1 = (maxim-M)/2;
        M2 = M + M1;
        jPanel1.setLayout(new GridLayout(maxim, maxim));
        
        
        for (int i0 = 0; i0 < maxim; ++i0) {
            panels.add(new ArrayList<>());
            for (int j0 = 0; j0 < maxim; ++j0) {
                if (N1 <= i0 && i0 < N2 && M1 <= j0 && j0 < M2) {
                    int i = i0-N1, j = j0-M1;
                    int val = hmc.getTempCellVal(i,j);
                    CapaDomini.Tauler.Type type = hmc.getTempCellType(i,j);
                    SquareCell p = new SquareCell(i,j,val,type,Colors.c(2),Colors.c(4),Colors.c(1),Colors.c(0), 500/maxim*5/10,true);
                    panels.get(i).add(p);
                    jPanel1.add(p, i0*maxim+j0);
                    p.changeType(hmc.getTempCellType(i,j));
                    p.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                            mousePress((SquareCell) evt.getComponent());
                        }
                        @Override
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                            mouseRelease((SquareCell) evt.getComponent());
                        }
                    });
                }
                else {
                    JPanel p = new JPanel();
                    jPanel1.add(p,i0*maxim+j0);
                }
            }
        }
        
        configurarRadioButtons();
        configurarBotons();
        //canviarColors(this);
        
    }
    
    private void configurarRadioButtons() {
        g1 = new ButtonGroup();
        g1.add(b_canvi_num); g1.add(b_canvi_type); g1.add(b_canvi_prop);
        g2 = new ButtonGroup();
        g2.add(b_blank); g2.add(b_given); g2.add(b_void);
        g3 = new ButtonGroup();
        g3.add(b_esborrar_num); g3.add(b_preguntar_num); g3.add(b_seq_num);
        
        b_canvi_num.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                totInvisible();
                panel_num.setVisible(true);
            }
        });
        b_canvi_type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                totInvisible();
                panel_type.setVisible(true);
            }
        });
        b_canvi_prop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                totInvisible();
                panel_nom.setVisible(true);
            }
        });
        
        b_esborrar_num.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                l_num_actual.setEnabled(false);
                spinner_num_actual.setEnabled(false);
            }
        });
        b_preguntar_num.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                l_num_actual.setEnabled(false);
                spinner_num_actual.setEnabled(false);
            }
        });
        b_seq_num.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                l_num_actual.setEnabled(true);
                spinner_num_actual.setEnabled(true);
            }
        });
        spinner_num_actual.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if ((Integer) spinner_num_actual.getValue() <= 0) {
                    spinner_num_actual.setValue(1);
                }
            }
        });
        spinner_num_actual.setValue(1);
        
        totInvisible();
    }
    
    
    private void configurarBotons() {
        b_esborrar_numeros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                for (int i = 0; i < N; ++i) {
                    for (int j = 0; j < M; ++j) {
                        hmc.setTempCellVal(i, j, 0);
                        panels.get(i).get(j).changeVal(0);
                    }
                }
            }
        });
        b_esborrar_tipus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                for (int i = 0; i < N; ++i) {
                    for (int j = 0; j < M; ++j) {
                        hmc.setTempCellType(i, j, CapaDomini.Tauler.Type.BLANK);
                        panels.get(i).get(j).changeType(CapaDomini.Tauler.Type.BLANK);
                    }
                }
            }
        });
        b_autocompletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (!hmc.tempHasOrigin()) {
                    msgError ("No s'ha pogut completar el hidato perquè no té casella inicial");
                    return;
                }
                boolean completat = hmc.completeTempHidato();
                if (completat) {
                    for (int i = 0; i < N; ++i) {
                        for (int j = 0; j < M; ++j) {
                            panels.get(i).get(j).changeType(hmc.getTempCellType(i, j));
                            panels.get(i).get(j).changeVal(hmc.getTempCellVal(i, j));
                        }
                    }
                }
                else {
                    msgError ("No s'ha pogut completar el hidato");
                }
            }
        });
        b_validar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (!hmc.tempHasOrigin()) {
                    msgError ("No s'ha pogut completar el hidato perquè no té casella inicial");
                    return;
                }
                if (!hmc.tempHasFinal()) {
                    msgError ("No s'ha pogut completar el hidato perquè no té casella final");
                    return;
                }
                boolean completat = hmc.solveTempHidato();
                if (completat) {
                    for (int i = 0; i < N; ++i) {
                        for (int j = 0; j < M; ++j) {
                            panels.get(i).get(j).changeType(hmc.getTempCellType(i, j));
                            panels.get(i).get(j).changeVal(hmc.getTempCellVal(i, j));
                        }
                    }
                    b_desar.setEnabled(true);
                    msgInfo ("S'ha validat el hidato. Ara ja es pot desar");
                }
                else {
                    msgError ("No s'ha pogut validar el hidato");
                }
            }
        });
        b_desar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                desar();
            }
        });
        b_sortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                tancar();
            }
        });
        
    }
    
    private void desar() {
        if (nomHidato != null) {
            hmc.saveTempHidato(nomHidato);
        }
        else {
            String newName = JOptionPane.showInputDialog(this,"Introdueix un nom per al hidato",null);
            if (hmc.usedName(newName)) msgError("Ja existeix un hidato amb aquest nom");
            else {
                hmc.saveTempHidato(newName);
                msgInfo("Hidato " + newName + " desat correctament");
                nomHidato = newName;
            }
        }
    }
    
    private void canviarColors(Container c) {
        Color fons = new Color(0x0C0C0C); // negre
        Color blanc = new Color(fons.getRGB()^0xFFFFFF);
        try {
            ((SquareCell) c).getA();
        }
        catch(Exception e1) {
            try {
                ((Container)c).setBackground(fons);
            }
            catch (Exception e) {}
            try {
                ((Container)c).setForeground(blanc);
            }
            catch (Exception e) {}
            try {
                Border d = ((JPanel)c).getBorder();
                ((TitledBorder)d).setTitleColor(blanc);
            }
            catch (Exception e) {}
            try {
                ((JButton)c).setForeground(fons);
            }
            catch(Exception e) {}
            int n = c.getComponentCount();
            for (int i = 0; i < n; ++i) {
                try {
                    canviarColors((Container)c.getComponent(i));
                }
                catch(Exception e) {}
            }
        }
        
        
    }
    
    private void totInvisible() {
        panel_type.setVisible(false);
        g2.clearSelection();
        panel_num.setVisible(false);
        g3.clearSelection();
        l_num_actual.setEnabled(false);
        spinner_num_actual.setEnabled(false);
        panel_nom.setVisible(false);
    } 
    
    private void mousePress (SquareCell p) {
        if (b_canvi_num.isSelected() || b_canvi_type.isSelected()) p.setLight(true);
    }
    
    private void mouseRelease (SquareCell p) {
        if (p.getLight()) {
            if (b_canvi_num.isSelected() && p.getType() != CapaDomini.Tauler.Type.VOID) {
                canviarNum(p);
                b_desar.setEnabled(false);
            }
            else if (b_canvi_type.isSelected()) {
                canviarType(p);
                b_desar.setEnabled(false);
            }
            p.setLight(false);
        }
    }
    
    private void canviarNum (SquareCell p) {
        int num = -2;
        if (b_esborrar_num.isSelected()) {
            num = 0;
        }
        else if (b_preguntar_num.isSelected()) {
            try {
                String input = JOptionPane.showInputDialog("Escriu un número:");
                if (input == null) return;
                num = Integer.parseInt(input);
                if (num <= 0) num = -1;
            }
            catch (Exception e) {
                num = -1;
            }
        }
        else if (b_seq_num.isSelected()) {
            num = (Integer) spinner_num_actual.getValue();
            spinner_num_actual.setValue(num+1);
        }
        if (num == -1) msgError("Número no vàlid");
        else if (num == -2) msgError("Selecciona una opció");
        else {
            hmc.setTempCellVal(p.getA(), p.getB(), num);
            p.changeVal(num);
        }
    }
    
    private void canviarType (SquareCell p) {
        if (b_blank.isSelected()) {
            hmc.setTempCellType(p.getA(), p.getB(), CapaDomini.Tauler.Type.BLANK);
            p.changeType(CapaDomini.Tauler.Type.BLANK);
        }
        else if (b_given.isSelected()) {
            hmc.setTempCellType(p.getA(), p.getB(), CapaDomini.Tauler.Type.GIVEN);
            p.changeType(CapaDomini.Tauler.Type.GIVEN);
        }
        if (b_void.isSelected()) {
            hmc.setTempCellType(p.getA(), p.getB(), CapaDomini.Tauler.Type.VOID);
            p.changeType(CapaDomini.Tauler.Type.VOID);
            p.changeVal(0);
        }
    }
    
    private void msgError(String text) {
        JOptionPane.showMessageDialog(this,text,"Error",JOptionPane.ERROR_MESSAGE);
    }
    
    private void msgInfo(String text) {
        JOptionPane.showMessageDialog(this,text,"",JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void tancar() {
        hmc.saveAll();
        parent.obrirMenu(this);
    }
}
