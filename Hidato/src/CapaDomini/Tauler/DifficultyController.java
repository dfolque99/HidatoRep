/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Tauler;
import CapaDomini.Partida.*;
import CapaDomini.Misc.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
/**
 * @since 07-12-2015
 * @author felix.axel.gimeno
 */
public class DifficultyController {
    private  DifficultyController(){}
    static final double medium_low = 2;
    static final double medium_high = 3;
    private static Difficulty doubleToDifficulty(final double d){
        if (d < medium_low) { 
            return Difficulty.EASY;
        } else if (d > medium_high) {
            return Difficulty.HARD;
        } else {
            return Difficulty.MEDIUM;
        }
    }
    private static double arrayToDouble(final Integer[][] cells){
        double sum = 0;
        double count = 0;
        for (int i = 0; i < cells.length;i+=1){
           for (int j = 0; j < cells[0].length; j+=1){
               sum += cells[i][j];
               count += 1;
           } 
        }
        return sum/count;
    }
    
    
    private boolean validPosition(PositionValue now,PositionValue next,boolean[][] used, final Hidato hidato){
        if (now.getValue() > next.getValue()) {return false;}
        final Integer x = now.getX();
        final Integer y = now.getY();
        if ((Math.min(x, y) < 0) || (Math.max(x - hidato.getSizeX(), y - hidato.getSizeY()) >= 0)) {
            return false;
        }
        if (hidato.getCell(x, y).getType() == Type.VOID){return false;}
        if (hidato.getCell(x, y).getType() == Type.GIVEN){return false;}
        if (PositionValue.notEnoughDistance(now,next)) {
            return false;
        }
        return !used[x][y];      
    }

    
    private ArrayList<PositionValue> Neighbours(PositionValue start,PositionValue next,boolean[][] used, final Hidato hidato){
        final Position[] Moore ={ 
            new Position(-1,-1), new Position(-1,0), new Position(0,-1),
            new Position(-1,+1), new Position(+1,-1),
            new Position(+1,+1), new Position(+1,0), new Position(0,+1),
        };
        return Arrays.stream(Moore)
                .map(p -> PositionValue.addValue(start,p))
                .filter(pv -> validPosition(pv,next,used,hidato))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    private Integer countPathForGiven(final PositionValue start, final PositionValue next, boolean[][] used, final Integer[][] count, final Hidato hidato){
        if (start.equals(next)) {
            return 1;
        } else if (start.getValue().equals(next.getValue())) {
            return 0;
        } else {
            used[start.getX()][start.getY()] = true;
            final Integer myCount = 
                    Neighbours(start,next,used,hidato)
                    .stream()
                    .mapToInt((PositionValue s)->countPathForGiven(s,next,used,count,hidato)
                    )
                    .reduce(Integer::sum).getAsInt();
            
            used[start.getX()][start.getY()] = false;
            count[start.getX()][start.getY()] += myCount;
            return myCount;
        }
    }
    private PositionValue[] getGivenCells(Hidato hidato){
        ArrayList<PositionValue> PV = new ArrayList<>(10);
        for (int i = 0; i < hidato.getSizeX(); i += 1) {
            for (int j = 0; j < hidato.getSizeY(); j += 1) {
                if ( hidato.getCell(i, j).getType().equals(Type.GIVEN) ) {
                    PV.add(new PositionValue(i,j,hidato.getCell(i, j).getVal()));
                } 
            }
        }
        PV.sort((CapaDomini.Tauler.PositionValue a,CapaDomini.Tauler.PositionValue b)->a.getValue()-b.getValue());//o al reves
        LOG.log(Level.SEVERE,"Recordar comprobar que el sort sea así");
        return (PositionValue[]) PV.toArray();
    }
    public static Difficulty getDifficulty(final Hidato hidato){
        DifficultyController myDC = new DifficultyController();
        boolean[][] used = new boolean[hidato.getSizeX()][hidato.getSizeY()];
        Integer[][] count = new Integer[hidato.getSizeX()][hidato.getSizeY()];
        PositionValue[] myGivenCells = myDC.getGivenCells(hidato);
        for (int i = 0; i + 1 < myGivenCells.length; i+=1){
            myDC.countPathForGiven(myGivenCells[i],myGivenCells[i+1],used,count,hidato);
        }
        return doubleToDifficulty(arrayToDouble(count));
    }
    private static final Logger LOG = Logger.getLogger(DifficultyController.class.getName());
}
