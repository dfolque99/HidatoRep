/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Misc;

import java.awt.Color;

/**
 *
 * @author David
 */
public class Colors {
    public static final Color verd = new Color(0x84F56C);
    public static final Color verd_fluix = new Color(aclarir(0x84F56C));
    public static final Color vermell = new Color(0xD73628);
    public static final Color vermell_fluix = new Color(aclarir(0xD73628));
    public static final Color blau = new Color(0x2843A6);
    public static final Color blau_fluix = new Color(aclarir(aclarir(0x2843A6)));
    public static final Color blau_fosc = new Color(0x1E273D);
    public static final Color blanc = new Color(0xF3F3F3);
    public static final Color negre = new Color(0x0D0D0D);
    
    
    private static int aclarir(int a) {
        int m1 = 0xFF0000;
        int m2 = 0x00FF00;
        int m3 = 0x0000FF;
        return (((a&m1)+m1)/2)&m1 | (((a&m2)+m2)/2)&m2 | (((a&m3)+m3)/2)&m3;
    }
}
