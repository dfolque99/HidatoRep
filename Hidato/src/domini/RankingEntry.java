/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

import java.util.Date;

/**
 * @author Guillem
 * Representa una entrada del Ranking, contiene el nombre del usuario que ha
 * conseguido la puntuacion, la puntuacion y la fecha en la que lo ha conseguido.
 */
public class RankingEntry {
    
    private final Date date;
    private final String username;
    private final int score; 
    
    
    //Constructor de entradas ranking (inicializa todos los atributos)
    public RankingEntry(Date date, String username, int score) {
        this.date = new Date(date.getTime());
        this.username = username;
        this.score = score;
    }

    //Metodos para consultar la informacion que contiene la entrada de ranking
    public Date getDate() {
        return new Date(date.getTime());
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return date + "/" + username + "/" + score;
    }
    
}
