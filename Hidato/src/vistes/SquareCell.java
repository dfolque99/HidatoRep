/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistes;


import domini.Tauler.Type;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;

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
    private final String font_nom = "Tahoma";
    private int font_num;
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
        font_num = 20;
        
        setLayout(null);
        label = new JLabel();
        add(label);
        label.setBounds(20,20,100,100);
        if (val == 0) label.setText("");
        else label.setText(Integer.toString(val));
        label.setFont(new Font(font_nom, Font.PLAIN, 20));
        //System.out.printf("%d, %d\n", label.getLocation().x, label.getLocation().y);
        //label.setLocation((int) label.getLocation().getX(), 200);
        //System.out.printf("%d, %d\n", label.getLocation().x, label.getLocation().y);
        //label.setBounds(new Rectangle (0,10,100,100));
        setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        setBackground(color_normal);
    }
    
    public void ficar() {
        Rectangle r = new Rectangle();
        r.x = 0;
        r.y = 0;
        r.height = 100;
        r.width = 100;
    }
    
    public void changeVal(int val) {
        this.val = val;
        if (val == 0) label.setText("");
        else label.setText(Integer.toString(val));
    }
    
    public void setNegreta (boolean negreta) {
        if (negreta) label.setFont(new Font(font_nom, Font.BOLD, font_num));
        else label.setFont(new Font(font_nom, Font.PLAIN, font_num));
    }
    
    public void setFontNum (int num) {
        font_num = num;
        label.setFont(new Font(font_nom, label.getFont().getStyle(), font_num));
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
