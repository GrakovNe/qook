package org.grakovne.qook.engine;

import org.grakovne.qook.entity.elements.Ball;
import org.grakovne.qook.entity.elements.Item;

import java.io.Serializable;
import java.util.Arrays;

public class Level implements Serializable, Cloneable {
    private Item[][] field;
    private int ballsCount;

    public Level(Item[][] field) {
        this.field = field;
        this.ballsCount = countBallsOnLevel(field);
    }

    public Item[][] getField() {
        return field;
    }

    private int countBallsOnLevel(Item[][] field) {
        int ballsCount = 0;

        for (Item[] aField : field) {
            for (int j = 0; j < field[0].length; j++) {
                if (aField[j] != null && aField[j].getClass().equals(Ball.class)) {
                    ballsCount++;
                }
            }
        }

        return ballsCount;
    }

    public int getBallsCount() {
        return ballsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Level level = (Level) o;
        return ballsCount == level.ballsCount && Arrays.deepEquals(field, level.field);

    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(field);
        result = 31 * result + ballsCount;
        return result;
    }

    @Override
    public Level clone() throws CloneNotSupportedException {
        return (Level) super.clone();
    }
}
