
package domini;

import java.util.Objects;

/**
 * Immutable class for storing 2 dimensional values
 * 
 * @author felix.axel.gimeno
 * @since 2015-11-14 position class, stores x,y position
 */
public final class Position {

    /**
     * x position
     */
    private final Integer x;
    /**
     * y position
     */
    private final Integer y;

    /**
     * @return x position
     */
    public Integer getX() {
        return x;
    }

    /**
     * @return y position
     */
    public Integer getY() {
        return y;
    }

    /**
     * To disallow constructor (), use Position(x,y)
     */
    @SuppressWarnings("unused")
    private Position() {
        throw new UnsupportedOperationException();
    }

    /**
     * @param x value for first dimension (x-position)
     * @param y value for second dimension (y-position)
     */
    public Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.x);
        hash = 67 * hash + Objects.hashCode(this.y);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
    	if (obj == this) {return true;}
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (!Objects.equals(this.getX(), other.getX())) {
            return false;
        }
        return Objects.equals(this.getY(), other.getY());
    }

    /**
     * adds a postion to another
     * 
     * @param p1 first position
     * @param p2 second position
     * @return a position of the added value
     */
    public static Position add(final Position p1, final Position p2){
    	return new Position(p1.getX()+p2.getX(),p1.getY() + p2.getY());
    }
    
    /**
     * (x,y) to (-x,-y)
     * 
     * @param p1
     * @return the symmetrical position of input
     */
    public static Position symmetric(final Position p1){
    	return new Position(-p1.getX(),-p1.getY());
    }
    
    /**
     * calculates the norm of a position 
     * 
     * @param p
     * @return
     */
    public static Integer norm(Position p){
    	return Math.max(Math.abs(p.getX()), Math.abs(p.getY()));
    }
    
    /**
     * Calculates distance from p1 to p2 
     * 
     * @param p1 first position not null
     * @param p2 second position not null
     * @return distance
     */
    public static Integer distance(final Position p1, final Position p2){
    	return norm(Position.add(p1, Position.symmetric(p2)));
    	//return Math.max(Math.abs(p1.getX() - p2.getX()), Math.abs(p1.getY() - p2.getY()));
   }
    
    /**
     * Is unreachable cell in p2 with value m from cell in p1 with value n ?
     * 
     * @param n value for first cell
     * @param p1 position for first cell
     * @param m value for second cell
     * @param p2 position for second cell
     * @return true if unreachable 
     */
    public static boolean notEnoughDistance(int n, Position p1, int m, Position p2) {
        return ( Position.distance(p1,p2)> Math.abs(n - m));
    }
}
