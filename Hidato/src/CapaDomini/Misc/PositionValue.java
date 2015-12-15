package CapaDomini.Misc;

import java.util.Objects;

/**
 *
 * @author felix.axel.gimeno
 * @see Position
 */
public class PositionValue extends Position {

    /**
     * add two positions and their values and return it
     *
     * @param a
     * @param b
     * @return
     */
    public static PositionValue addValue(PositionValue a, Position b) {
        Position p = Position.add(a, b);
        return new PositionValue(p.getX(), p.getY(), a.getValue() + 1);
    }

    @Deprecated
    public static Position add(final Position p1, final Position p2) {
        throw new UnsupportedOperationException("PositionValue add from Position");
    }

    /**
     * like Position.notEnoughDistance but for PositionValues
     *
     * @param pv1
     * @param pv2
     * @return
     */
    public static boolean notEnoughDistance(PositionValue pv1, PositionValue pv2) {
        return Position.notEnoughDistance(pv1.getValue(), pv1, pv2.getValue(), pv2);
    }
    /**
     * value
     */
    private final Integer value;

    /**
     * create a (x,y) position with value value
     *
     * @param x
     * @param y
     * @param value
     */
    public PositionValue(Integer x, Integer y, Integer value) {
        super(x, y);
        this.value = value;
    }

    /**
     *
     * @return value
     */
    public Integer getValue() {
        return value;
    }

    public boolean equals(PositionValue pv) {
        return super.equals(pv) && Objects.equals(this.getValue(), pv.getValue());
    }

}
