/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDomini.Tauler;

import CapaDomini.Misc.Position;
import java.util.Objects;

/**
 *
 * @author keur
 */
class PositionValue extends Position{
    private final Integer value;

    PositionValue(Integer x, Integer y, Integer value) {
        super(x, y);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public boolean equals(PositionValue pv) {
        return super.equals(pv) && Objects.equals(this.value, pv.value);
    }
    
    public static PositionValue addValue(PositionValue a, Position b) {
        Position p = Position.add(a,b);
        return new PositionValue(p.getX(),p.getY(),a.getValue()+1);
    }
    @Deprecated
    public static Position add(final Position p1, final Position p2){
        throw new UnsupportedOperationException("PositionValued add from Position");
    }
    public static boolean notEnoughDistance(PositionValue pv1, PositionValue pv2) {
        return Position.notEnoughDistance(pv1.getValue(),pv1,pv2.getValue(),pv2);
    }    
    
}
