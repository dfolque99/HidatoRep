/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacio;


import CapaDomini.Misc.Fonts;
import CapaDomini.Tauler.Type;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author David
 */
public class SquareCellRapida extends JPanel{
    
    private final Color color_normal, color_given, color_void;
    private JLabel label; //etiqueta que contindra el valor de la cell
    private final int font_num;
    
    
    public SquareCellRapida (int val, Type type, Color blankColor, Color givenColor, Color voidColor, int font_num) {
        color_normal = blankColor;
        color_given = givenColor;
        color_void = voidColor;
        this.font_num = font_num;
        
        if (val > 0) {
            setLayout(new GridBagLayout());
            label = new JLabel();
            add(label);
            if (val <= 0) label.setText("");
            else label.setText(Integer.toString(val));
            label.setFont(Fonts.getFont("OpenSans-Light", Font.BOLD, font_num));
        }
        setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        
        if (type == Type.VOID) {
            setBackground(color_void);
        }
        else if (type == Type.GIVEN) {
            setBackground(color_given);
        }
        else {
            setBackground(color_normal);
        }
    }
    
    public void setVal(int val) {
        if (label == null) {
            setLayout(new GridBagLayout());
            label = new JLabel();
            add(label);
        }
        if (val <= 0) label.setText("");
        else label.setText(Integer.toString(val));
        label.setFont(Fonts.getFont("OpenSans-Light",Font.BOLD, font_num));
    }
    
}
