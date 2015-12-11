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
 * @author Guillem
 */
public class GameDBController {
    
    private final static String directory = "Games/";
    private final static String extension = ".obj";

    //Pre: game no es null
    public void saveGame(Game game) {
        //Crea la carpeta raiz "Games/" (si no estaba ya hecho)
        File dir = new File(directory);
        dir.mkdir();
        //Crea la subcarpeta "Games/<nombreusuario>/" (si no estaba ya hecho)
        dir = new File(directory + getFolder(game));
        dir.mkdir();
        try {
            FileOutputStream fos = new FileOutputStream(getDirectory(game));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(game);
            fos.close();
        } catch (Exception e) {
            
        }
    }

    //Pre: El game identificado por (gameName,username) puede no existir
    public Game getGame(String gameName, String username) {
        Game ret = null;
        try {
            FileInputStream fis = new FileInputStream(getDirectory(gameName,username));
            ObjectInputStream ois = new ObjectInputStream(fis);
            ret = (Game)ois.readObject();
            fis.close();
        }
        catch (Exception  e) {
            
        }
        finally {
            return ret;
        }
    }
    //Pre: -
    //Post: Se elimina (en caso de existir) el game identificado por (gameName,username)
    public void deleteGame(String gameName, String username) {
        File f = new File(getDirectory(gameName,username));
        f.delete();
    }
    
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
    
    
    private static String getDirectory(Game game) {
        return getDirectory(getFolder(game),getName(game));
    }
    
    private static String getDirectory(String gameName, String username) {
        return directory + username + "/" + gameName + extension;
    }
    
    private static String getFolder(Game game) {
        return game.getUsername();
    }
    
    private static String getName(Game game) {
        return game.getName();
    }
    
}
