package CapaDomini.Partida;
import CapaDomini.Tauler.Cell;
import CapaDomini.Tauler.HidatoController;
import CapaDomini.Rank.RankingController;
import CapaDomini.Tauler.Hidato;
import CapaDomini.Usuari.HidatoUser;
import CapaDomini.Usuari.HidatoUserController;
import CapaDomini.Tauler.SolverController;
import CapaDomini.Tauler.Type;
import java.time.Duration;
import java.util.ArrayList;

/**
 * Controlador d'una partida: cada partida te assignada una instancia de CurrentGameController,
 que s'ocupa de comunicar la capa de vista i els altres controladors amb la partida.
 * @author Pau Surrell
 */
public class CurrentGameController {

    /**
     * Partida sobre la qual el controlador actuara
     */
    private final Game game;
    
    /**
     * Controlador de DB de game, de la capa de persistencia
     */
    private final GameSet gameSet;
    
    /**
     * Controlador de hidato, te metodes per modificar hidatos
     */
    private HidatoController ctrHidato;
    
    /**
     * Controlador de ranking, te metodes per modificar el ranking
     */
    private final RankingController ctrRanking;
    
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
     * Crea un controlador a partir d'un game, un GameDBController 
     * (que permet la comunicacio amb la capa de persistencia), un solver, 
     * un controlador de ranking  i un controlador d'usuari
     * @param game partida assignada a aquest controlador
     * @param gameSet conjunt de partides 
     * @param ctrRanking controlador del ranking per enviar-li les dades de la partida acabada
     * @param hidatoUserController controlador de l'usuari que juga la partida
     */    
    public CurrentGameController(Game game, GameSet gameSet, RankingController ctrRanking, HidatoUserController hidatoUserController){
        this.game = game;
        this.gameSet = gameSet;
        this.ctrHidato = new HidatoController(game.getHidato());
        this.ctrRanking = ctrRanking;
        this.hidatoUserController = hidatoUserController;
        this.time0 = (long) System.currentTimeMillis();
        initialize();
    }
    
    /**
     * Fa un check del hidato
     * @return true si el hidato te solucio, false si no en te
     */
    public boolean check(){
        game.incrementChecksMade();
        Hidato hidato = ctrHidato.getHidato();
        return (new SolverController()).solve(hidato);
    }
    
