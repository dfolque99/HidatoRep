/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

import java.util.Date;

/**
 *
 * @author Guillem
 */
public class CtrRanking {
    
    private Ranking easyRanking;
    private Ranking mediumRanking;
    private Ranking hardRanking;
    
    void addScoreToRanking(int score, String username, Difficulty difficulty) {
        RankingEntry newEntry = new RankingEntry(new Date(),username,score);
        switch(difficulty) {
            case EASY:
                easyRanking.addRankingEntry(newEntry);
                break;
                
            case MEDIUM:
                mediumRanking.addRankingEntry(newEntry);
                break;
                
            case HARD:
                hardRanking.addRankingEntry(newEntry);
                break;
        }
    }
    
}
