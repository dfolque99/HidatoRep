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
    String type;
    
    public Cell() {
        type = "Blanca";
        val = 0;
    }
    
    public Cell (Cell cell) {
        val = cell.getVal();
        type = cell.getType();
    }

    public int getVal() {
        return val;
    }

    public String getType() {
        return type;
    }
    
    public void setVal (int val) {
        this.val = val;
    }
    
    public void setType (String type) {
        this.type = type;
    }
}
