/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domini.Tauler;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author David
 */

public class HidatoSet implements Serializable {
    
    /**
     * ArrayList on es guarden tots els hidatos del repositori
     */
    ArrayList<Hidato> H;
    
    /**
     * Creadora per defecte
     */
    public HidatoSet() {
        H = new ArrayList<Hidato>();
    }
    
    /**
     * Pre: cert
     * Post: si existeix, retorna el hidato a la posicio pos de H
     */
    public Hidato getHidatoByPos (int pos) {
        if (pos >= 0 && pos < H.size()) return H.get(pos);
        return null;
    }
    
    /**
     * Pre: cert
     * Post: si existeix, retorna el hidato de H amb nom name
     */
    public Hidato getHidatoByName (String name) {
        for (int i = 0; i < H.size(); ++i){
            if (H.get(i).getBoardName().equals(name)) return H.get(i);
        }
        return null;
    }
    
    /**
     * Pre: cert
     * Post: retorna el nombre de hidatos de H
     */
    public int getTotalHidatos() {
        return H.size();
    }
    
    /**
     * Pre: cert
     * Post: si existeix, elimina el hidato a la posicio pos de H
     */
    public void deleteHidatoByPos (int pos){
        if (pos >= 0 && pos < H.size()) H.remove(pos);
    }
    
    /**
     * Pre: cert
     * Post: si existeix, elimina el hidato amb nom name de H
     */
    public void deleteHidatoByName (String name) {
        for (int i = 0; i < H.size(); ++i){
            if (H.get(i).getBoardName().equals(name)) H.remove(i);
        }
    }
    
    /**
     * Pre: h != null
     * Post: si no existeix un hidato amb el mateix nom que h, guarda h al
     *      repositori i retorna true; sino, retorna false
     */
    public void addHidato (Hidato h) {
        if (getHidatoByName(h.getBoardName()) == null) H.add(h);
    }
    
    /**
     * Pre: h.boardname = name != null
     * Post: si existeix un hidato amb nom name, el substitueix per h
     */
    public void replaceHidatoByName (String name, Hidato h) {
        for (int i = 0; i < H.size(); ++i){
            if (H.get(i).getBoardName().equals(name)) {
                H.set(i,h);
            }
        }
    }

    /**
     * Setter de l'array de hidatos
     */
    public void setHidatos(ArrayList<Hidato> H) {
        this.H = H;
    }
    
    
}