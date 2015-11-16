package domini.Tauler;
import domini.Misc.Utils;
import java.util.ArrayList;
import java.util.Scanner;
final public class DriverSolverController {
    
        @SuppressWarnings("UseOfSystemOutOrSystemErr")
        public static void showHelp(){
            System.out.println("A hidato to be solved must have starting and a finishing cell");
            System.out.println("Usage:");
            System.out.println("0 : shows this help again");
            System.out.println("1 X Y : creates a new hidato of size (X,Y) ");
            System.out.println("2 X Y V : modifies (X,Y) position of the hidato to value V (-1 is void, 0 blank, any other is given)");
            System.out.println("3 : is solvable + overwrite hidato with solution");
            System.out.println("4 : print hidato");
            System.out.println("5 : exit");
            System.out.println("6 : is hidato solvable?");
            System.out.println("7 : get random hint, overwrites hidato with hint");
            System.out.println("8 : clean hidato : every blank cell other than user input are set to zero");
            //System.out.println("9 : go to HidatoGeneratorDriver");
            System.out.println("Example: 1 5 7 : Creates Hidato 5x7");
            System.out.println("Example: 2 1 1 30 : Overwrites cell (1,1) to value 30");
            System.out.println("Example: 3 4 5 : Get solution, plus print hidato, plus exit ");
            System.out.println("Example: 1 5 7 2 1 1 30 3 4 5: Takes about 20 seconds");
            
        }
    
        @SuppressWarnings("UseOfSystemOutOrSystemErr")
	public static void main(final String... args){
            final SolverController solver = new SolverController();
            Hidato hidato = new Hidato(1,1);
            try (Scanner user_input = new Scanner( System.in )) {
                showHelp();  
                while (true){
                    final int foo = user_input.nextInt();
                    if (0 == foo) {showHelp();}
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
                    if (8 == foo){ 
                        Utils.clean(hidato);
                    }
                    /*if (9 == foo){
                        HidatoGeneratorDriver.main(new String[0]);
                        return;
                    }*/ 
                }
            }
        }
	private DriverSolverController() {throw new UnsupportedOperationException();}

}