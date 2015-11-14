/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

/**
 *
 * @author Guillem
 */
public class HidatoUser extends User {
    
    private int totalScore;
    
    public HidatoUser(String username, String password) {
        super(username,password);
        totalScore = 0;
    }
    
    public void incrementTotalScore(int score) {
        totalScore += score;
    }
    
}