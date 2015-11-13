/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import UserDBController;

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
        UserDBController userDBController = new UserDBController();
        loggedUser = userDBController.getUser(username);
        if (loggedUser == null) return -1;
        if (!password.equals(loggedUser.getPassword())) {
            loggedUser = null;
            return -2;
        }
        return 0;
    }

    //Hay que cambiar la especificacion de createUser
    public int createUser(String username, String password) {
        User newUser = new User(username,password);
        int error = UserDBController.createUser(newUser);
        return error;
    }
    
    public int deleteUser(String password) {
        if (!password.equals(loggedUser.getPassword())) return -1;
        if (UserDBController.deleteUser(loggedUser.getName()) != 0) return -2;
        loggedUser = null;
        return 0;
    }

    public User getLoggedUser() {
        return loggedUser;
    }    
    public User getUser(String username) {
        return UserDBController.getUser(username);
    }
    
    public int modifyName(String password, String newName) {
        if (!password.equals(loggedUser.getPassword())) return -1;
        if (UserDBController.getUser(newName) != null) return -2;
        String oldName = new String(loggedUser.getName());
        loggedUser.setName(newName);
        int error = UserDBController.modifyUser(loggedUser,loggedUser.getName());
        if (error != 0) {
            loggedUser.setName(oldName);
            return -3;
        }
        return 0;
    }
    
    
    public int modifyPassword(String oldPassword, String newPassword) {
        if (!oldPassword.equals(loggedUser.getPassword())) return -1;
        loggedUser.setPassword(newPassword);
        int error = UserDBController.modifyUser(loggedUser,loggedUser.getName());
        if (error != 0) {
            loggedUser.setPassword(oldPassword);
            return -2;
        }
        return 0;
    }
    
}
