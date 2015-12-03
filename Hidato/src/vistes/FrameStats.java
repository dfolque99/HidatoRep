package vistes;
import domini.Usuari.HidatoUser;
import domini.Usuari.HidatoUserController;
import domini.Usuari.UserController;
import domini.Usuari.UserStatsController;
import java.awt.*;
import javax.swing.*;
/**
 * @author felix.axel.gimeno
 */
public class FrameStats extends javax.swing.JFrame {
    private static String[] getStats(UserController uc) {
        UserStatsController usc = new UserStatsController((HidatoUser)uc.getLoggedUser());
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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                HidatoUserController uc = new HidatoUserController();
                uc.createUser("gu","gu");
                uc.login("gu","gu");
                new FrameStats(uc).setVisible(true);
            }
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
        initComponents(uc);
    }

    private void buttonDeleteActionPerformed() {
        JOptionPane.showMessageDialog(this,"No implementada todavía","No encara",JOptionPane.ERROR_MESSAGE);
        /*JDialog d = new JDialog(this, "No implementada todavía", true);
        d.setLocationRelativeTo(this);
        d.setVisible(true);*/
    }
    private void buttonModifyPasswordActionPerformed(){
        JOptionPane.showMessageDialog(this,"No implementada todavía","Not yet",JOptionPane.ERROR_MESSAGE);
    }
    private void buttonSelectHidatoToEditActionPerformed(){
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
        buttonDelete.addActionListener(new java.awt.event.ActionListener(){public void actionPerformed(java.awt.event.ActionEvent evt){buttonDeleteActionPerformed();}});
 
        JButton buttonModifyPassword = new JButton("Modify Password User");
        buttonModifyPassword.addActionListener(new java.awt.event.ActionListener(){public void actionPerformed(java.awt.event.ActionEvent evt){buttonModifyPasswordActionPerformed();}});
  
        JButton buttonSelectHidatoToEdit = new JButton("Edit Already Created Hidato");
        buttonSelectHidatoToEdit.addActionListener(new java.awt.event.ActionListener(){public void actionPerformed(java.awt.event.ActionEvent evt){buttonSelectHidatoToEditActionPerformed();}});

                
        JPanel myButtonPanel = new JPanel();
      //  myButtonPanel.setLayout(new BoxLayout(myButtonPanel,BoxLayout.Y_AXIS)); 
      //  myButtonPanel.add(Box.createVerticalGlue());
        
        
        myButtonPanel.add(buttonDelete);
        myButtonPanel.add(buttonModifyPassword);
        myButtonPanel.add(buttonSelectHidatoToEdit);
        
        
        
        /*layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(buttonDelete)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );*/
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(myButtonPanel)
                        .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup(/*javax.swing.GroupLayout.Alignment.LEADING*/)
                        .addComponent(myButtonPanel)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    
        pack();
    }
}
