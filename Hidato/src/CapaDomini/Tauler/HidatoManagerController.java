/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CapaDomini.Tauler;


import CapaPersistencia.HidatoSetDBController;
import CapaDomini.Partida.GameManagerController;
import CapaDomini.Partida.CurrentGameController;
import CapaDomini.Partida.Difficulty;
import CapaDomini.Partida.Help;
import CapaDomini.Usuari.HidatoUser;
import CapaDomini.Usuari.HidatoUserController;
import java.util.ArrayList;

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
    GameManagerController gmc;
    
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
    public HidatoManagerController (HidatoSet hset, HidatoUserController uc) {
        this.hset = hset;
        this.uc = uc;
    }
    
    public ArrayList<String> getUserHidatoList() {
        return ((HidatoUser) uc.getLoggedUser()).getCreatedHidatos();
    }
    
    public ArrayList<String> getAllHidatoList() {
        ArrayList<String> llista = new ArrayList<>();
        int total = hset.getTotalHidatos();
        for (int i = 0; i < total; ++i){
            llista.add(hset.getHidatoByPos(i).getBoardName());
        }
        return llista;
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
    public CurrentGameController playTempHidato(Help help) {
        return gmc.createGameFromBoard(null, tempHidato, help);
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
        tempHidato = solver.getHidato(tempHidato);
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
        HidatoUser user = (HidatoUser) uc.getLoggedUser();
        guardat.setBoardName(name);
        guardat.setUsername(user.getUsername());
        if (hset.getHidatoByName(name) == null) {
            user.addHidato(name);
            user.incrementTotalCreatedBoards();
            uc.updateUser();
            hset.addHidato(guardat);
        }
        else {
            hset.replaceHidatoByName(name, guardat);
        }
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
    
    public boolean tempHasFinal() {
        HidatoController hc = new HidatoController(tempHidato);
        return hc.hasFinal();
    }
    
    public boolean tempHasOrigin() {
        HidatoController hc = new HidatoController(tempHidato);
        return hc.hasOrigin();
    }
    
    public void calcTempDifficulty () {
        DifficultyController dc = new DifficultyController();
        tempHidato.setDifficulty(dc.getDifficulty(tempHidato));
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
    
    
    public void setGameManagerController (GameManagerController gmc) {
        this.gmc = gmc;
    }
    
    public Hidato getHidatoByName (String name) {
        return hset.getHidatoByName(name);
    }
    
    public Hidato getHidatoByPos (int pos) {
        return hset.getHidatoByPos(pos);
    }
    
    
    /**
     * Getters i setters sobre el hidato tempHidato
     */
    
    public String getTempBoardName () {
        return tempHidato.getBoardName();
    }
    
    public String getTempUsername () {
        return tempHidato.getUsername();
    }
    
    public String getTempDifficulty () {
        if (tempHidato.getDifficulty() == null) return "";
        return tempHidato.getDifficulty().toString();
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
    
    public void veure() {
        System.out.printf("%d hidatos:\n", hset.getTotalHidatos());
        hset.veure();
    }
}
