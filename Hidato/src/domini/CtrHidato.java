package domini;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pau Surrell
 */
public class CtrHidato {
    private final Hidato hidato;
    
    public CtrHidato(Hidato hidato){
        this.hidato = hidato;
    }
    
    public Hidato getHidato(){
        return hidato;
    }
    
    public int getCellPositionFromValue(int value, int is_x){
        int sizeX = hidato.getSizeX();
        int sizeY = hidato.getSizeY();
        for (int i = 0; i < sizeX; i++){
            for (int j = 0; j < sizeY; j++){
                Cell cell = hidato.getCell(i,j);
                if (cell.getVal() == value){
                    if (is_x == 1) return i;
                    else return j;
                }
            }
        }
        return -1;
    }
    
    public Boolean AreCellsContiguous(int value1, int value2){
        int x1 = getCellPositionFromValue (value1, 1);
        int y1 = getCellPositionFromValue (value1, 0);
        int x2 = getCellPositionFromValue (value2, 1);
        int y2 = getCellPositionFromValue (value2, 0);
        
        if (x1 == -1 || x2 == -1) return true;
        if (Math.abs(x1-x2) <= 1 && Math.abs(y1-y2) <= 1) return true;
        return false;
    }
    
    public int countValidCells(){
        int validCells = 0;
        for (int i = 0; i < hidato.getSizeX(); i++){
            for (int j = 0; j < hidato.getSizeY(); j++){
                Cell cell = hidato.getCell(i, j);
                if (cell.getType() != Type.VOID) validCells++;
            }
        }
        return validCells;
    }
    
    public void setBlankCellsToZero(){
        for (int i = 0; i < hidato.getSizeX(); i++){
            for (int j = 0; j < hidato.getSizeY(); j++){
                Cell cell = hidato.getCell(i, j);
                if (cell.getType() == Type.BLANK) cell.setVal(0);
            }
        }
    }
    
    public Boolean isSolved(){
        int validCells = countValidCells();
        for (int i = 1; i <= validCells; i++){
            if (getCellPositionFromValue(i,0) == -1) {
                return false;
            } //si la cell i esta posada
        }
        for (int i = 1; i <= validCells-1; i++){
            if (!AreCellsContiguous(i, i+1)) return false; //si i, i+1 son contigues
        }
        return true;
    }
}
