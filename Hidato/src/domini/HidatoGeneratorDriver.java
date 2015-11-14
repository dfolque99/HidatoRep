/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author David
 */
public class HidatoGeneratorDriver {
    
    public HidatoGeneratorDriver() {
        
    }
    
    public static void main(String[] args) {
        
        while (true) {
            Scanner s = new Scanner(System.in);
            System.out.printf("Linies: ");
            int sizeX = s.nextInt();
            System.out.printf("Columnes: ");
            int sizeY = s.nextInt();
            System.out.printf("Dificultat: ");
            int d = s.nextInt();
            System.out.printf("Hidato (0 per casella buida, -1 per casella no "
                    + "valida:\n");
            Hidato h = new Hidato(sizeX,sizeY);
            for (int i = 0; i < sizeX; ++i) {
                for (int j = 0; j < sizeY; ++j) {
                    int e = s.nextByte();
                    if (e == -1) h.getCell(i, j).setType("No Valida");
                    else h.setCell(i, j, new Cell(e,"Blanca"));
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
                        hg.generateHidato("Facil");
                        long t2 = System.currentTimeMillis();
                        System.out.printf("Milisegons: %d\n", t2-t1);
                        break;
                    default:
                        seguir = false;
                        break;
                }
            }
            
            /*System.out.printf("Generant hidato...\n");
            String diff[] = {"Easy, Medium, Hard"};
            hg.generateHidato(diff[d]);*/
        }
    }
}
