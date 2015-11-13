package domini;

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
    private Help help;
    private int duration;
    private int checksMade;
    private int changesMade;
    private int hints;
    private Date date;
    private Hidato hidato;
    private User user;
    private Difficulty difficulty;
    
    /**
     *  Crea un nou Game a partir d'un nom, un hidato, un usuari, un nivell d'ajuda i una dificultat
     */
    
    public int Game(String name,  Hidato hidato, User user, Help help, Difficulty difficulty){
        this.name = name;
        this.date = new Date();
        this.hidato = new Hidato(hidato);
        this.user = user;
        this.help = help;
        this.changesMade = 0;
        this.checksMade = 0;
        this.duration = 0;
        this.hints = 0;
        this.difficulty = difficulty;
        return 0;
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
    
    public User getUser(){
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
    
    public int getDuration(){
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
     * @param time durada afegida
     * @return 0
     */
    public int incrementDuration(double time){
        this.duration += time;
        return 0;
    }
}
