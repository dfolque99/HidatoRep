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
    
    //Inicializar los tres atributos con los rankings que devuelva el CtrDBRanking
    void init() {
        CtrDBRanking ctrDBRanking = new CtrDBRanking();
        easyRanking = ctrDBRanking.getRanking(Difficulty.EASY);
        mediumRanking = ctrDBRanking.getRanking(Difficulty.MEDIUM);
        hardRanking = ctrDBRanking.getRanking(Difficulty.HARD);
    }
    
    void save() {
        CtrDBRanking ctrDBRanking = new CtrDBRanking();
        ctrDBRanking.modifyRanking(Difficulty.EASY,easyRanking);
        ctrDBRanking.modifyRanking(Difficulty.MEDIUM,mediumRanking);
        ctrDBRanking.modifyRanking(Difficulty.HARD,hardRanking);
    }
    
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
