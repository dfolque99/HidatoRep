/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

/**
 *
 * @author David
 */
class Cell {
    int val;
    Type type;
    
    public Cell() {
        type = Type.BLANK;
        val = 0;
    }
    
    public Cell (Cell cell) {
        val = cell.getVal();
        type = cell.getType();
    }

    public Cell(int val, Type type) {
        this.val = val;
        this.type = type;
    }

    public int getVal() {
        return val;
    }

    public Type getType() {
        return type;
    }
    
    public void setVal (int val) {
        this.val = val;
    }
    
    public void setType (Type type) {
        this.type = type;
    }
}
