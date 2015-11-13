import java.util.*;

//import java.util.logging.*;

/**
 * @author felix.axel.gimeno
 * @version 0.1 
 * @since 2015-11-07
 * @see Hidato <a href="https://en.wikipedia.org/wiki/Hidato">Hidato</a> 
 * @see Warnsdorf's rule <a href="https://en.wikipedia.org/wiki/Knight%27s_tour#Warnsdorf.27s_rule">Warnsdorf's rule</a>
 */
public class Solver {
	//private final boolean debug = true;
	//static final Logger myLog = Logger.getLogger(Solver.class.getName()); 
	// if (debug) myLog.log(Level.FINE,"...");
	/**
	 * Hidato board as array of array of cells
	 */
	private Cell[][] board;
	/**
	 * Current used cells of Hidato board
	 */
	private boolean[][] used;	
	/**
	 * ArrayList of the GIVEN cells numbers, sorted, includes starting and finishing cells
	 */
	private ArrayList<Integer> given;
	
	/**
	 * position class, stores x,y position
	 */
	public class Position{
		int x;
		int y;
		public Position(int x, int y){this.x=x; this.y=y;}
	}
	
	/**
	 * stores for a value of a cell, the cell's position
	 */
	NavigableMap<Integer,Position> myMap;
	
	private Integer getBefore(int n){
		return myMap.ceilingKey(n);
	}
	
	private Integer getNext(int n){
		return myMap.higherKey(n);
	}
	
	private boolean notEnoughDistance(int n, Position p1, int m, Position p2){
		return (Math.min(Math.abs(p1.x-p2.x), Math.abs(p1.y-p2.y)) > Math.abs(n-m));	
	}
	
	/**
	 *  Value of the biggest cell, it must be a GIVEN cell 
	 */
	private int finish;
	/**
	 * equivalent to Hidato.sizeX
	 */
	private int nRows;
	/**
	 * equivalent to Hidato.sizeY
	 */
	private int nCols;

	/**
	 * constructor
	 */
	public Solver(){
				
	}
	
	/**
	 * tries to solve a Hidato
	 * 
	 * @param h 	Hidato to try to solve
	 * @return		true if a solution exists, false otherwise
	 */
	public boolean solve(Hidato h) {
		nRows = h.getSizeX();
		nCols = h.getSizeY();
		board = new Cell[nRows][nCols];
		used  = new boolean[nRows][nCols];
		given = new ArrayList<Integer>();
		myMap = new TreeMap<Integer,Position>();
		int startX = -1;
		int startY = -1;
		for (int i = 0; i < nRows; i += 1) {
			for (int j = 0; j < nCols; j += 1) {
				board[i][j] = h.getCell(i, j).toGiven();
				if (board[i][j].getType() == Type.GIVEN) {
					given.add(board[i][j].getValue());
					myMap.put(board[i][j].getValue(), new Position(i,j));
					if(board[i][j].getValue()==1) {startX=i;startY=j;}
				}
			}
		}
		Collections.sort(given); 
		if (given != null && !given.isEmpty()) {
			finish = given.get(given.size() - 1);
			return solve(startX,startY,1);
		}
		return false;
	}

