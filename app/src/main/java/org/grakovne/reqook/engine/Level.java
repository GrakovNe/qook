package org.grakovne.reqook.engine;

import org.grakovne.reqook.entity.Ball;
import org.grakovne.reqook.entity.Item;

import java.io.Serializable;
import java.util.Arrays;

public class Level implements Serializable, Cloneable {
    private Item[][] field;
    private int ballsCount;

    public Level(Item[][] field) {
        this.field = field;
        this.ballsCount = countBallsOnLevel(field);
    }

    public Level(Level oldLevel){
        this.field = Arrays.copyOf(oldLevel.getField(), oldLevel.getField().length);
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
    public Level clone() throws CloneNotSupportedException {
        return (Level) super.clone();
    }
}
