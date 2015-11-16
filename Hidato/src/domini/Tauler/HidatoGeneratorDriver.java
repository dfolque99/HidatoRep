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
        int sizeX, sizeY;
        Difficulty dificultat;
        HidatoGenerator hg;
        Hidato h;
        long t1, t2;
        
        while (true) {
            print(    "1) Generar hidato per dificultat\n"
                    + "2) Generar hidato amb condicions\n"
                    + "3) Sortir\n");
            int acc = s.nextInt();
            switch(acc) {
                case 1:
                    print ("Files: ");
                    sizeX = s.nextInt();
                    print ("Columnes: ");
                    sizeY = s.nextInt();
                    print ("Dificultat (EASY/MEDIUM/HARD): ");
                    dificultat = Difficulty.valueOf(s.next());
                    t1 = System.currentTimeMillis();
                    hg = new HidatoGenerator(sizeX,sizeY);
                    h = hg.generateHidato(dificultat);
                    t2 = System.currentTimeMillis();
                    print(Utils.toString(h));
                    print(Utils.toStringWithZeros(h));
                    System.out.printf("Generat en %d milisegons\n\n\n", t2-t1);
                    break;
                case 2:
                    print ("Files: ");
                    sizeX = s.nextInt();
                    print ("Columnes: ");
                    sizeY = s.nextInt();
                    print ("Escriu el hidato (0 a les caselles on no vulguis especificar cap numero)\n");
                    Hidato origen = new Hidato(sizeX, sizeY);
                    for (int i = 0; i < sizeX; ++i) {
                        for (int j = 0; j < sizeY; ++j) {
                            origen.getCell(i, j).setVal(s.nextInt());
                        }
                    }
                    print ("Escriu un altre cop el hidato, posant a cada posicio el tipus de casella que sera "
                            + "(b = BLANK, g = GIVEN, v = VOID\n");
                    for (int i = 0; i < sizeX; ++i) {
                        for (int j = 0; j < sizeY; ++j) {
                            String tip = s.next();
                            if (tip.equals("b")) origen.getCell(i, j).setType(Type.BLANK);
                            else if (tip.equals("g")) origen.getCell(i, j).setType(Type.GIVEN);
                            else origen.getCell(i, j).setType(Type.VOID);
                        }
                    }
                    print ("Dificultat (EASY/MEDIUM/HARD): ");
                    dificultat = Difficulty.valueOf(s.next());
                    t1 = System.currentTimeMillis();
                    hg = new HidatoGenerator(origen);
                    h = hg.generateHidato(dificultat);
                    t2 = System.currentTimeMillis();
                    print(Utils.toString(h));
                    print(Utils.toStringWithZeros(h));
                    System.out.printf("Generat en %d milisegons\n\n\n", t2-t1);
                    break;
                default: break;
            }
        }
    }
    
    private static void print (String a) {
        System.out.print(a);
    }
}

