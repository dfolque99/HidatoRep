/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacio;

import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * Classe que efectua animacions.
 * @author David
 */
public class Animacio implements Runnable {
    /*
     * Thread que efectua l'animació
     */
    private final Thread t;
    
    /*
     * Factor de velocitat en les animacions.
     * Si és 1, els moviments es realitzen en una iteracio, sinó en log en base
     * vel de la distancia que s'ha de recorrer
     */
    private int vel;
    
    /*
     * Array dels panels que s'estan animant
     */
    private ArrayList<JPanel> panels;
    
    /*
     * Posicions cap a on es dirigeixen els panels animats
     */
    private ArrayList<Point> posicions;
    
    /*
     * Constructora, amb paràmetre vel
     */
    public Animacio(int vel) {
        this.vel = vel;
        t = new Thread(this);
        panels = new ArrayList<>();
        posicions = new ArrayList<>();
    }
    
    /*
     * Afegeix el panel jp a l'array de panels, i afegeix la seva posicio
     * (perque de moment estigui quiet) al de posicions
     */
    public void addElement (JPanel jp) {
        panels.add(jp);
        posicions.add(jp.getLocation());
    }
    
    /*
     * Canvia la posicio destinacio del panel i
     */
    public void setPoint (int i, Point p) {
        posicions.set(i,p);
        if (!t.isAlive()) t.start();
    }

    /*
     * Setter de vel
     */
    public void setVel(int vel) {
        this.vel = vel;
    }
    
    /*
     * Mètode que es duu a terme al t.start()
     * La animació es duu a terme un cop cada 5ms
     */
    @Override
    public void run() {
        boolean seguir = true;
        while (seguir) {
            for (int i = 0; i < panels.size(); ++i) {
                JPanel jp = panels.get(i);
                Point p = posicions.get(i);
                Point ap = jp.getLocation();
                int dx = p.x-ap.x;
                dx = dx/vel;
                int dy = p.y-ap.y;
                dy = dy/vel;
                jp.setLocation(ap.x+dx, ap.y+dy);
                jp.repaint();
            }
            try {
                t.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(FrameMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
