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
    public static Color cVoid;
    public static Color cGiven;
    public static Color cBlank;
    public static Color cStart;
    public static Color cClick;
    public static int[][] tema_int = new int[][] {
        {0x33332D, 0xCCCC9F, 0xFFF8E3, 0x9FB4CC, 0xDB4105}
    };
    
    
    public static Color c (int i) {
        return new Color(tema_int[0][i]);
    }
}
