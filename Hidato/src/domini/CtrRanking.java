/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

import java.util.ArrayList;
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
        CtrDBRanking db = new CtrDBRanking();
        easyRanking = db.getRanking(Difficulty.EASY);
        mediumRanking = db.getRanking(Difficulty.MEDIUM);
        hardRanking = db.getRanking(Difficulty.HARD);
    }
    
    void save() {
        CtrDBRanking db = new CtrDBRanking();
        db.modifyRanking(Difficulty.EASY,easyRanking);
        db.modifyRanking(Difficulty.MEDIUM,mediumRanking);
        db.modifyRanking(Difficulty.HARD,hardRanking);
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
    
    ArrayList <String> getRankingInfo(Difficulty difficulty) {
        ArrayList <String> res = new ArrayList<>();
        switch(difficulty) {
            case EASY:
                for (int i = 0; i < easyRanking.getSize(); ++i) {
                    res.add(easyRanking.get(i).toString());
                }
                break;
                
            case MEDIUM:
                for (int i = 0; i < mediumRanking.getSize(); ++i) {
                    res.add(mediumRanking.get(i).toString());
                }
                break;
                
            case HARD:
                for (int i = 0; i < hardRanking.getSize(); ++i) {
                    res.add(hardRanking.get(i).toString());
                }
                break;
        }
        return res;
    }
    
    
    
    
    
}
