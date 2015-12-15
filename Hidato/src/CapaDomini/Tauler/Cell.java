/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Tauler;

import java.io.Serializable;


/**
 * Representa una cel·la d'un hidato. Classe de dades de domini.
 * @author David
 */
public class Cell implements Serializable{
    /**
     * Valor que correspon a la cel·la.
     * Si la casella és de tipus GIVEN, sempre serà el valor que li correspon.
     * Si la casella és de tipus VOID, és indiferent aquest paràmetre.
     * Si la casella és de tipus BLANK, podrà tenir diferents valors:
     * * Si està en un hidato al repositori, tindrà el valor d'una possible
     * solució.
     * * Si està en un hidato en partida, tindrà el valor que l'usuari li hagi
     * donat (0 si encara no n'hi ha donat cap).
     * * Si està en un hidato que s'està resolent o generant, tindrà el valor
     * que l'algorisme li hagi assignat fins al moment (0 si encara no).
     */
    private int val;
    
    /**
     * Tipus de la casella.
     */
    private Type type;
    
    /**
     * Creadora per defecte
     */
    public Cell() {
        type = Type.BLANK;
        val = 0;
    }
    
    /**
     * Creadora per deep copy
     * @param cell Cel·la que es copia
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

    /*
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
