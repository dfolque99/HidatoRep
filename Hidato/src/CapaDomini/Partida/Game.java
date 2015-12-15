package CapaDomini.Partida;

import CapaDomini.Tauler.Hidato;
import java.io.Serializable;
import java.time.Duration;
import java.util.Date;



/**
 * Representa una partida.
 * @author Pau Surrell
 */
public class Game implements Serializable {
    
    /**
     * Nom de la partida.
     */
    private String name;
    
    /**
     * Nivell d'ajuda.
     */
    private final Help help;
    
    /**
     * Duracio de la partida, en milisegons.
     */
    private Duration duration;
    
    /**
     * Nombre de checks fets (un check vol dir comprovar si te solucio).
     */
    private int checksMade;
    
    /**
     * Nombre de canvis fets (un canvi es o posar o treure un nombre d'una casella).
     */
    private int changesMade;
    
    /**
     * Nombre de pistes demanades (una pista es que la maquina posi un nombre a la seva posicio correcta).
     */
    private int hints;
    
    /**
     * Hidato (tauler) sobre el qual es juga la partida.
     */
    private Hidato hidato;
    
    /**
     * Hidato resolt a partir de l'inicial (no es solucio unica).
     */
    private final Hidato solvedHidato;
    
    /**
     * Hidato igual que l'inicial, que no es pot modificar.
     */
    private final Hidato origHidato;
    
    /**
     * Usuari que juga la partida.
     */
    private final String username;
    
    /**
     * Nivell de dificultat de la partida.
     */
    private final Difficulty difficulty;
    
    /**
     * Boolea que indica si per resoldre la partida s'ha utilitzat la opcio solve
     * (true = no s'ha utilitzat).
     */
    private Boolean legitSolve; 
    
    /**
     * Boolea que indica si el Hidato es volatil (volatil vol dir que no esta 
     * guardat al hidatoSet)
     */
    private final Boolean isHidatoVolatile;
    
    /**
     * Creadora. Inicialitza tots els parametres als valors que li passen.
     * @param name nom de la partida
     * @param hidato hidato sobre el qual es juga la partida
     * @param solvedHidato hidato resolt 
     * @param username nom de l'usuari que juga la partida
     * @param help nivell d'ajuda de la partida
     * @param difficulty nivell de dificultat de la partida
     * @param isVolatile indica si el hidato es volatil 
     */   
    public Game(String name, Hidato hidato, Hidato solvedHidato, String username, Help help, Difficulty difficulty, Boolean isVolatile){
        this.name = name;
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
        this.isHidatoVolatile = isVolatile;
    }
    
    public Game(Game g){
        this.name = g.name;
        this.hidato = new Hidato(g.hidato);
        this.origHidato = new Hidato(g.origHidato);
        this.solvedHidato = new Hidato(g.solvedHidato);
        this.username = g.username;
        this.help = g.help;
        this.changesMade = g.changesMade;
        this.checksMade = g.checksMade;
        this.duration = g.duration;
        this.hints = g.hints;
        this.difficulty = g.difficulty;
        this.legitSolve = g.legitSolve;
        this.isHidatoVolatile = g.isHidatoVolatile;
    }
    
    /**
     * Getter del nom del hidato
     * @return el nom de la partida
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Getter del hidato
     * @return el hidato (tauler) de la partida
     */
    public Hidato getHidato(){
        return this.hidato;
    }
    
    /**
     * Setter del hidato
     * @param hidato nou hidato de la partida
     */
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
    
    
    /**
     * Retorna si el hidato de la partida es volatil
     * @return true si es volatil, false si no
     */
    public Boolean isVolatile(){
        return this.isHidatoVolatile;
    }
    
    /**
     * Setter de legit solve
     * @param b nou valor de legitSolve
     */
    public void setLegitSolve(Boolean b){
        this.legitSolve = b;
    }
    
    /**
     * Setter del nom de la partida
     * @param name nou nom de la partida
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Reinicia la partida, i posa tots els parametres com al principi
     */
    public void restartGame(){
        this.hidato = new Hidato(this.origHidato);
        this.changesMade = 0;
        this.checksMade = 0;
        this.duration = Duration.ZERO;
        this.hints = 0;
    }
    
    /**
     * Resol automaticament la partida, i posa legitSolve a fals
     */
    public void solve(){
        this.hidato = new Hidato(this.solvedHidato);
    }
    
    /**
     * Incrementa el nombre de canvis fets en 1. 
     */
    public void incrementChangesMade(){
        this.changesMade++;
    }
    
    /**
     * Incrementa el nombre de checks fets en la partida en 1
     */
    public void incrementChecksMade(){
        this.checksMade++;
    }
    
    /**
     * Incrementa el nombre de pistes demanades en 1
     */
    public void incrementHints(){
        this.hints++;
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
