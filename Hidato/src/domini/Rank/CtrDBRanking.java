/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Rank;

import domini.Partida.Difficulty;

/**
 * Clase que gestiona la persistencia de los rankings.
 * @author Guillem
 */

//ES SOLO PARA QUE NO DE ERRORES DE COMPILACION EL PROYECTO
//SE IMPLEMENTARA MAS TARDE

public class CtrDBRanking {
    
    public Ranking getRanking(Difficulty difficulty) {
        return new Ranking(difficulty);
    }
    
    public void modifyRanking(Difficulty difficulty,Ranking ranking) {
        
    }
    
}
