/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Guillem
 */
public class RankingEntryDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RankingEntry entry = null;
        System.out.println("----RankingEntryDriver----");
        System.out.println("Selecciona una opcion:");
        System.out.println("1. Crear nuevo RankingEntry (con la fecha actual)");
        System.out.println("2. Consultar fecha del RankingEntry actual");
        System.out.println("3. Consultar nombre de usuario del RankingEntry actual");
        System.out.println("4. Consultar puntuacion del RankingEntry actual");
        int op = sc.nextInt();
        while (op != -1) {
            switch(op) {

                case 1:
                    System.out.print("Introduce un nombre: ");
                    String name = sc.next();
                    System.out.print("Introduce la puntuacion: ");
                    int puntuacion = sc.nextInt();
                    entry = new RankingEntry(new Date(),name,puntuacion);
                    break;

                case 2:
                    if (entry == null) {
                        System.out.println("Primero hay que crear un RankingEntry!");
                    }
                    else System.out.println("Fecha: " + entry.getDate());
                    break;

                case 3:
                    if (entry == null) {
                        System.out.println("Primero hay que crear un RankingEntry!");
                    }
                    else System.out.println("Nombre usuario: " + entry.getUsername());
                    break;

                case 4:
                    if (entry == null) {
                        System.out.println("Primero hay que crear un RankingEntry!");
                    }
                    else System.out.println("Puntuacion: " + entry.getScore());
                    break;
                    
                default: System.out.println("Opcion no disponible");
            }
            System.out.print("Introduce una nueva opcion: ");
            op = sc.nextInt();
        }
    }
    
}
