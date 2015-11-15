/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domini;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author David
 */

public class Hidato extends Board{
    
    private Difficulty difficulty;
    private int sizeX;
    private int sizeY;
    private ArrayList<ArrayList<Cell> > cells;
    private String boardName;
    private String username;
    private Date creationDate;
    
    public Hidato() {
    }
    
    public Hidato (int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
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
    
    public Hidato (int sizeX, int sizeY, Difficulty difficulty) {
        this(sizeX,sizeY);
        this.difficulty = difficulty;
    }
    
    public Hidato (Hidato h) {
        sizeX = h.getSizeX();
        sizeY = h.getSizeY();
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
