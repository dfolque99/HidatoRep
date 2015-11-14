/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

/**
 *
 * @author Guillem
 */

//DE MOMENTO ES SOLO UN STUB
public class CtrDBRanking {
    
    public Ranking getRanking(Difficulty difficulty) {
        return new Ranking(difficulty);
    }
    
    public void save() {
    }
    
}
