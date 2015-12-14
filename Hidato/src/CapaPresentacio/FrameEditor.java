/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacio;


import CapaDomini.Misc.Colors;
import CapaDomini.Misc.Fonts;
import CapaDomini.Partida.CurrentGameController;
import CapaDomini.Partida.Help;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Tauler.HidatoSet;
import CapaDomini.Tauler.SolverControllerStop;
import CapaDomini.Tauler.Type;
import CapaDomini.Usuari.HidatoUserController;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
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
    public FrameEditor(AdminVistes parent, HidatoManagerController hmc) {
        initComponents();
        inici(parent, hmc);
        setIconImage((new ImageIcon("icon.png")).getImage());
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
        jPanel4 = new javax.swing.JPanel();
        b_autocompletar = new javax.swing.JButton();
        b_validar = new javax.swing.JButton();
        b_esborrar_tot = new javax.swing.JButton();
        label_dificultat = new javax.swing.JLabel();
        b_desar = new javax.swing.JButton();
        b_sortir = new javax.swing.JButton();
        b_solucio = new javax.swing.JButton();
        b_jugar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        label_nom = new javax.swing.JLabel();
        panel_edicio = new javax.swing.JPanel();
        b_buida_esborrar = new javax.swing.JRadioButton();
        b_pista_posar = new javax.swing.JRadioButton();
        b_void = new javax.swing.JRadioButton();
        b_buida_posar = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Editor de hidato");
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

        b_autocompletar.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        b_autocompletar.setText("Autocompletar i validar");

        b_validar.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        b_validar.setText("Validar");

        b_esborrar_tot.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        b_esborrar_tot.setText("Esborrar tot");

        label_dificultat.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        label_dificultat.setText("Dificultat no calculada");

        b_desar.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        b_desar.setText("Desar");

        b_sortir.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        b_sortir.setText("Sortir");

        b_solucio.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        b_solucio.setText("Mostrar solució");

        b_jugar.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        b_jugar.setText("Jugar hidato");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b_autocompletar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b_validar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b_esborrar_tot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b_solucio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(label_dificultat, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(b_desar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(b_sortir, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(b_jugar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_dificultat, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_esborrar_tot, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_autocompletar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_validar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_solucio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_jugar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_sortir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_desar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(label_nom, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE))
        );

        panel_edicio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opcions d'edició de casella", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Fonts.getFont("OpenSans-Light", Font.PLAIN, 18)));

        b_buida_esborrar.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        b_buida_esborrar.setText("Buida (esborrar número)");

        b_pista_posar.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        b_pista_posar.setText("Pista (posar número)");

        b_void.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        b_void.setText("No vàlida");

        b_buida_posar.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        b_buida_posar.setText("Buida (posar número)");

        javax.swing.GroupLayout panel_edicioLayout = new javax.swing.GroupLayout(panel_edicio);
        panel_edicio.setLayout(panel_edicioLayout);
        panel_edicioLayout.setHorizontalGroup(
            panel_edicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_edicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_edicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b_buida_esborrar)
                    .addComponent(b_pista_posar)
                    .addComponent(b_void)
                    .addComponent(b_buida_posar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_edicioLayout.setVerticalGroup(
            panel_edicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_edicioLayout.createSequentialGroup()
                .addComponent(b_buida_esborrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_buida_posar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_pista_posar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_void))
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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_edicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(panel_edicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        parent.saveBeforeClose();
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
                HidatoManagerController hmc = new HidatoManagerController(new HidatoSet(), uc);
                hmc.loadAll();
                hmc.createRandom(7,7);
                new FrameEditor(null, hmc).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_autocompletar;
    private javax.swing.JRadioButton b_buida_esborrar;
    private javax.swing.JRadioButton b_buida_posar;
    private javax.swing.JButton b_desar;
    private javax.swing.JButton b_esborrar_tot;
    private javax.swing.JButton b_jugar;
    private javax.swing.JRadioButton b_pista_posar;
    private javax.swing.JButton b_solucio;
    private javax.swing.JButton b_sortir;
    private javax.swing.JButton b_validar;
    private javax.swing.JRadioButton b_void;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel label_dificultat;
    private javax.swing.JLabel label_nom;
    private javax.swing.JPanel panel_edicio;
    // End of variables declaration//GEN-END:variables

    // =============================AQUI LO MEU=================================

    
    
    private ArrayList<ArrayList<SquareCell>> panels;
    private ArrayList<ArrayList<Integer>> fixats;
    private int N, M;
    private HidatoManagerController hmc;
    private AdminVistes parent;
    private ButtonGroup g1;
    private String nomHidato;
    private boolean solMostrada;
    private final FrameEditor dis = this;
    DialogProgressBar dialog;
    
    
    
    public void inici(AdminVistes parent, HidatoManagerController hmc) {
        this.N = hmc.getTempSizeX();
        this.M = hmc.getTempSizeY();
        this.hmc = hmc;
        this.parent = parent;
        nomHidato = hmc.getTempBoardName();
        if (nomHidato == null) {
            label_nom.setText("Hidato no guardat");
            poderDesar(false);
        }
        else {
            label_nom.setText("Hidato: "+nomHidato);
            poderDesar(true);
        }
        label_nom.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 48));
        solMostrada = false;
        
        
        panels = new ArrayList<>();
        fixats = new ArrayList<>();
        int maxim, N1, N2, M1, M2;
        maxim = Math.max(N,M);
        N1 = (maxim-N)/2;
        N2 = N + N1;
        M1 = (maxim-M)/2;
        M2 = M + M1;
        jPanel1.setLayout(new GridLayout(maxim, maxim));
        for (int i0 = 0; i0 < maxim; ++i0) {
            panels.add(new ArrayList<>());
            fixats.add(new ArrayList<>());
            for (int j0 = 0; j0 < maxim; ++j0) {
                if (N1 <= i0 && i0 < N2 && M1 <= j0 && j0 < M2) {
                    int i = i0-N1, j = j0-M1;
                    int val = hmc.getTempCellVal(i,j);
                    CapaDomini.Tauler.Type type = hmc.getTempCellType(i,j);
                    if (type != CapaDomini.Tauler.Type.GIVEN) val = 0;
                    SquareCell p = new SquareCell(i,j,val,type,Colors.c(2),Colors.c(4),Colors.c(1),Colors.c(0), 500/maxim*5/10,true);
                    panels.get(i).add(p);
                    fixats.get(i).add(val);
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
        g1 = new ButtonGroup();
        g1.add(b_buida_esborrar); g1.add(b_pista_posar); g1.add(b_void); g1.add(b_buida_posar);
        
        configurarBotons();
        //canviarColors(this);
        
    }
    
    
    private void configurarBotons() {
        b_esborrar_tot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                for (int i = 0; i < N; ++i) {
                    for (int j = 0; j < M; ++j) {
                        hmc.setTempCellVal(i, j, 0);
                        panels.get(i).get(j).changeVal(0);
                        hmc.setTempCellType(i, j, CapaDomini.Tauler.Type.BLANK);
                        panels.get(i).get(j).changeType(CapaDomini.Tauler.Type.BLANK);
                        fixats.get(i).set(j,0);
                    }
                }
                poderDesar(false);
            }
        });
        b_autocompletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (!hmc.tempHasOrigin()) {
                    msgError ("No s'ha pogut completar el hidato perquè no té casella inicial");
                    return;
                }
                posarZeros();
                dis.setEnabled(false);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SolverControllerStop.allow();
                        boolean completat = hmc.completeTempHidato();
                        if (!SolverControllerStop.isStopped()) {
                            dis.setEnabled(true);
                            if (dialog != null) dialog.dispose();
                            if (completat) {
                                for (int i = 0; i < N; ++i) {
                                    for (int j = 0; j < M; ++j) {
                                        CapaDomini.Tauler.Type t = hmc.getTempCellType(i, j);
                                        panels.get(i).get(j).changeType(t);
                                        int val = hmc.getTempCellVal(i, j);
                                        if (t == CapaDomini.Tauler.Type.GIVEN) panels.get(i).get(j).changeVal(val);
                                    }
                                }
                                poderDesar(true);
                                msgInfo ("S'ha completat el hidato. Ara ja es pot desar.");
                            }
                            else {
                                msgError ("No s'ha pogut completar el hidato");
                            }
                        }
                    }
                });
                dialog = obrirProgressBar("Completant el hidato...", t);
                t.start();
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
                posarZeros();
                dis.setEnabled(false);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SolverControllerStop.allow();
                        boolean completat = hmc.solveTempHidato();
                        if (!SolverControllerStop.isStopped()) {
                            dis.setEnabled(true);
                            if (dialog != null) dialog.dispose();
                            if (completat) {
                                poderDesar(true);
                                msgInfo ("S'ha validat el hidato. Ara ja es pot desar.");
                            }
                            else {
                                msgError ("No s'ha pogut validar el hidato");
                            }
                        }
                    }
                });
                dialog = obrirProgressBar("Buscant una solució...", t);
                t.start();
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
        b_solucio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (solMostrada) {
                    b_solucio.setText("Mostrar solució");
                    noMostrarTot();
                    solMostrada = false;
                    enablePanelEdicio(true);
                }
                else {
                    b_solucio.setText("Amagar solució");
                    mostrarTot();
                    solMostrada = true;
                    enablePanelEdicio(false);
                }
            }
        });
        b_jugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                obrirJoc();
            }
        });
    }
    
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
    
    
    private void obrirJoc() {
        Object[] options = { "Baix", "Mitja","Alt" };
        int val = JOptionPane.showOptionDialog(null, "Tria el nivell d'ajuda", "",
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, options,options[0]);
        if (val != -1){
            Help h;
            if (val == 0) h = Help.LOW;
            else if (val == 1) h = Help.MEDIUM;
            else h = Help.HIGH;
            CurrentGameController cgc = hmc.playTempHidato(h);
            parent.obrirPartida(this, cgc);
        }
    }
    
    private void enablePanelEdicio(boolean b) {
        panel_edicio.setEnabled(b);
        b_buida_esborrar.setEnabled(b);
        b_buida_posar.setEnabled(b);
        b_pista_posar.setEnabled(b);
        b_void.setEnabled(b);
        b_esborrar_tot.setEnabled(b);
        b_autocompletar.setEnabled(b);
        if (!b) {
            g1.clearSelection();
        }
    }
    
    private void posarZeros() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                if (panels.get(i).get(j).getVal() == 0) {
                    hmc.setTempCellVal(i, j, 0);
                }
            }
        }
    }
    
    private void mostrarTot() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                if (hmc.getTempCellType(i, j) != CapaDomini.Tauler.Type.VOID) {
                    panels.get(i).get(j).changeVal(hmc.getTempCellVal(i, j));
                    panels.get(i).get(j).changeType(hmc.getTempCellType(i, j));
                }
            }
        }
    }
    
    private void noMostrarTot() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                if (hmc.getTempCellType(i, j) == CapaDomini.Tauler.Type.BLANK) {
                    panels.get(i).get(j).changeVal(fixats.get(i).get(j));
                }
            }
        }
    }
    
    private void desar() {
        if (nomHidato == null) {
            String newName = JOptionPane.showInputDialog(this,"Introdueix un nom per al hidato",null);
            if (hmc.usedName(newName)) {
                msgError("Ja existeix un hidato amb aquest nom");
                return;
            }
            nomHidato = newName;
        }
        if (label_dificultat.getText().equals("")) {
            hmc.calcTempDifficulty();
            label_dificultat.setText(hmc.getTempDifficulty());
        }
        hmc.saveTempHidato(nomHidato);
        msgInfo("Hidato " + nomHidato + " desat correctament");
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
    
    private void mousePress (SquareCell p) {
        p.setLight(true);
    }
    
    private void mouseRelease (SquareCell p) {
        if (p.getLight()) {
            if (b_buida_esborrar.isSelected()) {
                hmc.setTempCellType(p.getA(), p.getB(), CapaDomini.Tauler.Type.BLANK);
                p.changeType(CapaDomini.Tauler.Type.BLANK);
                hmc.setTempCellVal(p.getA(), p.getB(), 0);
                p.changeVal(0);
                fixats.get(p.getA()).set(p.getB(), 0);
                poderDesar(false);
            }
            else if (b_pista_posar.isSelected() || b_buida_posar.isSelected()) {
                int num;
                try {
                    String input = JOptionPane.showInputDialog("Escriu un número:");
                    if (input == null) return;
                    num = Integer.parseInt(input);
                    if (num <= 0) throw new Exception();
                    
                }
                catch (Exception e) {
                    msgError("Número no vàlid");
                    return;
                }
                hmc.setTempCellVal(p.getA(), p.getB(), num);
                p.changeVal(num);
                fixats.get(p.getA()).set(p.getB(), num);
                CapaDomini.Tauler.Type t;
                if (b_buida_posar.isSelected()) t = CapaDomini.Tauler.Type.BLANK;
                else t = CapaDomini.Tauler.Type.GIVEN;
                hmc.setTempCellType(p.getA(), p.getB(), t);
                p.changeType(t);
                poderDesar(false);
            }
            else if (b_void.isSelected()) {
                hmc.setTempCellType(p.getA(), p.getB(), CapaDomini.Tauler.Type.VOID);
                p.changeType(CapaDomini.Tauler.Type.VOID);
                hmc.setTempCellVal(p.getA(), p.getB(), -1);
                p.changeVal(0);
                fixats.get(p.getA()).set(p.getB(), 0);
                poderDesar(false);
            }
            p.setLight(false);
        }
    }
    
    private void poderDesar(boolean b) {
        b_desar.setEnabled(b);
        b_solucio.setEnabled(b);
        b_jugar.setEnabled(b);
        b_validar.setEnabled(!b);
        if (b) {
            hmc.calcTempDifficulty();
            label_dificultat.setText("Dificultat: " + hmc.getTempDifficulty());
            b_validar.setText("Hidato validat!");
        }
        else {
            solMostrada = false;
            label_dificultat.setText("Dificultat no calculada");
            b_solucio.setText("Mostrar solució");
            b_validar.setText("Validar");
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
