package CapaDomini.Partida;

import CapaDomini.Tauler.Hidato;
import java.io.Serializable;
import java.time.Duration;
import java.util.Date;



/**
 * Representa una partida
 * @author Pau Surrell
 */
public class Game implements Serializable {
    
    /**
     * Nom de la partida
     */
    private String name; //OK
    
    /**
     * Nivell d'ajuda
     */
    private final Help help; //OK
    
    /**
     * Duracio de la partida, en milisegons
     */
    private Duration duration; //OK
    
    /**
     * Nombre de checks fets (un check vol dir comprovar si te solucio)
     */
    private int checksMade; //OK
    
    /**
     * Nombre de canvis fets (un canvi es o posar o treure un nombre d'una casella)
     */
    private int changesMade; //OK
    
    /**
     * Nombre de pistes demanades (una pista es que la maquina posi un nombre a la seva posicio correcta)
     */
    private int hints; //OK
    
    /**
     * Data de creacio de la partida
     */
    private final Date date; //OK
    
    /**
     * Hidato (tauler) sobre el qual es juga la partida
     */
    private Hidato hidato; //OK
    
    /**
     * Hidato resolt a partir de l'inicial (no es solucio unica)
     */
    private final Hidato solvedHidato; //OK
    
    /**
     * Hidato igual que l'inicial, que no es pot modificar
     */
    private final Hidato origHidato; //OK
    
    /**
     * Usuari que juga la partida
     */
    private final String username; //OK
    
    /**
     * Nivell de dificultat de la partida
     */
    private final Difficulty difficulty; //OK
    
    /**
     * Boolea que indica si per resoldre la partida s'ha utilitzat la opcio solve
     * (true = no s'ha utilitzat)
     */
    private Boolean legitSolve; //OK
    
    private Boolean isHidatoRandom;
    
    /**
     *  Crea un nou Game a partir d'un nom, un hidato, un hidato solucio, un usuari, un nivell d'ajuda i una dificultat
     */    
    public Game(String name, Hidato hidato, Hidato solvedHidato, String username, Help help, Difficulty difficulty, Boolean isRandom){
        this.name = name;
        this.date = new Date();
        this.hidato = new Hidato(hidato);
        this.origHidato = new Hidato(hidato);
        this.solvedHidato = new Hidato(solvedHidato);
        this.username = username;
        this.help = help;
        this.changesMade = 0;
        this.checksMade = 0;
        this.duration = Duration.ZERO;
        this.hints = 0;
        this.difficulty = difficulty;
        this.legitSolve = true;
        this.isHidatoRandom = isRandom;
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
    
    public void setHidato(Hidato hidato){
        this.hidato = hidato;
    }
    
    /**
     * Getter de l'usuari
     * @return l'usuari de la partida
     */
    public String getUsername(){
        return this.username;
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
    
    public Boolean isRandom(){
        return this.isHidatoRandom;
    }
    
    
    public void setLegitSolve(Boolean b){
        this.legitSolve = b;
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
        this.duration = this.duration.plusMillis(time);
        return 0;
    }
}
