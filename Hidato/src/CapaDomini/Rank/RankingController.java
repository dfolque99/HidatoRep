/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Rank;

import CapaPersistencia.RankingDBController;
import CapaDomini.Partida.Difficulty;
import java.util.ArrayList;
import java.util.Date;

/**
 *Controlador que gestiona los rankings.
 * Tiene tres rankings, uno por cada dificultad de juego.
 * @author Guillem
 * 
 */
public class RankingController {
    
    private Ranking easyRanking;
    private Ranking mediumRanking;
    private Ranking hardRanking;
    
    /**
     * Inicializa los rankings con la informacion que habia almacenada en la
     * capa de datos.
     * Se debe realizar al principio de la ejecucion del programa
     */
    public void init() {
        RankingDBController db = new RankingDBController();
        easyRanking = db.getRanking(Difficulty.EASY);
        mediumRanking = db.getRanking(Difficulty.MEDIUM);
        hardRanking = db.getRanking(Difficulty.HARD);
    }
    
    /**
     * Guarda en la capa de datos los cambios realizados en los rankings.
     * Se debe realizar antes de cerrar el programa.
     */
    public void save() {
        RankingDBController db = new RankingDBController();
        db.modifyRanking(Difficulty.EASY,easyRanking);
        db.modifyRanking(Difficulty.MEDIUM,mediumRanking);
        db.modifyRanking(Difficulty.HARD,hardRanking);
    }
    
    /** 
     * Añade una entrada al ranking correspondiente con la informacion proporcionada.
     * @param score Informacion sobre la entrada a añadir
     * @param username Informacion sobre la entrada a añadir
     * @param difficulty Sirve para indicar a que ranking se debe añadir la entrada
     * con la informacion proporcionada.
     * Este metodo debe ser invocado cada vez que se finaliza una partida. La fecha
     * que se pone a la entrada del ranking es la fecha de creacion de esta (es decir,
     * la fecha en la que un record entra al ranking)
     * Pre: Hacer init()
     */
    public void addScoreToRanking(int score, String username, Difficulty difficulty) {
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
    
    public static int getRankingMaxSize() {
        return Ranking.getMaxSize();
    }
    
    /**
     * Funcion para pasar la informacion de un ranking a la vista
     * @param difficulty Sirve para indicar que ranking se quiere consultar
     * @return Un arraylist de strings donde cada string representa una entrada
     * del ranking. Cada parametro de las entradas del ranking esta separado
     * por un caracter '/'
     * Pre: Hacer init()
     */
    public ArrayList <String> getRankingInfo(Difficulty difficulty) {
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
    
    public void rename(String username) {
        easyRanking.rename(username);
        mediumRanking.rename(username);
        hardRanking.rename(username);
    }

}