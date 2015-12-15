/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Usuari;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;

/**
 * Herencia de la clase User.
 * Añade los atributos totalScore, bestScore, bestTime y createdHidatos, ademas
 * de unas cuantas funciones para devolver estadisticas relativas al usuario.
 * @author Guillem
 */
public class HidatoUser extends User implements Serializable {
    
    private int totalScore;
    private int bestScore;
    private Duration bestTime;
    private ArrayList<String> createdHidatos;
    
    /*
    Constructora de la clase. Inicializa los atributos totalScore y bestScore a 0,
    bestTime a null (inicialmente el mejor tiempo es indeterminado) y createdHidatos
    a un ArrayList vacio
    */
    public HidatoUser(String username, String password) {
        super(username,password);
        totalScore = 0;
        bestScore = 0;
        bestTime = null;
        createdHidatos = new ArrayList<>();
    }
    
    /*
    Pre: -
    Post: incrementa totalScore con el valor score
    */
    public void incrementTotalScore(int score) {
        totalScore += score;
    }
    
    /*
    Pre: -
    Post: devuelve el atributo totalScore
    */
    public int getTotalScore() {
        return totalScore;
    }
    
    /*
    Pre: -
    Post: actualiza el atributo bestScore
    */
    public void updateBestScore(int newScore) {
        if (newScore > bestScore) bestScore = newScore;
    }
    
    /*
    Pre: -
    Post: devuelve el atributo bestScore
    */
    public int getBestScore() {
        return bestScore;
    }
    
    /*
    Pre: newTime no es null
    Post: actualiza el atributo bestTime (teniendo en cuenta que si este era null
    cualquier tiempo newTime es mejor que este)
    */
    public void updateBestTime(Duration newTime) {
        if (bestTime == null) bestTime = newTime;
        else if (newTime.compareTo(bestTime) < 0) bestTime = newTime;
    }
    
    /*
    Pre: -
    Post: devuelve el atributo bestTime
    */
    public Duration getBestTime() {
        return bestTime;
    }
    
    /*
    Pre: -
    Post: devuelve el porcentaje de hidatos resueltos del usuario calculado como
    solvedGames/startedGames (en caso de startedGames igual a 0 este porcentaje
    es indeterminado y por esto devuelve un porcentaje de 0)
    */
    public double getSolvedPercentage() {
        if (getStartedGames() == 0) return 0;
        else return (float)getSolvedGames()/(float)getStartedGames()*100;
    }
    
    /*
    Pre: -
    Post: devuelve la puntuacion media en la que el usuario ha resuelto sus hidatos
    calculada como totalScore/solvedGame (en caso de solvedGames igual a 0 quiere
    decir que el usuario no ha resuelto aun ningun hidato y por lo tanto este
    parametro es indeterminado, en este caso devuelve 0)
    */
    public int getAverageScore() {
        if (getSolvedGames() == 0) return 0;
        else return totalScore/getSolvedGames();
    }
    
    /*
    Pre: -
    Post: devuelve el tiempo medio de resolucion de hidatos del usuario (se aplica
    el mismo comentario que a la funcion anterior)
    */
    public Duration getAverageTimePerSolve() {
        if (getSolvedGames() == 0) return Duration.ZERO;
        else return getTotalTimePlayed().dividedBy((long)getSolvedGames());
    }
    
    public ArrayList<String> getCreatedHidatos() {
        return createdHidatos;
    }
    
    public String getCreatedHidato(int pos) {
        return createdHidatos.get(pos);
    }
    
    @Override
    public int getActualCreatedBoard() {
        return createdHidatos.size();
    }
    
    public void addHidato (String name) {
        createdHidatos.add(name);
    }
    
    public void deleteHidato (int pos) {
        createdHidatos.remove(pos);
    }
}
