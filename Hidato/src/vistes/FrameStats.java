package vistes;
import domini.Usuari.HidatoUser;
import domini.Usuari.HidatoUserController;
import domini.Usuari.UserController;
import domini.Usuari.UserStatsController;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * Profile management use case
 * 
 * @author felix.axel.gimeno
 * @version 0.2
 * @since 05-12-2015
 */
@SuppressWarnings("serial")
public final class FrameStats extends javax.swing.JFrame {
    private static String[] getStats(final UserController uc) {
        domini.Usuari.User myUser = uc.getLoggedUser();
        if (myUser instanceof domini.Usuari.HidatoUser){
            domini.Usuari.HidatoUser myHidatoUser = (domini.Usuari.HidatoUser) myUser;    
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
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(() -> {
            HidatoUserController huc = new HidatoUserController();
            huc.createUser("chuu","chuu");
            huc.login("chuu","chuu");
            huc.updateUser();
            new FrameStats(huc).setVisible(true);
        });
    }
    private static javax.swing.JButton createButton(final String text, final java.awt.event.ActionListener l) {
        javax.swing.JButton myButton = new javax.swing.JButton(text);
        myButton.addActionListener(l);
        return myButton;
    }
    private FrameStats() {
        throw new UnsupportedOperationException();
    }
    /**
     *Profile management use case : Graphical USer Interface
     * @param uc UserController of current logged (hidato)user
     */
    public FrameStats(final UserController uc) {
        super("Profile management use case");
        initComponents(uc);
    }
    private void buttonDeleteActionPerformed(final UserController uc){
        String Password = JOptionPane.showInputDialog(this,"Introduce current password \nand press okay to delete current logged user, \npress cancel to cancel",null);
        Boolean truePass = uc.getLoggedUser().getPassword().equals(Password);
        if (truePass) {
            uc.deleteUser(Password);
            JOptionPane.showMessageDialog(this,"User deleted","User deleted",JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        } else if (null != Password) {
            JOptionPane.showMessageDialog(this,"Incorrect password","Incorrect password",JOptionPane.ERROR_MESSAGE);
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
    private void buttonSelectHidatoToEditActionPerformed(final UserController uc){
        JOptionPane.showMessageDialog(this,"No implementada todavía","To do",JOptionPane.ERROR_MESSAGE);
    }
    @SuppressWarnings(value = "unchecked")
    private void initComponents(final UserController uc) {
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jScrollPane1.setViewportView(new javax.swing.JList<String>(FrameStats.getStats(uc)));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        JButton buttonDelete = createButton("Delete User",(java.awt.event.ActionEvent evt) -> {buttonDeleteActionPerformed(uc);});   
        JButton buttonModifyPassword = createButton("Modify Password User",(java.awt.event.ActionEvent evt) -> {buttonModifyPasswordActionPerformed(uc);});
        JButton buttonSelectHidatoToEdit = createButton("Edit Already Created Hidato", (java.awt.event.ActionEvent evt) -> {buttonSelectHidatoToEditActionPerformed(uc);});
        
        JPanel myButtonPanel = new JPanel(); 
        myButtonPanel.add(buttonDelete);
        myButtonPanel.add(buttonModifyPassword);
        myButtonPanel.add(buttonSelectHidatoToEdit);
        
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(myButtonPanel)
                        .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(myButtonPanel)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    
        pack();
    }
}