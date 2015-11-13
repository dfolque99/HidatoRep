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
public class HidatoGenerator {

    public HidatoGenerator() { }
    
    public Hidato generateHidato(int sizeX, int sizeY, String difficulty) {
        Hidato h = new Hidato(sizeX, sizeY, difficulty);
        
        
        return h;
    }
}
