package CapaPresentacio;

import CapaDomini.Misc.Fonts;
import CapaDomini.Partida.GameManagerController;
import CapaDomini.Rank.RankingController;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Usuari.HidatoUserController;
import CapaDomini.Usuari.UserController;
import CapaDomini.Usuari.UserStatsController;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Profile management use case
 * <p>
 * To use do: new FrameStats(uc,hmc).setVisible(true); where uc is a valid
 * usercontroller
 *
 * @author felix.axel.gimeno
 * @version 0.33
 * @since 05-12-2015
 */
@SuppressWarnings("serial")
public class FrameStats extends javax.swing.JFrame {

    /**
     * sizeX of window, constant, for size of frame
     */
    private final static Integer sizeX = 800;
    /**
     * sizeY of window, constant, for size of frame
     */
    private final static Integer sizeY = 600;
    /**
     * font for texts
     */
    private final static Font myFont = Fonts.getFont("OpenSans-Light", Font.PLAIN, 18);

    /**
     *
     * @param uc of the logged user
     * @return string array of statistics
     */
    private static String[] getStats(final HidatoUserController uc) {
        UserStatsController usc = uc.getStatsController();
        String[] ret = new String[]{"Estadístiques de " + usc.getUsername(),
            "Hidatos resolts: " + String.valueOf(usc.getSolvedGames()),
            "Percentatge de resolts: ",
            "Puntuació mitjana: ",
            "Puntuació màxima en un hidato: ",
            "Temps de resolució mitjà: ",
            "Resolució més ràpida d'un hidato: ",
            "Hidatos creats: " + String.valueOf(usc.getTotalCreatedBoards())
        };
        if (usc.getSolvedGames() != 0) {
            String aux = String.format("%.2f", usc.getSolvedPercentage());
            ret[2] += aux + "%";
            ret[3] += String.valueOf(usc.getAverageScore()) + " punts";
            ret[4] += String.valueOf(usc.getBestScore()) + " punts";
            ret[5] += String.valueOf(usc.getAverageTimePerSolve() / 60) + " minuts, "
                    + usc.getAverageTimePerSolve() % 60 + " segons";
            ret[6] += String.valueOf(usc.getBestTime() / 60) + " minuts, "
                    + String.valueOf(usc.getBestTime() % 60) + " segons";
        } else {
            ret[2] += "--";
            ret[3] += "--";
            ret[4] += "--";
            ret[5] += "--";
            ret[6] += "--";
        }
        return ret;
    }

    /**
     * create a button
     *
     * @param text text of the button
     * @param l when button pressed do
     * @return created button
     */
    private static javax.swing.JButton createButton(final String text, final java.awt.event.ActionListener l) {
        javax.swing.JButton myButton = new javax.swing.JButton(text);
        myButton.setFont(myFont);
        myButton.addActionListener(l);
        return myButton;
    }

    /**
     * ask the user for password usiang an JOptionPane.showOptionDialog and a
     * KPasswordField
     *
     * @param myFrame frame for the dialog
     * @param extra extra text for the dialog
     * @return user input in the password field, or null if cancel
     */
    private static String askPassword(javax.swing.JFrame myFrame, String extra) {
        javax.swing.JPanel panel = new javax.swing.JPanel();
        javax.swing.JLabel label = new javax.swing.JLabel("Introdueix contrasenya " + extra + ":");
        javax.swing.JPasswordField pass = new javax.swing.JPasswordField(20);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel·lar"};
        int option = JOptionPane.showOptionDialog(myFrame, panel, null,
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);
        if (0 == option) {
            return new String(pass.getPassword());
        }
        return null;
    }
    /**
     * internal value
     */
    private GameManagerController gmc;
    /**
     * internal value
     */
    private HidatoManagerController hmc;
    /**
     * internal value
     */
    private CapaPresentacio.AdminVistes parent;
    /**
     *
     */
    private HidatoUserController uc;
    /**
     * 
     */
    private RankingController rc;
    
