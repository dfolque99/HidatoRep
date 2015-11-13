package domini;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pau Surrell
 */


/**
 * Un d'aquests controladors per cada usuari loguejat -> si es fa logout i login
 * amb un altre usuari, s'ha de crear un altre objecte CtrGameManager
 */
public class CtrGameManager {
    private final HidatoSet hidatoSet;
    private User loggedUser;
    private final CtrDBGame ctrDBGame;
    private final Solver solver;
    private CtrHidato ctrHidato;
    
    public CtrGameManager(HidatoSet hidatoSet, User loggedUser, CtrDBGame ctrDBGame, Solver solver, CtrHidato ctrHidato){
        this.hidatoSet = hidatoSet;
        this.loggedUser = loggedUser;
        this.ctrDBGame = ctrDBGame;
        this.solver = solver;
        this.ctrHidato = ctrHidato;
    }
    
    public CtrCurrentGame createGame(String name, String hidatoName, String help, String difficulty){
        Hidato hidato = hidatoSet.getHidatoByName(hidatoName);
        Game game = new Game(name, hidato, loggedUser, help, difficulty);
        CtrCurrentGame ctrCurrentGame = new CtrCurrentGame(game, ctrDBGame, solver, ctrHidato); //qui passa el ctr hidato?
        return ctrCurrentGame;
    }
  
    public CtrCurrentGame restoreGame(String name){
        Game game = ctrDBGame.getGame(name, loggedUser);
        ctrDBGame.deleteGame(name, loggedUser);
        CtrCurrentGame ctrCurrentGame = new CtrCurrentGame(game, ctrDBGame, solver, ctrHidato);
        return ctrCurrentGame;
    }
    
    public int deleteGame(String name){
        ctrDBGame.deleteGame(name, loggedUser);
        return 0;
    }
}
