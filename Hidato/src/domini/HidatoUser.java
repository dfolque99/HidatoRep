/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

import java.time.Duration;

/**
 *
 * @author Guillem
 */
public class HidatoUser extends User {
    
    private int totalScore;
    
    public HidatoUser(String username, String password) {
        super(username,password);
        totalScore = 0;
    }
    
    public void incrementTotalScore(int score) {
        totalScore += score;
    }
    
    public int getTotalScore() {
        return totalScore;
    }
    
    public double getSolvedPercentage() {
        if (getStartedGames() == 0) return 0;
        else return (float)getSolvedGames()/(float)getStartedGames();
    }
    
    public int getAverageScore() {
        if (getSolvedGames() == 0) return 0;
        else return totalScore/getSolvedGames();
    }
    
    public Duration getAverageTimePerSolve() {
        if (getSolvedGames() == 0) return Duration.ZERO;
        else return getTotalTimePlayed().dividedBy((long)getSolvedGames());
    }
    
}