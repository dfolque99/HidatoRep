/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Tauler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author felix.axel.gimeno
 */
public class SolverWithMemoryController {
    private static final Map<Integer, Boolean > solved = new HashMap<>(5);
    private static Integer hashCode(Hidato hidato){
               int hash = 7;
               hash = 61 * hash + hidato.getSizeX();
               hash = 61 * hash + hidato.getSizeY();
               for (int i = 0; i < hidato.getSizeX(); i += 1){
                   for (int j = 0; j < hidato.getSizeY(); j +=1){
                       hash = 61 * hash + hidato.getCell(i,j).getVal();
                   }
               }
               System.out.println("Hidato Hash: " +hash);
               return hash;
    }
    public static Boolean solve(Hidato hidato){
        int hash = hashCode(hidato);
        Boolean b = solved.get(hash);
        if (null == b) {
            solved.put(hash,new SolverController().solve(hidato) );
        }
        return solved.get(hash);
    }
    private SolverWithMemoryController() {}
}
