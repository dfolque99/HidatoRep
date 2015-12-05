/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Rank;

import CapaDomini.Partida.Difficulty;
import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Date;

/**
 * @author Guillem
 * Representa un ranking. Contiene un conjunto de entradas de ranking ordenadas por
 * puntuacion decreciente. En el ranking solo puede haber N (20 en este caso)
 * entradas, en caso de intentar añadir una más se eliminara la que tenga menos
 * puntuación.
 */
public class Ranking implements Serializable {

    private static final int N = 20;
    private final Difficulty difficulty;
    private ArrayList<RankingEntry> ranking;

    public Ranking(Difficulty difficulty) {
        this.difficulty = difficulty;
        ranking = new ArrayList<>();
        ranking.ensureCapacity(N+1);
    }
    
    /*
    Constructor para que en el caso en el que no esten los rankings ya creados
    en persistencia (primera vez que se ejecute el programa) se creen unos
    rankings "de muestra".
    */
    /*
    public Ranking(Difficulty difficulty, int i) {
        this(difficulty);
        RankingEntry entry = new RankingEntry(new Date(),"xX_ProHidato360_Xx",3000);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Mariano",2999);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"John",2500);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"William",2300);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"The Hidato Master",2183);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Joan",2015);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Einstein",1995);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Player 1",1824);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Heisenberg",1700);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Charlie",1650);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Oscar",1640);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Leonardo da Vinci",1600);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"David",1580);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"asdf",1500);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Newton",1432);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Heisenberg",1200);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Alan Turing",1000);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Dummy",500);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Lufranxucy",100);
        ranking.add(entry);
        entry = new RankingEntry(new Date(),"Bad Player",1);
        ranking.add(entry);
    }
    */

    /**
     * @return Numero actual de entradas en el ranking
     */
    public int getSize() {
        return ranking.size();
    }

    /**
     * @return Numero maximo de entradas en el ranking
     */
    public static int getMaxSize() {
        return N;
    }

    /**
     * @return Dificultad con la que se han conseguido las puntuaciones del ranking
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * @return La entrada que se encuentra en la posicion i del ranking o null
     * si esta no existe
     */
    public RankingEntry get(int i) {
        if (0 <= i && i < ranking.size()) {
            return ranking.get(i);
        }
        return null;
    }

    /**
     * @param newEntry entrada a añadair al ranking
     * Se añade la entrada en su posicion correspondiente, es decir, manteniendo
     * el orden decreciente por puntuacion. En caso de exceder el numero de 
     * entradas maximas permitidas (N) se elimina la ultima entrada (sale del
     * ranking)
     */
    public void addRankingEntry(RankingEntry newEntry) {
        /*
        Esta funcion utiliza una adaptacion del algoritmo de busqueda binaria
        para encontrar la posicion en la que se debe colocar la nueva entrada
        del ranking.
        */
        int newScore = newEntry.getScore();
        int l = 0;
        int r = ranking.size() - 1;
        int pos = r + 1;
        while (l <= r) {
            int m = (l+r)/2;
            int score = ranking.get(m).getScore();
            if (newScore < score) l = m + 1;
            else if (newScore > score) {
                pos = m;
                r = m - 1;
            }
            else l = m + 1;
        }
        ranking.add(pos,newEntry);
        if (ranking.size() > N) ranking.remove(N);
    }
    
}