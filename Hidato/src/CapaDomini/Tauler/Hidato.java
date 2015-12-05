/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CapaDomini.Tauler;

import CapaDomini.Partida.Difficulty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author David
 */

public class Hidato extends Board implements Serializable{
    
    /**
     * dificultat del hidato
     */
    private Difficulty difficulty;
    /**
     * numero de files
     */
    private int sizeX;
    /**
     * numero de columnes
     */
    private int sizeY;
    /**
     * matriu de Cells de que es compon el taulell
     */
    private ArrayList<ArrayList<Cell> > cells;
    /**
     * nom del Hidato
     */
    private String boardName;
    /**
     * nom de l'usuari que el va crear
     */
    private String username;
    /**
     * data de creacio del hidato
     */
    private Date creationDate;
    
    /** 
     * Constructora sense parametres: inicialitza amb sizeX = sizeY = 5
     */
    public Hidato() {
        this(5,5);
    }
    
    /**
     * Constructora amb les mides.
     * Pre: sizeX,sizeY >= 0
     * Post: crea un Hidato amb mida sizeX x sizeY amb data de creacio la
     *      actual, Cells per defecte, amb l'inici a la posicio (0,0) i el
     *      final a la (sizeX-1, sizeY-1).
     */
    public Hidato (int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        creationDate = new Date();
        cells = new ArrayList<>();
        for (int i = 0; i < sizeX; ++i) {
            ArrayList<Cell> llista = new ArrayList<>();
            for (int j = 0; j < sizeY; ++j) {
                llista.add(new Cell());
            }
            cells.add(llista);
        }
        setCell(0,0,new Cell(1,Type.GIVEN));
        setCell(sizeX-1,sizeY-1,new Cell(sizeX*sizeY,Type.GIVEN));
    }
    
    /**
     * Constructora amb les mides i la dificultat.
     * Pre: sizeX,sizeY >= 0, difficulty != null
     * Post: crea un Hidato amb mida sizeX x sizeY amb data de creacio la
     *      actual, Cells per defecte, amb l'inici a la posicio (0,0) i el
     *      final a la (sizeX-1, sizeY-1), i la dificultat = difficulty.
     */
    public Hidato (int sizeX, int sizeY, Difficulty difficulty) {
        this(sizeX,sizeY);
        this.difficulty = difficulty;
    }
    
    /**
     * Constructora per deep copy.
     * Pre: h != null
     * Post: crea un Hidato igual que h
     */
    public Hidato (Hidato h) {
        sizeX = h.getSizeX();
        sizeY = h.getSizeY();
        creationDate = h.getCreationDate();
        cells = new ArrayList<>();
        for (int i = 0; i < sizeX; ++i) {
            ArrayList<Cell> llista = new ArrayList<>();
            for (int j = 0; j < sizeY; ++j) {
                llista.add(new Cell(h.getCell(i, j)));
            }
            cells.add(llista);
        }
        boardName = h.getBoardName();
        difficulty = h.getDifficulty();
        username = h.getUsername();
    }
    
    /**
     * Getters i setters
    */
    
    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getUsername() {
        return username;
    }

    public Cell getCell(int x, int y) {
        return cells.get(x).get(y);
    }

    public Date getCreationDate() {
        return creationDate;
    }
    
    public void setDifficulty(Difficulty newDifficult) {
        difficulty = newDifficult;
    }

    public void setCell(int x, int y, Cell cell) {
        cells.get(x).set(y, cell);
    }

    public void setBoardName(String newName) {
        boardName = newName;
    }
}
