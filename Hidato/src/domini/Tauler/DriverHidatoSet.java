/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Tauler;


import domini.Partida.Difficulty;
import java.util.Scanner;

/**
 *
 * @author David
 */
public class DriverHidatoSet {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HidatoSet hs = new HidatoSet();
        Scanner s = new Scanner(System.in);
        int acc, n, m, d, val;
        Hidato h = new Hidato(2,2,Difficulty.EASY);
        String name;
        Difficulty diff[] = {Difficulty.EASY,Difficulty.MEDIUM,Difficulty.HARD};
        
        
        do {
            usage();
            acc = s.nextInt();
            switch (acc) {
                case 1:
                    n = s.nextInt(); m = s.nextInt(); d = s.nextInt(); name = s.next();
                    h = new Hidato(n,m,diff[d-1]);
                    h.setBoardName(name);
                    break;
                case 2:
                    hs.addHidato(h);
                    break;
                case 3:
                    hs.replaceHidatoByName(h.getBoardName(), h);
                    break;
                case 4:
                    n = s.nextInt();
                    hs.deleteHidatoByPos(n);
                    break;
                case 5:
                    name = s.next();
                    hs.deleteHidatoByName(name);
                    break;
                case 6:
                    name = s.next();
                    if (hs.getHidatoByName(name) != null) System.out.print("Hidato trobat");
                    else System.out.print("Hidato no trobat");
                default:
                    break;
            }
            System.out.print("\n");
            dibuixa(hs);
            dibuixa(h);
        } while (acc != 7);
    }
    
    private static void dibuixa (HidatoSet hs) {
        System.out.print("HidatoSet ->\n");
        int n = hs.getTotalHidatos();
        for (int i = 0; i < n; ++i) {
            Hidato h = hs.getHidatoByPos(i);
            System.out.printf(h.getBoardName() + 
                    ": %dx%d "+h.getDifficulty().toString() + "\n", 
                    h.getSizeX(), h.getSizeY());
        }
        System.out.print("\n");
    }
    private static void dibuixa (Hidato h) {
        System.out.printf("Temporal -> " + h.getBoardName() + 
                ": %dx%d "+h.getDifficulty().toString() + "\n\n", 
                h.getSizeX(), h.getSizeY());
    }
    
    private static void usage() {
        System.out.printf("USAGE:\n"
                + "1 n m d name => carrega un nou hidato buit temporal de mida nxm, "
                + "dificultat d (1 = EASY, 2 = MEDIUM, 3 = HARD) i nom name\n"
                + "2 => guarda el hidato temporal al set, si no hi ha cap amb aquell nom\n"
                + "3 => guarda el hidato temporal al set si hi ha algun amb aquell nom (el sobreescriu)\n"
                + "4 pos => elimina el hidato del set a la posicio pos\n"
                + "5 name => elimina el hidato del set amb nom name\n"
                + "6 name => diu si existeix algun hidato al set amb nom name\n"
                + "7 => sortir\n");
    }
    
}
