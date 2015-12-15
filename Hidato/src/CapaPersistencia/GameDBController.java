package CapaPersistencia;

import CapaDomini.Partida.Game;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase que gestiona la persistencia de los Games (partidas guardadas)
 * @author Guillem
 */
public class GameDBController {
    
    private final static String directory = "Games/";
    private final static String extension = ".obj";

    /*
    Guarda el Game game en persistencia
    */
    public void saveGame(Game game) {
        if (game != null) {
            //Crea la subcarpeta "Games/<nombreusuario>/" (si no estaba ya hecho)
            File dir = new File(directory + getFolder(game));
            dir.mkdir();
            try {
                FileOutputStream fos = new FileOutputStream(getDirectory(game));
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(game);
                fos.close();
            } catch (Exception e) {

            }
        }
    }

    /*
    Devuelve un ArrayList que contiene todos los Games del usuario username guardados
    en persistencia
    */
    public ArrayList<Game> getAllGames(String username){
        ArrayList<Game> ret = new ArrayList<>();
        File f = new File(directory + username);
        f.mkdir();
        ArrayList<String> gameNames = new ArrayList(Arrays.asList(f.list()));
        for (int i = 0; i < gameNames.size(); ++i) {
            try {
                FileInputStream fis = new FileInputStream(directory + username + "/" + gameNames.get(i));
                ObjectInputStream ois = new ObjectInputStream(fis);
                ret.add((Game)ois.readObject());
                fis.close();
            }
            catch (Exception e) {
                
            }
            
        }
        return ret;
    }
    
    /*
    Elimina todos los Games del usuario username guardados en persistencia.
    */
    public void deleteAllGames(String username) {
        File f = new File(directory + username);
        if (Arrays.asList(f.list()) != null) {
            ArrayList<String> gameNames = new ArrayList(Arrays.asList(f.list()));
            for (int i = 0; i < gameNames.size(); ++i) {
                File aux = new File(directory + username + "/" + gameNames.get(i));
                aux.delete();
            }
            f.delete();
        }
    }
    
    //A PARTIR DE AQUI FUNCIONES PRIVADAS
    
    private static String getDirectory(Game game) {
        return directory + getFolder(game) + "/" + getName(game) + extension;
    }
    
    private static String getFolder(Game game) {
        return game.getUsername();
    }
    
    private static String getName(Game game) {
        return game.getName();
    }
    
}
