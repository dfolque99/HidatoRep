package CapaPresentacio;
import CapaDomini.Usuari.HidatoUser;
import CapaDomini.Usuari.HidatoUserController;
import CapaDomini.Usuari.UserController;
import CapaDomini.Usuari.UserStatsController;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * Profile management use case
 * <p>
 * To use do: new FrameStats(uc).setVisible(true); 
 * where uc is a valida usercontroller
 * 
 * @author felix.axel.gimeno
 * @version 0.22
 * @since 05-12-2015
 */
@SuppressWarnings("serial")
public final class FrameStats extends javax.swing.JFrame {
    
    private static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
    private static String convertToString(String[] myArray){
        String ret = "";
        for (String s : myArray) {ret = ret.concat(s).concat("\n\n");}
        return ret;
    }
    
    private final static Integer sizeX = 800; 
    private final static Integer sizeY = 600; 
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
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(() -> {
            HidatoUserController huc = new HidatoUserController();
            try {
                final String foo = "ghu";
                huc.createUser(foo,foo);
                huc.login(foo,foo);
                huc.updateUser();
            } catch (Exception e){
                System.out.println("Couldn't do login");
            }
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
        this.initComponents(uc);
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
    
    private static Object[] getHidatos(final UserController uc){
        System.out.println("FrameStats.getHidatos() : Falta por implementar");
        return new Object[]{"Hidato1","Hidato2"}; // <- TO DO
    }
    private void buttonSelectHidatoToEditActionPerformed(final UserController uc){
        //JOptionPane.showOptionDialog(this,"Select Hidato To Edit","Select Hidato To Edit",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,new Object[]{"Hidato1","Hidato2"}, "Hidato1");
        Object[] myList = FrameStats.getHidatos(uc);
        if (myList.length > 0) {
            String hidatoName = (String)JOptionPane.showInputDialog(this,"Select Hidato To Edit","Select Hidato To Edit",JOptionPane.QUESTION_MESSAGE, null,myList,myList[0]);
            if (null != hidatoName) {
                this.setVisible(false);
                FrameEditor fe = new FrameEditor();
                //fe.inici(hidato); // <- TO DO
                this.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this,"No hidatoa found","The logged user has no hidatos",JOptionPane.ERROR_MESSAGE);
        }
    }
    @SuppressWarnings(value = "unchecked")
    private void initComponents(final UserController uc) {
        //javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JLabel jScrollPane1 = new javax.swing.JLabel(convertToMultiline( convertToString(FrameStats.getStats(uc)) ) ,  javax.swing.SwingConstants.CENTER);
        
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new java.awt.Dimension(sizeX,sizeY));
        
        //jScrollPane1.setViewportView(new javax.swing.JList<String>(FrameStats.getStats(uc)));
        //jScrollPane1.setText();
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        this.getContentPane().setLayout(layout);
        
        JButton buttonDelete = createButton("Delete User",(java.awt.event.ActionEvent evt) -> {buttonDeleteActionPerformed(uc);});   
        JButton buttonModifyPassword = createButton("Modify Password User",(java.awt.event.ActionEvent evt) -> {buttonModifyPasswordActionPerformed(uc);});
        JButton buttonSelectHidatoToEdit = createButton("Edit Already Created Hidato", (java.awt.event.ActionEvent evt) -> {buttonSelectHidatoToEditActionPerformed(uc);});
        /*
        JPanel myButtonPanel = new JPanel();
        myButtonPanel.setLayout(new java.awt.GridLayout(0,1));

        javax.swing.GroupLayout buttonLayout = new javax.swing.GroupLayout(myButtonPanel);
        buttonLayout.setHorizontalGroup( 
            buttonLayout.createSequentialGroup()
                .addComponent(buttonDelete)
                .addComponent(buttonModifyPassword)
                .addComponent(buttonSelectHidatoToEdit)
                );
        buttonLayout.setVerticalGroup( 
            buttonLayout.createSequentialGroup()
                .addComponent(buttonDelete)
                .addComponent(buttonModifyPassword)
                .addComponent(buttonSelectHidatoToEdit)
                );
        */        
        //myButtonPanel.add(buttonDelete);
        //myButtonPanel.add(buttonModifyPassword);
        //myButtonPanel.add(buttonSelectHidatoToEdit);
        /*
        JPanel myPanelForButtons = new JPanel();
        myPanelForButtons.setLayout(new java.awt.BorderLayout());
        myPanelForButtons.add(myButtonPanel,java.awt.BorderLayout.CENTER);
        */
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(30) //to separate the list scroll from the buttons, to beautify
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, false) //center alignment;  false for not enabling resizing the group to expand all it can
                                .addComponent(buttonDelete,             javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonModifyPassword,     javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonSelectHidatoToEdit, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        )
                         //.addComponent(myButtonPanel
                                /*, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE*/
                         //)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonDelete)
                                .addComponent(buttonModifyPassword)
                                .addComponent(buttonSelectHidatoToEdit)
                        )
                        //.addComponent(myButtonPanel)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    
        this.pack();
    }
}