package domini;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Controlador de totes les partides. Quan la vista vol crear/recuperar una partida,
 * aquest controlador li proporciona el controlador de partida corresponent. Hi ha
 * un d'aquests controladors per cada usuari loguejat -> si es fa logout i login
 * amb un altre usuari, s'ha de crear un altre objecte CtrGameManager
 * @author Pau Surrell
 */

public class CtrGameManager {
    /**
     * Declara els parametres i controladors de la classe
     */
    private final HidatoSet hidatoSet;
    private final HidatoUser loggedUser;
    private final CtrDBGame ctrDBGame;
    private final Solver solver;
    private final CtrRanking ctrRanking;
    
    /**
     * Creadora del controlador
     * @param hidatoSet conjunt amb tots els hidatos (taulers)
     * @param loggedUser usuari loguejat
     * @param ctrDBGame controlador de la DB, per comunicar-se amb la capa de persistencia
     * @param solver solver per poder resoldre hidatos
     * @param ctrRanking controlador del ranking per poder enviar-li entrades
     */
    public CtrGameManager(HidatoSet hidatoSet, HidatoUser loggedUser, CtrDBGame ctrDBGame, Solver solver, CtrRanking ctrRanking){
        this.hidatoSet = hidatoSet;
        this.loggedUser = loggedUser;
        this.ctrDBGame = ctrDBGame;
        this.solver = solver;
        this.ctrRanking = ctrRanking;
    }
    
    /**
     * Crea una partida
     * @param name Nom de la partida
     * @param hidato tauler sobre el que es crea la partida
     * @param help Nivell d'ajuda
     * @return el controlador de la partida creada
     */
    
    public CtrCurrentGame createGame(String name, Hidato hidato, Help help){
        Boolean error = false;
        Game game_aux = ctrDBGame.getGame(name, loggedUser);
        if (game_aux != null) {
            System.out.println("ja existeix partida amb aquest nom: "+name);
            error = true;
        }
        if (hidato == null) {
            System.out.println("el hidato passat es nul");
            error = true;
        }
        if (error) return null;
        Game game = new Game(name, hidato, loggedUser, help, hidato.getDifficult());
        CtrCurrentGame ctrCurrentGame = new CtrCurrentGame(game, ctrDBGame, solver, ctrRanking);
        
        return ctrCurrentGame;
    }
    /**
     * Recupera una partida que s'havia guardat amb nom 'name'
     * @param name
     * @return el controlador de la partida recuperada
     */
    public CtrCurrentGame restoreGame(String name){
        Game game = ctrDBGame.getGame(name, loggedUser);
        ctrDBGame.deleteGame(name, loggedUser);
        if (game == null) return null;
        CtrCurrentGame ctrCurrentGame = new CtrCurrentGame(game, ctrDBGame, solver, ctrRanking);
        return ctrCurrentGame;
    }
    
    /**
     * Elimina la partida del repositori amb nom 'name' i usuari 'loggedUser'
     * @param name nom del hidato a eliminar
     * @return 0
     */
    
    public int deleteGame(String name){
        ctrDBGame.deleteGame(name, loggedUser);
        return 0;
    }
}
