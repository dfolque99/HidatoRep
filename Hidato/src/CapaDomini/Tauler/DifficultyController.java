package CapaDomini.Tauler;

import CapaDomini.Misc.Position;
import CapaDomini.Misc.PositionValue;
import CapaDomini.Partida.Difficulty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * To calculate the difficulty of a hidato board
 * <p>
 * Difficulty is estimated based on the interferences between succesive pairs of
 * given cells the estimation is a double, that is converted to Difficulty,
 * through two values medium´_low and medium_high
 *
 * @since 07-12-2015
 * @version 0.8
 * @author felix.axel.gimeno
 * @see Hidato
 */
public final class DifficultyController {

    /**
     * lower bound of Difficulty.MEDIUM
     */
    static final double medium_low = 2;
    /**
     * higher bound of Difficulty.MEDIUM
     */
    static final double medium_high = 3;

    /**
     * converts double to Difficulty usign medium_low and medium_high
     *
     * @param d double
     * @return difficulty corresponding to d
     */
    private static Difficulty doubleToDifficulty(final double d) {
        if (d < medium_low) {
            return Difficulty.EASY;
        } else if (d > medium_high) {
            return Difficulty.HARD;
        } else {
            return Difficulty.MEDIUM;
        }
    }

    /**
     *
     * @param cells array of nonnegative values
     * @param hidato with same sizes as cells
     * @return average of all the values in cells
     */
    private static double arrayToDouble(final Integer[][] cells, final Hidato hidato) {
        double sum = 0;
        double blankCount = 0;
        for (int i = 0; i < cells.length; i += 1) {
            for (int j = 0; j < cells[i].length; j += 1) {
                sum += cells[i][j];
                if (hidato.getCell(i, j).getType() == Type.BLANK) {
                    blankCount += 1;
                }
            }
        }
        return sum / blankCount;
    }

    /**
     *
     * @param hidato
     * @return sorted arraylit of the PositionValues of the given cells in
     * Hidato hidato
     */
    private static ArrayList<PositionValue> getGivenCells(Hidato hidato) {
        ArrayList<PositionValue> PV = new ArrayList<>(10);
        for (int i = 0; i < hidato.getSizeX(); i += 1) {
            for (int j = 0; j < hidato.getSizeY(); j += 1) {
                if (hidato.getCell(i, j).getType().equals(Type.GIVEN)) {
                    PV.add(new PositionValue(i, j, hidato.getCell(i, j).getVal()));
                }
            }
        }
        PV.sort((CapaDomini.Misc.PositionValue a, CapaDomini.Misc.PositionValue b) -> a.getValue() - b.getValue());
        return PV;
    }

    /**
     * count for number of interferences per position
     */
    private Integer[][] count;
    /**
     * internal hidato, not modified
     */
    private Hidato hidato;

    /**
     *
     * @param hidato Hidato
     * @return difficulty as double
     */
    public double getDifficultyAsDouble(Hidato hidato) {
        this.count = new Integer[hidato.getSizeX()][hidato.getSizeY()];
        this.hidato = hidato;
        for (int i = 0; i < hidato.getSizeX(); i += 1) {
            Arrays.fill(count[i], 0);
        }
        ArrayList<PositionValue> myGivenCells = DifficultyController.getGivenCells(hidato);
        for (int i = 0; i + 1 < myGivenCells.size(); i += 1) {
            /**
             * if the given cells are consecutive don't count them,
             */
            if (-myGivenCells.get(i).getValue() + myGivenCells.get(i + 1).getValue() > 1) {
                this.countPossibilityOfBeingInAPAth(myGivenCells.get(i), myGivenCells.get(i + 1));
            }
        }
        return arrayToDouble(count, hidato);
    }

    /**
     *
     * @param hidato whose difficulty will be estimated
     * @return estimated difficulty of Hidato hidato
     */
    public Difficulty getDifficulty(Hidato hidato) {
        return doubleToDifficulty(getDifficultyAsDouble(hidato));
    }

    /**
     *
     * @param pv
     * @param n
     * @return bfs with starting node pv and depth n
     */
    private Integer[][] bfs(PositionValue pv, Integer n) {
        int infinity = hidato.getSizeX() * hidato.getSizeY() + 10;
        Queue<PositionValue> qp = new LinkedList<>();
        Integer[][] myArray = new Integer[hidato.getSizeX()][hidato.getSizeY()];
        for (int i = 0; i < hidato.getSizeX(); i += 1) {
            Arrays.fill(myArray[i], infinity);
        }
        qp.add(new PositionValue(pv.getX(), pv.getY(), 0));
        while (!qp.isEmpty()) {
            PositionValue pvHere = qp.remove();
            if (infinity == myArray[pvHere.getX()][pvHere.getY()]) {
                myArray[pvHere.getX()][pvHere.getY()] = pvHere.getValue();
                final Position[] Moore = {
                    new Position(-1, -1), new Position(-1, 0), new Position(0, -1),
                    new Position(-1, +1), new Position(+1, -1),
                    new Position(+1, +1), new Position(+1, 0), new Position(0, +1),};
                Arrays.stream(Moore)
                        .map(p -> PositionValue.addValue(pvHere, p))
                        .filter(p -> validPosition(p))
                        .forEach(p -> qp.add(p));

            } else {

            }

        }
        return myArray;
    }

    /**
     *
     * @param now position to test
     * @return availability of that Position to hold that value
     */
    private boolean validPosition(PositionValue now) {
        final Integer x = now.getX();
        final Integer y = now.getY();
        /**
         * we test if x,y are out of range
         */
        if ((Math.min(x, y) < 0) || (Math.max(x - hidato.getSizeX(), y - hidato.getSizeY()) >= 0)) {
            return false;
        }
        return hidato.getCell(x, y).getType() == Type.BLANK;

    }

    /**
     * adds to count array the number of interferences of the pair start,next
     *
     * @param start smaller
     * @param next bigger, direct consecutive of start
     */
    private void countPossibilityOfBeingInAPAth(PositionValue start, PositionValue next) {
        Integer v = next.getValue() - start.getValue();
        Integer[][] bfsStart = bfs(start, v / 2 + 1);
        Integer[][] bfsNext = bfs(next, v / 2 + 1);
        for (int i = 0; i < hidato.getSizeX(); i += 1) {
            for (int j = 0; j < hidato.getSizeY(); j += 1) {
                if (hidato.getCell(i, j).getType() == Type.BLANK && bfsStart[i][j] + bfsNext[i][j] <= v) {
                    count[i][j] += 1;
                }
            }
        }
    }

}
