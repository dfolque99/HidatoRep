/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Rank;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Representa una entrada del Ranking.
 * Contiene el nombre del usuario que ha conseguido la puntuacion, la puntuacion
 * y la fecha en la que lo ha conseguido.
 * @author Guillem
 * 
 */
public class RankingEntry implements Serializable {
    
    private final Date date;
    private String username;
    private final int score; 
    
    
    /*
    Constructor de entradas ranking (inicializa todos los atributos)
    */
    public RankingEntry(Date date, String username, int score) {
        this.date = new Date(date.getTime());
        this.username = username;
        this.score = score;
    }
    
    public void rename() {
        username = username + "*";
    }

    //METODOS PARA CONSULTAR LA INFORMACION QUE CONTIENE LA ENTRADA DEL RANKING
    
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
        
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        int d = c.get(Calendar.DAY_OF_MONTH);
        int m = c.get(Calendar.MONTH) + 1; //Enero == 0
        int y = c.get(Calendar.YEAR);
        
        String formattedDate;
        if (d < 10) formattedDate = "0";
        else formattedDate = "";
        formattedDate = formattedDate + Integer.toString(d) + "-";
        if (m < 10) formattedDate = formattedDate + "0";
        formattedDate = formattedDate + Integer.toString(m) + "-" + Integer.toString(y);
        
        return formattedDate + "/" + username + "/" + score;
    }
    
}
