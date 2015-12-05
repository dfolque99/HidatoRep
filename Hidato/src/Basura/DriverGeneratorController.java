/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Basura;


import CapaDomini.Misc.Utils;
import CapaDomini.Tauler.GeneratorController;
import CapaDomini.Tauler.Hidato;
import CapaDomini.Tauler.Type;
import java.util.Scanner;

/**
 *
 * @author David
 */
public class DriverGeneratorController {
    
    public DriverGeneratorController() {
        
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int sizeX, sizeY;
        GeneratorController hg;
        Hidato h;
        long t1, t2;
        int acc;
        do {
            print(    "              |  1) Generar hidato aleatori         |\n"
                    + "              |  2) Generar hidato amb condicions   |\n"
                    + "              |  3) Sortir                          |\n");
            acc = s.nextInt();
            switch(acc) {
                case 1:
                    print ("Files: ");
                    sizeX = s.nextInt();
                    print ("Columnes: ");
                    sizeY = s.nextInt();
                    t1 = System.currentTimeMillis();
                    hg = new GeneratorController();
                    h = hg.generateHidato(sizeX,sizeY);
                    t2 = System.currentTimeMillis();
                    if (h != null) {
                        print(Utils.toString(h));
                        print(Utils.toStringWithZeros(h));
                        System.out.printf("Generat en %d milisegons\n\n\n", t2-t1);
                    }
                    else System.out.printf("No generat\n");
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
                    t1 = System.currentTimeMillis();
                    hg = new GeneratorController();
                    h = hg.generateHidato(origen);
                    t2 = System.currentTimeMillis();
                    if (h != null) {
                        print(Utils.toString(h));
                        print(Utils.toStringWithZeros(h));
                        System.out.printf("Generat en %d milisegons\n\n\n", t2-t1);
                    }
                    else {
                        System.out.printf("Hidato no trobat\n");
                    }
                    break;
                default: break;
            }
        } while (acc != 3);
    }
    
    private static void print (String a) {
        System.out.print(a);
    }
}

