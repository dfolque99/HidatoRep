package gui;
import domini.Usuari.*;
/**
 * @author felix.axel.gimeno
 */
public class DisplayStatsUI extends javax.swing.JFrame {
    private final static String[] getStats(){
        HidatoUser h = CurrentUser.getHidatoUser();
        UserStatsController usc = new UserStatsController(h);
        return new String[]{"Statistics of user: "+h.getUsername(),
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
    public DisplayStatsUI(){
        initComponents();
    }
    @SuppressWarnings("unchecked")                   
private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = getStats();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }                   
public static void main(String args[]) {
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DisplayStatsUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration                   
}
