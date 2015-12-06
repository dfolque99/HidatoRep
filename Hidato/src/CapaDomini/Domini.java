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
        FrameLogin fl = new FrameLogin();
        fl.setUc(uc);
        fl.setVisible(true);
    }
    
    public void obrirEditor() {
        FrameEditor fe = new FrameEditor(hmc);
        fe.setVisible(true);
    }
}
