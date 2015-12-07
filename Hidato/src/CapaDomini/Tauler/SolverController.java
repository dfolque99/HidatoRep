package CapaDomini.Tauler;

import CapaDomini.Misc.Position;
import CapaDomini.Misc.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;
/**
 * hidoku, hidato, king's tour, hamiltonian path, longest simple path ...
 * Hidato is NP-complete
 * current algorithm is greedy aproximmation of longest simple path between two given cells
 * @author felix.axel.gimeno
 * @version 0.41
 * @since 2015-11-07
 * @see <a href="https://en.wikipedia.org/wiki/Hidato">Hidato</a>
 * @see <a href="https://en.wikipedia.org/wiki/Knight%27s_tour#Warnsdorf.27s_rule">Warnsdorf's rule</a>
 * @see <a href="https://en.wikipedia.org/wiki/Hamiltonian_path_problem">Hamiltonian</a>
 */
public class SolverController {
    /**
     * Moore Neighbourhood
     */
    private final static Position[] Moore ={ 
        new Position(-1,-1), new Position(-1,0), new Position(0,-1),
        new Position(-1,+1), new Position(+1,-1),
        new Position(+1,+1), new Position(+1,0), new Position(0,+1),
      };
    /**
     * the instance of hidato that will be modified and solved
     */
    private Hidato board;

    /**
     * Current used cells of Hidato board
     */
    private boolean[][] used;
   
    /**
     * value to position, only for given cells
     */
    private Position[] givenCells;
    
    /**
     * for a value, gives value next given cell
     */
    private Integer[] next;
    /**
     * value of starting cell
     */
    private Integer start;
    /**
     * value of finishing cell
     */
    private Integer finish; 
    
    /**
     * cache of function uncachedValidPosition for function cachedValidPosition
     */ 
    private boolean[][][] myCache;
    
    /**
     * constructor, does nothing
     */
    public SolverController() {

    }
    
    /**
     * Returns the internal board, warning, blank input cells with non zero values become given cells internally 
     * @return this.board
     */
    private Hidato getHidato() {
        return this.board;
    }

    public Hidato getHidato(Hidato toCompare){
        Hidato mine = new Hidato(getHidato());
        for (int i = 0; i < mine.getSizeX(); i+=1){
            for (int j = 0; j < mine.getSizeY();j+=1){
                Type f = toCompare.getCell(i, j).getType();
                mine.setCell(i, j, new Cell(mine.getCell(i, j).getVal(),f));
            }
        }
        return mine;
    }
    
    /**
     * sets the environment for solve
     *
     * @param hidato
     */
    private void upload(final Hidato hidato) {
        
        board = hidato;
        used = new boolean[board.getSizeX()][board.getSizeY()];
        //cache = new ConcurrentHashMap<>((board.getSizeX()+1)*(board.getSizeY()+1));
        NavigableMap<Integer, Position> myMap = new TreeMap<>();
        for (int i = 0; i < board.getSizeX(); i += 1) {
            for (int j = 0; j < board.getSizeY(); j += 1) {
                Utils.blankToGiven(board.getCell(i, j));
                if (board.getCell(i, j).getType() == Type.GIVEN) {
                    myMap.put(board.getCell(i, j).getVal(), new Position(i, j));
                }
            }
        }
        start = myMap.firstKey();
        finish = myMap.lastKey(); 
        givenCells = new Position[finish+1];
        next = new Integer[finish+1];
        
        for(Integer i = start; i <= finish; i += 0x1){
            givenCells[i]=myMap.get(i);
            next[i]=myMap.higherKey(i);

        }
        myCache = new boolean[board.getSizeX()+1][board.getSizeY()+1][finish+1];
        preprocessBeforeBacktracking();
        
    }

    /**
     * Solves a Hidato modifying the hidato given to a solution
     *
     * @param hidato is a hidato with start and finish cells correct
     * @return true if hidato has a solution
     */
    public boolean solve(final Hidato hidato) {
        upload(new Hidato(hidato));
        return solve();
    }

