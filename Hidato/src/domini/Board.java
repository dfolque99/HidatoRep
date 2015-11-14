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
public class Board {
    private int id;
    private Difficulty difficult;
    private int sizeX;
    private int sizeY;
    private ArrayList<ArrayList<Cell> > boards;
    private String boardName;
    private String username;
    private Date creationDate;

    public Board() {
        
    }
    
    public Board(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        boards = new ArrayList<ArrayList<Cell> >();
        for (int i = 0; i < sizeX; ++i) {
            ArrayList<Cell> llista = new ArrayList<>();
            for (int j = 0; j < sizeY; ++j) {
                llista.add(new Cell());
            }
            boards.add(llista);
        }
        setCell(0,0,new Cell(1,Type.GIVEN));
        setCell(sizeX-1,sizeY-1,new Cell(sizeX*sizeY,Type.GIVEN));

    }
    
    public Board(Board board) {
        sizeX = board.getSizeX();
        sizeY = board.getSizeY();
        boards = new ArrayList<ArrayList<Cell> >();
        for (int i = 0; i < sizeX; ++i) {
            ArrayList<Cell> llista = new ArrayList<>();
            for (int j = 0; j < sizeY; ++j) {
                llista.add(new Cell(board.getCell(i, j)));
            }
            boards.add(llista);
        }
        boardName = board.getBoardName();
        difficult = board.getDifficult();
        username = board.getUsername();
    }
    
    public Difficulty getDifficult() {
        return difficult;
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
        return boards.get(x).get(y);
    }

    public void setDifficult(Difficulty newDifficult) {
        difficult = newDifficult;
    }

    public void setCell(int x, int y, Cell cell) {
        boards.get(x).set(y, cell);
    }

    public void setBoardName(String newName) {
        boardName = newName;
    }

    
}