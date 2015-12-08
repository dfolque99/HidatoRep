/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini;

import CapaDomini.Partida.GameManagerController;
import CapaDomini.Partida.Help;
import CapaDomini.Rank.RankingController;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Tauler.HidatoSet;
import CapaDomini.Usuari.HidatoUserController;
import CapaPresentacio.FrameEditor;
import CapaPresentacio.FrameGame;
import CapaPresentacio.FrameLlista;
import CapaPresentacio.FrameLogin;
import CapaPresentacio.FrameMenu;
import CapaPresentacio.FrameNewGameType;
import CapaPresentacio.FrameRanking;
import CapaPresentacio.FrameStats;
import java.awt.Point;
import javax.swing.JFrame;

/**
 *
 * @author David
 */
public class Domini {
    
    private HidatoUserController uc;
    private HidatoManagerController hmc;
    private HidatoSet hs;
    private GameManagerController gmc;
    private RankingController rc;
    private boolean convidat;
    public Domini(){}
    public Domini(HidatoUserController uc, RankingController rc, GameManagerController gmc, HidatoManagerController hmc){
        this.uc = uc;
        this.rc = rc;
        this.gmc = gmc;
        this.hmc = hmc;   
    }
    
    public void adelanteee() {
        this.uc = new HidatoUserController();
        this.rc = new RankingController();
        rc.init();
        this.gmc = new GameManagerController(rc, uc);
        this.hmc = new HidatoManagerController(hs, gmc, uc);
        hmc.loadAll();
        convidat = false;
        FrameLogin fl = new FrameLogin(this, uc);
        fl.setLocationByPlatform(true);
        fl.setVisible(true);
        hmc.veure();
    }
    
    public void setConvidat() {
        convidat = true;
        uc = null;
    }
    
    public void obrirMenu(JFrame antic) {
        FrameMenu fm;
        if (convidat) {
            fm = new FrameMenu(this, "Convidat");
        }
        else {
            fm = new FrameMenu(this, uc.getLoggedUser().getUsername());
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
    
    public void obrirEditor(JFrame antic) {
        FrameEditor fe = new FrameEditor(this, hmc);
        fe.setVisible(true);
        fe.setLocation(antic.getLocation());
        antic.dispose();
    }
    
    public void obrirStats(JFrame antic) {
        FrameStats fs = new FrameStats(this, uc, hmc);
        fs.setVisible(true);
        fs.setLocation(antic.getLocation());
        antic.dispose();
    }
    
    public void obrirPartida(JFrame antic, Help h, String gameName) {
        FrameGame fg = new FrameGame(this, rc, uc, gmc, h, gameName);
        fg.setVisible(true);
        fg.setLocation(antic.getLocation());
        antic.dispose();
    }
    
    public void obrirRanking(JFrame antic){
        FrameRanking fr = new FrameRanking();
        fr.inicialitza(rc);
        fr.setVisible(true);
        fr.setLocation(antic.getLocation());
        antic.dispose();
    }
    
    public void obrirNewGameType(JFrame antic){
        FrameNewGameType fngt = new FrameNewGameType(this);
        fngt.setVisible(true);
        fngt.setLocation(antic.getLocation());
        antic.dispose();
    }
    
    public void obrirLlistaHidatos(JFrame antic){
        //FrameLlista flh = new FrameLlista();
    }
}
