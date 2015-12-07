/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Tauler;
import CapaDomini.Partida.*;
import CapaDomini.Misc.*;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
/**
 * To calculate the difficulty of a hidato board
 * @since 07-12-2015
 * @version 0.7
 * @author felix.axel.gimeno
 */
public class DifficultyController {

    static final double medium_low = 2;
    static final double medium_high = 3;
    private static final Logger LOG = Logger.getLogger(DifficultyController.class.getName());
    private static final Level myLevel = Level.OFF;
    private static Difficulty doubleToDifficulty(final double d) {
        LOG.log(myLevel, "Dificultad d {0}", String.valueOf(d));
        if (d < medium_low) { 
            return Difficulty.EASY;
        } else if (d > medium_high) {
            return Difficulty.HARD;
        } else {
            return Difficulty.MEDIUM;
        }
    }
    private static double arrayToDouble(final Integer[][] cells) {
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
   
    private static <T> void printArray(T[] t){
        System.out.print("[");
        for(T tt:t) {
            if (tt instanceof PositionValue) {
                PositionValue pv = (PositionValue) tt;
                System.out.print("Value "+pv.getValue()+" X "+pv.getX()+" Y "+pv.getY());
            } else {System.out.print(tt);}
            System.out.print(", ");
        }
        System.out.print("]\n");
    }
    private static <T> void printArray(T[][] t){
        System.out.print("[\n");
        for(T[] tt:t) {
            printArray(tt);
        }
        System.out.print("]\n");
    }
    
    public static void main(String[] args) {System.out.println(new DifficultyController().getDifficulty(new Hidato(10,10)));}
    private boolean[][] used;
    private Integer[][] count;
    private Hidato hidato;
    private boolean validPosition(PositionValue now, PositionValue next){
        if (now.getValue() > next.getValue()) {return false;}
        if (now.equals(next)) {return true;}
        final Integer x = now.getX();
        final Integer y = now.getY();
        if ((Math.min(x, y) < 0) || (Math.max(x - hidato.getSizeX(), y - hidato.getSizeY()) >= 0)) {
            return false;
        }
        if (hidato.getCell(x, y).getType() == Type.VOID){return false;}
        if (hidato.getCell(x, y).getType() == Type.GIVEN){return false;}
        /*
        if (PositionValue.notEnoughDistance(now,next)) {
        return false;
        }
        */
        return !used[x][y];      
    }
    private ArrayList<PositionValue> Neighbours(PositionValue start, PositionValue next){
        final Position[] Moore ={ 
            new Position(-1,-1), new Position(-1,0), new Position(0,-1),
            new Position(-1,+1), new Position(+1,-1),
            new Position(+1,+1), new Position(+1,0), new Position(0,+1),
        };
        ArrayList<PositionValue> apv ;
        apv = Arrays.stream(Moore)
                .map(p -> PositionValue.addValue(start,p))
                .filter(pv -> validPosition(pv,next))
                .collect(Collectors.toCollection(ArrayList::new));
        //apv.stream().forEach(p->System.out.println(p.getX()+" "+p.getY()+" "+p.getValue()));
        return apv;
    }
    private Integer countPathForGiven(final PositionValue start, final PositionValue next){
        if (Objects.equals(start.getX(), next.getX()) && Objects.equals(start.getY(), next.getY()) && Objects.equals(next.getValue(), start.getValue())  ) {
            LOG.log(myLevel,"Camino con Solucion");
            return 1;
        } else if (start.getValue().equals(next.getValue())) {
            LOG.log(myLevel,"No deberia pasar");
            return 0;
        } else {
            used[start.getX()][start.getY()] = true;
            int myCount = 0;
            try {
                myCount = Neighbours(start,next)
                        .stream()
                        .mapToInt((PositionValue s)->countPathForGiven(s,next))
                        .reduce(Integer::sum).getAsInt();
                        LOG.log(myLevel,"myCount "+myCount);
            } catch (java.util.NoSuchElementException e){
                LOG.log(myLevel,e.getMessage());
            }
            used[start.getX()][start.getY()] = false;
            count[start.getX()][start.getY()] += myCount;
            return myCount;
        }
    }
    private ArrayList<PositionValue> getGivenCells(Hidato hidato){
        ArrayList<PositionValue> PV = new ArrayList<>(10);
        for (int i = 0; i < hidato.getSizeX(); i += 1) {
            for (int j = 0; j < hidato.getSizeY(); j += 1) {
                if ( hidato.getCell(i, j).getType().equals(Type.GIVEN) ) {
                    PV.add(new PositionValue(i,j,hidato.getCell(i, j).getVal()));
                } 
            }
        }
        PV.sort((CapaDomini.Tauler.PositionValue a,CapaDomini.Tauler.PositionValue b)->a.getValue()-b.getValue());//o al reves
        return PV;
    }
    public double getDifficultyAsDouble(Hidato hidato){
        LOG.setUseParentHandlers(false);//to deactivate the logger
        this.used = new boolean[hidato.getSizeX()][hidato.getSizeY()];
        this.count = new Integer[hidato.getSizeX()][hidato.getSizeY()];  
        this.hidato = hidato;
        for (int i = 0; i < hidato.getSizeX(); i +=1){
            Arrays.fill(count[i],0);
        }
        
        ArrayList<PositionValue> myGivenCells = this.getGivenCells(hidato);
        //printArray(myGivenCells.toArray());
        for (int i = 0; i + 1 < myGivenCells.size(); i+=1){
            //this.countPathForGiven(myGivenCells.get(i),myGivenCells.get(i+1));
            this.countPossibilityOfBeingInAPAth(myGivenCells.get(i),myGivenCells.get(i+1));
        }
        
        return arrayToDouble(count);
        //System.out.print(Utils.toString(hidato));
        //printArray(count);
        //return Difficulty.EASY;
    }
    public Difficulty getDifficulty(Hidato hidato){
        return doubleToDifficulty(getDifficultyAsDouble(hidato));    
    }
    private Integer[][] bfs(PositionValue pv, Integer n){
         
        Queue<PositionValue> qp = new LinkedList<PositionValue>(); 
        Integer[][] myArray = new Integer[hidato.getSizeX()][hidato.getSizeY()];
        for (int i = 0; i < hidato.getSizeX(); i +=1){
            Arrays.fill(myArray[i],0);
        }        
        qp.add(new PositionValue(pv.getX(),pv.getY(),0));
        while (!qp.isEmpty()){
            PositionValue pvHere = qp.remove();
            if (0 == myArray[pvHere.getX()][pvHere.getY()]){
                myArray[pvHere.getX()][pvHere.getY()] = pvHere.getValue();
                final Position[] Moore ={ 
                    new Position(-1,-1), new Position(-1,0), new Position(0,-1),
                    new Position(-1,+1), new Position(+1,-1),
                    new Position(+1,+1), new Position(+1,0), new Position(0,+1),
                };
                Arrays.stream(Moore)
                    .map(p -> PositionValue.addValue(pvHere,p))
                    .filter(p -> validPosition(p))
                    //.map(p -> new PositionValue(p.getX(),p.getY(),p.getValue()))
                    .forEach(p -> qp.add(p));                
                
            } else {
                
            }
            
        }
        printArray(myArray);
        return myArray;
    }
    private boolean validPosition(PositionValue now){
        final Integer x = now.getX();
        final Integer y = now.getY();
        if ((Math.min(x, y) < 0) || (Math.max(x - hidato.getSizeX(), y - hidato.getSizeY()) >= 0)) {
            return false;
        }
        if (hidato.getCell(x, y).getType() == Type.VOID){return false;}
        if (hidato.getCell(x, y).getType() == Type.GIVEN){return false;}
        return !used[x][y];      
    }    
    private void countPossibilityOfBeingInAPAth(PositionValue start, PositionValue next){
        Integer v = next.getValue()-start.getValue(); 
        Integer[][] bfsStart = bfs(start,v/2+1);
        Integer[][] bfsNext = bfs(next,v/2+1);
        for (int i = 0; i < hidato.getSizeX(); i+= 1){
            for (int j = 0; j < hidato.getSizeY(); j+=1){
                if (bfsStart[i][j]+bfsNext[i][j] < v) {
                    count[i][j] += 1;
                }
            }
        }
        printArray(count);
        
    }
}
