/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Usuari;

import java.util.Scanner;

/**
 *
 * @author Guillem
 */
public class UserControllerDriver {

    /**
     * @param args the command line arguments
     * Driver de la clase UserController.
     * Permite hacer pruebas con la clase UserController, para realizar las
     * operaciones 3, 4, 5, 6 y 8 hay que realizar login con exito previamente.
    */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User userActual = null;
        UserController u = new UserController();
        System.out.println("----UserControllerDriver----");
        System.out.println("Seleccion una opcion:");
        System.out.println("1. Crear un nuevo usuario");
        System.out.println("2. Hacer login");
        System.out.println("3. Eliminar el usuario logeado");
        System.out.println("4. Modificar el nombre del usuario logeado");
        System.out.println("5. Modificar la contraseña del usuario logeado");
        /*
        Estas ultimas opciones sirven para comprobar que las modificaciones sobre
        loggedUser solo se hacen en capa de datos si se utiliza la funcion updateUser
        */
        System.out.println("6. Actualiza la bd con las modificaciones de loggedUser");
        System.out.println("7. Modifica el atributo solvedGames de loggedUser");
        System.out.println("8. Muestra el atributo solvedGames de loggedUser");
        
        System.out.println("-1.Finalizar ejecucion");
        int op = sc.nextInt();
        while (op != -1) {
            switch(op) {
                case 1:
                    System.out.print("Introduce el nombre de usuario: ");
                    String username = sc.next();
                    System.out.print("Introduce la contraseña: ");
                    String password = sc.next();
                    int errorCode = u.createUser(username, password);
                    if (errorCode == -1) error(1);
                    else if (errorCode == -2) error(0);
                    else exito();
                    break;
                    
                case 2:
                    System.out.print("Introduce el nombre de usuario: ");
                    String username2 = sc.next();
                    System.out.print("Introduce la contraseña: ");
                    String password2 = sc.next();
                    int errorCode2 = u.login(username2, password2);
                    if (errorCode2 == -1) error(2);
                    else if (errorCode2 == -2) error(3);
                    else if (errorCode2 == -3) error(0);
                    else exito();
                    break;
                    
                case 3:
                    System.out.print("Introduce la contraseña: ");
                    String password3 = sc.next();
                    int errorCode3 = u.deleteUser(password3);
                    if (errorCode3 == -1) error(3);
                    else if (errorCode3 == -2) error(0);
                    else exito();
                    break;
                    
                case 4:
                    System.out.print("Introduce la contraseña: ");
                    String password4 = sc.next();
                    System.out.print("Introduce el nuevo nombre: ");
                    String newName = sc.next();
                    int errorCode4 = u.modifyName(password4,newName);
                    if (errorCode4 == -1) error(3);
                    else if (errorCode4 == -2) error(1);
                    else if (errorCode4 == -3) error(0);
                    else exito();
                    break;
                        
                    
                case 5:
                    System.out.print("Introduce la contraseña actual: ");
                    String password5 = sc.next();
                    System.out.print("Introduce la contraseña nueva: ");
                    String newPass = sc.next();
                    int errorCode5 = u.modifyPassword(password5, newPass);
                    if (errorCode5 == -1) error(3);
                    else if (errorCode5 == -2) error(0);
                    else exito();
                    break;
                    
                case 6:
                    int errorCode6 = u.updateUser();
                    if (errorCode6 != 0) error(0);
                    else exito();
                    break;
                    
                case 7:
                    userActual = u.getLoggedUser();
                    userActual.incrementSolvedGames();
                    exito();
                    break;
                    
                case 8:
                    userActual = u.getLoggedUser();
                    System.out.println("solvedGames: " + userActual.getSolvedGames());
                    break;
                    
                default:
                    System.out.println("Opcion no disponible");
                    
            }
            op = sc.nextInt();
        }
    }
    
    public static void error(int i) {
        switch(i){
            case 1:
                System.out.println("Ya existe un usuario con ese nombre.");
                break;
            case 2:
                System.out.println("No existe un usuario con este nombre.");
                break;
            case 3:
                System.out.println("Contraseña incorrecta.");
                break;
            default:
                System.out.println("Error desconocido");
        }
    }
    
    public static void exito() {
        System.out.println("Operacion realizada con exito!");
    }
    
}