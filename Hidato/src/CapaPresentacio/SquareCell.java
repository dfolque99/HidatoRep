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
 * Classe que representa una cel·la com a component
 * @author David
 */
public class SquareCell extends JPanel{
    
    /*
     * Fila on està la cel·la en el seu hidato
     */
    private final int a;
    
    /*
     * Columna on està la cel·la en el seu hidato
     */
    private final int b;
    
    /*
     * Valor de la cel·la
     */
    private int val;
    
    /*
     * Tipus de la cel·la
     */
    private Type type;
    
    /*
     * Colors que adopta la casella en funció del seu tipus i valor
     */
    private final Color color_normal, color_highlight, color_given, color_void;
    
    /*
     * JLabel on es mostrarà el número de la cel·la
     */
    private final JLabel label;
    
    /*
     * Boolean que indica si el ratolí ha pitjat a la casella i encara no ha
     * sortit de sobre seu. Serveix per coordinar l'esdeveniment
     * press+release-mouseExited
     */
    private boolean light;
    
    /*
     * Boolean que indica si l'usuari ha de ser capaç de modificar la cel·la
     */
    private boolean modificable;
    
    /*
     * Creadora amb paràmetres
     * Posa el color de fons, la vora del panel, crea el label amb el valor, 
     * defineix els listeners mouseEntered i mouseExited
     */
    public SquareCell (int a, int b, int val, Type type, Color blankColor, Color clickColor, Color givenColor, Color voidColor, int font_num, boolean mod) {
        this.a = a;
        this.b = b;
        this.val = val;
        this.type = type;
        color_normal = blankColor;
        color_highlight = clickColor;
        color_given = givenColor;
        color_void = voidColor;
        modificable = mod;
        light = false;
        
        setLayout(new GridBagLayout());
        label = new JLabel();
        add(label);
        if (val <= 0) label.setText("");
        else label.setText(Integer.toString(val));
        label.setFont(Fonts.getFont("OpenSans-Light",Font.PLAIN, font_num));
        setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        setType(type);
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
                setLight(false);
            }
        });
    }
    
    /**
     * 
     */
    public void setVal(int val) {
        this.val = val;
        if (val <= 0) label.setText("");
        else label.setText(Integer.toString(val));
    }
    
    public final void setType(Type type) {
        this.type = type;
        if (type == Type.VOID) {
            setBackground(color_void);
        }
        else if (type == Type.GIVEN) {
            label.setFont(Fonts.getFont("OpenSans-Regular", Font.BOLD, label.getFont().getSize()));
            setBackground(color_given);
        }
        else {
            setBackground(color_normal);
            label.setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, label.getFont().getSize()));
        }
    }
    
    public void setLight(boolean light) {
        if (modificable) {
            this.light = light;
            if (light) {
                setBackground(color_highlight);
            }
            else {
                setType(type);
            }
        }
    }

    public void setModificable(boolean modificable) {
        this.modificable = modificable;
    }
    
    public int getVal() {
        return val;
    }
    
    public Type getType() {
        return type;
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
