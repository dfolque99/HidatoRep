package domini.Partida;

import domini.Tauler.Hidato;
import domini.Usuari.HidatoUser;
import java.time.Duration;
import java.util.Date;



/**
 * Representa una partida
 * @author Pau Surrell
 */
public class Game {
    
    /**
     * Nom de la partida
     */
    private String name;
    
    /**
     * Nivell d'ajuda
     */
    private final Help help;
    
    /**
     * Duracio de la partida, en milisegons
     */
    private Duration duration;
    
    /**
     * Nombre de checks fets (un check vol dir comprovar si te solucio)
     */
    private int checksMade;
    
    /**
     * Nombre de canvis fets (un canvi es o posar o treure un nombre d'una casella)
     */
    private int changesMade;
    
    /**
     * Nombre de pistes demanades (una pista es que la maquina posi un nombre a la seva posicio correcta)
     */
    private int hints;
    
    /**
     * Data de creacio de la partida
     */
    private final Date date;
    
    /**
     * Hidato (tauler) sobre el qual es juga la partida
     */
    private Hidato hidato;
    
    /**
     * Hidato resolt a partir de l'inicial (no es solucio unica)
     */
    private final Hidato solvedHidato;
    
    /**
     * Hidato igual que l'inicial, que no es pot modificar
     */
    private final Hidato origHidato;
    
    /**
     * Usuari que juga la partida
     */
    private final HidatoUser user;
    
    /**
     * Nivell de dificultat de la partida
     */
    private final Difficulty difficulty;
    
    /**
     * Boolea que indica si per resoldre la partida s'ha utilitzat la opcio solve
     * (true = no s'ha utilitzat)
     */
    private Boolean legitSolve;
    
    /**
     *  Crea un nou Game a partir d'un nom, un hidato, un hidato solucio, un usuari, un nivell d'ajuda i una dificultat
     */    
    public Game(String name, Hidato hidato, Hidato solvedHidato, HidatoUser user, Help help, Difficulty difficulty){
        this.name = name;
        this.date = new Date();
        this.hidato = new Hidato(hidato);
        this.origHidato = new Hidato(hidato);
        this.solvedHidato = new Hidato(solvedHidato);
        this.user = user;
        this.help = help;
        this.changesMade = 0;
        this.checksMade = 0;
        this.duration = Duration.ZERO;
        this.hints = 0;
        this.difficulty = difficulty;
        this.legitSolve = true;
    }
    
    /**
     * Getter del nom del hidato
     * @return el nom de la partida
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Getter de la data
     * @return la data de creacio de la partida
     */
    public Date getDate(){
        return this.date;
    }
    
    /**
     * Getter del hidato
     * @return el hidato (tauler) de la partida
     */
    public Hidato getHidato(){
        return this.hidato;
    }
    
    /**
     * Getter del hidato resolt
     * @return el hidato resolt
     */
    public Hidato getSolvedHidato(){
        return new Hidato(solvedHidato);
    }
    
    /**
     * Getter de l'usuari
     * @return l'usuari de la partida
     */
    public HidatoUser getUser(){
        return this.user;
    }
    
    /**
     * Getter del nivell d'ajuda
     * @return el nivell d'ajuda de la partida (LOW/MEDIUM/HIGH)
     */
    public Help getHelp(){
        return this.help;
    }
    
    /**
     * Getter del nombre de canvis fets
     * @return el nombre de canvis fets (posar/treure un numero en una casella)
     */
    public int getChangesMade(){
        return this.changesMade;
    }
    
    /**
     * Getter del nombre de checks fets
     * @return el nombre de checks fets (comprovacions si la partida te solucio)
     */
    public int getChecksMade(){
        return this.checksMade;
    }
    
    /**
     * Getter de la duracio 
     * @return la duracio actual de la partida, en milisegons
     */
    public Duration getDuration(){
        return this.duration;
    }
    
    /**
     * Getter del nombre de pistes (una pista es un nombre colocat automaticament pel sistema)
     * @return el nombre de pistes demanades 
     */
    public int getHints(){
        return this.hints;
    }
    
    /**
     * Getter de la dificultat
     * @return la dificultat de la partida (EASY/MEDIUM/HARD)
     */
    public Difficulty getDifficulty(){
        return this.difficulty;
    }
    
    /**
     * Getter de legitSolved
     * @return un boolea que indica si s'ha utilitzat la opcio solve per resoldre la partida
     */
    public Boolean getLegitSolved(){
        return this.legitSolve;
    }
    
    /**
     * Setter del nom de la partida
     * @param name nou nom de la partida
     * @return 0
     */
    public int setName(String name){
        this.name = name;
        return 0;
    }
    
    /**
     * Reinicia la partida, i posa tots els parametres com al principi
     * @return 
     */
    public int restartGame(){
        this.hidato = new Hidato(this.origHidato);
        this.changesMade = 0;
        this.checksMade = 0;
        this.duration = Duration.ZERO;
        this.hints = 0;
        return 0;
    }
    
    /**
     * Resol automaticament la partida, i posa legitSolve a fals
     * @return 0
     */
    public int solve(){
        this.hidato = new Hidato(this.solvedHidato);
        this.legitSolve = false;
        return 0;
    }
    
    /**
     * Incrementa el nombre de canvis fets en 1. 
     * @return 0
     */
    public int incrementChangesMade(){
        this.changesMade++;
        return 0;
    }
    
    /**
     * Incrementa el nombre de checks fets en la partida en 1
     * @return 0
     */
    public int incrementChecksMade(){
        this.checksMade++;
        return 0;
    }
    
    /**
     * Incrementa el nombre de pistes demanades en 1
     * @return 0
     */
    public int incrementHints(){
        this.hints++;
        return 0;
    }
    
    /**
     * Incrementa la durada de la partida en time
     * @param time durada afegida, en milisegons
     * @return 0
     */
    public int incrementDuration(long time){
        this.duration.plusMillis(time);
        return 0;
    }
}
