/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Basura;


import CapaDomini.Misc.Utils;
import CapaDomini.Partida.Difficulty;
import CapaDomini.Tauler.Cell;
import CapaDomini.Tauler.GeneratorController;
import CapaDomini.Tauler.Hidato;
import CapaDomini.Tauler.Type;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author David
 */
public class GenProves {
    
    public static void main (String[] args) throws FileNotFoundException {
        
        File file = new File("entrada.txt");
        Scanner s = new Scanner(file);
        int n = s.nextInt();
        int m = s.nextInt();
        
        Hidato h = new Hidato(n,m);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                int a = s.nextInt();
                if (a == -1) h.getCell(i, j).setType(Type.VOID);
                else h.setCell(i, j, new Cell(a, Type.BLANK));
            }
        }
        long t1 = System.currentTimeMillis();
        GeneratorController gc = new GeneratorController();
        Hidato sol = gc.generateHidato(h);
        long t2 = System.currentTimeMillis();
        if (sol == null) {
            System.out.print("No s'ha trobat solucio\n");
        }
        else {
            System.out.print("Solució trobada:\n" + Utils.toString(sol) + "\n" + Utils.toStringWithZeros(sol));
        }
        System.out.printf("Milions d'iteracions: %f\n", gc.iteracions/1000000);
        System.out.printf("Milisegons : %d\n", t2-t1);
        s.close();
    }
}
