/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacio;

/**
 * Classe principal del programa. Conté el mètode main del programa.
 * @author David
 */
public class HidatoMain {
    
    /*
     * És la funció que comença el programa.
     * Crea un objecte AdminVistes i crida el seu mètode executaPrograma
     */
    public static void main (String args[]) {
        AdminVistes d = new AdminVistes();
        d.executaPrograma();
    }
}
