package domini;

public final class Utils {
        private Utils(){throw new UnsupportedOperationException();}
        
	public static int toInt(final Cell myCell){
		if (Type.VOID == myCell.getType()) return -1;
		return myCell.getVal();
	}
	public static String toString(final Cell myCell){return Integer.toString(Utils.toInt(myCell));}
	public static void blankToGiven(Cell myCell){
		if (myCell.getType() == Type.BLANK && myCell.getVal() != 0) {myCell.setType(Type.GIVEN);}
	}
	public static Cell copy(final Cell otherCell){
		Cell myCell = new Cell();
		myCell.setType(otherCell.getType());
		myCell.setVal(otherCell.getVal());
		return myCell;
	}
	public static void toZero(final Cell myCell){
		if (myCell.getType() == Type.BLANK) {myCell.setVal(0);}
	}
	public static String toString(final Hidato hidato) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < hidato.getSizeX(); i+=1) {
			for (int j = 0; j < hidato.getSizeY(); j+=1) {
				ret.append(String.format("%2d ", Utils.toInt(hidato.getCell(i,j))));	
			}
			ret.append("\n");
		}
		return ret.toString();
	}
	public static void clean(final Hidato hidato){
		for (int i = 0; i < hidato.getSizeX(); i+=1) {
			for (int j = 0; j < hidato.getSizeY(); j+=1) {
				Utils.toZero(hidato.getCell(i,j));	
			}
		}
	}
	
	
}
