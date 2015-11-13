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

public class HidatoManager {
    
    HidatoSet hset;
    Hidato tempHidato;
    
    public HidatoManager (HidatoSet hset) {
        this.hset = hset;
    }
    
    // crea un hidato aleatori i el deixa a tempHidato
    public void createRandom (int sizeX, int sizeY, String difficulty) {
        HidatoGenerator hg = new HidatoGenerator(sizeX, sizeY);
        Hidato newH = hg.generateHidato(difficulty);
        
    }
    
    // inicialitza tempHidato amb sizeX x sizeY, i retorna el seu editor
    public void createManual (int sizeX, int sizeY) {
        tempHidato = new Hidato (sizeX, sizeY);
    }
    
    // pre: existeix un hidato amb nom name
    // fa una copia a tempHidato amb les dades del que te nom name, retorna l'editor
    public void modifyHidato (String name) {
        tempHidato = new Hidato(hset.getHidatoByName(name));
    }
    
    public boolean usedName (String name) {
        return (hset.getHidatoByName(name) != null);
    }
    
    /** coses que es poden fer amb el tempHidato:
     * jugar-lo
     * resoldre'l
     * guardar-lo
     */
    
    public void playTempHidato() {
        
    }
    
    public void solveTempHidato() {
        
    }
    
    // pre: si name != null, no es el nom de cap hidato existent
    // si name == null, guarda'l amb el mateix nom, sino guardal amb el nom name
    public void saveTempHidato(String name) {
        if (name == null) {
            hset.replaceHidatoByName(tempHidato.getBoardName(), tempHidato);
        }
        else {
            tempHidato.setBoardName(name);
            hset.addHidato(tempHidato);
        }
    }
    
    // agafa el tempHidato i el completa (pel cas d'us crea amb limitacions)
    public void completeTempHidato (/* limitacions */) {
        // falten coses!
    }
    
    public int getTempCellVal (int x, int y) {
        return tempHidato.getCell(x, y).getVal();
    }
    
    public String getTempCellType (int x, int y) {
        return tempHidato.getCell(x, y).getType();
    }
    
    public int getTempSizeX () {
        return tempHidato.getSizeX();
    }
    
    public int getTempSizeY () {
        return tempHidato.getSizeY();
    }
    
    public void setTempCellVal (int x, int y, int val) {
        tempHidato.getCell(x, y).setVal(val);
    }
    
    public void setTempCellType (int x, int y, String type) {
        tempHidato.getCell(x, y).setType(type);
    }
    
    // nomes pel main
    public void dibuixaTots() {
        for (int k = 0; k < hset.getTotalHidatos(); ++k) {
            Hidato h = hset.getHidatoByPos(k);
            System.out.printf("Hidato %s:\n", h.getBoardName());
            int n = h.getSizeY();
            int m = h.getSizeX();
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    System.out.printf("%d ", h.getCell(j,i).getVal());
                }
                System.out.print("\n");
            }
        }
    }
    
}