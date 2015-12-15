/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Misc;

import java.awt.Color;

/**
 *
 * @author David
 */
public class Colors {
    /**
     * Diferents conjunts de 5 colors, per als hidatos.
     * S'ordenen de la següent manera:
     * casella void, casella given, casella blank, casella inici/final, casella clicada
     */
    private static final int[][] tema_int = new int[][] {
        {0x33332D, 0xCCCC9F, 0xFFF8E3, 0x9FB4CC, 0xDB4105},
        {0x000000, 0x7E8AA2, 0xF0F0F0, 0x263248, 0xFF9800},
        {0x2A253C, 0x7E8AA2, 0xF0F0F0, 0xB4A9D5, 0xD58849},
        {0x0F224A, 0x3988B0, 0xF0F0F0, 0x80BDDE, 0xF26101},
        {0x081329, 0x285F7A, 0xF0F0F0, 0x689AB5, 0xF88F01},
        {0x595241, 0xB8AE9C, 0xF0F0F0, 0xACCFCC, 0x8A0917}
    };
    
    /**
     * El tema de colors seleccionat actualment.
     */
    private static final int tema = 5;
    
    
    /**
     * Funció que retorna el color segons el tema seleccionat.
     * @param i Especifica el tipus de color que vol. 0 -> caselles void,
     * 1 -> caselles given, 2 -> caselles blank, 3 -> caselles inici/final, 
     * 4 -> casella clicada.
     * @return El color corresponent.
     */
    public static final Color c(int i) {
        return new Color(tema_int[tema][i]);
    }
}
