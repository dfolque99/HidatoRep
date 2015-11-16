/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.Usuari;

import domini.Tauler.Board;
import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;

/**
 *
 * @author Marc Ferré Monné
 */
public class User implements Serializable{
    
    private String username;
    private String password;
    private int solvedGames;
    private int startedGames;
    private Duration totalTimePlayed;
    private int totalCreatedBoards;
    private ArrayList<Board> createdBoards;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        totalTimePlayed = Duration.ZERO;
    }
    
    /*
    Pre: Cert
    Post: Retorna el username de l'usuari.
    */
    public String getUsername() {
        return username;
    }
    
    /*
    Pre: username != null
    Post: El username de l'usuari es modificat pel parametre username.
    */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /*
    Pre: Cert
    Post: Ens retorna el password de l'usuari.
    */
    public String getPassword() {
        return password;
    }
    
    /*
    Pre: password != null
    Post: Ens modifica el password de l'usuari actual amb el parametre password.
    */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /*
    Pre: Cert
    Post: Ens retorna el nombre de jocs solucionats.
    */
    public int getSolvedGames(){
        return solvedGames;
    }
    
    /*
    Pre: Cert
    Post: Ens retorna la quantitat de jocs comencats.
    */
    public int getStartedGames(){
        return startedGames;
    }
    
    /*
    Pre: Cert.
    Post: Ens retorna l'objecte Duration totalTimePlayed que emmagatzema el temps jugat.
    */
    public Duration getTotalTimePlayed(){
        return totalTimePlayed;
    }
    
    /*
    Pre: Cert
    Post: Ens retorna la quantitat de taulers creat per l'usuari.
    */
    public int getTotalCreatedBoards(){
        return totalCreatedBoards;
    }
    
    /*
    Pre: Cert
    Post: Ens retorna la ArrayList<Board> que conte els Boards creats per l'usuari.
    */
    public ArrayList<Board> getCreatedBoards(){
        return createdBoards;
    }
    
    /*
    Pre: 0 >= pos < this.createdBoards.size()
    Post: Ens retorna la Board la qual esta a la posicio pos de createdBoard
    */
    public Board getCreatedBoard(int pos){
        return createdBoards.get(pos);
    }
    
    /*
    Pre: Cert
    Post: Ens retorna els taulers que tenim creats actualment.
    */
    public int getActualCreatedBoard(){
        return createdBoards.size();
    }
    
    /*
    Pre: Cert
    Post: Incrementa en 1 els jocs solucionats de l'usuari.
    */
    public void incrementSolvedGames(){
        solvedGames++;
    }
    
    /*
    Pre: Cert
    Post: Incrementa en 1 els jocs comencats de l'usuari.
    */
    public void incrementStartedGames(){
        startedGames++;
    }
    
    /*
    Pre: time != null
    Post: Suma el temps actual jugat amb el parametre time.
    */
    public void incrementTotalTimePlayed(Duration time){
        totalTimePlayed = totalTimePlayed.plus(time);
    }
    
    /*
    Pre: Cert
    Post: Incrementa en 1 els taulers creats de l'usuari.
    */
    public void incrementTotalCreatedBoards(){
        totalCreatedBoards++;
    }
    
    /*
    Pre: newBoard != null
    Post: Afegeix un nou Tauler al createdBoard de l'usuari.
    */
    public void addBoard(Board newBoard){
        createdBoards.add(newBoard);
    }
    
    /*
    Pre: 0 >= pos < createdBoards.size()
    Post: Esborra el tauler de la posicio passada per parametre.
    */
    public void deleteBoard(int pos){
        createdBoards.remove(pos);
    }
    /*
    public ArrayList<String> userToString(){
        ArrayList<String> arr = new ArrayList();
        arr.add(this.username);
        arr.add(this.password);
        arr.add(Integer.toString(this.solvedGames));
        arr.add(Integer.toString(this.startedGames));
        arr.add(Long.toString(this.totalTimePlayed.getSeconds()));
        arr.add(Integer.toString(this.totalCreatedBoards));
        for (Board b : this.createdBoards){
            arr.add(b.toString());
        }
        return arr;
    }
    
    public static User stringToUser(ArrayList<String> arr){
        User user = new User(arr.get(0), arr.get(1));
        user.solvedGames = Integer.parseInt(arr.get(2));
        user.startedGames = Integer.parseInt(arr.get(3));
        Duration time = Duration.ZERO;
        user.totalTimePlayed = time.plusSeconds(Long.parseLong(arr.get(4)));
        for(int i = 5; i < arr.size(); i++){
            user.addBoard(Board.toBoard(arr.get(i)));
        }
        return user;
    }
    */
}
