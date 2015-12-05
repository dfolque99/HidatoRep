/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Rank;

import java.util.Scanner;
import java.util.ArrayList;
import CapaDomini.Partida.Difficulty;

/**
 *
 * @author Guillem
 */
public class DriverRankingController {

    /**
     * @param args the command line arguments
     * Driver de la clase RankingController.
     * Permite ir añadiendo entradas a cada ranking (un ranking por dificultad).
     * Inicialmente los rankings empiezan vacios. Tambien se puede consultar el
     * estado de los rankings para comprobar que el resultado es el esperado.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RankingController ranking = new RankingController();
        System.out.println("----DriverRankingController----");
        System.out.println("Seleccion una opcion:");
        System.out.println("1. Insertar una entrada en un ranking");
        System.out.println("2. Mostrar un ranking");
        System.out.println("3. Cargar rankings guardados");
        System.out.println("4. Guardar rankings");
        System.out.println("-1. Finalizar ejecucion");
        int op = sc.nextInt();
        while (op != -1) {
            switch(op) {
                case 1:
                    System.out.println("En que ranking?");
                    System.out.println("1. Facil");
                    System.out.println("2. Medio");
                    System.out.println("3. Dificil");
                    int d = sc.nextInt();
                    Difficulty diff = int2diff(d);
                    System.out.print("Introduce nombre usuario: ");
                    String name = sc.next();
                    System.out.print("Introduce puntuacion: ");
                    int punt = sc.nextInt();
                    ranking.addScoreToRanking(punt, name, diff);
                    break;
                    
                case 2:
                    System.out.println("Que ranking quieres consultar?");
                    System.out.println("1. Facil");
                    System.out.println("2. Medio");
                    System.out.println("3. Dificil");
                    int a = sc.nextInt();
                    Difficulty diffi = int2diff(a);
                    ArrayList <String> v = ranking.getRankingInfo(diffi);
                    for (int i = 0; i < v.size(); ++i) {
                        System.out.println((i+1) +  ". " + v.get(i));
                    }
                    break;
                    
                case 3:
                    ranking.init();
                    break;
                    
                case 4:
                    ranking.save();
                    break;
                    
                default:
                    System.out.println("Opcion no disponible");
            }
            System.out.print("Introduce una nueva opcion: ");
            op = sc.nextInt();
        }
    }
    
    public static Difficulty int2diff(int d) {
        if (d == 1) return Difficulty.EASY;
        if (d == 2) return Difficulty.MEDIUM;
        return Difficulty.HARD;
    }
    
}

