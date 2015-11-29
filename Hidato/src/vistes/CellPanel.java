/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistes;

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
    
    CellPanel(int i, int j){
        super();
        posx = i;
        posy = j;
        //value.setText("");
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