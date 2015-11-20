package gui;
import domini.Usuari.HidatoUser;
/**
 *
 * @author felix.axel.gimeno
 */
public class CurrentUser {
    private static HidatoUser h; 
    private static CurrentUser cu = new CurrentUser();
    private CurrentUser(){
        h = new HidatoUser("paco","paco");
    }
    public static CurrentUser getInstance(){return cu;}
    public static HidatoUser getHidatoUser(){return h;}
    public static void setHidatoUser(HidatoUser hidato){h = hidato;}
}
