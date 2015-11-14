package domini;
import java.util.*;

/**
 * @author felix.axel.gimeno
 * @version 0.2
 * @since 2015-11-07
 * @see Hidato <a href="https://en.wikipedia.org/wiki/Hidato">Hidato</a> 
 * @see Warnsdorf's_rule <a href="https://en.wikipedia.org/wiki/Knight%27s_tour#Warnsdorf.27s_rule">Warnsdorf's rule</a>
 */
public class Solver {
        /**
         * debug parameter, set to false
         */
	private final static boolean DEBUG = false;
        /**
         * the instance of hidato that will be modified and solved
         */
	private Hidato board;
        
        /**
         * 
         * @return this.board  
         */
	public Hidato getHidato(){return this.board;}
	
        /**
	 * Current used cells of Hidato board
	 */
	private boolean[][] used;
	
	/**
	 * stores for a value of a cell, the cell's position
	 */
	private NavigableMap<Integer,Position> myMap;

        /**
         * 
         * 
         * @param value  >0 , <=finish,     
         * @return null if finish, otherwise the least higer value of the given cells of the hidato contained in the class 
         */
	private Integer getNext(Integer value){
		return myMap.higherKey(value);
	}	

	/**
	 * constructor
	 */
	public Solver(){
				
	}
	
        /**
         * sets the environment for solve
         * 
         * @param hidato 
         */
	private void upload(final Hidato hidato) {
		board = hidato;
		used  = new boolean[board.getSizeX()][board.getSizeY()];
		myMap = new TreeMap<>();
		for (int i = 0; i < board.getSizeX(); i += 1) {
			for (int j = 0; j < board.getSizeY(); j += 1) {
				Utils.blankToGiven(board.getCell(i,j));
				if (board.getCell(i,j).getType() == Type.GIVEN) {
					myMap.put(board.getCell(i,j).getVal(), new Position(i,j));
				}
			}
		}
	}
        
	/**
	 * Solves a Hidato modifying the hidato given to a solution
	 * 
	 * @param hidato is a hidato with start and finish cells correct
	 * @return true if hidato has a solution
	 */
	public boolean solve(final Hidato hidato){	upload(new Hidato(hidato)); return solve();	}

        /**
         * tries to solve hidato
         * 
         * @return true if hidato can be solved,
         */
	private boolean solve(){ return solve(myMap.get(1).getX(),myMap.get(1).getY(),1); } 
	
	/**
	 * Solves a Hidato and returns a random cell not in input/non void
	 * 
	 * @param hidato	Hidato to solve, it is not modified
	 * @return		3 numbers, x-position, y-position, value of a cell in a solution to hidato H, if not possible returns null
	 */
	public ArrayList<Integer> getHint(final Hidato hidato) {
		upload(new Hidato(hidato));
		if (!solve()) {return null;}
		Random rand = new Random();
		for (int count = 0; count < 100; count +=1){
			
			int x = rand.nextInt(board.getSizeX());
			int y = rand.nextInt(board.getSizeY());
			if (board.getCell(x, y).getType()==Type.BLANK){return new ArrayList<>(Arrays.asList(x,y,board.getCell(x, y).getVal()));}
		}
		return null;
	}
	/**
	 * Distinguishes whether cell input is available for number n
	 * 
	 * @param x		x-position of cell input
	 * @param y 	y-position of cell input
	 * @param n 	number to try for cell input
	 * @return 		true if cell can be assigned number n, false otherwise
	 */
	private boolean validPosition(int x, int y, int n) {
		if ( (Math.min(x, y) < 0)	|| (Math.max(x - board.getSizeX(), y - board.getSizeY()) >= 0)) return false;
		if (myMap.containsKey(n)) return myMap.get(n).equals(new Position(x,y));
		if (Position.notEnoughDistance(getNext(n),myMap.get(getNext(n)),n,new Position(x,y))) return false;
		boolean isValid = true;
		if ( (board.getCell(x,y).getType() == Type.VOID)
				|| used[x][y]
				|| (board.getCell(x,y).getType() == Type.GIVEN && board.getCell(x,y).getVal() != n)
				|| (myMap.containsKey(n) && board.getCell(x,y).getVal() != n)
		) 
		{
			isValid = false;
		}
		if (DEBUG) System.out.format("isValid %b x %d y %d n %d \n", isValid,x,y,n);
		return isValid;
	}

	/**
	 * 	 
	 * @param x		x-position of cell base
	 * @param y		y-position of cell base
	 * @param n		number to try in neighbour cells
	 * @return		list of available neighbours of cell base
	 */
	private ArrayList<int[]> getNeighboursUnsorted(int x, int y, int n) {
		ArrayList<int[]> result = new ArrayList<>();
		for (int i = -1; i < 2; i += 1) {
			for (int j = -1; j < 2; j += 1) {
				if (Math.max(Math.abs(i), Math.abs(j)) == 1
						&& validPosition(x + i, y + j, n + 1)) {
					result.add(new int[] { i, j, n + 1 });
				}
			}
		}
		return result;
	}

	/**
	 * @param x		x-position of cell base
	 * @param y		y-position of cell base
	 * @param n		number to try in neighbour cells
	 * @return		list of available neighbours of cell base sorted using Warnsdoff's rule
	 */
	private ArrayList<int[]> getNeighboursSorted(int x, int y, int n) {
		ArrayList<int[]> result = getNeighboursUnsorted(x, y, n);
		Collections.sort(result, (final int[] a, final int[] b) -> - getNeighboursUnsorted(b[0], b[1], b[2]).size()
                        + getNeighboursUnsorted(a[0], a[1], a[2]).size());
		return result;
	}
		
	/**
	 * Tries to solve a Hidato board using backtracking/recursion
	 * It must start with the starting cell (n=1)
	 * 
	 * @param x	x-position of cell input
	 * @param y 	y-position of cell input
	 * @param n 	number to try for cell input
	 * @return 	true if current Hidato board can be solved with cell in (x,y) with number n, otherwise false
	 */
	private boolean solve(int x, int y, int n) {
		if (n == myMap.lastKey() && board.getCell(x,y).getVal() == n) {return true;}
		if (!validPosition(x, y, n)) {return false;}
		if (board.getCell(x,y).getVal() != n) {board.getCell(x,y).setVal(n);}
		
		used[x][y]=true;
                if (getNeighboursSorted(x, y, n).stream().anyMatch((s) -> (solve(x + s[0], y + s[1], n + 1)))) {
                    return true;
                }
		used[x][y]=false;
		
		if (DEBUG) System.out.format("n %d, x %d y %d \n",n,x,y);
		return false;
	}
        
        /**
         * only for backwards compliance
         */
        public boolean uploadAndSolve(Hidato hidato){return this.solve(hidato);}
}
