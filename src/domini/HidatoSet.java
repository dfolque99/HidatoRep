/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domini;

import java.util.ArrayList;

/**
 *
 * @author David
 */

public class HidatoSet {
    // perque funcioni tot, H no pot tenir mai dos elements amb el mateix nom
    ArrayList<Hidato> H;
    
    
    public HidatoSet() {
        H = new ArrayList<Hidato>();
    }
    
    public Hidato getHidatoByPos (int pos) {
        if (pos >= 0 && pos < H.size()) return H.get(pos);
        return null;
    }
    
    public Hidato getHidatoByName (String name) {
        for (int i = 0; i < H.size(); ++i){
            if (H.get(i).getBoardName().equals(name)) return H.get(i);
        }
        return null;
    }
    
    public int getTotalHidatos() {
        return H.size();
    }
    
    public void deleteHidatoByPos (int pos){
        if (pos >= 0 && pos < H.size()) H.remove(pos);
    }
    
    public void deleteHidatoByName (String name) {
        for (int i = 0; i < H.size(); ++i){
            if (H.get(i).getBoardName().equals(name)) H.remove(i);
        }
    }
    
    public void addHidato (Hidato h) {
        H.add(h);
    }
    
    public void replaceHidatoByName (String name, Hidato h) {
        System.out.printf("Guardant %s a la llista :\n", name);
        for (int i = 0; i < H.size(); ++i){
            System.out.printf("%s\n",H.get(i).getBoardName());
            if (H.get(i).getBoardName().equals(name)) {
                System.out.printf("Coincidencia trobada\n");
                H.set(i,h);
            }
        }
    }
    
    // nomes pel main
    public void setHidatos (ArrayList<Hidato> H) {
            this.H = H;
    }
}
