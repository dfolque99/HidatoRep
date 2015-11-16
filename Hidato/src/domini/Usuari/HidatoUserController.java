/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Usuari;

/**
 *
 * @author Guillem
 */
public class HidatoUserController extends UserController {
    
    public HidatoUserController() {
        super();
    }

    @Override
    public int createUser(String username, String password) {
        UserDBController db = new UserDBController();
        HidatoUser newHidatoUser = new HidatoUser(username,password);
        int error = db.createUser(newHidatoUser);
        return error;
    }
    
    
    
    
}
