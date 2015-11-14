/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;



import java.util.Scanner;

/**
 *
 * @author Pau
 */
public class GameDriver {
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
            CtrDBGame ctrDBGame = new CtrDBGame();
            Solver solver = new Solver();
            CtrRanking ctrRanking = new CtrRanking();
            CtrGameManager ctrGameManager = new CtrGameManager(hidatoSet, user, ctrDBGame, solver, ctrRanking);
            CtrCurrentGame ctrCurrentGame = null;


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
                                System.out.println("Introdueix nom de partida, nom del tauler i nivell d'ajuda (LOW/MEDIUM/HARD):");
                                String name = sc.next();
                                String hidatoName = sc.next();
                                Help help = Help.valueOf(sc.next());
                                ctrCurrentGame = ctrGameManager.createGame(name, hidatoName, help);
                                if (ctrCurrentGame == null){
                                    System.out.println("Error. Possibles causes:");
                                    System.out.println("- Ja existia una partida amb el nom introduit");
                                    System.out.println("- No existeix cap tauler amb el nom introduit");
                                    System.out.println("- El nivell d'ajuda no es LOW/MEDIUM/HARD");
                                }else System.out.println("S'ha creat correctament la partida");
                                break;

                            case 2: //Recuperar partida guardada
                                System.out.println("Introdueix el nom de la partida a recuperar:");
                                String name2 = sc.next();
                                ctrCurrentGame = ctrGameManager.restoreGame(name2);
                                if (ctrCurrentGame == null) System.out.println("Error. No hi ha cap partida d'aquest usuari amb aquest nom");
                                break;
                            default: break;
                        }
                        if (op2 != -1){
                            Game game = ctrCurrentGame.getGame();
                            Hidato hidato = game.getHidato();
                            CtrHidato ctrHidato = new CtrHidato(hidato);
                            System.out.println(Utils.toString(hidato));
                            System.out.println("Selecciona quina accio vols fer:");
                            System.out.println("1 v x y -> posar el nombre v a la casella (x,y)");
                            System.out.println("2 -> demanar una pista");
                            System.out.println("3 -> fer un check (comprovar si el hidato te solucio)");
                            System.out.println("4 -> pausar la partida");
                            System.out.println("5 -> guardar la partida");
                            int op3 = sc.nextInt();
                            while (op3 != 5){
                                switch(op3){
                                    //FALTA CASE 1 2 3 4
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
                                    default:
                                }
                                System.out.println(Utils.toString(hidato));
                                System.out.println("Selecciona quina accio vols fer:");
                                System.out.println("1 v x y -> posar el nombre v a la casella (x,y)");
                                System.out.println("2 -> demanar una pista");
                                System.out.println("3 -> fer un check (comprovar si el hidato te solucio)");
                                System.out.println("4 -> pausar la partida");
                                System.out.println("5 -> guardar la partida");
                                op3 = sc.nextInt();
                            }
                            ctrCurrentGame.saveGame();
                            System.out.println("S'ha guardat la partida");
                        }
                        break;
                    case 2: //Eliminar partida
                        //FALTA
                        System.out.println("Introdueix el nom de la partida a eliminar:");
                        String name3 = sc.next();
                        ctrGameManager.deleteGame(name3);
                        break;
                    default: System.out.println("Introdueix un dels nombres valids!"); break;    

                }
            System.out.println("Selecciona una opcio:");
            System.out.println("1. Jugar una partida");
            System.out.println("2. Eliminar una partida");
            System.out.println("3. Sortir sessio");
            System.out.println("-1. Finalitzar execucio");
            op = sc.nextInt();            
            }
        }
        System.out.println("----GameDriver----");
        System.out.println("Introdueix el nom d'usuari i la contrasenya, o '.' per sortir:");
        username = sc.next();
        password = sc.next();
    }
}
