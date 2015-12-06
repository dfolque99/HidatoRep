/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini;

import CapaDomini.Partida.GameManagerController;
import CapaDomini.Rank.RankingController;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Tauler.HidatoSet;
import CapaDomini.Usuari.HidatoUserController;
import CapaPresentacio.FrameEditor;
import CapaPresentacio.FrameLogin;
import CapaPresentacio.FrameMenu;
import CapaPresentacio.FrameStats;
import javax.swing.JFrame;

/**
 *
 * @author David
 */
public class Domini {
    
    HidatoUserController uc;
    HidatoManagerController hmc;
    HidatoSet hs;
    GameManagerController gmc;
    RankingController rc;
    
    public Domini(HidatoUserController uc, RankingController rc, GameManagerController gmc, HidatoManagerController hmc){
        this.uc = uc;
        this.rc = rc;
        this.gmc = gmc;
        this.hmc = hmc;   
    }
    
    public void adelanteee() {
        this.uc = new HidatoUserController();
        this.rc = new RankingController();
        this.gmc = new GameManagerController(null, null, null, rc, uc);
        this.hmc = new HidatoManagerController(hs, gmc, uc);
        hmc.loadAll();
        FrameLogin fl = new FrameLogin(this, uc);
        fl.setLocationByPlatform(true);
        fl.setVisible(true);
        hmc.veure();
    }
    
    public void obrirMenu(JFrame antic) {
        FrameMenu fm = new FrameMenu(this, uc.getLoggedUser().getUsername());
        fm.setVisible(true);
        fm.setLocation(antic.getLocation());
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
}
