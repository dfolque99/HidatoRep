/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacio;

/**
 * Interfeç que serveix per coordinar les accions a dur a terme al escollir una
 * acció en un FrameLlista
 * @author David
 */
public interface RetornadorString {
    
    /*
     * És el mètode que es crida quan es selecciona un hidato/partida per a la
     * acció principal
     */
    public void retorna(String s);
    
    /*
     * Mètode que es crida quan es selecciona un hidato/partida per a eliminar
     */
    public void elimina(String s);
    
    /*
     * Mètode que es crida quan es selecciona un hidato/partida per a canviar-li
     * el nom
     */
    public void canviaNom(String oldName, String newName);
}
