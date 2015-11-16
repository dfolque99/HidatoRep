package domini.Partida;

/**
 * Nota: hi ha funcionalitats que no funcionen perquè les classes de la capa de 
 * persistencia son stubs. Coses que no funcionen:
 * 
 * - No es comprova usuari/contrasenya
 * - No es poden guardar partides, ni carregar-ne, ni esborrar-ne
 */

import domini.Tauler.CtrHidato;
import domini.Rank.RankingController;
import domini.Tauler.Hidato;
import domini.Tauler.GeneratorController;
import domini.Tauler.HidatoSet;
import domini.Usuari.HidatoUser;
import domini.Usuari.HidatoUserController;
import domini.Tauler.SolverController;
import domini.Misc.Utils;
import java.util.Scanner;

/**
 * Driver de partida. Conte l'estructura que seguiria un jugador que vulgues jugar 
 * una partida. Permet fer moltes partides consecutives, i tambe canviar d'usuari
 * @author Pau Surrell
 */
public class DriverGame {
    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("----GameDriver----");
        System.out.println("Introdueix el nom d'usuari i la contrasenya, o '.' per sortir:");
        String username = sc.next();
        String password = sc.next();
        while (username != "." && password != null){
            System.out.println("Selecciona una opcio:");
            System.out.println("1. Jugar una partida");
            System.out.println("2. Eliminar una partida");
            System.out.println("-1. Sortir sessio i finalitzar");
            HidatoSet hidatoSet = new HidatoSet();
            HidatoUser user = new HidatoUser(username, password);
            HidatoUserController hidatoUserController = new HidatoUserController();
            CtrDBGame ctrDBGame = new CtrDBGame();
            SolverController solver = new SolverController();
            RankingController ctrRanking = new RankingController();
           // GeneratorController hidatoGenerator = new GeneratorController(4,5); //tamany del hidato
            CtrGameManager ctrGameManager = new CtrGameManager(hidatoSet, ctrDBGame, solver, ctrRanking, hidatoUserController);
            CurrentGameController ctrCurrentGame = null;
            hidatoUserController.login(username, password);

            int op = sc.nextInt();
            while (op != -1){
                switch(op){
                    case 1: //Jugar partida
                        System.out.println("Vols crear una nova partida, o recuperar una guardada?");
                        System.out.println("1. Crear nova partida");
                        System.out.println("2. Recuperar una partida guardada");
                        System.out.println("-1. Tornar al primer menu");
                        int op2 = sc.nextInt();
                        while (op2 != 1 && op2 != 2 && op2 != -1){
                            System.out.println("Introdueix un dels nombres correctes!");
                            op2 = sc.nextInt();
                        }
                        switch(op2){
                            case 1: //Crear nova partida
                                System.out.println("Introdueix nom de partida, nivell d'ajuda (LOW/MEDIUM/HIGH) i dificultat (EASY/MEDIUM/HARD):");
                                String name = sc.next();    
                                Help help = Help.valueOf(sc.next());
                                Difficulty difficulty = Difficulty.valueOf(sc.next());
                                GeneratorController hidatoGenerator;
                                switch (difficulty){
                                    case EASY: 
                                        hidatoGenerator = new GeneratorController(6,6); //tamany del hidato
                                        break;
                                    case MEDIUM:
                                        hidatoGenerator = new GeneratorController(7,8); //tamany del hidato
                                        break;
                                    case HARD:
                                        hidatoGenerator = new GeneratorController(9,9); //tamany del hidato
                                        break;
                                    default: hidatoGenerator = new GeneratorController(0,0);//Mai s'hauria d'arribar aqui
                                }
                                Hidato hidato = hidatoGenerator.generateHidato(difficulty);
                                if (hidato == null) System.out.println("hidato nul");
                                ctrCurrentGame = ctrGameManager.createGame(name, hidato, help);
                                if (ctrCurrentGame == null){
                                    System.out.println("Error. Possibles causes:");
                                    System.out.println("- Ja existia una partida amb el nom introduit");
                                    System.out.println("- El nivell d'ajuda no es LOW/MEDIUM/HIGH");
                                    op2 = -1;
                                }else System.out.println("S'ha creat correctament la partida");
                                break;

                            case 2: //Recuperar partida guardada
                                System.out.println("Introdueix el nom de la partida a recuperar:");
                                String name2 = sc.next();
                                ctrCurrentGame = ctrGameManager.restoreGame(name2);
                                if (ctrCurrentGame == null) System.out.println("Error. No hi ha cap partida d'aquest usuari amb aquest nom");
                                break;
                            default: break; //Mai s'hauria d'arribar aqui
                        }
                        if (op2 != -1){
                            Game game = ctrCurrentGame.getGame();
                            Hidato hidato = game.getHidato();
                            CtrHidato ctrHidato = new CtrHidato(hidato);
                            System.out.println(Utils.toString(hidato));
                            System.out.println("Selecciona quina accio vols fer:");
                            System.out.println("1 v x y -> posar el nombre v a la casella (x,y) (v = 0 per esborrar)");
                            System.out.println("2 -> demanar una pista");
                            System.out.println("3 -> fer un check (comprovar si el hidato te solucio)");
                            System.out.println("4 -> pausar la partida");
                            System.out.println("5 -> guardar la partida");
                            System.out.println("6 -> reiniciar la partida");
                            System.out.println("7 -> resoldre la partida");
                            int op3 = sc.nextInt();
                            while (op3 != 5 && !ctrHidato.isSolved()){
                                switch(op3){
                                    case 1:
                                        int value = sc.nextInt();
                                        int posx = sc.nextInt();
                                        int posy = sc.nextInt();
                                        int errCode = ctrCurrentGame.putValue(value, posx, posy);
                                        switch(errCode){
                                            case -1: System.out.println("Compte! El hidato ja no te solucio"); break;
                                            case -2: System.out.println("Compte! Hi ha dos nombres consecutius separats"); break;
                                            case -3: System.out.println("Error: valor no valid"); break;
                                            case -4: System.out.println("Error: ja hi ha una cell amb aquest valor"); break;
                                            case -5: System.out.println("Error: la posicio donada no es valida"); break;
                                            default: System.out.println("S'ha colocat el valor correctament"); break;
                                        }
                                        break;
                                    case 2:
                                        int errCode2 = ctrCurrentGame.requestHint();
                                        if (errCode2 == -1) System.out.println("No s'ha posat pista perque el hidato no te solucio");
                                        else System.out.println("S'ha colocat la pista");
                                        break;
                                    case 3:
                                        Boolean solvable = ctrCurrentGame.check();
                                        if (solvable) System.out.println("El hidato te solucio");
                                        else System.out.println("El hidato no te solucio");
                                        break;
                                    case 4:    
                                        ctrCurrentGame.pause();
                                        System.out.println("Joc pausat. Escriu un 0 per continuar");
                                        int aux = sc.nextInt();
                                        while (aux != 0) aux = sc.nextInt();
                                        System.out.println("Continua el joc");
                                        ctrCurrentGame.unpause();
                                        break;
                                    case 6:
                                        ctrCurrentGame.restartGame();
                                        hidato = game.getHidato();
                                        ctrHidato = new CtrHidato(hidato);
                                        break;
                                    case 7:
                                        ctrCurrentGame.solve();
                                        hidato = game.getHidato();
                                        ctrHidato = new CtrHidato(hidato);
                                    default:
                                }
                                System.out.println(Utils.toString(ctrHidato.getHidato()));
                                System.out.println("Selecciona quina accio vols fer:");
                                System.out.println("1 v x y -> posar el nombre v a la casella (x,y) (v = 0 per esborrar");
                                System.out.println("2 -> demanar una pista");
                                System.out.println("3 -> fer un check (comprovar si el hidato te solucio)");
                                System.out.println("4 -> pausar la partida");
                                System.out.println("5 -> guardar la partida");
                                System.out.println("6 -> reiniciar la partida");
                                System.out.println("7 -> resoldre la partida");
                                
                                op3 = sc.nextInt();
                            }
                            ctrCurrentGame.saveGame();
                            if (op3 == 5) System.out.println("S'ha guardat la partida");
                            else System.out.println("Hidato resolt!!");
                        }
                        break;
                    case 2: //Eliminar partida
                        System.out.println("Introdueix el nom de la partida a eliminar:");
                        String name3 = sc.next();
                        ctrGameManager.deleteGame(name3);
                        break;
                    default: System.out.println("Introdueix un dels nombres valids!"); break;    

                }
            System.out.println("Selecciona una opcio:");
            System.out.println("1. Jugar una partida");
            System.out.println("2. Eliminar una partida");
            System.out.println("-1. Sortir sessio i finalitzar");
            op = sc.nextInt();            
            }
        System.out.println("----GameDriver----");
        System.out.println("Introdueix el nom d'usuari i la contrasenya, o '.' per sortir:");
        username = sc.next();
        password = sc.next();
        }        
    }
}
