/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Misc;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

/**
 * Classe estàtica que reuneix les fonts especials utilitzades al projecte.
 * @author David
 */
public class Fonts {
    
    /**
     * Retorna un objecte Font (que pot ser del sistema, o estar incorporada
     * als arxius del projecte), amb les propietats especificades.
     * @param nom Nom de la font demanada.
     * @param style Estil de la font.
     * @param num Tamany de la font.
     * @return Retorna la Font esperada.
     */
    public static Font getFont(String nom, int style, int num) {
        File fil = new File(nom + ".ttf");
        Font f;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, fil);
        } catch (FontFormatException | IOException ex) {
            f = new Font("Tahoma", Font.PLAIN, 18);
        }
        
        f = f.deriveFont(style, (float)num);
        return f;
    }
}
