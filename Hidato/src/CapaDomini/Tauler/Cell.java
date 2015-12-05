/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Tauler;

import java.io.Serializable;


/**
 *
 * @author David
 */
public class Cell implements Serializable{
    int val;
    Type type;
    
    /**
     * Creadora per defecte
     */
    public Cell() {
        type = Type.BLANK;
        val = 0;
    }
    
    /**
     * Creadora per deep copy
     * @param cell 
     */
    public Cell (Cell cell) {
        val = cell.getVal();
        type = cell.getType();
    }

    /**
     * Creadora amb parametres
     * @param val
     * @param type 
     */
    public Cell(int val, Type type) {
        this.val = val;
        this.type = type;
    }

    /**
     * A partir d'aquí, getters i setters
     */
    
    public int getVal() {
        return val;
    }

    public Type getType() {
        return type;
    }
    
    public void setVal (int val) {
        this.val = val;
    }
    
    public void setType (Type type) {
        this.type = type;
    }
}
