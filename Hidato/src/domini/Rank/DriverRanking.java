/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Rank;

import domini.Partida.Difficulty;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Guillem
 */
public class DriverRanking {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ranking ranking = null;
        System.out.println("----RankingDriver----");
        System.out.println("Selecciona una opcion:");
        System.out.println("1. Crear nuevo Ranking vacio");
        System.out.println("2. Consultar numero de entradas del ranking");
        System.out.println("3. Consultar máximo numero de entradas en un ranking");
        System.out.println("4. Consultar dificultad del ranking");
        System.out.println("5. Consultar una entrada en el ranking");
        System.out.println("6. Añadir una entrada al ranking");
        System.out.println("7. Mostrar estado del ranking");
        System.out.println("-1. Finalizar Ejecucion");
        System.out.println();
        int op;
        op = sc.nextInt();
        while (op != -1) {
            switch(op) {
                case 1:
                    System.out.println("Selecciona nivel de dificultad:");
                    System.out.println("1. Facil");
                    System.out.println("2. Medio");
                    System.out.println("3. Dificil");
                    int dif = sc.nextInt();
                    switch(dif) {
                        case 1:
                            ranking = new Ranking(Difficulty.EASY);
                            break;
                            
                        case 2:
                            ranking = new Ranking(Difficulty.MEDIUM);
                            break;
                            
                        case 3:
                            ranking = new Ranking(Difficulty.HARD);
                            break;
                    }
                    break;
                    
                case 2:
                    if (ranking == null) error(1);
                    else System.out.println("Numero de entradas : " + ranking.getSize());
                    break;
                    
                case 3:
                    System.out.println("Maximo numero de entradas: " + Ranking.getMaxSize());
                    break;
                    
                case 4:
                    if (ranking == null) error(1);
                    else System.out.println("Dificultad ranking: " + ranking.getDifficulty());
                    break;
                    
                case 5:
                    if (ranking == null) error(1);
                    else {
                        System.out.print("Introduce la entrada que quieres consultar: ");
                        int i = sc.nextInt();
                        System.out.println(ranking.get(i));
                    }
                    break;
                    
                case 6:
                    if (ranking == null) error(1);
                    else {
                        System.out.print("Introduce el nombre de usuario: ");
                        String name = sc.next();
                        System.out.print("Introduce la puntuacion: ");
                        int punt = sc.nextInt();
                        ranking.addRankingEntry(new RankingEntry(new Date(),name,punt));
                    }
                    break;
                case 7:
                    if (ranking == null) error(1);
                    else {
                        int n = ranking.getSize();
                        for (int i = 0; i < n; ++i) {
                            System.out.println(i + ". " + ranking.get(i));
                        }
                    }
                    break;
                    
                default:
                    error(2);
                    break;
            }
            System.out.print("Introduce una nueva opcion: ");
            op = sc.nextInt();
        }
    }
    
    public static void error(int i) {
        switch(i) {
            case 1:
                System.out.println("Primero hay que crear un ranking!");
                break;
                
            case 2:
                System.out.println("Opcion no disponible.");
                break;
                
            default:
                System.out.println("Error desconocido.");
        }
    }
    
}
