package org.grakovne.qook.entity;

import org.grakovne.qook.entity.elements.Ball;
import org.grakovne.qook.entity.elements.Block;
import org.grakovne.qook.entity.elements.Hole;
import org.grakovne.qook.entity.elements.Item;
import org.grakovne.qook.enums.Color;

public class Level {
    private Item[][] field;
    private int ballsCount;

    public Item[][] getField() {
        return field;
    }

    public Level(Item[][] field) {
        this.field = field;
        this.ballsCount = countBallsOnLevel(field);
    }

    private int countBallsOnLevel(Item[][] field){
        int ballsCount = 0;

        for (int i = 0; i < field.length; i++){
            for (int j = 0; j < field[0].length; j++){
                if (field[i][j] != null && field[i][j].getClass().equals(Ball.class)){
                    ballsCount++;
                }
            }
        }

        return ballsCount;
    }

    public int getBallsCount() {
        return ballsCount;
    }
}
