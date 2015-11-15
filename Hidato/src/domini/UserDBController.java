/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alberto.lopez.sanchez
 * 
 * Detalles que no tienen sentido de la especificacion:
 * 
 */
public class UserDBController {
   
    private static final String Directory = "Users/";
    private static final String Extension = ".obj";
    
    /* Pre:  cert
    ** Post: Retorna un int el qual, segons el valor que tingui, indicarà que
             s’ha creat a la base de dades un nou usuari amb valors newUser, 
             o bé que hi ha hagut alguna excepció.
        Return:
             0 = usuari creat correctament
            -1 = usuari existent
            -2 = error intern
    */
    public int createUser(User newUser){
        int result = 1;
        String filepath = getPath(newUser);
        
        if (new File(filepath).isFile()){
            result = -1;
        }
        else {
            result = writeUser(newUser, filepath);
        }
       
        return result;
    }
    
    /*  Pre: user != NULL
    ** Post: Retorna un int el qual, segons el valor que tingui, indicarà si 
       s’ha modificat a la base de dades l’usuari que, abans de la modificació, 
       tenia com a nom oldname, i se li ha donat uns nous valors user amb èxit, 
       o bé indicarà si hi ha hagut alguna excepció.
    Return:
        0 = usuari modificat correctament
        -1 = usuari inexistent
        -2 = error intern
    */
    public int modifyUser(User user, String oldName){
        int result = -2;
        
        if (oldName != null && ! exists(user)) {
            if (deleteUser(oldName) == 0){
                result = writeUser(user, getPath(user));
            }
        }
        else if (oldName == null){
            if (exists(user)) {
                result = writeUser(user, getPath(user));
            }
            else {
                result = -1;
            }
        } 
            
        return result;
    }
    
    /*  Pre: username != NULL
    ** Post: Retorna un int el qual, segons el valor que tingui, indicarà si 
             s’ha eliminal de la base de dades l’usuari amb nom username, o bé 
             si s’ha produït alguna excepció.
    Return:
         0 = usuari eliminat correctament
        -1 = usuari no existent
        -2= s’està intentant eliminar un usuari que no és el loguejat
        -3 = error intern
    */
    public int deleteUser(String username){
        int result = -3;
        
        if(!exists(username)){
            result = -1;
        }else {
            try {
                Files.delete(FileSystems.getDefault().getPath(getPath(username)));
                result = 0;
            } catch (IOException ex) {
                Logger.getLogger(UserDBController.class.getName()).log(Level.SEVERE, null, ex);
                result = -3;
            }
        }
        
        return result;
    }
    
    /*
    Pre: username != NULL
    Post: Retorna un User amb els atributs de l'usuari a la base de dades amb 
    nom username.
    */
    public User getUser(String username){
        
        FileInputStream fis;
        User user = null;
        
        try {
            fis = new FileInputStream(getPath(username));
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (User) ois.readObject();
            fis.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserDBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserDBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
    private String getPath(User user) {
        return getPath(user.getUsername());
    }
    
    private String getPath(String username) {
        return Directory+username+Extension;
    }
    
    private boolean exists(User user) {
        return exists(user.getUsername());
    }
    
    private boolean exists(String username) {
        return (new File(getPath(username)).isFile());
    }
    
    private int writeUser(User user, String filepath) {
        int result = -2;
        
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            fos.close();
            result = 0;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserDBController.class.getName()).log(Level.SEVERE, null, ex);
            result = -2;
        } catch (IOException ex) {
            Logger.getLogger(UserDBController.class.getName()).log(Level.SEVERE, null, ex);
            result = -2;
        }        
        
        return result;
    }
}