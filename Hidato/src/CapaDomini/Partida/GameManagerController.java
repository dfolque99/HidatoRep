package CapaDomini.Partida;

import CapaPersistencia.GameDBController;
import CapaDomini.Tauler.HidatoController;
import CapaDomini.Rank.RankingController;
import CapaDomini.Tauler.DifficultyController;
import CapaDomini.Tauler.Hidato;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Usuari.HidatoUser;
import CapaDomini.Usuari.HidatoUserController;
import java.util.ArrayList;

/**
 * Controlador de totes les partides. Quan la vista vol crear/recuperar una partida,
 * aquest controlador li proporciona el controlador de partida corresponent. 
 * @author Pau Surrell
 */

public class GameManagerController {
    
    /**
     * Controlador de la capa de persistencia
     */
    private final GameDBController ctrDBGame;
    
    /**
     * Controlador de ranking
     */
    private final RankingController ctrRanking;
    
    /**
     * Controlador d'usuari
     */
    private final HidatoUserController hidatoUserController;
    
    /**
     * Conjunt de partides
     */
    private GameSet gameSet;
    
    /**
     * Controlador de conjunt de hidatos
     */
    private final HidatoManagerController hidatoManagerController;
    
    /**
     * Creadora. Inicialitza tots els controladors amb els que es comunicara.
     * @param ctrRanking controlador de ranking
     * @param hidatoUserController controlador d'usuari
     * @param hidatoManagerController controlador de conjunt de hidatos
     */
    public GameManagerController(RankingController ctrRanking, HidatoUserController hidatoUserController, HidatoManagerController hidatoManagerController){
        this.hidatoUserController = hidatoUserController;
        this.ctrDBGame = new GameDBController();
        this.ctrRanking = ctrRanking;
        this.gameSet = new GameSet();
        this.hidatoManagerController = hidatoManagerController;
    }
    
    /**
     * Inicialitza el game set amb les partides de l'usuari guardades en fitxers
     */
    public void initGameSet(){
        gameSet = new GameSet(ctrDBGame.getAllGames(hidatoUserController.getLoggedUser().getUsername()));
        ctrDBGame.deleteAllGames(hidatoUserController.getLoggedUser().getUsername());
    }
    
    /**
     * Guarda el game set en fitxers. S'executa just abans de tancar el programa.
     */
    public void saveGameSet(){
        for (int i = 0; i < gameSet.getSize(); ++i){
            ctrDBGame.saveGame(gameSet.getGameByPos(i));
        }
    }
    
    /**
     * Crea una partida a partir d'un nom de tauler 
     * @param name Nom de la partida creada
     * @param hidatoName nom del hidato de la partida (el hidato ha d'existir)
     * @param help Nivell d'ajuda (LOW/MEDIUM/HIGH)
     * @return el controlador de la partida creada
     */
    public CurrentGameController createGameFromBoardName(String name, String hidatoName, Help help){
        HidatoUser loggedUser = (HidatoUser) hidatoUserController.getLoggedUser();
        Game game_aux = gameSet.getGameByName(name);
        if (game_aux != null) return null;
        Hidato solvedHidato = hidatoManagerController.getHidatoByName(hidatoName);
        if (solvedHidato == null) return null;
        Hidato hidato = new Hidato(solvedHidato);
        HidatoController ctrHidato = new HidatoController(hidato);
        ctrHidato.setBlankCellsToZero();
        DifficultyController diffController = new DifficultyController();
        String username;
        if (loggedUser == null) username = null;
        else username = loggedUser.getUsername();
        Game game = new Game(name, hidato, solvedHidato, username, help, diffController.getDifficulty(hidato), false);
        CurrentGameController ctrCurrentGame = new CurrentGameController(game, gameSet, ctrRanking, hidatoUserController);
        
        return ctrCurrentGame;
    }
    
    /**
     * Crea una partida a partir d'un tauler 
     * @param name Nom de la partida creada
     * @param solvedHidato hidato de la partida. Ha d'estar resolt, i no ha d'estar 
     * guardat al hidatoSet.
     * @param help Nivell d'ajuda (LOW/MEDIUM/HIGH)
     * @return el controlador de la partida creada
     */
    public CurrentGameController createGameFromBoard(String name, Hidato solvedHidato, Help help){
        HidatoUser loggedUser = (HidatoUser) hidatoUserController.getLoggedUser();
        if (loggedUser == null) System.out.println("user null"); 
        Game game_aux = gameSet.getGameByName(name);
        if (game_aux != null) return null;
        if (solvedHidato == null) return null;
        Hidato hidato = new Hidato(solvedHidato);
        HidatoController ctrHidato = new HidatoController(hidato);
        ctrHidato.setBlankCellsToZero();
        DifficultyController diffController = new DifficultyController();
        String username;
        if (loggedUser == null) username = null;
        else username = loggedUser.getUsername();
        Game game = new Game(name, hidato, solvedHidato, username, help, diffController.getDifficulty(hidato), true);
        CurrentGameController ctrCurrentGame = new CurrentGameController(game, gameSet, ctrRanking, hidatoUserController);
        
        return ctrCurrentGame;
    }
    
    /**
     * Recupera una partida que s'havia guardat amb nom 'name'
     * @param name nom de la partida a recuperar
     * @return el controlador de la partida recuperada. Si no existeix cap partida,
     * retorna null.
     */
    public CurrentGameController restoreGame(String name){
        Game game = new Game(gameSet.getGameByName(name));
        if (game == null) return null;
        CurrentGameController ctrCurrentGame = new CurrentGameController(game, gameSet, ctrRanking, hidatoUserController);
        return ctrCurrentGame;
    }
    
    /**
     * Elimina la partida del conjunt amb nom 'name'
     * @param name nom del hidato a eliminar
     */
    public void deleteGame(String name){
        gameSet.deleteGame(name);
    }
    
    /**
     * Getter dels noms totes les partides del gameSet
     * @return noms de les partides del gameSet
     */
    public ArrayList<String> getGameList(){
        ArrayList<String> ret = new ArrayList<>();
        for(int i = 0; i < gameSet.getSize();i++){
            ret.add((gameSet.getGameByPos(i)).getName());
        }
        return ret;
    }
    
    /**
     * Getter d'una partida del gameSet
     * @param name nom de la partida buscada
     * @return la partida amb el nom donat. Si no existeix, retorna null.
     */
    public CurrentGameController getGame(String name){
        Game game =  gameSet.getGameByName(name);
        if (game == null) return null;
        CurrentGameController ctrCurrentGame = new CurrentGameController(game, gameSet, ctrRanking, hidatoUserController);
        return ctrCurrentGame;
    }
    //Elimina tots els games del gameSet actual
    public void deleteAllGames() {
        gameSet = new GameSet();
    }
}
