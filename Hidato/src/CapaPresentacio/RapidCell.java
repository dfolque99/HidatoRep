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
 * Classe que representa una cel·la com a component. No és editable.
 * Pretén ser més ràpida que SquareCell, pel que no escriu el número directament
 * @author David
 */
public class RapidCell extends JPanel{
    
    /*
     * Colors que adopta la casella en funció del seu tipus i valor
     */
    private final Color color_normal, color_given, color_void;
    
    /*
     * JLabel on es mostrarà el número de la cel·la
     */
    private JLabel label;
    
    /*
     * Mida de la font amb la qual es mostrarà el valor de la cel·la
     */
    private final int font_num;
    
    
    /**
     * Creadora amb parametres
     * Posa el color de fons, la vora del panel, però no crea listeners, ni
     * crea el label amb el valor, per que es creï més ràpid
     */
    public RapidCell (int val, Type type, Color blankColor, Color givenColor, Color voidColor, int font_num) {
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
    
    /*
     * Crea (si no esta creat ja) el label, i hi escriu el valor de la cel·la
     * Es fa per separat de la creadora, per no ralentitzar el programa
     * principal
     */
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
