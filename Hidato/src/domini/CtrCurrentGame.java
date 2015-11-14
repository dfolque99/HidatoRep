package domini;
//lin 73, 128
import java.util.ArrayList;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Controlador d'una partida: cada partida te assignada una instancia de CtrCurrentGame,
 * que s'ocupa de comunicar la capa de vista amb la partida.
 * @author Pau Surrell
 */
public class CtrCurrentGame {
    /**
     * Declaracio dels parametres i controladors amb els que necessita comunicar-se
     */
    private final Game game;
    private final CtrDBGame ctrDBGame;
    private final Solver solver;
    private final CtrHidato ctrHidato;
    private final CtrRanking ctrRanking;
    private long time0;
    
    
    /**
     * Creadora
     * Crea un controlador a partir d'un game, un CtrDBGame (que permet la comunicacio
     * amb la capa de persistencia) i un solver
     */    
    public CtrCurrentGame(Game game, CtrDBGame ctrDBGame, Solver solver, CtrRanking ctrRanking){
        this.game = game;
        this.ctrDBGame = ctrDBGame;
        this.ctrHidato = new CtrHidato(game.getHidato());
        this.solver = solver;
        this.ctrRanking = ctrRanking;
    }

    /**
     * Fa un check del hidato
     * @return true si el hidato te solucio, false si no en te
     */
    
    public boolean check(){
        game.incrementChecksMade();
        Hidato hidato = ctrHidato.getHidato();
        return solver.solve(hidato);
    }
    
    /**
     * Demana una pista del hidato
     * @return una array list amb la posicio i valor de la pista
     */
    public ArrayList<Integer> requestHint(){
        game.incrementHints();
        Hidato hidato = ctrHidato.getHidato();
        return solver.getHint(hidato);
    }
    
    /**
     * Intenta colocar el valor 'value' a la posicio (x,y) del hidato
     * @return 0 si s'ha colocat el valor correctament, -1 si el nivell d'ajuda 
     * era alt i el hidato no te solucio, o -2 si el nivell d'ajuda es mitja i 
     * hi ha dos nombres consecutius separats en el hidato
     */
    
    public int putValue(int value, int x, int y){
        Hidato hidato = ctrHidato.getHidato();
        game.incrementChangesMade();
        hidato.getCell(x,y).setVal(value); //CUIDAO
        Help helpAux = game.getHelp(); 
        //controlar que la cela sigui valida
        if (helpAux == Help.HIGH){
            if (!solver.solve(hidato)){
                return -1; // -1 = EL HIDATO NO TE SOLUCIO
            }else return 0;
        }else if (helpAux == Help.MEDIUM){
            if (ctrHidato.AreCellsContiguous(value, value+1) && ctrHidato.AreCellsContiguous(value, value-1)){
                return 0;
            }else return -2; // -2 = HI HA DOS NOMBRES CONSECUTIUS SEPARATS
        }else return 0;
    }
    
    /**
     * Fa una pausa en la partida (atura el temps de partida)
     * @return 0
     */
    
    public int pause(){
        long time1 = (long) System.currentTimeMillis();
        game.incrementDuration((time1 - time0));
        return 0;
    }
    
    /**
     * Torna a la partida despres d'una pausa (el temps de partida torna a comptar)
     * @return 0
     */
    public int unpause(){
        time0 = System.currentTimeMillis();
        return 0;
    }
    
    /**
     * Guarda la partida al repositori
     * @return 0
     */
    public int saveGame(){
        pause();
        ctrDBGame.saveGame(game);
        return 0;
    }
    
    /**
     * Actualitza les estadistiques de l'usuari i el ranking, quan la partida esta acabada
     * @return 0
     */
    public int finishGame(){
        long time1 = System.currentTimeMillis();
        game.incrementDuration(time1 - time0);
        
        User user = game.getUser();
        user.IncrementSolvedHidatos();
        user.IncrementTimePlayed(game.getDuration());
        String username = user.getUsername();
        
        int score = calculateScore();
        user.IncrementTotalScore(score);
        
        ctrRanking.addEntryToRanking(score, username, game.getDifficulty());
        return 0;
    }
    
    /**
     * Calcula la puntuacio de la partida
     * @return la puntuacio
     */
    public int calculateScore(){
        int checkPenalty = 60000;
        int changePenalty = 1000;
        long totalDuration = game.getDuration().toMillis() + checkPenalty*game.getChecksMade() + changePenalty * game.getChangesMade();
        long score = (long) (1e10 / totalDuration);
        return (int) score;
    }
    
    /**
     * Inicialitza la partida
     * @return 0
     */
    public int initialize(){
        game.getUser().IncrementStartedHidatos();
        unpause();
        return 0;
    }
    
    
}
