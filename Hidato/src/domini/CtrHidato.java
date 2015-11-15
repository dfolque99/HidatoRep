package domini;


/**
 * Controlador de hidatos. Conte metodes per modificar hidatos i fer comprovacions
 * Cada instancia de la classe te assignada un hidato, sobre el qual fara els metodes
 * @author Pau Surrell
 */
public class CtrHidato {
    
    /**
     * Hidato sobre el qual s'apliquen els metodes de la classe
     */
    private final Hidato hidato;
    
    
    /**
     * Creadora
     * @param hidato hidato de la classe 
     */
    public CtrHidato(Hidato hidato){
        this.hidato = hidato;
    }
    
    /**
     * Getter del hidato
     * @return el hidato de la classe
     */
    public Hidato getHidato(){
        return this.hidato;
    }
    
    /**
     * Retorna la posicio (la coordenada x o y) de la cell amb el valor demanat
     * @param value valor de la cell buscada
     * @param is_x determina si retorna la coordenada x (is_x != 0) o y (is_x = 0)
     * @return la coordenada x de la cell, si is_x != 0
     * la coordenada y de la cell, si is_x = 0
     * -1 si no hi ha cap cell amb el valor demanat
     */
    public int getCellPositionFromValue(int value, int is_x){
        int sizeX = hidato.getSizeX();
        int sizeY = hidato.getSizeY();
        for (int i = 0; i < sizeX; i++){
            for (int j = 0; j < sizeY; j++){
                Cell cell = hidato.getCell(i,j);
                if (cell.getVal() == value){
                    if (is_x != 0) return i;
                    else return j;
                }
            }
        }
        return -1;
    }
    
    /**
     * Retorna si dues cells amb valors donats son contigues o no
     * @param value1 valor de la primera cell
     * @param value2 valor de la segona cell
     * @return true si les dues cells estan posades i son contigues
     * false si les dues cells estan posades i no son contigues
     * true si alguna cell no esta posada
     */
    public Boolean AreCellsContiguous(int value1, int value2){
        int x1 = getCellPositionFromValue (value1, 1);
        int y1 = getCellPositionFromValue (value1, 0);
        int x2 = getCellPositionFromValue (value2, 1);
        int y2 = getCellPositionFromValue (value2, 0);
        
        if (x1 == -1 || x2 == -1) return true;
        if (Math.abs(x1-x2) <= 1 && Math.abs(y1-y2) <= 1) return true;
        return false;
    }
    
    
    /**
     * Compta el nombre de cells valides del hidato
     * @return el nombre de cells valides (de tipus GIVEN o BLANK)
     */
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
    
    /**
     * Assigna el valor 0 a les cells de tipus BLANK
     */
    public void setBlankCellsToZero(){
        for (int i = 0; i < hidato.getSizeX(); i++){
            for (int j = 0; j < hidato.getSizeY(); j++){
                Cell cell = hidato.getCell(i, j);
                if (cell.getType() == Type.BLANK) cell.setVal(0);
            }
        }
    }
    
    /**
     * Comprova si el hidato esta resolt
     * @return true si esta resolt
     * false si no esta resolt
     */
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
