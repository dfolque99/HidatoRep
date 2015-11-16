/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domini.Tauler;

import java.util.ArrayList;
import java.util.Scanner;
import domini.Partida.Difficulty;

/**
 *
 * @author David
 */

public class DriverHidatoManagerController {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HidatoSet hs = new HidatoSet();
        HidatoSetDBController dbc = new HidatoSetDBController(null);
        HidatoManagerController hm = new HidatoManagerController(hs, null, dbc);
        Scanner s = new Scanner(System.in);
        int acc, n, m, d, val;
        String name;
        hm.createManual(4,4);
        Difficulty diff[] = {Difficulty.EASY,Difficulty.MEDIUM,Difficulty.HARD};
        Type tipus[] = {Type.BLANK,Type.GIVEN,Type.VOID};
        
        do {
            usage();
            acc = s.nextInt();
            switch (acc) {
                case 11:
                    n = s.nextInt(); m = s.nextInt(); d = s.nextInt();
                    hm.createRandom(n,m,diff[d-1]);
                    break;
                case 12:
                    n = s.nextInt(); m = s.nextInt();
                    hm.createManual(n,m);
                    break;
                case 2:
                    name = s.next();
                    hm.loadHidato(name);
                    break;
                case 31:
                    n = s.nextInt(); m = s.nextInt(); val = s.nextInt();
                    hm.setTempCellVal(n, m, val);
                    break;
                case 32:
                    n = s.nextInt(); m = s.nextInt(); val = s.nextInt();
                    hm.setTempCellType(n, m, tipus[val-1]);
                    break;
                case 4:
                    d = s.nextInt();
                    hm.completeTempHidato(diff[d-1]);
                    break;
                case 5:
                    hm.solveTempHidato();
                    break;
                case 6:
                    name = s.next();
                    if (hm.usedName(name)) System.out.printf("Esta utilitzat");
                    else System.out.printf("No esta utilitzat");
                    break;
                case 71:
                    name = s.next();
                    hm.saveTempHidato(name);
                    break;
                case 72:
                    hm.saveTempHidato(null);
                    break;
                case 8:
                    if (hm.saveAll() == 0) System.out.printf("Guardat correctament\n");
                default:
                    break;
            }
            dibuixa(hm);
        } while (acc != 8);
    }
    
    private static void dibuixa (HidatoManagerController hm) {
        int n = hm.getTempSizeX();
        int m = hm.getTempSizeY();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                System.out.printf("%3d ", hm.getTempCellVal(i,j));
            }
            System.out.print("\n");
        }
    }
    
    private static void usage() {
        System.out.printf("USAGE:\n");
        System.out.printf("11 n m d => crea nou hidato aleatori de mida nxm "
                + "i dificultat d (1 = EASY, 2 = MEDIUM, 3 = HARD)\n");
        System.out.printf("12 n m => crea nou hidato buit de mida nxm\n");
        System.out.printf("2 name => obre el hidato amb nom name");
        System.out.printf("31 x y v => canvia el valor de (x,y) per v\n");
        System.out.printf("32 x y t => canvia el tipus de (x,y) per t "
                + "(1 = BLANK, 2 = GIVEN, 3 = VOID)\n");
        System.out.printf("4 d => acaba de generar el hidato posant-li"
                + "dificultat d (1 = EASY, 2 = MEDIUM, 3 = HARD)\n");
        System.out.printf("5 => soluciona el hidato temporal\n");
        System.out.printf("6 name => mira si el nom name ja esta utilitzat\n");
        System.out.printf("71 name => guarda el hidato temporal amb nom name\n");
        System.out.printf("72 => guarda el hidato temporal amb nom que ja te\n");
        System.out.printf("8 => guardar tot a disc\n"
                + "9 => sortir\n");
    }
    
}