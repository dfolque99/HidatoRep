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
    private final int N = 20;
    private final Difficulty difficulty;
    private ArrayList <RankingEntry> ranking;

    public Ranking(Difficulty difficulty) {
        this.difficulty = difficulty;
        ranking = new ArrayList<>();
    }
    
    public RankingEntry getEntry(int i) {
        return ranking.get(i);
    }
    
    
}
