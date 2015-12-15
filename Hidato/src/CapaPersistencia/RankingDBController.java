/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPersistencia;

import CapaDomini.Partida.Difficulty;
import CapaDomini.Rank.Ranking;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Clase que gestiona la persistencia de los rankings.
 * @author Guillem
 */


public class RankingDBController {
    
    private static final String directory = "Ranking/";
    private static final String extension = ".obj";
    
    /*
    Funcion que devuelve el Ranking que se corresponde con la dificultad
    difficulty guardado en persistencia. En caso de no existir este devuelve
    un nuevo Ranking vacio con parametro de dificultad difficulty.
    */
    public Ranking getRanking(Difficulty difficulty) {
        Ranking ret;
        try {
            FileInputStream fis = new FileInputStream(getDirectory(difficulty));
            ObjectInputStream ois = new ObjectInputStream(fis);
            ret = (Ranking)ois.readObject();
            fis.close();
        }
        catch(Exception e) {
            ret = new Ranking(difficulty);
        }
        return ret;
    }
    
    /*
    Guarda o sobreescribe (en caso de ya existir un ranking con la dificultad
    difficulty) el Ranking ranking.
    */
    public void modifyRanking(Difficulty difficulty,Ranking ranking) {
        File f = new File(directory);
        f.mkdir();
        try {
            FileOutputStream fos = new FileOutputStream(getDirectory(difficulty));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ranking);
            fos.close();
        }
        catch (Exception e) {
            
        }
        
    }
    
    //A PARTIR DE AQUI FUNCIONES PRIVADAS
    
    
    /*
    Funcion que devuelve un String que contiene el path donde se debe guardar el
    Ranking cuya dificultad es difficulty.
    */
    private String getDirectory(Difficulty difficulty) {
        return directory + difficulty.toString() + extension;
    }
    
}
