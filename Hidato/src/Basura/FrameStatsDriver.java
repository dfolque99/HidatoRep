package Basura;

import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Tauler.HidatoSet;
import CapaDomini.Usuari.HidatoUser;
import CapaDomini.Usuari.HidatoUserController;
import CapaPresentacio.FrameStats;

/**
 * @version 3
 * @author felix.axel.gimeno
 */
public class FrameStatsDriver {

    public static void main(String[] args) {
        final String foo = "ghyy";
        final String name = "Hidato" + foo + "9";

        java.awt.EventQueue.invokeLater(() -> {
            HidatoUserController huc;
            HidatoManagerController hmc;

            try {
                huc = new HidatoUserController();

                huc.createUser(foo, foo);
                huc.login(foo, foo);
                huc.updateUser();

                ((HidatoUser) huc.getLoggedUser()).addHidato(name);

                hmc = new HidatoManagerController(new HidatoSet(), huc);

                hmc.loadAll();
                hmc.createRandom(3, 3);
                hmc.saveTempHidato(name);
                hmc.saveAll();
                hmc.loadAll();

                //((HidatoUser)huc.getLoggedUser()).addHidato("felix");
                /*CapaDomini.Tauler.Hidato h = new CapaDomini.Tauler.Hidato(5,5);
                 h.setBoardName(name);
                 hs.addHidato(h);
                 */
                System.out.println(hmc.loadHidato(name));
                //CapaDomini.Domini d = ;
                //d.hmc = hmc;
                new FrameStats(new CapaPresentacio.AdminVistes(huc, null, null, hmc), huc, hmc).setVisible(true);
                //hacer new CapaDomini.Domini() hace que FrameEditor tenga hmc distinto del de esta funcion
            } catch (Exception e) {
                System.out.println("Couldn't do login");
            }

        });

    }

}
