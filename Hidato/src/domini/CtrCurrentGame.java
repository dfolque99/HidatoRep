package domini;

import java.util.ArrayList;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pau Surrell
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
    
    public CtrCurrentGame(Game game,CtrDBGame ctrDBGame, Solver solver){
        this.game = game;
        this.ctrDBGame = ctrDBGame;
        this.ctrHidato = new CtrHidato(game.getHidato());
        this.solver = solver;
    }

    
    public boolean check(){
        game.incrementChecksMade();
        Hidato hidato = game.getHidato();
        return solver.solve(hidato);
    }
    
    
    public ArrayList<Integer> requestHint(){
        game.incrementHints();
        Hidato hidato = game.getHidato();
        return solver.getHint(hidato);
    }
    
    
    public int putValue(int value, int x, int y){
        Hidato hidato = ctrHidato.getHidato();
        game.incrementChangesMade();
        hidato.getCell(x,y).setVal(value); //CUIDAO
        Help helpAux = game.getHelp();
        if (helpAux == Help.HIGH){
            if (!solver.solve(hidato)){
                return -1; // -1 = EL HIDATO NO TE SOLUCIO
            }else return 0;
        }else if (helpAux == Help.MEDIUM){
            if (ctrHidato.AreCellsContiguous(value, value+1) && ctrHidato.AreCellsContiguous(value, value-1)){
                return 0;
            }else return -2; // -2 = HI HA DOS NOMBRES CONSECUTIUS SEPARATS
            
            // COM MIRO SI HE POSAT DOS NOMBRES SEGUITS SEPARATS?
        }
        return 0;
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
        pause();
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
        unpause();
        return 0;
    }
    
    
}