    /**
     * tries to solve hidato
     *
     * @return true if hidato can be solved,
     */
    private boolean solve() {
        return solveWithBacktracking(givenCells[start].getX(), givenCells[start].getY(), start);
    }

    /**
     * Solves a Hidato and returns a random cell not in input/non void
     *
     * @param hidato	Hidato to solve, it is not modified, has BLANK cells with value 0
     * @return	3 numbers, x-position, y-position, value of a cell in a solution
     * to hidato H, if not possible returns null
     */
    public ArrayList<Integer> getHint(final Hidato hidato) {
        /*upload(new Hidato(hidato));
        if (!solve()) {
            return null;
        }*/
        if (!solve(hidato)) {return null;}
        Random rand = new Random();
        for (int count = 0; count < 100; count += 1) {

            int x = rand.nextInt(hidato.getSizeX());
            int y = rand.nextInt(hidato.getSizeY());
            if (hidato.getCell(x, y).getType() == Type.BLANK && 0 == hidato.getCell(x, y).getVal()) {
                return new ArrayList<>(Arrays.asList(x, y, board.getCell(x, y).getVal()));
            }
        }
        for (int i = 0; i < hidato.getSizeX(); i+=1){
            for (int j = 0; j < hidato.getSizeY(); j += 1){
                //System.out.println("debug gethint inside loop " +((board.getCell(i, j).getType() == Type.BLANK) ?"BLANK" :"NOT BLANK") );
                //System.out.println("debug gethint inside loop " +((board.getCell(i, j).getType() == Type.VOID)  ?"VOID" :"NOT VOID") );
                //System.out.println("debug gethint inside loop " +((board.getCell(i, j).getType() == Type.GIVEN)  ?"GIVEN" :"NOT GIVEN") );
                
            if (hidato.getCell(i, j).getType() == Type.BLANK && 0 == hidato.getCell(i, j).getVal()) {
                    return new ArrayList<>(Arrays.asList(i, j, board.getCell(i, j).getVal()));
                }               
            }
        }
        //System.out.println("debug: gethint");
        return null;
    }
    /**
    * recursively solves the easy bits of the internal hidato 
    */
    private void preprocessBeforeBacktracking(){
        for (int i = 0; i < board.getSizeX(); i+=1){
            for (int j = 0; j < board.getSizeY(); j+=1 ){
                Integer count = 0;
                Integer n = -1;
                for (int k = start; k <= finish; k+=1){
                    boolean b = uncachedValidPosition(i,j,k);
                    myCache[i][j][k]= b;
                    if (Type.GIVEN != board.getCell(i, j).getType()) {
                        count += b ? 1 : 0;
                        n = b ? k : n;
                    }
                    
                }
                if (1 == count) {
                    board.setCell(i,j,new Cell(n,Type.GIVEN));
                    //System.out.println("presolve, only one value for a position i,j,n "+i+" "+j+" "+n);
                    upload(board);
                    return;
                }
            }
        }
        for (int k = start; k <= finish; k+=1){
            Integer count = 0;
            Integer x = -1;
            Integer y = -1;
            for (int i = 0; i < board.getSizeX(); i+=1){
                for (int j = 0; j < board.getSizeY(); j+=1 ){
                    boolean b = uncachedValidPosition(i,j,k);
                    myCache[i][j][k]= b;
                    if (Type.GIVEN != board.getCell(i, j).getType()) {
                        count += b ? 1 : 0;
                        x = b ? i : x;
                        y = b ? j : y;                    
                    }
                    
                }
            }    
            if (1 == count) {
                board.setCell(x,y,new Cell(k,Type.GIVEN));
                //System.out.println("presolve, only one position for a value i,j,n "+x+" "+y+" "+k);
                upload(board);
                return;
            }

        }
        
    }
    private boolean uncachedValidPosition(final int x, final int y, final int n){
        if (n > finish) {return false;}
        if ((Math.min(x, y) < 0) || (Math.max(x - board.getSizeX(), y - board.getSizeY()) >= 0)) {
            return false;
        }
        if (board.getCell(x, y).getType() == Type.VOID){return false;}
        
        final Position positionOfValueN = givenCells[n];
        if (positionOfValueN != null){
            return positionOfValueN.equals(new Position(x, y));
        }
        if (board.getCell(x, y).getType() == Type.GIVEN){return false;}
        if (Position.notEnoughDistance(next[n], givenCells[next[n]], n, new Position(x, y))) {
            return false;
        }
        return true;
    }
    private boolean cachedValidPosition(final int x, final int y, final int n){
        if (n > finish) {return false;}
        if ((Math.min(x, y) < 0) || (Math.max(x - board.getSizeX(), y - board.getSizeY()) >= 0)) {
            return false;
        }
        Boolean b = myCache[x][y][n];
        return b&&!used[x][y];
   }

