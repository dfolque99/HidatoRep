/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domini.Tauler;

import domini.Partida.Difficulty;
import domini.Partida.GameManagerController;
import domini.Partida.CurrentGameController;
import domini.Partida.Help;

/**
 *
 * @author David
 */

public class HidatoManagerController {
    
    HidatoSet hset;
    GameManagerController cgm;
    Hidato tempHidato;
    
    public HidatoManagerController (HidatoSet hset, GameManagerController cgm) {
        this.hset = hset;
        this.cgm = cgm;
    }
    
    // crea un hidato aleatori i el deixa a tempHidato
    public void createRandom (int sizeX, int sizeY, Difficulty difficulty) {
        GeneratorController hg = new GeneratorController(sizeX, sizeY);
        tempHidato = hg.generateHidato(difficulty);
    }
    
    // inicialitza tempHidato amb sizeX x sizeY
    public void createManual (int sizeX, int sizeY) {
        tempHidato = new Hidato (sizeX, sizeY);
    }
    
    // pre: existeix un hidato amb nom name
    // fa una copia a tempHidato amb les dades del que te nom name, retorna l'editor
    public void loadHidato (String name) {
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
    
    public CurrentGameController playTempHidato(String name, Help help) {
        return cgm.createGame(name, tempHidato, help);
    }
    
    public boolean solveTempHidato() {
        SolverController solver = new SolverController();
        return solver.solve(tempHidato);
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
    public void completeTempHidato (Difficulty difficulty) {
        GeneratorController hg = new GeneratorController(tempHidato);
        tempHidato = hg.generateHidato(difficulty);
    }
    
    public int getTempCellVal (int x, int y) {
        return tempHidato.getCell(x, y).getVal();
    }
    
    public Type getTempCellType (int x, int y) {
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
    
    public void setTempCellType (int x, int y, Type type) {
        tempHidato.getCell(x, y).setType(type);
    }
    
    
}