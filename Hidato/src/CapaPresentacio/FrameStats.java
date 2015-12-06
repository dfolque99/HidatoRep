package CapaPresentacio;
import CapaDomini.Tauler.HidatoManagerController;
import CapaDomini.Tauler.HidatoSet;
import CapaDomini.Usuari.HidatoUser;
import CapaDomini.Usuari.HidatoUserController;
import CapaDomini.Usuari.UserController;
import CapaDomini.Usuari.UserStatsController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
/**
 * Profile management use case
 * <p>
 * To use do: new FrameStats(uc,hmc).setVisible(true); 
 * where uc is a valid usercontroller
 * 
 * TO DO: getStats beautify, 
 * 
 * @author felix.axel.gimeno
 * @version 0.27
 * @since 05-12-2015
 */
@SuppressWarnings("serial")
public final class FrameStats extends javax.swing.JFrame {
    private final static Integer sizeX = 800; 
    private final static Integer sizeY = 600; 
    private static String convertToMultiline(final String orig) {return "<html>" + orig.replaceAll("\n", "<br>");}
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
            return new String[]{"Statistics of user: "+uc.getLoggedUser().getUsername(),
                "getAverageScore: "+String.valueOf(usc.getAverageScore()),
                "getAverageTimePerSolve: "+String.valueOf(usc.getAverageTimePerSolve().getSeconds()),
                "getSolvedGames: "+String.valueOf(usc.getSolvedGames()),
                "getSolvedPercentage: "+String.valueOf(usc.getSolvedPercentage()),
                "getStartedGames: "+String.valueOf(usc.getStartedGames()),
                "getTotalCreatedBoards: "+String.valueOf(usc.getTotalCreatedBoards()),
                "getTotalScore: "+String.valueOf(usc.getTotalScore()),
                "getTotalTimePlayed: "+String.valueOf(usc.getTotalTimePlayed().getSeconds())
            };
        } else {
            return new String[]{"El cast a HidatoUSer ha fallado"};
        }
    }
    private static javax.swing.JButton createButton(final String text, final java.awt.event.ActionListener l) {
        javax.swing.JButton myButton = new javax.swing.JButton(text);
        myButton.addActionListener(l);
        return myButton;
    }
    private HidatoManagerController hmc;
    private CapaDomini.Domini parent;
    private FrameStats(){throw new UnsupportedOperationException();}
    
    public FrameStats(CapaDomini.Domini parent, final UserController uc, HidatoManagerController hmc){
        super("Profile management use case");
        this.parent = parent;
        this.hmc = hmc;
        this.parent = parent;
        this.initComponents(uc);
    }
    
    private static void buttonDeleteActionPerformed(javax.swing.JFrame myJFrame,final UserController uc){
        String Password = JOptionPane.showInputDialog(myJFrame,"Introduce current password \nand press okay to delete current logged user, \npress cancel to cancel",null);
        Boolean truePass = uc.getLoggedUser().getPassword().equals(Password);
        if (truePass) {
            uc.deleteUser(Password);
            JOptionPane.showMessageDialog(myJFrame,"User deleted","User deleted",JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        } else if (null != Password) {
            JOptionPane.showMessageDialog(myJFrame,"Incorrect password","Incorrect password",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buttonModifyPasswordActionPerformed(final UserController uc){
        String OldPassword = JOptionPane.showInputDialog(this,"Introduce current password",null); 
        Boolean truePass = uc.getLoggedUser().getPassword().equals(OldPassword);
        if (truePass) {
            String NewPassword = JOptionPane.showInputDialog(this,"Introduce new password",null); 
            uc.modifyPassword(OldPassword, NewPassword);
            JOptionPane.showMessageDialog(this,"Change done correctly","Change done correctly",JOptionPane.PLAIN_MESSAGE);
            
        } else if (null != OldPassword) {
            JOptionPane.showMessageDialog(this,"Incorrect password","Incorrect password",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    private Object[] getHidatos(final UserController uc) {
        String[] ret = hmc.getHidatoList().toArray(new String[]{});
        return ret;
    }
    
    private void buttonSelectHidatoToEditActionPerformed(final UserController uc) {
        Object[] myList = getHidatos(uc);
        
        if (myList.length > 0) {
            String hidatoName = (String)JOptionPane.showInputDialog(this,"Select Hidato To Edit","Select Hidato To Edit",JOptionPane.QUESTION_MESSAGE, null,myList,myList[0]);
            if (null != hidatoName) {
                boolean b = false;
                try {
                    b = hmc.loadHidato(hidatoName);
                    if (b) {
                        try {hmc.getTempSizeX();} 
                        catch (java.lang.NullPointerException e) {LOG.log(Level.SEVERE,"buttonSelectHidatoToEditActionPerformed  hmc.getTempSizeX");}
                        try {parent.obrirEditor(this);} 
                        catch (Exception e){LOG.log(Level.SEVERE,"buttonSelectHidatoToEditActionPerformed ",e);}
                        JOptionPane.showMessageDialog(this,"There was an error: See log","There was an error: See log",JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,"The selecte Hidato couldn't be loaded correctly","The selecte Hidato couldn't be loaded correctly",JOptionPane.PLAIN_MESSAGE);    
                    }
                } catch (Exception e) {
                    LOG.log(Level.SEVERE,"Edición de Hidato falló: b "+b,e);
                }
            } else {
                //JOptionPane.showMessageDialog(this,"You didn't select anything","You didn't select anything",JOptionPane.PLAIN_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,"No hidatos found","The logged user has no hidatos",JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings(value = "unchecked")
    private void initComponents(final UserController uc) {
        javax.swing.JLabel myJLabel = new javax.swing.JLabel(convertToMultiline( convertToString(FrameStats.getStats(uc)) ) ,  javax.swing.SwingConstants.CENTER); //SwingConstants.CENTER is for centering the jlabel text
        
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new java.awt.Dimension(sizeX,sizeY));
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        this.getContentPane().setLayout(layout);
        
        final JButton buttonReturnToMenu = createButton("Return To Menu",(java.awt.event.ActionEvent evt) -> {parent.obrirMenu(this);});   
        final JButton buttonDelete = createButton("Delete User",(java.awt.event.ActionEvent evt) -> {buttonDeleteActionPerformed(this,uc);});   
        final JButton buttonModifyPassword = createButton("Modify Password User",(java.awt.event.ActionEvent evt) -> {buttonModifyPasswordActionPerformed(uc);});
        final JButton buttonSelectHidatoToEdit = createButton("Edit Already Created Hidato", (java.awt.event.ActionEvent evt) -> {buttonSelectHidatoToEditActionPerformed(uc);});
        
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
    private static final Logger LOG = Logger.getLogger(FrameStats.class.getName());
}
