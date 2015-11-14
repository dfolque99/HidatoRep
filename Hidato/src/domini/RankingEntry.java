/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;


/**
 *
 * @author Guillem
 */
import java.util.Date;
public class RankingEntry {
    
    private final Date date;
    private final String username;
    private final int score;    

    public RankingEntry(Date date, String username, int score) {
        this.date = date;
        this.username = new String(username);
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return date + "  " + username + "  " + score;
    }
    
}
