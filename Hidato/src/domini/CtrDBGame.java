/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

/**
 *
 * @author Pau
 */
public class CtrDBGame {

    void saveGame(Game game) {}

    Game getGame(String name, User loggedUser) {
        Hidato hidato = new Hidato(10,15);
        Game game = new Game(name, hidato, loggedUser, Help.LOW, Difficulty.EASY);
        return game;
    }

    void deleteGame(String name, User loggedUser) {}
    
}
