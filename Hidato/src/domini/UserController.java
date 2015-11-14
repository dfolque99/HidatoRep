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

    
    public int createUser(String username, String password) {
        UserDBController db = new UserDBController();
        User newUser = new User(username,password);
        int error = db.createUser(newUser);
        return error;
    }
    
    public int deleteUser(String password) {
        UserDBController db = new UserDBController();
        if (!password.equals(loggedUser.getPassword())) return -1;
        if (db.deleteUser(loggedUser.getUsername()) != 0) return -2;
        loggedUser = null;
        return 0;
    }
    
    public int updateUser() {
        UserDBController db = new UserDBController();
        int error = db.modifyUser(loggedUser,loggedUser.getUsername());
        return error;
    }

    public User getLoggedUser() {
        return loggedUser;
    }    
    public User getUser(String username) {
        UserDBController db = new UserDBController();
        return db.getUser(username);
    }
    
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
    
    
    public int modifyPassword(String oldPassword, String newPassword) {
        UserDBController db = new UserDBController();
        if (!oldPassword.equals(loggedUser.getPassword())) return -1;
        loggedUser.setPassword(newPassword);
        int error = db.modifyUser(loggedUser,loggedUser.getUsername());
        if (error != 0) {
            loggedUser.setPassword(oldPassword);
            return -2;
        }
        return 0;
    }
    
}