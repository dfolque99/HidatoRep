/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistes;

import domini.Tauler.Cell;
import domini.Tauler.Type;
import domini.Misc.Colors;
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
    private Color color_normal, color_highlight, color_void;
    private final JLabel label;
    private Font fontNormal, fontNegreta;
    private boolean light;
    
    
    
    public SquareCell (int a, int b) {
        this.a = a;
        this.b = b;
        light = false;
        fontNormal = new Font("Tahoma", Font.PLAIN, 20);
        fontNormal = new Font("Tahoma", Font.BOLD, 20);
        
        label = new JLabel();
        add(label);
        setBorder(new BevelBorder(BevelBorder.RAISED));
    }
    
    public void setColors (Color color_normal, Color color_highlight, Color color_void) {
        this.color_normal = color_normal;
        this.color_highlight = color_highlight;
        this.color_void = color_void;
    }
    
    public void omplir (Cell c) {
        int num = c.getVal();
        Type t = c.getType();
        if (t == Type.VOID) {
            setBackground(color_void);
        }
        else {
            setBackground(color_normal);
            if (num == 0) label.setText("");
            else label.setText(Integer.toString(num));
            if (t == Type.GIVEN) {
                label.setFont(fontNegreta);
            }
            else {
                label.setFont(fontNormal);
            }
        }
    }
    
    public void setLight(boolean light) {
        this.light = light;
        if (light) {
            setBackground(color_highlight);
        }
        else {
            setBackground(color_normal);
        }
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
