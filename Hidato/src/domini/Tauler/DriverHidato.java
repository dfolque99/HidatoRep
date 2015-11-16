/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domini.Tauler;

import domini.Misc.Utils;
import java.util.Scanner;
import domini.Partida.Difficulty;

/**
 *
 * @author David
 */

public class DriverHidato {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        int acc, n, m, d, val, t;
        String name;
        Difficulty diff[] = {Difficulty.EASY,Difficulty.MEDIUM,Difficulty.HARD};
        Type tipus[] = {Type.BLANK,Type.GIVEN,Type.VOID};
        
        Hidato h = new Hidato(3,3);
        do {
            usage();
            acc = s.nextInt();
            switch (acc) {
                case 1:
                    n = s.nextInt(); m = s.nextInt(); d = s.nextInt();
                    h = new Hidato(n,m,diff[d-1]);
                    break;
                case 2:
                    n = s.nextInt(); m = s.nextInt();
                    h = new Hidato(n,m);
                    break;
                case 3:
                    h = new Hidato(h);
                    break;
                case 4:
                    n = s.nextInt(); m = s.nextInt(); val = s.nextInt(); t = s.nextInt();
                    h.setCell(n, m, new Cell(val,tipus[t-1]));
                    break;
                case 5:
                    d = s.nextInt();
                    h.setDifficulty(diff[d-1]);
                    break;
                case 6:
                    name = s.next();
                    h.setBoardName(name);
                    break;
                default:
                    break;
            }
            dibuixa(h);
        } while (acc != 8);
    }
    
    private static void dibuixa (Hidato h) {
        System.out.print("Nom del hidato: " + h.getBoardName() + "\n");
        if (h.getDifficulty() != null) System.out.print("Dificultat del hidato: " + h.getDifficulty().toString() + "\n");
        else System.out.print("Dificultat del hidato: null\n");
        System.out.print("Data de creacio: " + h.getCreationDate().toString() + "\n");
        int n = h.getSizeX();
        int m = h.getSizeY();
        System.out.print(Utils.toString(h));
        System.out.print(Utils.toStringWithZeros(h));
    }
    
    private static void usage() {
        System.out.printf("USAGE:\n");
        System.out.printf("1 n m d => crea nou hidato buit de mida nxm "
                + "(amb l'inici i el final als extrems) "
                + "i dificultat d (1 = EASY, 2 = MEDIUM, 3 = HARD)\n");
        System.out.printf("2 n m => crea nou hidato buit de mida nxm "
                + "(amb l'inici i el final als extrems)\n"
                +         "3 => crea un nou hidato a partir de l'actual\n");
        System.out.printf("4 x y v t => canvia el valor de (x,y) per v i "
                + "el seu tipus per t (1 = BLANK, 2 = GIVEN, 3 = VOID\n");
        System.out.printf("5 d => canvia la dificultat del hidato a "
                + "dificultat d (1 = EASY, 2 = MEDIUM, 3 = HARD)\n");
        System.out.printf("6 name => canvia el nom del hidato a name\n");
        System.out.printf("7 => sortir\n");
    }
    
}