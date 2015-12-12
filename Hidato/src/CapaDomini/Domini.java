/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini;

import CapaDomini.Partida.CurrentGameController;
import CapaDomini.Partida.GameManagerController;
import CapaDomini.Partida.Help;
import CapaDomini.Rank.RankingController;
import CapaDomini.Tauler.Hidato;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Tauler.HidatoSet;
import CapaDomini.Usuari.HidatoUserController;
import CapaPresentacio.FrameEditor;
import CapaPresentacio.FrameGame;
import CapaPresentacio.FrameLlista;
import CapaPresentacio.FrameLlistaPartides;
import CapaPresentacio.FrameLogin;
import CapaPresentacio.FrameMenu;
import CapaPresentacio.FrameRanking;
import CapaPresentacio.FrameStats;
import CapaPresentacio.RetornadorString;
import java.awt.Point;
import java.io.File;
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
        File f = new File("Users/");
        f.mkdir();
        this.hs = new HidatoSet();
        this.uc = new HidatoUserController();
        this.rc = new RankingController();
        rc.init();
        this.hmc = new HidatoManagerController(hs, uc);
        this.gmc = new GameManagerController(rc, uc, hmc);
        hmc.setGameManagerController(gmc);
        hmc.loadAll();
        convidat = false;
        FrameLogin fl = new FrameLogin(this, uc, gmc);
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
    
    public void obrirPartida(JFrame antic, CurrentGameController cgc) {
        FrameGame fg = new FrameGame(this, cgc, hmc);
        fg.setVisible(true);
        fg.setLocation(antic.getLocation());
        antic.dispose();
    }
    
    public void obrirRanking(JFrame antic){
        FrameRanking fr = new FrameRanking(this,rc);
        fr.setVisible(true);
        fr.setLocation(antic.getLocation());
        antic.dispose();
    }
    
    
    public void obrirLlista(JFrame antic){
        FrameLlista flh = new FrameLlista(new RetornadorString(){
            @Override
            public void retorna(String s){
                System.out.println("Retorna la string "+s);
            }
        },hmc);
        flh.setVisible(true);
        flh.setLocation(antic.getLocation());
        antic.dispose();
    }
    
    public void obrirLlistaPartides(JFrame antic){
        FrameLlistaPartides flp = new FrameLlistaPartides(new RetornadorString(){
            @Override
            public void retorna(String s){
                System.out.println("Retorna la string "+s);
            }
        },gmc,hmc);
        flp.setVisible(true);
        flp.setLocation(antic.getLocation());
        antic.dispose();
    }
    
    public void saveBeforeClose() {
        rc.save();
        hmc.saveAll();
    }
}
