/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Tauler;

import java.util.Scanner;

/**
 *
 * @author David
 */
public class DriverCell {
    
    public static void main (String[] args) {
        Scanner s = new Scanner(System.in);
        Cell c = new Cell();
        int acc, v, t;
        Type tipus[] = {Type.BLANK, Type.GIVEN, Type.VOID};
        while (true) {
            usage();
            acc = s.nextInt();
            switch(acc) {
                case 1:
                    c = new Cell();
                    break;
                case 2:
                    v = s.nextInt(); t = s.nextInt();
                    c = new Cell(v, tipus[t-1]);
                    break;
                case 3:
                    c = new Cell(c);
                    break;
                case 4:
                    v = s.nextInt();
                    c.setVal(v);
                    break;
                case 5:
                    t = s.nextInt();
                    c.setType(tipus[t-1]);
                    break;
                default:
                    break;
            }
            System.out.printf("c te valor %d i es de tipus " + c.getType().toString() + "\n", c.getVal());
        }
    }
    
    private static void usage() {
        System.out.print(    "1 => crear Cell sense parametres\n"
                + "2 v t => crear Cell amb valor v i tipus t (1 = BLANK, 2 = GIVEN, 3 = VOID\n"
                + "3 => crear Cell a partir de la Cell actual\n"
                + "4 v => canviar el valor a v\n"
                + "5 t => canviar el tipus a t (1 = BLANK, 2 = GIVEN, 3 = VOID\n"
                + "6 => sortir\n");
    }
}