	/**
	 * Solves a Hidato and returns a random cell not in input/non void
	 * 
	 * @param h		Hidato to solve
	 * @return		3 numbers, x-position, y-position, value of a cell in a solution to hidato H, if not possible returns null
	 */
	public ArrayList<Integer> getHint(Hidato h) {
		if (!solve(h)) {return null;}
		Random rand = new Random();
		for (int count = 0; count < 100; count +=1){
			
			int x = rand.nextInt(nRows);
			int y = rand.nextInt(nCols);
			if (board[x][y].getType()==Type.BLANK){return new ArrayList<Integer>(Arrays.asList(x,y,board[x][y].getValue()));}
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
		boolean isValid = true;

		if ((Math.min(x, y) < 0)
				|| (Math.max(x - nRows, y - nCols) >= 0)
				|| (board[x][y].getType() == Type.VOID)
				|| used[x][y]//(board[x][y].getType() == Type.BLANK && board[x][y].getValue() < n && board[x][y].getValue() != 0) //quizas hay que tener boolean[][] used
				|| (board[x][y].getType() == Type.GIVEN && board[x][y].getValue() != n)
				|| (given.contains(n) && board[x][y].getValue() != n)
		) 
		{
			isValid = false;
		}
		Integer m = getNext(n);
		if (m != null){
			//if (notEnoughDistance(n, new Position(x,y), m, myMap.get(m))) {isValid = false;}
		}
		
		//if (debug && n == 4) System.out.println(String.format("isValid %b x %d y %d n %d", isValid,x,y,n));
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
		ArrayList<int[]> result = new ArrayList<int[]>();
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
		Collections.sort(result, new Comparator<int[]>() {
			@Override
			public int compare(final int[] a, final int[] b) {
				return getNeighboursUnsorted(b[0], b[1], b[2]).size()
						- getNeighboursUnsorted(a[0], a[1], a[2]).size();
			}
		});
		return result;
	}
	/**
	 * Tries to solve a Hidato board using backtracking/recursion
	 * It must start with the starting cell (n=1)
	 * 
	 * @param x		x-position of cell input
	 * @param y 	y-position of cell input
	 * @param n 	number to try for cell input
	 * @return 		true if current Hidato board can be solved with cell in (x,y) with number n, otherwise false
	 */
	private boolean solve(int x, int y, int n) {
		if (n == finish && board[x][y].getValue() == n) {return true;}
		if (!validPosition(x, y, n)) {return false;}
		if (board[x][y].getValue() != n) {board[x][y].setValue(n);}
		used[x][y]=true;
		for (int[] s : getNeighboursSorted(x, y, n)) {if (solve(x + s[0], y + s[1], n + 1)) {return true;}}
		used[x][y]=false;
		return false;
		//lento, hace falta extructura datos doble diccionario numero<->posicion
	}
	
	/**
	 * Add random given cells, to fill Hidato h
	 * 
	 * @param h Hidato with only void and given cells, the result has a solution
	 */
	public void generator(Hidato h, int iterations){
		if(!solve(h)) return;
		Random rand = new Random();
		ArrayList<Integer> Foo = new ArrayList<Integer>(myMap.keySet());
		
		boolean b = solve(h);
		for (int count = 0; count < iterations; count +=1){
			int n = 1 + rand.nextInt(finish-1);
			int x = rand.nextInt(nRows);
			int y = rand.nextInt(nCols);
			if (h.getCell(x,y).getType() == Type.BLANK && !myMap.containsKey(n)){
				board[x][y] = new GivenCell(n);
				
				h.setCell(x, y, new GivenCell(n));
				if(!solve(myMap.get(1).x,myMap.get(1).y,1)){
					h.setCell(x, y, new BlankCell());
				}
				else {
					h.setCell(x,y,new GivenCell(n));
					myMap.put(n, new Position(x,y));
				}
			}
		}
		//System.out.println(myMap);
	}
	
/*
	@SuppressWarnings("unchecked")
	public void upload(Cell[][] board, ArrayList<Integer> given, int finish) {
		this.board = board.clone();
		this.given = (ArrayList<Integer>) given.clone();
		this.finish = finish;
		nRows = board.length;
		nCols = board[0].length;
	}

	public Cell[][] download() {
		return board.clone();
	}
*/	
	public final Cell getCell(int x, int y){return (Cell) board[x][y];}

	public static void main(String[] args) {
		Solver s = new Solver();
		//Cell[] r1 = new Cell[] { new GivenCell(1) {}, new BlankCell() {}, new GivenCell(5) {}};
		//Cell[] r2 = new Cell[] { new VoidCell() {}, new GivenCell(3) {}, new BlankCell() {}};
		//s.upload(new Cell[][] { r1, r2 },
		//new ArrayList<Integer>(Arrays.asList(1, 3, 5)), 5);
		
		int n = 5;
		//s.generator(new Hidato(n,n), 0);
		//System.out.println(s.solve(0,0,1));
		s.solve(new Hidato(n,n));
		for (int i = 0; i < n; i+=1) {
			for (int j = 0; j < n; j+=1) {
				System.out.format("%s ", s.getCell(i,j).toString());	
			}
			System.out.format("\n");
		}
	}

}
