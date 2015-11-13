/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domini;

/**
 *
 * @author David
 */

public class Hidato extends Board {
    
    public Hidato() {
        
    }
    
    //nomes pel main
    public Hidato (String name) {
        super.setBoardName(name);
    }
    
    public Hidato (int sizeX, int sizeY) {
        super (sizeX,sizeY);
    }
    
    public Hidato (Hidato h) {
        super(h);
    }
}
