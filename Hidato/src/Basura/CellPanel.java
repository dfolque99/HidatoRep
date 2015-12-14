/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Basura;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Pau
 */
public class CellPanel extends JPanel{
    private final int posx;
    private final int posy;
    private javax.swing.JLabel value;
    private final String font_nom = "Tahoma";
    private final Color blankColor = new Color(0xFFFFFF);
    private final Color hintColor = new Color(0xFFE0E0);
    private final Color voidColor = new Color(0x000000);
    private final Color hoverColor = new Color(0xDFDFDF);
    
    
    CellPanel(int i, int j){
        super();
        posx = i;
        posy = j;
        value = new javax.swing.JLabel();
        add(value);
        value.setFont(new Font(font_nom, Font.PLAIN, 20));
        setLayout(new GridBagLayout());
        //value.setText("");
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(hoverColor);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(blankColor);
            }
            
        });
    }
    
    int getPosX(){
        return posx;
    }
    
    int getPosY(){
        return posy;
    }
    
    int getValue(){
        return Integer.parseInt(value.getText());
    }
    
    void setValue(int s){
            value.setText(Integer.toString(s));
    }
    
    
    
}