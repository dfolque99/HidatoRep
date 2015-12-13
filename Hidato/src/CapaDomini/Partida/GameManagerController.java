package CapaDomini.Partida;

import CapaPersistencia.GameDBController;
import CapaDomini.Tauler.HidatoController;
import CapaDomini.Rank.RankingController;
import CapaDomini.Tauler.DifficultyController;
import CapaDomini.Tauler.GeneratorController;
import CapaDomini.Tauler.Hidato;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Tauler.HidatoSet;
import CapaDomini.Usuari.HidatoUser;
import CapaDomini.Usuari.HidatoUserController;
import CapaDomini.Tauler.SolverController;
import java.util.ArrayList;
import java.util.Random;

/**
 * Controlador de totes les partides. Quan la vista vol crear/recuperar una partida,
 * aquest controlador li proporciona el controlador de partida corresponent. Hi ha
 * un d'aquests controladors per cada usuari loguejat -> si es fa logout i login
 amb un altre usuari, s'ha de crear un altre objecte GameManagerController
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
    
    private GameSet gameSet;
    
    private HidatoSet hidatoSet;
    
    private HidatoManagerController hidatoManagerController;
    
    /**
     * Creadora
     * @param hidatoSet conjunt amb tots els hidatos (taulers)
     * @param ctrDBGame controlador de la DB, per comunicar-se amb la capa de persistencia
     * @param solver solver per poder resoldre hidatos
     * @param ctrRanking controlador del ranking per poder enviar-li entrades
     * @param hidatoUserController controlador d'usuari per modificar els parametres de l'usuari loguejat
     */
    public GameManagerController(RankingController ctrRanking, HidatoUserController hidatoUserController, HidatoManagerController hmc){
        this.hidatoUserController = hidatoUserController;
        this.ctrDBGame = new GameDBController();
        this.ctrRanking = ctrRanking;
        this.gameSet = new GameSet();
        this.hidatoSet = hidatoSet;
        this.hidatoManagerController = hmc;
    }
    
    public void initGameSet(){
        this.gameSet = new GameSet(this.ctrDBGame.getAllGames(this.hidatoUserController.getLoggedUser().getUsername()));
    }
    
    public void saveGameSet(){
        for (int i = 0; i < gameSet.getSize(); ++i){
            ctrDBGame.saveGame(gameSet.getGameByPos(i));
        }
    }
    
    public HidatoManagerController getHMC(){
        return hidatoManagerController;
    }
    
    public CurrentGameController createRandomGame(String name, Help help){
        //Si ja existeix una partida amb aquell nom, no fa res
        HidatoUser loggedUser = (HidatoUser) hidatoUserController.getLoggedUser();
        Game game_aux = gameSet.getGameByName(name);
        if (game_aux != null) return null;
        
        //x, y aleatoris entre 3 i 8 (inclosos)
        Random rand = new Random();
        int x = rand.nextInt(5)+3;
        int y = rand.nextInt(5)+3;
        GeneratorController gc = new GeneratorController();
        Hidato solvedHidato = gc.generateHidato(x, y);
        Hidato hidato = new Hidato(solvedHidato);
        HidatoController ctrHidato = new HidatoController(hidato);
        ctrHidato.setBlankCellsToZero();
        DifficultyController diffController = new DifficultyController();
        Game game = new Game(name, hidato, solvedHidato, loggedUser.getUsername(), help, diffController.getDifficulty(hidato), true);
        CurrentGameController ctrCurrentGame = new CurrentGameController(game, gameSet, ctrRanking, hidatoUserController);
        
        return ctrCurrentGame;
    }
    
    /**
     * Crea una partida
     * @param name Nom de la partida creada
     * @param solvedHidato Hidato de la partida (Ha d'estar resolt)
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
        Game game = new Game(name, hidato, solvedHidato, loggedUser.getUsername(), help, diffController.getDifficulty(hidato), false);
        CurrentGameController ctrCurrentGame = new CurrentGameController(game, gameSet, ctrRanking, hidatoUserController);
        
        return ctrCurrentGame;
    }
    
    public CurrentGameController createGameFromBoard(String name, Hidato solvedHidato, Help help){
        HidatoUser loggedUser = (HidatoUser) hidatoUserController.getLoggedUser();
        Game game_aux = gameSet.getGameByName(name);
        if (game_aux != null) return null;
        if (solvedHidato == null) return null;
        Hidato hidato = new Hidato(solvedHidato);
        HidatoController ctrHidato = new HidatoController(hidato);
        ctrHidato.setBlankCellsToZero();
        DifficultyController diffController = new DifficultyController();
        Game game = new Game(name, hidato, solvedHidato, loggedUser.getUsername(), help, diffController.getDifficulty(hidato), true);
        CurrentGameController ctrCurrentGame = new CurrentGameController(game, gameSet, ctrRanking, hidatoUserController);
        
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
        Game game = gameSet.getGameByName(name);
        gameSet.deleteGame(name);
        if (game == null) return null;
        CurrentGameController ctrCurrentGame = new CurrentGameController(game, gameSet, ctrRanking, hidatoUserController);
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
        gameSet.deleteGame(name);
        return 0;
    }
    
    public ArrayList<String> getGameList(){
        ArrayList<String> ret = new ArrayList<>();
        if (gameSet == null) System.out.println("gameset null");
        for(int i = 0; i < gameSet.getSize();i++){
            ret.add((gameSet.getGameByPos(i)).getName());
        }
        return ret;
    }
    
    public CurrentGameController getGame(String name){
        Game game =  gameSet.getGameByName(name);
        if (game == null) return null;
        CurrentGameController ctrCurrentGame = new CurrentGameController(game, gameSet, ctrRanking, hidatoUserController);
        return ctrCurrentGame;
    }
}
