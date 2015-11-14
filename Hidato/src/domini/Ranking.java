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
    private ArrayList<RankingEntry> ranking;

    public Ranking(Difficulty difficulty) {
        this.difficulty = difficulty;
        ranking = new ArrayList<>(N+1);
        ranking.ensureCapacity(N+1);
    }

    public int getSize() {
        return ranking.size();
    }

    public int getMaxSize() {
        return N;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public RankingEntry get(int i) {
        if (0 <= i && i < ranking.size()) {
            return ranking.get(i);
        }
        return null;
    }

    public void addRankingEntry(RankingEntry newEntry) {
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
