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
 *
 * @author David
 */
public class Animacio implements Runnable{
    private Thread t;
    private int vel;
    private ArrayList<JPanel> jps;
    private ArrayList<Point> ps;
    
    public Animacio(int vel) {
        this.vel = vel;
        t = new Thread(this);
        jps = new ArrayList<>();
        ps = new ArrayList<>();
    }
    
    public void addElement (JPanel jp) {
        jps.add(jp);
        ps.add(jp.getLocation());
    }
    
    public void setPoint (int i, Point p) {
        ps.set(i,p);
        if (!t.isAlive()) t.start();
    }

    public void setVel(int vel) {
        this.vel = vel;
    }
    
    
    
    public void run() {
        boolean seguir = true;
        while (seguir) {
            for (int i = 0; i < jps.size(); ++i) {
                JPanel jp = jps.get(i);
                Point p = ps.get(i);
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
