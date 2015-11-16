/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Tauler;


import domini.Partida.Difficulty;
import domini.Misc.Utils;
import java.util.Scanner;

/**
 *
 * @author David
 */
public class HidatoGeneratorDriver {
    
    public HidatoGeneratorDriver() {
        
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        
        while (true) {
            System.out.printf("Linies: ");
            int sizeX = s.nextInt();
            System.out.printf("Columnes: ");
            int sizeY = s.nextInt();
            System.out.printf("Dificultat: ");
            int d = s.nextInt();
            System.out.printf("Hidato (cada casella s'ha de dir si és blank(b) given(g) o void(v) i despres el numero (0 si esta per determinar)");
            Hidato h = new Hidato(sizeX,sizeY);
            for (int i = 0; i < sizeX; ++i) {
                for (int j = 0; j < sizeY; ++j) {
                    String t = s.next();
                    int e = s.nextInt();
                    if (t.equals("g")) h.getCell(i, j).setType(Type.GIVEN);
                    else if (t.equals("b")) h.getCell(i, j).setType(Type.BLANK);
                    else h.getCell(i, j).setType(Type.VOID);
                    h.getCell(i,j).setVal(e);
                }
            }
            HidatoGenerator hg = new HidatoGenerator(h);
            boolean seguir = true;
            while (seguir) {
                System.out.printf("1) Particionat\n2) Comptar caselles\n3) Generar hidato\n4) Sortir\n");
                int acc = s.nextInt();
                switch(acc) {
                    case 1:
                        System.out.printf("Valor maxim a tenir en compte: ");
                        int val = s.nextInt();
                        if (hg.particionat(val)) System.out.printf("Particionat\n");
                        else System.out.printf("No particionat\n");
                        break;
                    case 2:
                        System.out.printf("Caselles = %d\n", hg.getTotalCaselles());
                        break;
                    case 3:
                        long t1 = System.currentTimeMillis();
                        Difficulty diff[] = {Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD};
                        Hidato resolt = hg.generateHidato(diff[d]);
                        long t2 = System.currentTimeMillis();
                        System.out.printf("Milisegons: %d\n", t2-t1);
                        if (resolt == null) System.out.printf("Impossible de generar\n");
                        else {
                            System.out.printf(Utils.toString(resolt));
                            System.out.printf(Utils.toStringWithZeros(resolt));
                        }
                        break;
                    default:
                        seguir = false;
                        break;
                }
            }
        }
    }
}

