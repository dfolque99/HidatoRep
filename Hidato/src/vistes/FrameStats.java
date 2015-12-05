package vistes;
import domini.Usuari.HidatoUser;
import domini.Usuari.HidatoUserController;
import domini.Usuari.UserController;
import domini.Usuari.UserStatsController;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * @author felix.axel.gimeno
 */
@SuppressWarnings("serial")
public final class FrameStats extends javax.swing.JFrame {
    private static String[] getStats(UserController uc) {
        UserStatsController usc = new UserStatsController((HidatoUser)uc.getLoggedUser());
        //return new String[]{"No va"};
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
        
    }
    
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(() -> {
            HidatoUserController huc = new HidatoUserController();
            huc.createUser("zu","zu");
            huc.login("zu","zu");
            huc.updateUser();
            new FrameStats(huc).setVisible(true);
        });
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration                   

    private FrameStats() {
        throw new UnsupportedOperationException();
    }

    
    public FrameStats(UserController uc) {
        super("Statistics and others");
        initComponents(uc);
    }

    private void buttonDeleteActionPerformed(UserController uc) {
        JOptionPane.showMessageDialog(this,"No implementada todavía","No encara",JOptionPane.ERROR_MESSAGE);
        /*JDialog d = new JDialog(this, "No implementada todavía", true);
        d.setLocationRelativeTo(this);
        d.setVisible(true);*/
    }
    private void buttonModifyPasswordActionPerformed(UserController uc){
        String OldPassword = JOptionPane.showInputDialog(this,"Introduce current password",null); 
        Boolean truePass = uc.getLoggedUser().getPassword().equals(OldPassword);
        if (truePass) {
            String NewPassword = JOptionPane.showInputDialog(this,"Introduce new password",null); 
            uc.modifyPassword(OldPassword, NewPassword);
            JOptionPane.showMessageDialog(this,"Change done correctly","Change done correctly",JOptionPane.PLAIN_MESSAGE);
            
        } else if (null != OldPassword) {
            JOptionPane.showMessageDialog(this,"Invalid password for this user","Invalid password for this user",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    private void buttonSelectHidatoToEditActionPerformed(UserController uc){
        JOptionPane.showMessageDialog(this,"No implementada todavía","To do",JOptionPane.ERROR_MESSAGE);
    } 
    
    @SuppressWarnings(value = "unchecked")
    private void initComponents(UserController uc) {
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = getStats(uc);
            @Override
            public int getSize() { return strings.length; }
            @Override
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        JButton buttonDelete = new JButton("Delete User");
        buttonDelete.addActionListener((java.awt.event.ActionEvent evt) -> {buttonDeleteActionPerformed(uc);});
 
        JButton buttonModifyPassword = new JButton("Modify Password User");
        buttonModifyPassword.addActionListener((java.awt.event.ActionEvent evt) -> {buttonModifyPasswordActionPerformed(uc);});
  
        JButton buttonSelectHidatoToEdit = new JButton("Edit Already Created Hidato");
        buttonSelectHidatoToEdit.addActionListener((java.awt.event.ActionEvent evt) -> {buttonSelectHidatoToEditActionPerformed(uc);});
         
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
