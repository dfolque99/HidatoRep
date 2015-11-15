/**
 * @author felix.axel.gimeno
 * @since 2015-11-14 position class, stores x,y position
 */
package domini;

import java.util.Objects;

public final class Position {

    private final Integer x;
    private final Integer y;

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
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
        if (!Objects.equals(this.getX(), other.getX())) {
            return false;
        }
        return Objects.equals(this.getY(), other.getY());
    }

    public static boolean notEnoughDistance(int n, Position p1, int m, Position p2) {
        return (Math.min(Math.abs(p1.getX() - p2.getY()), Math.abs(p1.getY() - p2.getY())) > Math.abs(n - m));
    }
}
