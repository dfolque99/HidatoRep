/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Rank;

import domini.Partida.Difficulty;
import java.util.ArrayList;

/**
 * @author Guillem
 * Representa un ranking. Contiene un conjunto de entradas de ranking ordenadas por
 * puntuacion decreciente. En el ranking solo puede haber N (20 en este caso)
 * entradas, en caso de intentar añadir una más se eliminara la que tenga menos
 * puntuación.
 */
public class Ranking {

    private static final int N = 20;
    private final Difficulty difficulty;
    private ArrayList<RankingEntry> ranking;

    public Ranking(Difficulty difficulty) {
        this.difficulty = difficulty;
        ranking = new ArrayList<>(N+1);
        ranking.ensureCapacity(N+1);
    }

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
     * entradas maximas permitidas (N) se elimina la ultiam entrada (sale del
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