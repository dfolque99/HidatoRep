/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacio;

import CapaDomini.Domini;
import CapaDomini.Misc.Colors;
import CapaDomini.Misc.Fonts;
import CapaDomini.Partida.GameManagerController;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Tauler.HidatoSet;
import CapaDomini.Usuari.HidatoUserController;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author David
 */
public class FrameLlista extends javax.swing.JFrame {

    /**
     * Creates new form FrameLlista
     */
    public FrameLlista(RetornadorString ret, HidatoManagerController hmc) {
        initComponents();
        inici(ret, hmc);
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
        label_nom = new javax.swing.JLabel();
        label_autor = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<String>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        label_nom.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        label_nom.setText("Hidato:");

        label_autor.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        label_autor.setText("Autor");

        jButton1.setText("Seleccionar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_nom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_autor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 152, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_nom, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_autor)
                .addGap(11, 11, 11)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Hola", "com", "estas", "mes", "elements", "hooahoahoahaohoaa", "dfase", "gaefsedga", "fgaesfsada", "geafaesg", "aefaesgae" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
            java.util.logging.Logger.getLogger(FrameLlista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameLlista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameLlista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameLlista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HidatoUserController uc = new HidatoUserController();
                //uc.createUser("Usuari", "password");
                uc.login("david", "david");
                HidatoManagerController hmc = new HidatoManagerController(new HidatoSet(), uc);
                hmc.loadAll();
                FrameLlista fll = new FrameLlista(new RetornadorString() {
                    @Override
                    public void retorna (String s) {
                        System.out.print("Retornaria la string " + s + "\n");
                    }
                }, hmc);
                fll.setVisible(true);
                fll.loadHidatosUsuari();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    protected javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel label_autor;
    private javax.swing.JLabel label_nom;
    // End of variables declaration//GEN-END:variables

    private RetornadorString ret;
    private HidatoManagerController hmc;
    private String selected;
    private ArrayList<ArrayList<SquareCellRapida>> panels;
    private Thread t;
    private boolean seguir;
    
    private void inici(RetornadorString ret, HidatoManagerController hmc) {
        this.ret = ret;
        this.hmc = hmc;
        label_nom.setText("");
        label_nom.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 48));
        posaLabelAutor();
        selected = null;
        jList1.setSelectionBackground(Colors.c(1));
        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged (ListSelectionEvent evt) {
                if (!jList1.getSelectedValue().equals(selected)) {
                    selected = jList1.getSelectedValue();
                    seleccio();
                }
            }
        });
        jList1.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        jButton1.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                retorna();
            }
        });
    }
    
    private void posaLabelAutor(){
        label_autor.setText("");
        label_autor.setFont(Fonts.getFont("OpenSans-Light", Font.ITALIC, 24));
    }
    
    private void retorna() {
        ret.retorna(jList1.getSelectedValue());
        this.dispose();
    }
    
    public void loadHidatosUsuari () {
        ArrayList<String> llista = hmc.getUserHidatoList();
        jList1.setListData(llista.toArray(new String[]{}));
        if (!llista.isEmpty()) jList1.setSelectedIndex(0);
    }
    
    public void loadHidatosTots () {
        ArrayList<String> llista = hmc.getAllHidatoList();
        jList1.setListData(llista.toArray(new String[]{}));
        if (!llista.isEmpty()) jList1.setSelectedIndex(0);
    }

    private void seleccio() {
        seguir = false;
        boolean result = hmc.loadHidato(selected);
        if (!result) {
            msgError("No s'ha trobat l'element");
            return;
        }
        label_nom.setText(selected);
        label_autor.setText("By: " + hmc.getTempUsername());
        dibuixarHidato();
    }

    private void dibuixarHidato() {
        int N = hmc.getTempSizeX();
        int M = hmc.getTempSizeY();
        jPanel2.removeAll();
        jPanel2.updateUI();
        int maxim, N1, N2, M1, M2;
        maxim = Math.max(N,M); 
        N1 = (maxim-N)/2;
        N2 = N + N1;
        M1 = (maxim-M)/2;
        M2 = M + M1;
        jPanel2.setLayout(new GridLayout(maxim, maxim));
        panels = new ArrayList<>();
        for (int i0 = 0; i0 < maxim; ++i0) {
            panels.add(new ArrayList<>());
            for (int j0 = 0; j0 < maxim; ++j0) {
                if (N1 <= i0 && i0 < N2 && M1 <= j0 && j0 < M2) {
                    int i = i0-N1, j = j0-M1;
                    CapaDomini.Tauler.Type type = hmc.getTempCellType(i,j);
                    SquareCellRapida p = new SquareCellRapida(0,type,Colors.c(2),Colors.c(1),Colors.c(0), 400/maxim*5/10);
                    jPanel2.add(p, i0*maxim+j0);
                    panels.get(i0).add(p);
                }
                else {
                    JPanel p = new JPanel();
                    jPanel2.add(p,i0*maxim+j0);
                    panels.get(i0).add(null);
                }
            }
        }
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                seguir = true;
                for (int i0 = 0; i0 < maxim; ++i0) {
                    for (int j0 = 0; j0 < maxim; ++j0) {
                        if (N1 <= i0 && i0 < N2 && M1 <= j0 && j0 < M2) {
                            int i = i0-N1, j = j0-M1;
                            if (!seguir) return;
                            try {
                                int val = hmc.getTempCellVal(i,j);
                                if (hmc.getTempCellType(i,j) == CapaDomini.Tauler.Type.GIVEN) panels.get(i0).get(j0).setVal(val);
                            }
                            catch(Exception e) {}
                        }
                    }
                }
            }
        });
        t.start();
    }
    
    private void msgError(String text) {
        JOptionPane.showMessageDialog(this,text,"Error",JOptionPane.ERROR_MESSAGE);
    }
}
