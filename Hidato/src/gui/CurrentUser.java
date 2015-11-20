package gui;
import domini.Usuari.HidatoUser;
/**
 * SingletonPattern 
 * @author felix.axel.gimeno
 */
public class CurrentUser {
    private static HidatoUser h; 
    private static CurrentUser cu = null;
    private CurrentUser(){
        h = new HidatoUser("paco","paco");
    }
    public static CurrentUser getInstance(){
        if (null == cu) {cu = new CurrentUser();} 
        return cu;
    }
    public HidatoUser getHidatoUser(){return this.h;}
    public void setHidatoUser(HidatoUser hidato){this.h = hidato;}
}
