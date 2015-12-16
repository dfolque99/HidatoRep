/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacio;

import CapaDomini.Partida.CurrentGameController;
import CapaDomini.Partida.GameManagerController;
import CapaDomini.Tauler.HidatoManagerController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Vista que mostra la llista de totes les partides d'un usuari. L'usuari pot seleccionar 
 * les partides, i li apareixerà dibuixat el tauler, amb l'opció de continuar jugant.
 * Es una classe heredada de FrameLlista ja que utilitza molts metodes d'aquesta.
 * @author Pau
 */
public class FrameLlistaPartides extends FrameLlista {

    /**
     * Instancia de game manager, que li permet comunicar-se amb la capa de domini.
     */
    private final GameManagerController gmc;
    
    /**
     * Instancia de current game controller, que li proporciona informacio de cada partida.
     */
    private CurrentGameController cgc;
    
    /**
     * Creadora. Inicialitza els parametres.
     * @param ret Instancia de retornador String
     * @param gmc
     * @param hmc
     */
    public FrameLlistaPartides(RetornadorString ret, GameManagerController gmc, HidatoManagerController hmc) {
        super(ret,hmc);
        this.gmc = gmc;
        this.cgc = null;
        initComponents();
        setIconImage((new ImageIcon("icon.png")).getImage());
        jButton3.setText("Elimina");
        jButton3.setVisible(true);
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                canviaNom();
            }
        });
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                elimina();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Partides guardades");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Quan es tanca la finestra, no fa res ja que el programa no acaba 
     * (si es tanca, s'obre la finestra de menu)
     * @param evt event de clicar en el boto
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        retorna(false);
    }//GEN-LAST:event_formWindowClosing

    /**
     * Metode per canviar el nom d'una partida. S'utilitza en el RetornadorString
     */
    @Override
    protected void canviaNom() {
        String newName = JOptionPane.showInputDialog(this, "Escriu un nou nom per la partida "
            + jList1.getSelectedValue() + ":");
        if (newName == null) return;
        if (newName.equals("")) {
            msgError("Escriu algun nom!");
            return;
        }
        if (gmc.getGame(newName) != null) {
            msgError("Ja existeix un hidato amb aquest nom");
            return;
        }
        ret.canviaNom(jList1.getSelectedValue(), newName);
        this.dispose();
    }
    
    /**
     * Carrega les partides d'un usuari en la llista.
     */
    public void loadPartidesUsuari () {
        ArrayList<String> llista = gmc.getGameList();
        jList1.setListData(llista.toArray(new String[]{}));
        if (!llista.isEmpty()) jList1.setSelectedIndex(0);
    }
    
    /**
     * Metode que s'executa quan es clica una partida.
     */
    @Override
    protected void seleccio() {
        seguir = false;
        cgc = gmc.getGame(selected);
        if (cgc == null) {
            msgError("No s'ha trobat l'element");
            return;
        }
        label_nom.setText(selected);
        dibuixarHidato();
    }
    
    /**
     * Demana el tipus de la cell amb posició (i,j)
     * @param i coordenada x de la cell
     * @param j coordenada y de la cell
     * @return tipus de la cell
     */
    @Override
    protected CapaDomini.Tauler.Type getType(int i, int j){
        return cgc.getCellType(i,j);
    }
    
    /**
     * Demana el valor de la cell amb posició (i,j)
     * @param i coordenada x de la cell
     * @param j coordenada y de la cell
     * @return tipus de la cell
     */
    @Override
    protected int getVal(int i, int j){
        return cgc.getCellVal(i,j);
    }
    
    /**
     * Posa el valor d'una cell en el panel, si la cell te algun numero posat.
     * @param i coordenada x de la cell en el controlador
     * @param j coordenada y de la cell en el controlador
     * @param i0 coordenada x de la cell en el panel
     * @param j0 coordenada y de la cell en el panel
     */
    @Override
    protected void setVal(int i, int j, int i0, int j0){
        int val = cgc.getCellVal(i,j);
        if (cgc.getCellType(i,j) == CapaDomini.Tauler.Type.GIVEN) panels.get(i0).get(j0).setVal(val);
        else if (cgc.getCellType(i,j) == CapaDomini.Tauler.Type.BLANK && cgc.getCellVal(i, j) != 0)
            panels.get(i0).get(j0).setVal(val);
        
    }
    
    /**
     * Getter del nombre de files del hidato
     * @return nombre de files del hidato
     */
    @Override
    protected int hidatoX(){
        return cgc.getSizeX();
    }
    
    /**
     * Getter del nombre de columnes del hidato
     * @return nombre de columnes del hidato
     */
    @Override
    protected int hidatoY(){
        return cgc.getSizeY();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
