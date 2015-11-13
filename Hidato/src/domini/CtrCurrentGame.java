package domini;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pau
 */
public class CtrCurrentGame {
    /**
     * Stores the current game
     */
    private final Game game;  
    private CtrDBGame ctrDBGame;
    private Solver solver;
    private CtrHidato ctrHidato;
    private double time0;
    private double time1;
    
    public CtrCurrentGame(Game game,CtrDBGame ctrDBGame, Solver solver, CtrHidato ctrHidato){
        this.game = game;
        this.ctrDBGame = ctrDBGame;
        this.ctrHidato = ctrHidato;
        this.solver = solver;
    }

    
    public boolean check(){
        game.incrementChecksMade();
        Hidato hidato = game.getHidato();
        return solver.solve(hidato);
    }
    
    
    public int[] requestHint(){
        game.incrementHints();
        Hidato hidato = game.getHidato();
        return solver.getHint(hidato);
    }
    
    
    public int putValue(int value, int x, int y){
        Hidato hidato = game.getHidato();
        game.incrementChangesMade();
        hidato.setValue(value, x, y);
        Help helpAux = game.getHelp();
        if (helpAux == "HIGH"){
            if (!solver.solve(hidato)){
                return -1; // -1 = EL HIDATO NO TE SOLUCIO
            }else return 0;
        }else if (helpAux == "MEDIUM"){
            // COM MIRO SI HE POSAT DOS NOMBRES SEGUITS SEPARATS?
        }
    }
    
    public int pause(){
        double time1 = System.currentTimeMillis();
        game.incrementDuration(time1 - time0);
        return 0;
    }
    
    public int unpause(){
        time0 = System.currentTimeMillis();
        return 0;
    }
    
    public int saveGame(){
        ctrDBGame.saveGame(game);
        return 0;
    }
    
    /**
     * Updates statistics, after the game is finished
     * @return 
     */
    public int finishGame(){
        double time1 = System.currentTimeMillis();
        game.incrementDuration(time1 - time0);
        User user_aux = game.getUser();
        user_aux.IncrementSolvedHidatos();
        user_aux.IncrementTimePlayed(game.getDuration());
        user_aux.IncrementTotalScore(calculateScore());
        return 0;
    }
    
    public int calculateScore(){
        return 9001;
    }
    
    public int initialize(){
        game.getUser().IncrementStartedHidatos();
    }
    
    
}
