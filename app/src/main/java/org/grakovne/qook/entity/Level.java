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

    public Level() {
        //field = new Item[13][13];
        field = new Item[6][6];
        field[0][0] = new Block();
        field[0][1] = new Block();
        field[0][2] = new Hole(Color.RED);
        field[0][3] = new Block();
        field[0][4] = new Block();
        field[0][5] = new Block();

        field[1][0] = new Block();
        field[1][1] = new Ball(Color.RED);
        field[1][5] = new Block();

        field[2][0] = new Block();
        field[2][5] = new Block();

        field[3][0] = new Block();
        field[3][5] = new Hole(Color.BLUE);

        field[4][0] = new Block();
        field[4][1] = new Block();
        field[4][3] = new Ball(Color.BLUE);
        field[4][5] = new Block();

        field[5][1] = new Block();
        field[5][2] = new Block();
        field[5][3] = new Block();
        field[5][4] = new Block();

        ballsCount = 2;
    }

    public int getBallsCount() {
        return ballsCount;
    }
}
