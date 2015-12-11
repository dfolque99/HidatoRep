package CapaPresentacio;
import CapaDomini.Misc.Fonts;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Usuari.UserController;
import CapaDomini.Usuari.UserStatsController;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JOptionPane;
/**
 * Profile management use case
 * <p>
 * To use do: new FrameStats(uc,hmc).setVisible(true); 
 * where uc is a valid usercontroller
 * 
 * @author felix.axel.gimeno
 * @version 0.30
 * @since 05-12-2015
 */
@SuppressWarnings("serial")
public class FrameStats extends javax.swing.JFrame {
    private final static Integer sizeX = 800; 
    private final static Integer sizeY = 600;
    private final static Font myFont = Fonts.getFont("OpenSans-Light", Font.PLAIN, 18);
    private static String convertToMultiline(final String orig) {return "<html><font face=\"OpenSans-Light\">"+orig.replaceAll("\n", "<br>")+"</font></html>";}
    private static String convertToString(String[] myArray){
        String ret = ""; 
        for (String s : myArray) {ret = ret.concat(s).concat("\n\n");}
        return ret;
    }
    private static String[] getStats(final UserController uc) {
        CapaDomini.Usuari.User myUser = uc.getLoggedUser();
        if (myUser instanceof CapaDomini.Usuari.HidatoUser){
            CapaDomini.Usuari.HidatoUser myHidatoUser = (CapaDomini.Usuari.HidatoUser) myUser;    
            UserStatsController usc = new UserStatsController(myHidatoUser);
            return new String[]{"Estadistiques de l'usuari: "+uc.getLoggedUser().getUsername(),
                "Puntuació mitjana: "+String.valueOf(usc.getAverageScore()),
                "Temps mitja per resoldre (en segons): "+String.valueOf(usc.getAverageTimePerSolve().getSeconds()),
                "Quantitat de hidatos resolts: "+String.valueOf(usc.getSolvedGames()),
                "Percentatge de hidatos resolts: "+String.valueOf(usc.getSolvedPercentage()),
                "Quantitat de hidatos començats: "+String.valueOf(usc.getStartedGames()),
                "Quantitat de hidatos creats: "+String.valueOf(usc.getTotalCreatedBoards()),
                "Puntuació total: "+String.valueOf(usc.getTotalScore()),
                "Temps total jugant: "+String.valueOf(usc.getTotalTimePlayed().getSeconds())
            };
        } else {
            return new String[]{"El cast a HidatoUSer ha fallat"};
        }
    }
    private static javax.swing.JButton createButton(final String text, final java.awt.event.ActionListener l) {
        javax.swing.JButton myButton = new javax.swing.JButton(text);
        myButton.setFont(myFont);
        myButton.addActionListener(l);
        return myButton;
    }

