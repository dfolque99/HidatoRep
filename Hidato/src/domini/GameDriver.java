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
            System.out.println("3. Sortir sessio");
            System.out.println("-1. Finalitzar execucio");
            HidatoSet hidatoSet = new HidatoSet();
            User user = new User(username, password);
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
                            default: System.out.println("Introdueix un dels nombres correctes!");
                        }
                        Game game = ctrCurrentGame.getGame();
                        Hidato hidato = game.getHidato();
                        CtrHidato ctrHidato = new CtrHidato(hidato);
                        
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
                            }
                        }
                        //FALTA CASE 5
                        break;
                    case 2: //Recuperar partida guardada
                        System.out.println("Introdueix el nom de la partida a recuperar:");
                        String name2 = sc.next();
                        ctrCurrentGame = ctrGameManager.restoreGame(name2);
                        if (ctrCurrentGame == null) System.out.println("Error. No hi ha cap partida d'aquest usuari amb aquest nom");
                        break;
                    case 3: //Eliminar partida
                        //FALTA
                    case 4: //Sortir sessio
                        //FALTA
                    default:     

                }
            }
            System.out.println("Selecciona una opcio:");
            System.out.println("1. Jugar una partida");
            System.out.println("2. Eliminar una partida");
            System.out.println("3. Sortir sessio");
            System.out.println("-1. Finalitzar execucio");
            op = sc.nextInt();
        }
        System.out.println("----GameDriver----");
        System.out.println("Introdueix el nom d'usuari i la contrasenya, o '.' per sortir:");
        username = sc.next();
        password = sc.next();
    }
}
