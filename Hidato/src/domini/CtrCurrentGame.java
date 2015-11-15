package domini;
import java.util.ArrayList;

/**
 * Controlador d'una partida: cada partida te assignada una instancia de CtrCurrentGame,
 * que s'ocupa de comunicar la capa de vista i els altres controladors amb la partida.
 * @author Pau Surrell
 */
public class CtrCurrentGame {

    /**
     * Partida sobre la qual el controlador actuara
     */
    private final Game game;
    
    /**
     * Controlador de DB de game, de la capa de persistencia
     */
    private final CtrDBGame ctrDBGame;
    
    /**
     * Solver, te els metodes per resoldre hidatos
     */
    private final Solver solver;
    
    /**
     * Controlador de hidato, te metodes per modificar hidatos
     */
    private CtrHidato ctrHidato;
    
    /**
     * Controlador de ranking, te metodes per modificar el ranking
     */
    private final CtrRanking ctrRanking;
    
    /**
     * Controlador d'usuari, te metodes per modificar dades de l'usuari
     */
    private final HidatoUserController hidatoUserController;
    
    /**
     * Variable auxiliar utilitzada per calcular la durada de la partida
     */
    private long time0;
    
    /**
     * Creadora
     * Crea un controlador a partir d'un game, un CtrDBGame (que permet la comunicacio
     * amb la capa de persistencia), un solver, un controlador de ranking  i un
     * controlador d'usuari
     * @param game partida assignada a aquest controlador
     * @param ctrDBGame controlador de la DB per comunicar-se amb la capa de persistencia
     * @param solver solver per poder resoldre els hidatos
     * @param ctrRanking controlador del ranking per enviar-li les dades de la partida acabada
     * @param hidatoUserController controlador de l'usuari que juga la partida
     */    
    public CtrCurrentGame(Game game, CtrDBGame ctrDBGame, Solver solver, CtrRanking ctrRanking, HidatoUserController hidatoUserController){
        this.game = game;
        this.ctrDBGame = ctrDBGame;
        this.ctrHidato = new CtrHidato(game.getHidato());
        this.solver = solver;
        this.ctrRanking = ctrRanking;
        this.hidatoUserController = hidatoUserController;
    }

    /**
     * Getter de la partida del controlador
     * @return la partida del controlador
     */
    public Game getGame(){
        return this.game;
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
     * @return 0 si s'ha colocat la pista, 
     * -1 si no s'ha colocat degut a que el hidato no tenia solucio
     */
    public int requestHint(){
        game.incrementHints();
        Hidato hidato = ctrHidato.getHidato();
        ArrayList<Integer> hint = solver.getHint(hidato); //x y value
        if (hint == null) return -1; //-1 = el hidato no te solucio
        int x = hint.get(0);
        int y = hint.get(1);
        int value = hint.get(2);
        Cell cell = hidato.getCell(x,y);
        cell.setVal(value);
        return 0;
    }
    
    /**
     * Intenta colocar el valor 'value' a la posicio (x,y) del hidato
     * @param value el valor de la cell
     * @param x posicio x de la cell en el hidato
     * @param y posicio y de la cell en el hidato
     * @return 0 si s'ha colocat el valor correctament, 
     * -1 si el nivell d'ajuda era alt i el hidato no te solucio, 
     * -2 si el nivell d'ajuda es mitja i hi ha dos nombres consecutius separats en el hidato, 
     * -3 si el valor esta fora del rang de cells, 
     * -4 si ja esta colocada la cell amb el valor donat,
     * -5 si la posicio donada no es una cell valida
     */
    public int putValue(int value, int x, int y){
        Hidato hidato = ctrHidato.getHidato();
        if (value < 0 || value > ctrHidato.countValidCells()) return -3;
        if (ctrHidato.getCellPositionFromValue(value,0) != -1 && value != 0) return -4;
        if (x < 0 || x >= hidato.getSizeX()) return -5;
        if (y < 0 || y >= hidato.getSizeY()) return -5;
        if (hidato.getCell(x, y).getType() != Type.BLANK) return -5;
        
        game.incrementChangesMade();
        hidato.getCell(x,y).setVal(value);
        Help helpAux = game.getHelp(); 
        //controlar que la cell sigui valida
        if (helpAux == Help.HIGH){
            if (!solver.solve(hidato)){
                hidato.getCell(x,y).setVal(0);
                return -1; // -1 = EL HIDATO NO TE SOLUCIO
            }else return 0;
        }else if (helpAux == Help.MEDIUM){

            if (ctrHidato.AreCellsContiguous(value, value+1) && ctrHidato.AreCellsContiguous(value, value-1)){
                    game.incrementChangesMade();
                    hidato.getCell(x,y).setVal(value);
                return 0;
            }else {
                hidato.getCell(x,y).setVal(0);
                return -2;
            } // -2 = HI HA DOS NOMBRES CONSECUTIUS SEPARATS
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
     * Reinicia la partida (torna el hidato i les estadistiques tal com estaven al principi)
     * @return 0
     */
    public int restartGame(){
        game.restartGame();
        this.ctrHidato = new CtrHidato(game.getHidato());
        return 0;
    }
    
    /**
     * Resol la partida automaticament
     * @return 0
     */
    public int solve(){
        game.solve();
        this.ctrHidato = new CtrHidato(game.getHidato());
        return 0;
    }
    
    /**
     * Guarda la partida al repositori
     * NOTA: com que ctrDBGame actualment es un stub, la partida es eliminada i no es guarda enlloc
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
        
        HidatoUser user = game.getUser();
        user.incrementSolvedGames();
        user.incrementTotalTimePlayed(game.getDuration());
        user.updateBestTime(game.getDuration());
        String username = user.getUsername();
        
        int score = calculateScore();
        user.incrementTotalScore(score);
        user.updateBestScore(score);
        
        hidatoUserController.updateUser();
        
        ctrRanking.addScoreToRanking(score, username, game.getDifficulty());
        return 0;
    }
    
    /**
     * Calcula la puntuacio de la partida (val 0 si s'ha utilitzat el solve per acabar-la)
     * @return la puntuacio
     */
    public int calculateScore(){
        if (!game.getLegitSolved()) return 0;
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
        game.getUser().incrementStartedGames();
        hidatoUserController.updateUser();
        unpause();
        return 0;
    }
}
