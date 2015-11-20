/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Tauler;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author felix.axel.gimeno
 */
public class SolverWithMemoryController {
    private static final Map<Hidato, Boolean > solved = new HashMap<>(5);
    public static Boolean solve(Hidato hidato){
        return solved.computeIfAbsent(hidato,t->new SolverController().solve(t));
    }
    private SolverWithMemoryController() {}
}
