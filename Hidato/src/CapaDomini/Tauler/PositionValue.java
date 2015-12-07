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
class PositionValue extends Position {
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
    
    public static PositionValue add(PositionValue a, PositionValue b) {
        Position p = Position.add(a,b);
        return new PositionValue(p.getX(),p.getY(),a.getValue()+b.getValue());
    }
    
}