    /**
     * disabled
     */
    private FrameStats() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param parent
     * @param uc
     * @param hmc
     * @param gmc
     */
    public FrameStats(CapaPresentacio.AdminVistes parent, final HidatoUserController uc, HidatoManagerController hmc, GameManagerController gmc, RankingController rc) {
        super("Gestió del perfil d'usuari");
        this.parent = parent;
        this.gmc = gmc;
        this.hmc = hmc;
        this.uc = uc;
        this.rc = rc;
        this.parent = parent;
        this.initComponents();
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                parent.saveBeforeClose();
            }
        });
    }

    /**
     * button delete user
     */
    private void buttonDeleteActionPerformed() {
        String Password = FrameStats.askPassword(this, "actual \ni pressiona \"OK\" per esborrar l'usuari, \npressiona \"Cancel·la\" per cancelar");
        Boolean truePass = uc.getLoggedUser().getPassword().equals(Password);
        if (truePass) {
            gmc.deleteAllGames();
            hmc.renameUserHidatos();
            rc.rename(uc.getLoggedUser().getUsername());
            uc.deleteUser(Password);
            JOptionPane.showMessageDialog(this, "Usuari esborrat", "Usuari esborrat", JOptionPane.PLAIN_MESSAGE);
            parent.saveBeforeClose();
            System.exit(0);
        } else if (null != Password) {
            JOptionPane.showMessageDialog(this, "Contrasenya incorrecta", "Contrasenya incorrecta", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * button modify passwrod user
     */
    private void buttonModifyPasswordActionPerformed() {
        String OldPassword = FrameStats.askPassword(this, "actual");
        Boolean truePass = uc.getLoggedUser().getPassword().equals(OldPassword);
        if (truePass) {
            String NewPassword = FrameStats.askPassword(this, "nova");
            if (null != NewPassword) {
                uc.modifyPassword(OldPassword, NewPassword);
                JOptionPane.showMessageDialog(this, "Contrasenya canviada", "Contrasenya canviada", JOptionPane.PLAIN_MESSAGE);
            }

        } else if (null != OldPassword) {
            JOptionPane.showMessageDialog(this, "Contrasenya incorrecta", "Contrasenya incorrecta", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     *
     * @return string[] of hidato names of user
     */
    private Object[] getHidatos() {
        return hmc.getUserHidatoList().toArray(new String[]{});
    }

    /**
     * button select hidato user
     */
    private void buttonSelectHidatoToEditActionPerformed() {
        if (getHidatos().length > 0) {
            obrirLlista();
        } else {
            JOptionPane.showMessageDialog(this, "Cap hidato trobat", "L'usuari no té hidatos guardats", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @see FrameLLista
     * @see RetornadorString
     */
    private void obrirLlista() {
        FrameLlista fll = new FrameLlista(new RetornadorString() {
            public void retorna(String s) {
                openSelectedHidato(s);
            }

            public void elimina(String s) {
                deleteHidato(s);
            }

            public void canviaNom(String oldName, String newName) {
                renameHidato(oldName, newName);
            }
        }, hmc);
        fll.setLocation(this.getLocation());
        fll.loadHidatosUsuari();
        fll.setVisible(true);
        this.setVisible(false);
    }

    /**
     *
     * @param name
     */
    private void deleteHidato(String name) {
        hmc.deleteHidato(name);
        Object[] myList = getHidatos();
        if (myList.length > 0) {
            obrirLlista();
        } else {
            this.setVisible(true);
        }
    }

    /**
     *
     * @param oldName
     * @param newName
     */
    private void renameHidato(String oldName, String newName) {
        hmc.renameHidato(oldName, newName);
        buttonSelectHidatoToEditActionPerformed();
    }

    /**
     *
     * @param hidatoName
     */
    private void openSelectedHidato(String hidatoName) {
        if (hidatoName == null) {
            this.setVisible(true);
        } else {
            if (hmc.loadHidato(hidatoName)) {
                parent.obrirEditor(this);
            } else {
                JOptionPane.showMessageDialog(this, "No s'ha pogut carregar", "No s'ha pogut carregar", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    /**
     *
     * @param uc
     */
    @SuppressWarnings(value = "unchecked")
    private void initComponents() {
        javax.swing.JLabel[] myJLabels = new javax.swing.JLabel[8]; //SwingConstants.CENTER is for centering the jlabel text
        String[] text = getStats(uc);
        for (int i = 0; i < 8; ++i) {
            myJLabels[i] = new javax.swing.JLabel(text[i], javax.swing.SwingConstants.CENTER);
            myJLabels[i].setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 18));
        }
        myJLabels[0].setFont(Fonts.getFont("OpenSans-Light", Font.PLAIN, 36));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new java.awt.Dimension(sizeX, sizeY));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        this.getContentPane().setLayout(layout);

        final JButton buttonReturnToMenu = createButton("Tornar al Menú", (java.awt.event.ActionEvent evt) -> {
            parent.obrirMenu(this);
        });
        final JButton buttonDelete = createButton("Esborrar usuari", (java.awt.event.ActionEvent evt) -> {
            buttonDeleteActionPerformed();
        });
        final JButton buttonModifyPassword = createButton("Canviar contrasenya", (java.awt.event.ActionEvent evt) -> {
            buttonModifyPasswordActionPerformed();
        });
        final JButton buttonSelectHidatoToEdit = createButton("Editar hidato guardat", (java.awt.event.ActionEvent evt) -> {
            buttonSelectHidatoToEditActionPerformed();
        });

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, true)
                        .addComponent(myJLabels[0], javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myJLabels[1], javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myJLabels[2], javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myJLabels[3], javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myJLabels[4], javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myJLabels[5], javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myJLabels[6], javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myJLabels[7], javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, true)
                        .addComponent(buttonSelectHidatoToEdit, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonModifyPassword, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonDelete, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonReturnToMenu, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonSelectHidatoToEdit)
                        .addComponent(buttonModifyPassword)
                        .addComponent(buttonDelete)
                        .addComponent(buttonReturnToMenu)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(myJLabels[0], javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(myJLabels[1], javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(myJLabels[2], javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(myJLabels[3], javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(myJLabels[4], javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(myJLabels[5], javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(myJLabels[6], javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(myJLabels[7], javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                )
        );

        this.pack();
        setIconImage((new ImageIcon("icon.png")).getImage());
    }

}
