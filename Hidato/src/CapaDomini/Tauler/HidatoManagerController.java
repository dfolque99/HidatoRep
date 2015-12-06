/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CapaDomini.Tauler;


import CapaPersistencia.HidatoSetDBController;
import CapaDomini.Partida.GameManagerController;
import CapaDomini.Partida.CurrentGameController;
import CapaDomini.Partida.Help;
import CapaDomini.Usuari.HidatoUser;
import CapaDomini.Usuari.HidatoUserController;

/**
 *
 * @author David
 */

public class HidatoManagerController {
    
    /**
     * Controlador HidatoSet al qual enviar queries
     */
    HidatoSet hset;
  
    /**
     * Controlador GameManagerController per enviar queries
     */
    GameManagerController cgm;
    
    /**
     * Controlador HidatoUserController per enviar queries
     */
    HidatoUserController uc;
    
    /**
     * Hidato sobre el qual anirem aplicant els canvis
     */
    Hidato tempHidato;
    
    /**
     * Creadora amb parametres
     * @param hset
     * @param cgm
     */
    public HidatoManagerController (HidatoSet hset, GameManagerController cgm, HidatoUserController uc) {
        this.hset = hset;
        this.cgm = cgm;
        this.uc = uc;
    }
    
    /**
     * Pre: sizeX,sizeY >= 0, difficulty != null
     * Post: crea un hidato aleatori i el carrega a tempHidato
     */
    public void createRandom (int sizeX, int sizeY) {
        GeneratorController hg = new GeneratorController();
        tempHidato = hg.generateHidato(sizeX, sizeY);
    }
    
    /**
     * Pre: sizeX,sizeY >= 0
     * Post: inicialitza tempHidato amb sizeX x sizeY (buit: nomes extrems)
     */
    public void createManual (int sizeX, int sizeY) {
        tempHidato = new Hidato (sizeX, sizeY);
    }
    
    /**
     * Pre: name != null
     * Post: si no existeix un hidato a hset amb nom name, retorna false
     *      si existeix, el carrega a tempHidato i retorna true
     */
    public boolean loadHidato (String name) {
        Hidato obert = hset.getHidatoByName(name);
        if (obert == null) return false;
        tempHidato = new Hidato(obert);
        return true;
    }
    
    /**
     * Pre: name != null
     * Post: retorna true si existeix un Hidato anomenat name a hset
     */
    public boolean usedName (String name) {
        return (hset.getHidatoByName(name) != null);
    }
    
    /**
     * Pre: name,help!= null
     * Post: retorna un CurrentGameController per jugar al hidato amb nom name
     *      del hset; si no existeix aquest hidato, retorna null
     */
    public CurrentGameController playTempHidato(String name, Help help) {
        if (hset.getHidatoByName(name)== null) return null;
        return cgm.createGame(name, tempHidato, help);
    }
    
    /**
     * Pre: cert
     * Post: si tempHidato te solucio, la posa a tempHidato i retorna true;
     *      sino, retorna false
     */
    public boolean solveTempHidato() {
        SolverController solver = new SolverController();
        boolean teSolucio = solver.solve(tempHidato);
        if (!teSolucio) return false;
        tempHidato = solver.getHidato();
        return true;
    }
    
    public boolean validateTempHidato() {
        SolverController solver = new SolverController();
        boolean teSolucio = solver.solve(tempHidato);
        if (!teSolucio) return false;
        return true;
    }
    
    /**
     * Pre: name != null
     * Post: guarda el tempHidato a hset amb nom name (sobreescriu si cal)
     */
    public void saveTempHidato(String name) {
        Hidato guardat = new Hidato(tempHidato);
        guardat.setBoardName(name);
        if (hset.getHidatoByName(name) == null) {
            HidatoUser user = (HidatoUser) uc.getLoggedUser();
            user.addHidato(name);
            user.incrementTotalCreatedBoards();
        }
        hset.addHidato(guardat);
    }
    
    /**
     * Pre: difficulty != null
     * Post: si tempHidato es pot completar, ho fa a tempHidato i retorna true;
     *      sino, retorna false
     */
    public boolean completeTempHidato () {
        GeneratorController hg = new GeneratorController();
        Hidato completat = hg.generateHidato(tempHidato);
        if (completat == null ) return false;
        tempHidato = completat;
        return true;
    }
    
    /**
     * Pre: cert
     * Post: guarda l'estat de hset al disc
     * @return 0
     */
    public int saveAll() {
        HidatoSetDBController.saveAll(hset);
        return 0;
    }
    
    /**
     * Pre: cert
     * Post: carrega l'estat de hset del disc
     * @return 0
     */
    public int loadAll() {
        hset = HidatoSetDBController.loadAll();
        return 0;        
    }
    
    /**
     * Getters i setters sobre el hidato tempHidato
     */
    
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