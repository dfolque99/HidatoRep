/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pau Surrell
 */
public class Game {
    
    /**
     *  Game name
     */
    private String name;
    
    /**
     *  Indicates the Help level chosen
     */
    private final Help help;
    
    /**
     *  Stores the duration of the game (in seconds)
     */
    private int duration;
    
    /**
     *  Stores the number of checks made in the game
     */
    private int checksMade;
    
    /**
     *  Stores the number of changes made in the game.
     *  A change includes putting a value into a cell,
     *  modifying it or deleting it.
     */
    private int changesMade;
    
    /**
     *  Stores the number of hints the user has requested
     */
    private int hints;
    
    /**
     *  Stores the date in which the game was created
     */
    private final Date date;
    
    /**
     *  Stores the Hidato board of the game
     */
    private final Hidato hidato;
    
    /**
     *  Stores the user that created the game
     */
    private final User user;
    
    private final String difficulty;
    
    /**
     *  Creator of a game, having the date, the hidato, the user, the name and the help
     */
    
    public int Game(String name,  Hidato hidato, User user, Help help, Difficulty difficulty){
        this.name = name;
        this.date = Date();
        this.hidato = hidato.clone();
        this.user = user;
        this.help = help;
        this.changesMade = 0;
        this.checksMade = 0;
        this.duration = 0;
        this.hints = 0;
        this.difficulty = difficulty;
    }
    
    /**
     * Getter of the name
     * @return the name of the game
     */
    
    public String getName(){
        return this.name;
    }
    
    /**
     * Getter of the creation date
     * @return the creation date of the game
     */
    public Date getDate(){
        return this.date;
    }
    
    /**
     * Getter of the hidato board 
     * @return the board of the game
     */
    public Hidato getHidato(){
        return this.hidato;
    }
    
    /**
     * Getter of the user
     * @return the user playing this game
     */
    
    public User getUser(){
        return this.user;
    }
    
    /**
     * Getter of the help level
     * @return the help level
     */
    
    public Help getHelp(){
        return this.help;
    }
    
    /**
     * Getter of the changes made
     * @return the number of changes made in the game
     */
    
    public int getChangesMade(){
        return this.changesMade;
    }
    
    /**
     * Getter of the checks made
     * @return the number of checks made in the game
     */
    public int getChecksMade(){
        return this.checksMade;
    }
    
    /**
     * Getter of the duration
     * @return the duration of the game, in seconds
     */
    public int getDuration(){
        return this.duration;
    }
    
    /**
     * Getter of the number of hints
     * @return the number of hints made
     */
    public int getHints(){
        return this.hints;
    }
    
    
    public Difficulty getDifficulty(){
        return this.difficulty;
    }
    
    /**
     * Sets the Game name to the parameter name
     * @param name the new name of the game
     * @return 0
     */
    public int setName(String name){
        this.name = name;
        return 0;
    }
    
    /**
     * Increments the number of changes made by 1
     * @return 0
     */
    public int incrementChangesMade(){
        this.changesMade++;
        return 0;
    }
    
    /**
     * Increments the number of checks made by 1
     * @return 0
     */
    public int incrementChecksMade(){
        this.checksMade++;
        return 0;
    }
    
    /**
     * Increments the number of hints requested by 1
     * @return 0
     */
    public int incrementHints(){
        this.hints++;
        return 0;
    }
    
    public int incrementDuration(double time){
        this.duration += time;
        return 0;
    }
}
