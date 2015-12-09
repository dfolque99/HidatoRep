/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Partida;

import java.util.ArrayList;

/**
 *
 * @author Pau
 */
public class GameSet {
    private ArrayList<Game> g;
    
    public GameSet(){
        g = new ArrayList<>();
    }
    
    public Game getGameByName (String name) {
        for (int i = 0; i < g.size(); ++i){
            if (g.get(i).getName().equals(name)) return g.get(i);
        }
        return null;
    }
    
    public Game getGameByPos (int pos){
        return g.get(pos);
    }
    
    public void addGame (Game game) {
        if (getGameByName(game.getName()) == null) g.add(game);
    }
    
    public int getSize(){
        return g.size();
    }
}
