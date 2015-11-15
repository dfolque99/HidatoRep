/**
 * @author felix.axel.gimeno
 * @since 2015-11-14 position class, stores x,y position
 */
package domini;

import java.util.Objects;

public class Position {

    private Integer x;
    private Integer y;

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @SuppressWarnings("unused")
    private Position() {
        throw new UnsupportedOperationException();
    }

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
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (!Objects.equals(this.x, other.x)) {
            return false;
        }
        if (!Objects.equals(this.y, other.y)) {
            return false;
        }
        return true;
    }

    public static boolean notEnoughDistance(int n, Position p1, int m, Position p2) {
        return (Math.min(Math.abs(p1.x - p2.x), Math.abs(p1.y - p2.y)) > Math.abs(n - m));
    }
}
