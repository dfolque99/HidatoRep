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
    
    public void adelanteee() {
        this.uc = new HidatoUserController();
        this.hs = new HidatoSet();
        this.rc = new RankingController();
        this.gmc = new GameManagerController(hs, null, null, rc, uc);
        this.hmc = new HidatoManagerController(hs, gmc, uc);
        hmc.loadAll();
        FrameLogin fl = new FrameLogin(this);
        fl.setUc(uc);
        fl.setVisible(true);
        System.out.printf("%d hidatos\n",hs.getTotalHidatos());
    }
    
    public void obrirMenu(JFrame antic) {
        antic.dispose();
        FrameMenu fm = new FrameMenu(this, uc.getLoggedUser().getUsername());
        fm.setVisible(true);
    }
    
    public void obrirEditor(JFrame antic) {
        antic.dispose();
        FrameEditor fe = new FrameEditor(hmc);
        fe.setVisible(true);
    }
    
    public void obrirStats(JFrame antic) {
        antic.dispose();
        FrameStats fs = new FrameStats(uc, hmc);
        fs.setVisible(true);
    }
}
