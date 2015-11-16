/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Rank;

import domini.Rank.CtrRanking;
import java.util.Scanner;

/**
 *
 * @author Guillem
 */
public class CtrRankingDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CtrRanking ranking = new CtrRanking();
        System.out.println("----CtrRankingDriver----");
        System.out.println("Seleccion una opcion:");
        System.out.println("1. Inicializar los rankings.");
        System.out.println("2. Insertar una entrada en un ranking");
    }
    
}
