package domini;

import java.time.Duration;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Game representa una partida
 * @author Pau Surrell
 */
public class Game {
    
    /**
     *  Declara tots els parametres d'un Game
     */
    private String name;
    private final Help help;
    private Duration duration;
    private int checksMade;
    private int changesMade;
    private int hints;
    private final Date date;
    private Hidato hidato;
    private final Hidato origHidato;
    private final HidatoUser user;
    private final Difficulty difficulty;
    
    /**
     *  Crea un nou Game a partir d'un nom, un hidato, un usuari, un nivell d'ajuda i una dificultat
     */
    
    public Game(String name,  Hidato hidato, HidatoUser user, Help help, Difficulty difficulty){
        this.name = name;
        this.date = new Date();
        this.hidato = new Hidato(hidato);
        this.origHidato = new Hidato(hidato);
        this.user = user;
        this.help = help;
        this.changesMade = 0;
        this.checksMade = 0;
        this.duration = Duration.ZERO;
        this.hints = 0;
        this.difficulty = difficulty;
    }
    
    /**
     * Getters
     */
    
    public String getName(){
        return this.name;
    }
    
    public Date getDate(){
        return this.date;
    }
    
    public Hidato getHidato(){
        return this.hidato;
    }
    
    public Hidato getOrigHidato(){
        return new Hidato(origHidato);
    }
    
    public HidatoUser getUser(){
        return this.user;
    }
    
    public Help getHelp(){
        return this.help;
    }
    
    public int getChangesMade(){
        return this.changesMade;
    }
    
    public int getChecksMade(){
        return this.checksMade;
    }
    
    public Duration getDuration(){
        return this.duration;
    }
    
    public int getHints(){
        return this.hints;
    }
    
    public Difficulty getDifficulty(){
        return this.difficulty;
    }
    
    /**
     * Setter del nom de la partida
     * @return 0
     */
    public int setName(String name){
        this.name = name;
        return 0;
    }
    
    /**
     * Reinicia la partida, i posa tots els paràmetres com al principi
     * @return 
     */
    public int restartGame(){
        this.hidato = this.origHidato;
        this.changesMade = 0;
        this.checksMade = 0;
        this.duration = Duration.ZERO;
        this.hints = 0;
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
     * Incrementa el nombre de checks fets en el game
     * @return 0
     */
    public int incrementChecksMade(){
        this.checksMade++;
        return 0;
    }
    
    /**
     * Incrementa el nombre de pistes demanades
     * @return 0
     */
    public int incrementHints(){
        this.hints++;
        return 0;
    }
    
    /**
     * Incrementa la durada de la partida
     * @param time durada afegida, en milliseconds
     * @return 0
     */
    public int incrementDuration(long time){
        this.duration.plusMillis(time);
        return 0;
    }
}
