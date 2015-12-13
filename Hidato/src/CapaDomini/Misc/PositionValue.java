package CapaDomini.Misc;

import java.util.Objects;

/**
 *
 * @author felix.axel.gimeno
 */
public class PositionValue extends Position {

    public static PositionValue addValue(PositionValue a, Position b) {
        Position p = Position.add(a, b);
        return new PositionValue(p.getX(), p.getY(), a.getValue() + 1);
    }

    @Deprecated
    public static Position add(final Position p1, final Position p2) {
        throw new UnsupportedOperationException("PositionValue add from Position");
    }

    public static boolean notEnoughDistance(PositionValue pv1, PositionValue pv2) {
        return Position.notEnoughDistance(pv1.getValue(), pv1, pv2.getValue(), pv2);
    }
    private final Integer value;

    public PositionValue(Integer x, Integer y, Integer value) {
        super(x, y);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public boolean equals(PositionValue pv) {
        return super.equals(pv) && Objects.equals(this.getValue(), pv.getValue());
    }

}
