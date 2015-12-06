/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Usuari;

import java.time.Duration;
import java.util.ArrayList;

/**
 *
 * @author Guillem
 */
public class HidatoUser extends User {
    
    private int totalScore;
    private int bestScore;
    private Duration bestTime;
    private ArrayList<String> createdHidatos;
    
    public HidatoUser(String username, String password) {
        super(username,password);
        totalScore = 0;
        bestScore = 0;
        bestTime = null;
        createdHidatos = new ArrayList<>();
    }
    
    public void incrementTotalScore(int score) {
        totalScore += score;
    }
    
    public int getTotalScore() {
        return totalScore;
    }
    
    public void updateBestScore(int newScore) {
        if (newScore > bestScore) bestScore = newScore;
    }
    
    public int getBestScore() {
        return bestScore;
    }
    
    public void updateBestTime(Duration newTime) {
        if (bestTime == null) bestTime = newTime;
        else if (newTime.compareTo(bestTime) < 0) bestTime = newTime;
    }
    
    public Duration getBestTime() {
        return bestTime;
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
    
    public ArrayList<String> getCreatedHidato() {
        return createdHidatos;
    }
    
    public String getCreatedHidato(int pos) {
        return createdHidatos.get(pos);
    }
    
    @Override
    public int getActualCreatedBoard() {
        return createdHidatos.size();
    }
    
    public void addHidato (String name) {
        createdHidatos.add(name);
    }
    
    public void deleteHidato (int pos) {
        createdHidatos.remove(pos);
    }
}