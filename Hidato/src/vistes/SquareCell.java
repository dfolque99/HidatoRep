/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistes;


import domini.Tauler.Type;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author David
 */
public class SquareCell extends JPanel{
    
    private final int a;
    private final int b;
    private int val;
    private Type type;
    private final Color color_normal, color_highlight, color_void;
    private final JLabel label;
    private final Font fontNormal, fontNegreta;
    private boolean light;
    
    
    
    
    public SquareCell (int a, int b, int val, Type type, Color cn, Color ch, Color cv) {
        this.a = a;
        this.b = b;
        this.val = val;
        this.type = type;
        color_normal = cn;
        color_highlight = ch;
        color_void = cv;
        light = false;
        fontNormal = new Font("Tahoma", Font.PLAIN, 20);
        fontNegreta = new Font("Tahoma", Font.BOLD, 20);
        
        label = new JLabel();
        label.setFont(fontNormal);
        add(label);
        if (val == 0) label.setText("");
        else label.setText(Integer.toString(val));
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setBackground(color_normal);
    }
    
    public void changeVal(int val) {
        this.val = val;
        if (val == 0) label.setText("");
        else label.setText(Integer.toString(val));
    }
    
    public void setNegreta (boolean negreta) {
        if (negreta) label.setFont(fontNegreta);
        else label.setFont(fontNormal);
    }
    
    public void setLight(boolean light) {
        if (type != Type.VOID) {
            this.light = light;
            if (light) {
                setBackground(color_highlight);
            }
            else {
                setBackground(color_normal);
            }
        }
    }
    
    public int getVal() {
        return val;
    }
    
    public boolean getLight() {
        return light;
    }
    
    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }
    
    
}
