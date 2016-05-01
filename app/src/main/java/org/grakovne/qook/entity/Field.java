package org.grakovne.qook.entity;

import android.util.Log;

import org.grakovne.qook.entity.elements.Ball;
import org.grakovne.qook.entity.elements.Hole;
import org.grakovne.qook.entity.elements.Item;
import org.grakovne.qook.enums.Direction;

public class Field {
    private Item[][] field;
    private int ballsCount;

    public Field(int xSize, int ySize) {
        this.field = new Item[xSize][ySize];
    }

    public Field(Level level) {
        this.field = level.getField();
        this.ballsCount = level.getBallsCount();
    }

    public Item[][] getField() {
        return field;
    }

    private Coordinates moveItem(Coordinates itemCoords, Direction direction) {

        int xCoord = itemCoords.getHorizontal();
        int yCoord = itemCoords.getVertical();

        if (direction.equals(Direction.NOWHERE) || field[yCoord][xCoord] == null) {
            return null;
        }

        Class clazz = field[yCoord][xCoord].getClass();
        if (!clazz.equals(Ball.class)) {
            return null;
        }

        switch (direction) {
            case RIGHT:
                return moveRight(xCoord, yCoord);

            case LEFT:
                return moveLeft(xCoord, yCoord);

            case UP:
                return moveUp(xCoord, yCoord);

            case DOWN:
                return moveDown(xCoord, yCoord);
        }

        return null;
    }

    private void catchBall(){
        ballsCount--;
    }

    public boolean makeTurn(Coordinates coordinates, Direction direction) {
        Coordinates newCoords = moveItem(coordinates, direction);
        return newCoords != null && acceptHole(newCoords, direction);
    }

    private boolean acceptHole(Coordinates coordinates, Direction direction) {
        boolean isAccepted = false;
        switch (direction) {
            case UP:
                isAccepted = acceptUp(coordinates);
                break;

            case DOWN:
                isAccepted = acceptDown(coordinates);
                break;

            case RIGHT:
                isAccepted = acceptRight(coordinates);
                break;

            case LEFT:
                isAccepted = acceptLeft(coordinates);
                break;
        }

        if (!isAccepted){
            return false;
        }

        catchBall();

        return checkWin();

    }

    private boolean acceptUp(Coordinates coordinates) {
        try {
            int xCoord = coordinates.getHorizontal();
            int yCoord = coordinates.getVertical();

            Item upItem = field[yCoord - 1][xCoord];
            Item item = field[yCoord][xCoord];

            if (upItem == null || !upItem.getClass().equals(Hole.class) || !(upItem.getColor().equals(item.getColor()))) {
                return false;
            }

            field[yCoord][xCoord] = null;
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return true;
    }

    private boolean acceptDown(Coordinates coordinates) {
        try {
            int xCoord = coordinates.getHorizontal();
            int yCoord = coordinates.getVertical();

            Item downItem = field[yCoord + 1][xCoord];
            Item item = field[yCoord][xCoord];

            if (downItem == null || !downItem.getClass().equals(Hole.class) || !(downItem.getColor().equals(item.getColor()))) {
                return false;
            }

            field[yCoord][xCoord] = null;
            Log.d("catched hole", "down");

        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return true;
    }

    private boolean acceptRight(Coordinates coordinates) {
        try {
            int xCoord = coordinates.getHorizontal();
            int yCoord = coordinates.getVertical();

            Item upItem = field[yCoord][xCoord + 1];
            Item item = field[yCoord][xCoord];

            if (upItem == null || !upItem.getClass().equals(Hole.class) || !(upItem.getColor().equals(item.getColor()))) {
                return false;
            }

            Log.d("catched hole", "right");
            field[yCoord][xCoord] = null;
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return true;
    }

    private boolean acceptLeft(Coordinates coordinates) {
        try {
            int xCoord = coordinates.getHorizontal();
            int yCoord = coordinates.getVertical();

            Item upItem = field[yCoord][xCoord - 1];
            Item item = field[yCoord][xCoord];

            if (upItem == null || !upItem.getClass().equals(Hole.class) || !(upItem.getColor().equals(item.getColor()))) {
                return false;
            }

            Log.d("catched hole", "left");
            field[yCoord][xCoord] = null;
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return true;
    }

    private Coordinates moveRight(int xCoord, int yCoord) {
        try {
            while (field[yCoord][xCoord + 1] == null) {
                field[yCoord][xCoord + 1] = field[yCoord][xCoord];
                field[yCoord][xCoord++] = null;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return new Coordinates(xCoord, yCoord);
    }

    private Coordinates moveLeft(int xCoord, int yCoord) {
        try {
            while (field[yCoord][xCoord - 1] == null) {
                field[yCoord][xCoord - 1] = field[yCoord][xCoord];
                field[yCoord][xCoord--] = null;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return new Coordinates(xCoord, yCoord);
    }

    private Coordinates moveUp(int xCoord, int yCoord) {
        try {
            while (field[yCoord - 1][xCoord] == null) {
                field[yCoord - 1][xCoord] = field[yCoord][xCoord];
                field[yCoord--][xCoord] = null;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return new Coordinates(xCoord, yCoord);
    }

    private Coordinates moveDown(int xCoord, int yCoord) {
        try {
            while (field[yCoord + 1][xCoord] == null) {
                field[yCoord + 1][xCoord] = field[yCoord][xCoord];
                field[yCoord++][xCoord] = null;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return new Coordinates(xCoord, yCoord);
    }

    private boolean checkWin(){
        return ballsCount == 0;
    }
}
