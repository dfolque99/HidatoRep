/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

import java.util.ArrayList;

/**
 *
 * @author Guillem
 */
public class Ranking {
    private static final int N = 20;
    private final Difficulty difficulty;
    private ArrayList <RankingEntry> ranking;
    
    public Ranking(Difficulty difficulty) {
        this.difficulty = difficulty;
        ranking = new ArrayList<>();
    }
    
    public void insertEntry(RankingEntry newEntry);
    
    public int getMaxSize() {
        return N;
    }
    
    public int getSize() {
        return ranking.size();
    }
    
    public getDifficulty() {
        return difficulty;
    }
    
    
}
