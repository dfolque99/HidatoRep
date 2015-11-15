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
 * Esta clase sirve para gestionar los rankings.
 */
public class CtrRanking {
    
    private Ranking easyRanking;
    private Ranking mediumRanking;
    private Ranking hardRanking;
    
    /**
     * Inicializa los rankings con la informacion guardada en la capa de datos.
     * Se debe realizar al principio de la ejecucion del programa
     */
    void init() {
        CtrDBRanking db = new CtrDBRanking();
        easyRanking = db.getRanking(Difficulty.EASY);
        mediumRanking = db.getRanking(Difficulty.MEDIUM);
        hardRanking = db.getRanking(Difficulty.HARD);
    }
    
    /**
     * Guarda en la capa de datos los cambios realizados en los rankings.
     * Se debe realizara antes de cerrar el programa.
     */
    void save() {
        CtrDBRanking db = new CtrDBRanking();
        db.modifyRanking(Difficulty.EASY,easyRanking);
        db.modifyRanking(Difficulty.MEDIUM,mediumRanking);
        db.modifyRanking(Difficulty.HARD,hardRanking);
    }
    
    /**
     * Añade una entrada al ranking correspondiente con la informacion proporcionada.
     * La fecha con la que se crea la entrada es la actual.
     */
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
    
    /**
     * Funcion para pasar la informacion de un ranking a la vista
     * @return Un arraylist de strings donde cada string representa una entrada
     * del ranking. Cada parametro de las entradas del ranking esta separado
     * por un caracter '/'
     */
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