    /**
     *
     * @param x	x-position of cell base
     * @param y	y-position of cell base
     * @param n	number to try in neighbour cells
     * @return	list of available neighbours of cell base
     */
    private ArrayList<int[]> getNeighboursUnsorted(final int x, final int y, final int n) {
        return Arrays.stream(Moore)
                .filter(p -> cachedValidPosition(x + p.getX(), y + p.getY(), n + 1))
                .map(p -> new int[]{p.getX(), p.getY(), n + 1})
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Warnsdoff's rule
     * @param x	x-position of cell base
     * @param y	y-position of cell base
     * @param n	number to try in neighbour cells
     * @return	list of available neighbours of cell base sorted using
     */
    private ArrayList<int[]> getNeighboursSorted(final int x, final int y, final int n) {
        ArrayList<int[]> result = getNeighboursUnsorted(x, y, n);
        Map<int[], Integer> Values = new HashMap<>(8);
        result.stream().forEach(s-> {Values.put(s,getNeighboursUnsorted(s[0], s[1], s[2]).size());});
        Collections.sort(result, (final int[] a, final int[] b) -> -Values.get(b) + Values.get(a));
        return result;
    }
    /**
     * Worst-first search, worst is most distance to next given cell
     * For greedy search longest simple path 
     * @param x
     * @param y
     * @param n
     * @return 
     */
    private ArrayList<int[]> getNeighboursSortedWorst(final int x, final int y, final int n) { //worst first search ?
        //ArrayList<int[]> result = getNeighboursUnsorted(x, y, n); //does this make any noticiable difference?
        ArrayList<int[]> result = getNeighboursSorted(x, y, n);
        Map<int[], Integer> Values = new HashMap<>(8);
        result.stream().forEach(s-> {Values.put(s,Position.distance(givenCells[next[n]],new Position(s[0],s[1])));});
        Collections.sort(result, (final int[] a, final int[] b) -> +Values.get(b) -Values.get(a));
        return result;
    }
    /**
     * Tries to solve a Hidato board using backtracking/recursion It must start
     * with the starting cell (n=1)
     *
     * @param x	x-position of cell input
     * @param y y-position of cell input
     * @param n number to try for cell input
     * @return true if current Hidato board can be solved with cell in (x,y)
     * with number n, otherwise false
     */
    private boolean solveWithBacktracking(final int x, final int y, final int n) {
        if (n == finish) { //exit case
            return board.getCell(x, y).getVal() == n;
        }
        //recursion case
        board.getCell(x, y).setVal(n);    
        used[x][y] = true;
        if (getNeighboursSortedWorst(x, y, n).stream().anyMatch((s) -> (solveWithBacktracking(x + s[0], y + s[1], n + 1)))) {
            return true;
        }
        used[x][y] = false;
        return false;
    }

    
}