    /**
     * Demana una pista del hidato
     * @return 0 si s'ha colocat la pista, 
     * -1 si no s'ha colocat degut a que el hidato no tenia solucio
     */
    public ArrayList<Integer> requestHint(){
        game.incrementHints();
        Hidato hidato = ctrHidato.getHidato();
        ArrayList<Integer> hint = (new SolverController()).getHint(hidato); //x y value
        if (hint == null) return null; //El hidato no te solucio
        int x = hint.get(0);
        int y = hint.get(1);
        int value = hint.get(2);
        Cell cell = hidato.getCell(x,y);
        cell.setVal(value);
        return hint;
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
            if (!(new SolverController()).solve(hidato)){
                hidato.getCell(x,y).setVal(0);
                return -1; // -1 = EL HIDATO NO TE SOLUCIO
            }else return 0;
        }else if (helpAux == Help.MEDIUM){

            if (ctrHidato.AreCellsContiguous(value, value+1) && ctrHidato.AreCellsContiguous(value, value-1)){
                    game.incrementChangesMade();
                    hidato.getCell(x,y).setVal(value);
                return 0;
            }else if (value != 0){
                hidato.getCell(x,y).setVal(0);
                return -2;
            } // -2 = HI HA DOS NOMBRES CONSECUTIUS SEPARATS
            else return 0;
        }else return 0;
    }
    
    /**
     * Fa una pausa en la partida (atura el temps de partida)
     */
    public void pause(){
        long time1 = (long) System.currentTimeMillis();
        game.incrementDuration(time1 - time0);
    }
       
    /**
     * Torna a la partida despres d'una pausa (el temps de partida torna a comptar)
     */
    public void unpause(){
        time0 = System.currentTimeMillis();
    }
    
    /**
     * Reinicia la partida (torna el hidato i les estadistiques tal com estaven al principi)
     */
    public void restartGame(){
        game.restartGame();
        this.ctrHidato = new HidatoController(game.getHidato());
    }
    
    /**
     * Resol la partida automaticament
     */
    public void solve(){
        game.setLegitSolve(false);
        SolverController solver = (new SolverController());
        if (solver.solve(game.getHidato())){
            game.setHidato(solver.getHidato(game.getHidato()));
        }else game.solve();
        
        this.ctrHidato = new HidatoController(game.getHidato());
    }
    
    /**
     * Guarda la partida al repositori
     * NOTA: com que ctrDBGame actualment es un stub, la partida es eliminada i no es guarda enlloc
     */
    public void saveGame(){
        pause();
        gameSet.deleteGame(game.getName());
        gameSet.addGame(game);
    }
    
    public Boolean existsGame(String name){
        return (gameSet.getGameByName(name) != null);
    }
    
    /**
     * Actualitza les estadistiques de l'usuari i el ranking, quan la partida esta acabada
     */
    public void finishGame(){
        long time1 = System.currentTimeMillis();
        game.incrementDuration(time1 - time0);
        
        gameSet.deleteGame(game.getName());
        HidatoUser user = (HidatoUser) hidatoUserController.getLoggedUser();
        if (user == null) return;
        user.incrementSolvedGames();
        user.incrementTotalTimePlayed(game.getDuration());
        user.updateBestTime(game.getDuration());
        String username = user.getUsername();
        
        int score = calculateScore();
        user.incrementTotalScore(score);
        user.updateBestScore(score);
        
        hidatoUserController.updateUser();
        ctrRanking.addScoreToRanking(score, username, game.getDifficulty());
    }
    
    /**
     * Calcula la puntuacio de la partida (val 0 si s'ha utilitzat el solve per acabar-la)
     * @return la puntuacio obtinguda
     */
    public int calculateScore(){
        if (!game.getLegitSolved()) return 0;
        int checkPenalty = 60000;
        int changePenalty = 1000;
        int hintPenalty = 30000;
        long totalDuration = game.getDuration().toMillis() + checkPenalty*game.getChecksMade() + changePenalty * game.getChangesMade() + hintPenalty * game.getHints();
        long score = (long) (1e8 / totalDuration);
        if (game.getHelp() == Help.MEDIUM) score = score/2;
        if (game.getHelp() == Help.HIGH) score = score/5;
        return (int) score;
    }
    
    /**
     * Inicialitza la partida. Si es una nova partida, actualitza estadistiques de l'usuari.
     */
    public void initialize(){
        unpause();
        if (hidatoUserController.getLoggedUser() == null) return;
        if (game.getName() != null) return;
        hidatoUserController.getLoggedUser().incrementStartedGames();
        hidatoUserController.updateUser();
    }
   
    /**
     * Getter del valor de la cell en la posicio (i,j)
     * @param i coordenada x de la cell
     * @param j coordenada y de la cell
     * @return valor de la cell
     */
    public int getCellVal(int i, int j){
        Hidato hidato = game.getHidato();
        Cell c = hidato.getCell(i,j);
        return c.getVal();
    }
    
    /**
     * Getter del tipus de la cell en la posicio (i,j)
     * @param i coordenada x de la cell
     * @param j coordenada y de la cell
     * @return tipus de la cell
     */
    public CapaDomini.Tauler.Type getCellType(int i, int j){
        Hidato hidato = game.getHidato();
        Cell c = hidato.getCell(i,j);
        return c.getType();
    }
    
    /**
     * Getter del nombre de files
     * @return nombre de files del hidato
     */
    public int getSizeX(){
        return game.getHidato().getSizeX();
    }
    
    /**
     * Getter del nombre de columnes
     * @return nombre de columnes del hidato
     */
    public int getSizeY(){
        return game.getHidato().getSizeY();
    }
    
    /**
     * Getter del nombre de cells valides
     * @return nombre de cells valides del hidato
     */
    public int getValidCells(){
        return ctrHidato.countValidCells();
    }
    
    /**
     * Getter de la duracio
     * @return duracio de la partida
     */
    public Duration getDuration(){
        return game.getDuration();
    }
    
    /**
     * Retorna si el hidato esta resolt o no
     * @return true si esta resolt, false si no
     */
    public Boolean isSolved(){
        return ctrHidato.isSolved();
    }
    
    /**
     * Getter del nivell d'ajuda 
     * @return nivell d'ajuda de la partida
     */
    public Help getHelp(){
        return game.getHelp();
    }
    
    /**
     * Getter de la dificultat
     * @return nivell de dificultat de la partida
     */
    public Difficulty getDifficulty(){
        return game.getDifficulty();
    }
    
    /**
     * Getter del nom
     * @return nom de la partida
     */
    public String getName(){
        return game.getName();
    }
    
    /**
     * Getter del nom de l'usuari
     * @return nom de l'usuari de la partida
     */
    public String getUsername(){
        return game.getUsername();
    }
    
    /**
     * Calcula el seguent nombre que no esta colocat al hidato, a partir d'un donat.
     * Si arriba al final, torna a començar.
     * @param ini Nombre donat
     * @return Minim nombre mes gran que ini que no esta al hidato. 
     * Retorna -1 si no n'hi ha cap.
     */
    public int nextNumber(int ini){
        for (int i = ini+1; i <= ctrHidato.countValidCells(); i++){
            if (ctrHidato.getCellPositionFromValue(i, 1) == -1){
                return i;
            }
        }
        if (ini != 1){
            return nextNumber(1);
        }
        return -1;
    }
    
    /**
     * Retorna si el hidato es volatil (es a dir, si no esta guardat al hidatoSet)
     * @return true si es volatil, false si no
     */
    public Boolean isVolatile(){
        return game.isVolatile();
    }
    
    /**
     * Setter del nom
     * @param newName nou nom de la partida 
     */
    public void setName(String newName){
        game.setName(newName);
    }
    
    /**
     * Setter del nom del hidato
     * @param newName nou nom del hidato
     */
    public void setBoardName(String newName){
        game.getHidato().setBoardName(newName);
    }
}
