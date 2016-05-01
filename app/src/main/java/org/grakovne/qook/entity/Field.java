package org.grakovne.qook.entity;

import org.grakovne.qook.dimensionality.Coordinates;
import org.grakovne.qook.entity.elements.Ball;
import org.grakovne.qook.entity.elements.Hole;
import org.grakovne.qook.entity.elements.Item;
import org.grakovne.qook.enums.Direction;

public class Field {
    private Level level;
    private int ballsCount;

    public Field(Level level) {
        this.level = level;
        this.ballsCount = level.getBallsCount();
    }

    public Item[][] getField() {
        return level.getField();
    }

    private Coordinates moveItem(Coordinates itemCoords, Direction direction) {

        int xCoord = itemCoords.getHorizontal();
        int yCoord = itemCoords.getVertical();

        if (direction.equals(Direction.NOWHERE) || level.getField()[yCoord][xCoord] == null) {
            return null;
        }

        Class clazz = level.getField()[yCoord][xCoord].getClass();
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

            Item upItem = level.getField()[yCoord - 1][xCoord];
            Item item = level.getField()[yCoord][xCoord];

            if (upItem == null || !upItem.getClass().equals(Hole.class) || !(upItem.getColor().equals(item.getColor()))) {
                return false;
            }

            level.getField()[yCoord][xCoord] = null;
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return true;
    }

    private boolean acceptDown(Coordinates coordinates) {
        try {
            int xCoord = coordinates.getHorizontal();
            int yCoord = coordinates.getVertical();

            Item downItem = level.getField()[yCoord + 1][xCoord];
            Item item = level.getField()[yCoord][xCoord];

            if (downItem == null || !downItem.getClass().equals(Hole.class) || !(downItem.getColor().equals(item.getColor()))) {
                return false;
            }

            level.getField()[yCoord][xCoord] = null;

        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return true;
    }

    private boolean acceptRight(Coordinates coordinates) {
        try {
            int xCoord = coordinates.getHorizontal();
            int yCoord = coordinates.getVertical();

            Item upItem = level.getField()[yCoord][xCoord + 1];
            Item item = level.getField()[yCoord][xCoord];

            if (upItem == null || !upItem.getClass().equals(Hole.class) || !(upItem.getColor().equals(item.getColor()))) {
                return false;
            }

            level.getField()[yCoord][xCoord] = null;
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return true;
    }

    private boolean acceptLeft(Coordinates coordinates) {
        try {
            int xCoord = coordinates.getHorizontal();
            int yCoord = coordinates.getVertical();

            Item upItem = level.getField()[yCoord][xCoord - 1];
            Item item = level.getField()[yCoord][xCoord];

            if (upItem == null || !upItem.getClass().equals(Hole.class) || !(upItem.getColor().equals(item.getColor()))) {
                return false;
            }

            level.getField()[yCoord][xCoord] = null;
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return true;
    }

    private Coordinates moveRight(int xCoord, int yCoord) {
        try {
            while (level.getField()[yCoord][xCoord + 1] == null) {
                level.getField()[yCoord][xCoord + 1] = level.getField()[yCoord][xCoord];
                level.getField()[yCoord][xCoord++] = null;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return new Coordinates(xCoord, yCoord);
    }

    private Coordinates moveLeft(int xCoord, int yCoord) {
        try {
            while (level.getField()[yCoord][xCoord - 1] == null) {
                level.getField()[yCoord][xCoord - 1] = level.getField()[yCoord][xCoord];
                level.getField()[yCoord][xCoord--] = null;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return new Coordinates(xCoord, yCoord);
    }

    private Coordinates moveUp(int xCoord, int yCoord) {
        try {
            while (level.getField()[yCoord - 1][xCoord] == null) {
                level.getField()[yCoord - 1][xCoord] = level.getField()[yCoord][xCoord];
                level.getField()[yCoord--][xCoord] = null;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return new Coordinates(xCoord, yCoord);
    }

    private Coordinates moveDown(int xCoord, int yCoord) {
        try {
            while (level.getField()[yCoord + 1][xCoord] == null) {
                level.getField()[yCoord + 1][xCoord] = level.getField()[yCoord][xCoord];
                level.getField()[yCoord++][xCoord] = null;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return new Coordinates(xCoord, yCoord);
    }

    private boolean checkWin(){
        return ballsCount == 0;
    }
}
