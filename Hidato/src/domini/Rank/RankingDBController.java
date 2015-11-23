/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Rank;

import domini.Partida.Difficulty;
import java.io.*;

/**
 * Clase que gestiona la persistencia de los rankings.
 * @author Guillem
 */

//Para funcionar necesita que ya haya un ranking creado para cada dificultad
//en la carpeta "Ranking". Se pueden poner en esta carpeta unos rankings fakes
//iniciales
//SE PUEDE MEJORAR/CAMBIAR TODAVIA


public class RankingDBController {
    
    private static final String directory = "Ranking/";
    private static final String extension = ".obj";
    
    public Ranking getRanking(Difficulty difficulty) {
        Ranking ret = null;
        try {
            FileInputStream fis = new FileInputStream(getDirectory(difficulty));
            ObjectInputStream ois = new ObjectInputStream(fis);
            ret = (Ranking)ois.readObject();
            fis.close();
        }
        catch(Exception e) {
            ret = new Ranking(difficulty,0);
        }
        return ret;
    }
    
    public void modifyRanking(Difficulty difficulty,Ranking ranking) {
        try {
            FileOutputStream fos = new FileOutputStream(getDirectory(difficulty));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ranking);
            fos.close();
        }
        catch (Exception e) {
            
        }
        
    }
    
    private String getDirectory(Difficulty difficulty) {
        return directory+difficulty.toString()+extension;
    }
    
}
