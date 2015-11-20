package domini.Tauler;

import domini.Misc.Position;
import domini.Misc.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
/**
 * @author felix.axel.gimeno
 * @version 0.4
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
     * constructor, does nothing
     */
    public SolverController() {

    }

    /**
     *
     * @return this.board
     */
    public Hidato getHidato() {
        return this.board;
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
        presolve();
        
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
        return solve(givenCells[start].getX(), givenCells[start].getY(), start);
    }

    /**
     * Solves a Hidato and returns a random cell not in input/non void
     *
     * @param hidato	Hidato to solve, it is not modified, has BLANK cells with value 0
     * @return	3 numbers, x-position, y-position, value of a cell in a solution
     * to hidato H, if not possible returns null
     */
    public ArrayList<Integer> getHint(final Hidato hidato) {
        upload(new Hidato(hidato));
        if (!solve()) {
            return null;
        }
        Random rand = new Random();
        for (int count = 0; count < 100; count += 1) {

            int x = rand.nextInt(board.getSizeX());
            int y = rand.nextInt(board.getSizeY());
            if (board.getCell(x, y).getType() == Type.BLANK) {
                return new ArrayList<>(Arrays.asList(x, y, board.getCell(x, y).getVal()));
            }
        }
        for (int i = 0; i < board.getSizeX(); i+=1){
            for (int j = 0; j < board.getSizeY(); j += 1){
                if (board.getCell(i, j).getType() == Type.BLANK) {
                    return new ArrayList<>(Arrays.asList(i, j, board.getCell(i, j).getVal()));
                }               
            }
        }
        return null;
    }

    /**
     * Distinguishes whether cell input is available for number n
     *
     * @param x	x-position of cell input
     * @param y y-position of cell input
     * @param n number to try for cell input
     * @return true if cell can be assigned number n, false otherwise
     */
    /*private boolean validPosition(final int x, final int y, final int n) { //to do: can i split this, mnemoize this ...
        if (n > finish) {return false;}
        if ((Math.min(x, y) < 0) || (Math.max(x - board.getSizeX(), y - board.getSizeY()) >= 0)) {
            return false;
        }
        final Position positionOfValueN = givenCells[n];
        if (positionOfValueN != null){
            return positionOfValueN.equals(new Position(x, y));
        }
        if (Position.notEnoughDistance(next[n], givenCells[next[n]], n, new Position(x, y))) {
            return false;
        }
        if (board.getCell(x, y).getType() == Type.VOID){return false;}
        boolean isValid = true;
        if (used[x][y]
                || (board.getCell(x, y).getType() == Type.GIVEN && board.getCell(x, y).getVal() != n)
                ) {
            isValid = false;
        }
        return isValid;
    }*/
    /*public class PositionValue {
        private final Position p;
        private final Integer n;
        private PositionValue() {throw new UnsupportedOperationException();}
        public PositionValue(Position p,Integer n){this.p=p;this.n=n;}
        public PositionValue(final int x, final int y, final int n){this.p = new Position(x,y); this.n = n;}
        public Position getP(){return this.p;}
        public Integer  getN(){return this.n;}
    }
    */
    //private Map<PositionValue, Boolean> cache; 
    private boolean[][][] myCache; 
    private void presolve(){
        for (int i = 0; i < board.getSizeX(); i+=1){
            for (int j = 0; j < board.getSizeY(); j+=1 ){
                Integer count = 0;
                Integer n = -1;
                for (int k = start; k <= finish; k+=1){
                    boolean b = f(i,j,k);
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
                    boolean b = f(i,j,k);
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
    private boolean f(final int x, final int y, final int n){
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
    private boolean g(final int x, final int y, final int n){
        //Boolean b = /*f(pv); // */ cache.computeIfAbsent(new PositionValue(x,y,n), t -> f(t.getP().getX(),t.getP().getY(),t.getN()));
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
                //.filter(p -> validPosition(x + p.getX(), y + p.getY(), n + 1))
                .filter(p -> g(x + p.getX(), y + p.getY(), n + 1) /*&& !used[x + p.getX()][y + p.getY()]*/)
                //.filter(p ->g(new PositionValue( new Position(x + p.getX(), y + p.getY()), n + 1)))
                .map(p -> new int[]{p.getX(), p.getY(), n + 1})
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * @param x	x-position of cell base
     * @param y	y-position of cell base
     * @param n	number to try in neighbour cells
     * @return	list of available neighbours of cell base sorted using
     * Warnsdoff's rule
     */
    private ArrayList<int[]> getNeighboursSorted(final int x, final int y, final int n) {
        ArrayList<int[]> result = getNeighboursUnsorted(x, y, n);
        Map<int[], Integer> Values = new HashMap<>(8);
        result.stream().forEach(s-> {Values.put(s,getNeighboursUnsorted(s[0], s[1], s[2]).size());});
        Collections.sort(result, (final int[] a, final int[] b) -> -Values.get(b)
                + Values.get(a));
        return result;
    }
    private ArrayList<int[]> getNeighboursSortedWorst(final int x, final int y, final int n) { //worst first search ?
        //ArrayList<int[]> result = getNeighboursUnsorted(x, y, n);
        ArrayList<int[]> result = getNeighboursSorted(x, y, n);
        Map<int[], Integer> Values = new HashMap<>(8);
        result.stream().forEach(s-> {Values.put(s,Position.distance(givenCells[next[n]],new Position(s[0],s[1])));});
        Collections.sort(result, (final int[] a, final int[] b) -> +Values.get(b)
                -Values.get(a));
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
    private boolean solve(final int x, final int y, final int n) {
        if (n == finish) { //exit case
            return board.getCell(x, y).getVal() == n;
        }
        //recursion case
        board.getCell(x, y).setVal(n);    
        used[x][y] = true;
        if (getNeighboursSortedWorst(x, y, n).stream().anyMatch((s) -> (solve(x + s[0], y + s[1], n + 1)))) {
            return true;
        }
        used[x][y] = false;
        return false;
    }
    
    public boolean solve2(Hidato h, Integer x, Integer y){
        upload(h);
        for (int m = finish; m >= start; m-=1 ){
            if (f(x,y,m)){
                board.setCell(x,y,new Cell(m,Type.GIVEN));
                if ( new SolverController().solve(board) ){return true;}
                board.setCell(x,y,new Cell(0,Type.BLANK));
            }
        }
        return false;
    }    
    public boolean solve3(Hidato h, Integer n){
        upload(h);
        for (int i = 0; i < board.getSizeX(); i += 1) {
            for (int j = 0; j < board.getSizeY(); j += 1) {
                if (f(i,j,n)){
                    board.setCell(i,j,new Cell(n,Type.GIVEN));
                    if ( new SolverController().solve(board) ){return true;}
                    board.setCell(i,j,new Cell(0,Type.BLANK));
                }
            }
        }
        return false;
    }
    
}
