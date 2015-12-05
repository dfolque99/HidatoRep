/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini;

import CapaDomini.Usuari.UserController;
import CapaPresentacio.FrameLogin;

/**
 *
 * @author David
 */
public class Domini {
    public void adelanteee() {
        UserController uc = new UserController();
        FrameLogin fl = new FrameLogin();
        fl.setUc(uc);
        fl.setVisible(true);
    }
}
