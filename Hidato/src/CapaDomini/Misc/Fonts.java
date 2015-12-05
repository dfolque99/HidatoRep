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
 *
 * @author David
 */
public class Fonts {
    public static Font getFont(String nom, int style, int num) {
        File fil = new File(nom + ".ttf");
        Font f;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, fil);
        } catch (FontFormatException | IOException ex) {
            f = Font.getFont("Tahoma");
        }
        
        f = f.deriveFont(style, (float)num);
        return f;
    }
}
