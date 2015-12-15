/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacio;

import CapaDomini.Partida.CurrentGameController;
import CapaDomini.Partida.GameManagerController;
import CapaDomini.Rank.RankingController;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Tauler.HidatoSet;
import CapaDomini.Usuari.HidatoUserController;
import java.awt.Point;
import java.io.File;
import javax.swing.JFrame;

/**
 * Classe administradora de les vistes.
 * @author David
 */
public class AdminVistes {
    
    /*
     * Controladors que seran unics en tota la execució
     */
    private HidatoUserController uc;
    private HidatoManagerController hmc;
    private GameManagerController gmc;
    private RankingController rc;
    
    /*
     * Diu si s'ha entrat en mode convidat
     */
    private boolean convidat;
    
    /**
     * Funció que executarà el programa.
     * Crea els controladors principals i les carpetes on guardar dades.
     * Inicialitza la vista de login
     */
    public void executaPrograma() {
        File f = new File("Users/");
        f.mkdir();
        f = new File("Games/");
        f.mkdir();
        this.uc = new HidatoUserController();
        this.rc = new RankingController();
        rc.init();
        this.hmc = new HidatoManagerController(uc);
        this.gmc = new GameManagerController(rc, uc, hmc);
        hmc.setGameManagerController(gmc);
        hmc.loadAll();
        convidat = false;
        FrameLogin fl = new FrameLogin(this, uc, gmc);
        fl.setLocationByPlatform(true);
        fl.setVisible(true);
    }
    
    /*
     * Posa convidat a true
     */
    public void setConvidat() {
        convidat = true;
        uc = null;
    }
    
    /*
     * Obre la vista del menu principal
     */
    public void obrirMenu(JFrame antic) {
        FrameMenu fm;
        if (convidat) {
            fm = new FrameMenu(this, hmc, gmc, "Convidat");
        }
        else {
            fm = new FrameMenu(this, hmc, gmc, uc.getLoggedUser().getUsername());
        }
        fm.setVisible(true);
        Point location = antic.getLocation();
        try {
            if (antic.getClass() == Class.forName("CapaPresentacio.FrameLogin")) {
                location.translate(-390/2, -295/2);
                location.x = Math.max(location.x,30);
                location.y = Math.max(location.y,30);
            }
        }
        catch (Exception e) {}
        fm.setLocation(location);
        antic.dispose();
    }
    
    /*
     * Obre la vista de l'editor
     */
    public void obrirEditor(JFrame antic) {
        FrameEditor fe = new FrameEditor(this, hmc);
        fe.setVisible(true);
        fe.setLocation(antic.getLocation());
        antic.dispose();
    }
    
    /*
     * Obre la vista d'estadistiques
     */
    public void obrirStats(JFrame antic) {
        FrameStats fs = new FrameStats(this, uc, hmc, gmc, rc);
        fs.setVisible(true);
        fs.setLocation(antic.getLocation());
        antic.dispose();
    }
    
    /*
     * Obre la vista de partida
     */
    public void obrirPartida(JFrame antic, CurrentGameController cgc) {
        FrameGame fg = new FrameGame(this, cgc, hmc);
        fg.setVisible(true);
        fg.setLocation(antic.getLocation());
        antic.dispose();
    }
    
    /*
     * Obre la vista del ranking
     */
    public void obrirRanking(JFrame antic){
        FrameRanking fr = new FrameRanking(this,rc);
        fr.setVisible(true);
        fr.setLocation(antic.getLocation());
        antic.dispose();
    }
    
    /*
     * Executa les funcions de guardar les dades a disc
     */
    public void saveBeforeClose() {
        rc.save();
        hmc.saveAll();
        gmc.saveGameSet();
    }
}
