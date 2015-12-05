package CapaDomini.Partida;

import CapaDomini.Tauler.HidatoController;
import CapaDomini.Rank.RankingController;
import CapaDomini.Tauler.Hidato;
import CapaDomini.Tauler.HidatoSet;
import CapaDomini.Usuari.HidatoUser;
import CapaDomini.Usuari.HidatoUserController;
import CapaDomini.Tauler.SolverController;

/**
 * Controlador de totes les partides. Quan la vista vol crear/recuperar una partida,
 * aquest controlador li proporciona el controlador de partida corresponent. Hi ha
 * un d'aquests controladors per cada usuari loguejat -> si es fa logout i login
 amb un altre usuari, s'ha de crear un altre objecte GameManagerController
 * @author Pau Surrell
 */

public class GameManagerController {

    /**
     * Conte tots els hidatos del repositori
     */
    private final HidatoSet hidatoSet;
    
    /**
     * Usuari loguejat
     */
    private final HidatoUser loggedUser;
    
    /**
     * Controlador de la capa de persistencia
     */
    private final GameDBController ctrDBGame;
    
    /**
     * SolverController, conte els metodes per resoldre hidatos
     */
    private final SolverController solver;
    
    /**
     * Controlador de ranking
     */
    private final RankingController ctrRanking;
    
    /**
     * Controlador d'usuari
     */
    private final HidatoUserController hidatoUserController;
    
    /**
     * Creadora
     * @param hidatoSet conjunt amb tots els hidatos (taulers)
     * @param ctrDBGame controlador de la DB, per comunicar-se amb la capa de persistencia
     * @param solver solver per poder resoldre hidatos
     * @param ctrRanking controlador del ranking per poder enviar-li entrades
     * @param hidatoUserController controlador d'usuari per modificar els parametres de l'usuari loguejat
     */
    public GameManagerController(HidatoSet hidatoSet, GameDBController ctrDBGame, SolverController solver, RankingController ctrRanking, HidatoUserController hidatoUserController){
        this.hidatoSet = hidatoSet;
        this.hidatoUserController = hidatoUserController;        
        this.loggedUser = (HidatoUser) hidatoUserController.getLoggedUser();
        this.ctrDBGame = ctrDBGame;
        this.solver = solver;
        this.ctrRanking = ctrRanking;
    }
    
    /**
     * Crea una partida
     * @param name Nom de la partida creada
     * @param solvedHidato Hidato de la partida (Ha d'estar resolt)
     * @param help Nivell d'ajuda (LOW/MEDIUM/HIGH)
     * @return el controlador de la partida creada
     */
    public CurrentGameController createGame(String name, Hidato solvedHidato, Help help){
        Boolean error = false;
        Game game_aux = ctrDBGame.getGame(name, loggedUser);
        if (game_aux != null) {
            error = true;
        }
        if (solvedHidato == null) {
            error = true;
        }
        if (error) return null;
        Hidato hidato = new Hidato(solvedHidato);
        HidatoController ctrHidato = new HidatoController(hidato);
        ctrHidato.setBlankCellsToZero();
        Game game = new Game(name, hidato, solvedHidato, loggedUser, help, hidato.getDifficulty());
        CurrentGameController ctrCurrentGame = new CurrentGameController(game, ctrDBGame, solver, ctrRanking, hidatoUserController);
        
        return ctrCurrentGame;
    }
    
    /**
     * Recupera una partida que s'havia guardat amb nom 'name'
 NOTA: com que GameDBController actualment es un stub, no guarda mai cap partida, 
 i per tant les partides no es poden recuperar
     * @param name nom de la partida a recuperar
     * @return el controlador de la partida recuperada
     */
    public CurrentGameController restoreGame(String name){
        Game game = ctrDBGame.getGame(name, loggedUser);
        ctrDBGame.deleteGame(name, loggedUser);
        if (game == null) return null;
        CurrentGameController ctrCurrentGame = new CurrentGameController(game, ctrDBGame, solver, ctrRanking, hidatoUserController);
        return ctrCurrentGame;
    }
    
    /**
     * Elimina la partida del repositori amb nom 'name' i usuari 'loggedUser'
     * NOTA: com que CtrDBgame actualment es un stub, no guarda mai cap partida,
     * per tant mai eliminara res
     * @param name nom del hidato a eliminar
     * @return 0
     */
    public int deleteGame(String name){
        ctrDBGame.deleteGame(name, loggedUser);
        return 0;
    }
}
