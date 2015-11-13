/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domini;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author David
 */

public class HidatoManagerDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HidatoSet hs = new HidatoSet();
        HidatoManager hm = new HidatoManager(hs);
        Scanner s = new Scanner(System.in);
        System.out.printf("USAGE:\n");
        System.out.printf("1 m n => crea nou hidato de mida mxn\n");
        System.out.printf("2 x y v => canvia el valor de (x,y) per v\n");
        System.out.printf("3 x y t => canvia el tipus de (x,y) per t "
                + "(1 = blanca, 2 = pista, 3 = no valida)\n");
        System.out.printf("4 => escriu el hidato\n");
        System.out.printf("41 => escriu tots els hidatos guardats\n");
        System.out.printf("5 name => guarda el nou hidato amb nom name\n");
        System.out.printf("6 => actualitza el hidato a la base de dades\n");
        System.out.printf("7 name => obre el hidato amb nom name\n");
        System.out.printf("8 => sortir\n");
        int acc;
        hm.createManual(4,4);
        String nom;
        do {
            acc = s.nextInt();
            switch(acc){
                case 1:
                    hm.createManual(s.nextInt(),s.nextInt());
                    System.out.print("Creat nou hidato\n");
                    break;
                case 2:
                    hm.setTempCellVal(s.nextInt(), s.nextInt(), s.nextInt());
                    break;
                case 3:
                    String a[] = {"Buida", "Pista", "No Valida"};
                    hm.setTempCellType(s.nextInt(), s.nextInt(), a[s.nextInt()]);
                    break;
                case 4:
                    dibuixa(hm);
                    break;
                case 41:
                    hm.dibuixaTots();
                    break;
                case 5:
                    s.skip(" ");
                    nom = s.nextLine();
                    hm.saveTempHidato(nom);
                    break;
                case 6:
                    hm.saveTempHidato(null);
                    break;
                case 7:
                    s.skip(" ");
                    nom = s.nextLine();
                    hm.modifyHidato(nom);
                default:
                    break;
            }
        } while (acc != 8);
    }
    
    private static void dibuixa (HidatoManager hm) {
        int n = hm.getTempSizeY();
        int m = hm.getTempSizeX();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                System.out.printf("%d ", hm.getTempCellVal(j,i));
            }
            System.out.print("\n");
        }
    }
    
}
