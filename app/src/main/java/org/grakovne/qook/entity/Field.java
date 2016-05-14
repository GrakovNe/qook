package org.grakovne.qook.entity;

import org.grakovne.qook.dimensionality.Coordinates;
import org.grakovne.qook.entity.elements.Ball;
import org.grakovne.qook.entity.elements.Hole;
import org.grakovne.qook.entity.elements.Item;
import org.grakovne.qook.enums.Direction;

import java.io.Serializable;
import java.util.Arrays;

public class Field implements Serializable {
    private Level level;
    private int ballsCount;

    public Field(Level level) {
        this.level = level;
        this.ballsCount = level.getBallsCount();
    }

    public Item[][] getField() {
        return level.getField();
    }

    public Level getLevel() {
        return level;
    }

    public int getBallsCount() {
        return ballsCount;
    }

    public void setBallsCount(int ballsCount) {
        this.ballsCount = ballsCount;
    }

    private Coordinates moveItem(Coordinates coordinates, Direction direction) {

        int horizontal = coordinates.getHorizontal();
        int vertical = coordinates.getVertical();

        if (direction.equals(Direction.NOWHERE) || level.getField()[vertical][horizontal] == null) {
            return null;
        }

        Class clazz = level.getField()[vertical][horizontal].getClass();
        if (!clazz.equals(Ball.class)) {
            return null;
        }

        switch (direction) {
            case RIGHT:
                return moveRight(horizontal, vertical);

            case LEFT:
                return moveLeft(horizontal, vertical);

            case UP:
                return moveUp(horizontal, vertical);

            case DOWN:
                return moveDown(horizontal, vertical);
        }

        return null;
    }

    private void catchBall() {
        ballsCount--;
    }

    public boolean makeTurn(Coordinates coordinates, Direction direction) {
        Coordinates newCoordinates = moveItem(coordinates, direction);
        return newCoordinates != null && acceptHole(newCoordinates, direction);
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

        if (!isAccepted) {
            return false;
        }

        catchBall();

        return checkWin();

    }

    private boolean acceptUp(Coordinates coordinates) {
        try {
            int horizontal = coordinates.getHorizontal();
            int vertical = coordinates.getVertical();

            Item upItem = level.getField()[vertical - 1][horizontal];
            Item item = level.getField()[vertical][horizontal];

            if (upItem == null || !upItem.getClass().equals(Hole.class) || !(upItem.getColor().equals(item.getColor()))) {
                return false;
            }

            level.getField()[vertical][horizontal] = null;
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return true;
    }

    private boolean acceptDown(Coordinates coordinates) {
        try {
            int horizontal = coordinates.getHorizontal();
            int vertical = coordinates.getVertical();

            Item downItem = level.getField()[vertical + 1][horizontal];
            Item item = level.getField()[vertical][horizontal];

            if (downItem == null || !downItem.getClass().equals(Hole.class) || !(downItem.getColor().equals(item.getColor()))) {
                return false;
            }

            level.getField()[vertical][horizontal] = null;

        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return true;
    }

    private boolean acceptRight(Coordinates coordinates) {
        try {
            int horizontal = coordinates.getHorizontal();
            int vertical = coordinates.getVertical();

            Item upItem = level.getField()[vertical][horizontal + 1];
            Item item = level.getField()[vertical][horizontal];

            if (upItem == null || !upItem.getClass().equals(Hole.class) || !(upItem.getColor().equals(item.getColor()))) {
                return false;
            }

            level.getField()[vertical][horizontal] = null;
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return true;
    }

    private boolean acceptLeft(Coordinates coordinates) {
        try {
            int horizontal = coordinates.getHorizontal();
            int vertical = coordinates.getVertical();

            Item upItem = level.getField()[vertical][horizontal - 1];
            Item item = level.getField()[vertical][horizontal];

            if (upItem == null || !upItem.getClass().equals(Hole.class) || !(upItem.getColor().equals(item.getColor()))) {
                return false;
            }

            level.getField()[vertical][horizontal] = null;
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

    private boolean checkWin() {
        return ballsCount == 0;
    }
}
