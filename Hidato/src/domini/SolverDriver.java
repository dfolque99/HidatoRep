package domini;
import java.util.*;
/**
 * @author felix.axel.gimeno
 * @since 2015-11-13
 * @see Solver Solver
 */
import java.util.Scanner;
final public class SolverDriver {
	private SolverDriver(){throw new UnsupportedOperationException();}
	public static void main(final String... args) {
		final Solver solver = new Solver();
		Hidato hidato = new Hidato(1,1);
                try (Scanner user_input = new Scanner( System.in )) {
                System.out.println("Usage:");
                System.out.println("1 X Y → creates a new hidato of size (X,Y)");
                System.out.println("2 X Y V → modifies (X,Y) position of the hidato to value V (-1 is void, 0 blank, any other is given)");
                System.out.println("3 → tries to solve hidato (hidato must have least and biggest values)");
                System.out.println("4 → print hidato");
                System.out.println("5 → exit");
                System.out.println("6 → is hidato solvable?");
                System.out.println("7 → get random hint");
                System.out.println("8 → to clean hidato");
                
                while (true){
                    final int foo = user_input.nextInt();
                    if (1 == foo){
                        final int x = user_input.nextInt(), y = user_input.nextInt();
                        hidato = new Hidato(x,y);
                    }
                    if (2 == foo){
                        final int x = user_input.nextInt(), y = user_input.nextInt(), v = user_input.nextInt();
                        hidato.setCell(x, y, new Cell(v, v > 0 ? Type.GIVEN : Type.VOID));
                    }
                    if (3 == foo){
                        Utils.clean(hidato);
                        final boolean b = solver.solve(hidato);
                        System.out.println(b ? "Has solution" : "No solution");
                        hidato = solver.getHidato();
                    }
                    if (4 == foo){
                        System.out.println(Utils.toString(hidato));
                    }
                    if (5 == foo) {break;}
                    if (6 == foo){
                        final boolean b = solver.solve(hidato);
                        System.out.println(b ? "Has solution" : "No solution");
                    }
                    if (7 == foo){
                        final ArrayList<Integer> A = solver.getHint(hidato);
                        hidato.setCell(A.get(0),A.get(1),new Cell(A.get(2),Type.GIVEN));
                    
                    }
                }
            }
	}

}