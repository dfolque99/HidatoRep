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
    
    private Hidato h;
    
    public HidatoGenerator(int sizeX, int sizeY) {
        this.h = new Hidato(sizeX, sizeY);
    }
    public HidatoGenerator(Hidato h) {
        this.h = new Hidato(h);
    }
    
    
    /*
        completa el hidato h a un complet
        retorna null si no hi ha casella inicial o si es impossible de generar
    */
    public Hidato generateHidato(String difficulty) {
        if (noCasellaInicial()) return null;
        
        return h;
    }
    
    
    
    
    /*============================== PRIVADES ================================*/
    
    private boolean noCasellaInicial() {
        boolean ret = true;
        for (int i = 0; i < h.getSizeY(); ++i){
            for (int j = 0; j < h.getSizeX(); ++j) {
                if (h.getCell(i,j).getVal() == 1) ret = false;
            }
        }
        return ret;
    }
    
    
}