    private static String askPassword(javax.swing.JFrame myFrame, String extra) {    
        javax.swing.JPanel panel = new javax.swing.JPanel();
        javax.swing.JLabel label = new javax.swing.JLabel("Introdueix contrasenya "+extra+" :");
        javax.swing.JPasswordField pass = new javax.swing.JPasswordField(20);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, null,
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);
        if (0 == option) {return new String(pass.getPassword());}
        return null;
    }

    private static void buttonDeleteActionPerformed(javax.swing.JFrame myJFrame, final UserController uc) {
        String Password = FrameStats.askPassword(myJFrame, "actual \ni pressiona ok per borrar l'usuari, \npressiona cancel per cancelar");
        Boolean truePass = uc.getLoggedUser().getPassword().equals(Password);
        if (truePass) {
            uc.deleteUser(Password);
            JOptionPane.showMessageDialog(myJFrame,"Usuari borrat","Usuari borrat",JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        } else if (null != Password) {
            JOptionPane.showMessageDialog(myJFrame,"Contraseñya incorrecta","Contraseñya incorrecta",JOptionPane.ERROR_MESSAGE);
        }
    }
    private HidatoManagerController hmc;
    private CapaDomini.Domini parent;
    private String hidatoName;
    private FrameStats(){throw new UnsupportedOperationException();}
    
    public FrameStats(CapaDomini.Domini parent, final UserController uc, HidatoManagerController hmc){
        super("Gestió del perfil de l'usuarie");
        this.parent = parent;
        this.hmc = hmc;
        this.parent = parent;
        this.initComponents(uc);
    }
    private void buttonModifyPasswordActionPerformed(final UserController uc){
        String OldPassword = FrameStats.askPassword(this,"actual");
        Boolean truePass = uc.getLoggedUser().getPassword().equals(OldPassword);
        if (truePass) {
            String NewPassword = FrameStats.askPassword(this,"nova");
            if (null != NewPassword){
                uc.modifyPassword(OldPassword, NewPassword);
                JOptionPane.showMessageDialog(this,"Contraseña canviada","Contraseña canviada",JOptionPane.PLAIN_MESSAGE);
            }
            
        } else if (null != OldPassword) {
            JOptionPane.showMessageDialog(this,"Contraseñya incorrecta","Contraseñya incorrecta",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    private Object[] getHidatos(final UserController uc){
        String[] ret = hmc.getHidatoList().toArray(new String[]{});
        return ret;
    }
    
    private void buttonSelectHidatoToEditActionPerformed(final UserController uc){
        Object[] myList = getHidatos(uc);
        
        if (myList.length > 0) {
            //String hidatoName = (String)JOptionPane.showInputDialog(this,"Select Hidato To Edit","Select Hidato To Edit",JOptionPane.QUESTION_MESSAGE, null,myList,myList[0]);
            hidatoName = null;
            FrameLlista fll = new FrameLlista( (String s) -> {obreHidato(s);}, hmc);
            fll.setLocation(this.getLocation());
            fll.loadHidatosUsuari();
            fll.setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this,"No hidatos trobats","L'usuari no te hidatos guardats",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void obreHidato(String s) {
        hidatoName = s;
        if (null != hidatoName) {
            boolean b = hmc.loadHidato(hidatoName);
            if (b) {
                //hmc.getTempSizeX(); 
                parent.obrirEditor(this); 
            } else {
                JOptionPane.showMessageDialog(this,"No se ha pogut carregar","No se ha pogut carregar",JOptionPane.PLAIN_MESSAGE);    
            }
        }
    }
    
    @SuppressWarnings(value = "unchecked")
    private void initComponents(final UserController uc) {
        javax.swing.JLabel myJLabel = new javax.swing.JLabel(convertToMultiline( convertToString(FrameStats.getStats(uc)) ) ,  javax.swing.SwingConstants.CENTER); //SwingConstants.CENTER is for centering the jlabel text
        
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new java.awt.Dimension(sizeX,sizeY));
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        this.getContentPane().setLayout(layout);
        
        final JButton buttonReturnToMenu = createButton("Volver al Menu",(java.awt.event.ActionEvent evt) -> {parent.obrirMenu(this);});
        final JButton buttonDelete = createButton("Borrar usuari",(java.awt.event.ActionEvent evt) -> {buttonDeleteActionPerformed(this,uc);});   
        final JButton buttonModifyPassword = createButton("Canviar contrasenya",(java.awt.event.ActionEvent evt) -> {buttonModifyPasswordActionPerformed(uc);});
        final JButton buttonSelectHidatoToEdit = createButton("Editar hidato guardat", (java.awt.event.ActionEvent evt) -> {buttonSelectHidatoToEditActionPerformed(uc);});
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(myJLabel)
                        .addGap(30) //to separate the list scroll from the buttons, to beautify
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, false) //center alignment;  false for not enabling resizing the group to expand all it can
                                .addComponent(buttonReturnToMenu,javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonDelete,             javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonModifyPassword,     javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonSelectHidatoToEdit, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        )
                        .addGap(30)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonReturnToMenu)
                                .addComponent(buttonDelete)
                                .addComponent(buttonModifyPassword)
                                .addComponent(buttonSelectHidatoToEdit)
                        )
                        .addComponent(myJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    
        this.pack();
    }

}
