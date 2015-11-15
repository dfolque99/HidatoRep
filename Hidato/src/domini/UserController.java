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
public class UserController {
    
    private User loggedUser;
    
    public UserController() {
        loggedUser = null;
    }
    
    /* Pre: username i password no són nuls
    ** Post: Retorna un enter que indica en quin estat ha acabat l’operació. 
    **	  Si retorna 0 vol dir que l’operació ha finalitzat correctament, en aquest
    **	  cas loggedUser s’inicialitza amb l’usuari amb nom username.
    **	  Si retorna -1 vol dir que l’usuari amb nom username no existeix.
    **	  Si retorna -2 vol dir que l’usuari existeix, però la contrasenya no és
    **	  password.
    **	  Si retorna -3 la font d’error és una altra.
    */
    public int login(String username, String password) {
        UserDBController db = new UserDBController();
        loggedUser = db.getUser(username);
        if (loggedUser == null) return -1;
        if (!password.equals(loggedUser.getPassword())) {
            loggedUser = null;
            return -2;
        }
        return 0;
    }

    /* Pre: username i password no són nuls
    ** Post: Retorna una enter que indica en quin estat ha acabat l’operació.
    **	  Si retorna 0 vol dir que l’operació ha finalitzat correctament, en aquest
    **	  cas s’ha creat un usuari en la capa de dades amb nom username i 	
    **	  contrasenya password.
    **	  Si retorna -1 vol dir que ja existeix un usuari amb nom username.
    **	  Si retorna -2 la font d’error és una altra.
    */
    public int createUser(String username, String password) {
        UserDBController db = new UserDBController();
        User newUser = new User(username,password);
        int error = db.createUser(newUser);
        return error;
    }
    
    /* Pre: password no és nul
    ** Post: Retorna un enter que indica en quin estat ha acabat l’operació.
    **	  Si retorna 0 vol dir que l’operacio ha finalitzat correctament, en aquest
    **	  cas l’usuari amb nom username s’ha eliminat de la capa de dades.
    **	  Si retorna -1 vol dir que la contrasenya és incorrecta.
    **	  Si retorna -2 la font d’error és una altra.
    */
    public int deleteUser(String password) {
        UserDBController db = new UserDBController();
        if (!password.equals(loggedUser.getPassword())) return -1;
        if (db.deleteUser(loggedUser.getUsername()) != 0) return -2;
        loggedUser = null;
        return 0;
    }
    
    /* Pre: Previament s’ha fet login correctament
    ** Post: Actualitza en la capa de dades els canvis que s’hagin produit en
    **	  loggedUser. Retorna 0 si s’ha pogut realitzar l’operació correctament.
    */
    public int updateUser() {
        UserDBController db = new UserDBController();
        int error = db.modifyUser(loggedUser,null);
        return error;
    }

    /* Pre: -
    ** Post: Retorna loggedUser.
    */
    public User getLoggedUser() {
        return loggedUser;
    }    
    
    /* Pre: username no és nul
    ** Post: Retorna l’usuari amb nom username, en cas d’error (p.e. no existeix)
    **	  es null.
    */
    public User getUser(String username) {
        UserDBController db = new UserDBController();
        return db.getUser(username);
    }
    /* Pre: Previament s’ha fet login correctament i password i newName no són nuls.
    ** Post: Retorna un enter que indica en quin estat ha acabat l’operació. 
    **	  Si retorna 0 vol dir que l’operació ha finalitzat correctament, en aquest
    **	  cas el nom de loggedUser passa a ser newName (també en capa de
    **	  dades).
    **	  Si retorna -1 la contrasenya no es correcta.
    **	  Si retorna -2 ja existeix un usuari amb nom newName.
    **	  Si retorna -3 la font d’error és una altra.
    */
    public int modifyName(String password, String newName) {
        UserDBController db = new UserDBController();
        if (!password.equals(loggedUser.getPassword())) return -1;
        if (db.getUser(newName) != null) return -2;
        String oldName = loggedUser.getUsername();
        loggedUser.setUsername(newName);
        int error = db.modifyUser(loggedUser,oldName);
        if (error != 0) {
            loggedUser.setUsername(oldName);
            return -3;
        }
        return 0;
    }
    
    /* Pre: Previament s’ha fet login correctament i oldPassword i newPassword no són nuls.
    ** Post: Retorna un enter que indica en quin estat ha acabat l’operació. 
    **	  Si retorna 0 vol dir que la contrasenya de loggedUser passa a ser
    **	  newPassword (també en capa de dades).
    **	  Si retorna -1 vol dir que la contrasenya (oldPassword) no coincideix
    **	  amb la que tenia loggedUser.
    **	  Si retorna -2 la font d’error és una altra.
    */
    public int modifyPassword(String oldPassword, String newPassword) {
        UserDBController db = new UserDBController();
        if (!oldPassword.equals(loggedUser.getPassword())) return -1;
        loggedUser.setPassword(newPassword);
        int error = db.modifyUser(loggedUser,null);
        if (error != 0) {
            loggedUser.setPassword(oldPassword);
            return -2;
        }
        return 0;
    }
    
}