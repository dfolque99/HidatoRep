/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

/**
 * Stub del controlador DB de la partida: hauria de proporcionar la informacio de les partides
 * a la capa de domini
 * @author Pau
 */
public class CtrDBGame {

    void saveGame(Game game) {}

    Game getGame(String name, HidatoUser loggedUser) {
        return null;
        /*Hidato hidato = new Hidato(10,15);
        Game game = new Game(name, hidato, loggedUser, Help.LOW, Difficulty.EASY);
        return game;*/
    }

    void deleteGame(String name, HidatoUser loggedUser) {}
    
}
