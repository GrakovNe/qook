package org.grakovne.qook.entity;

import android.util.Log;

import org.grakovne.qook.entity.elements.Ball;
import org.grakovne.qook.entity.elements.Item;
import org.grakovne.qook.enums.Direction;

public class Field {
    private Item[][] field;
    private int xSize;
    private int ySize;

    public Field(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.field = new Item[xSize][ySize];
    }

    public Field(Level level) {
        this.field = level.getField();
        this.xSize = level.getField().length;
        this.ySize = level.getField()[0].length;
    }

    public Item[][] getField() {
        return field;
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    public void moveItem(Coordinates itemCoords, Direction direction) {

        int xCoord = itemCoords.getxCoord();
        int yCoord = itemCoords.getyCoord();

        if (direction.equals(Direction.NOWHERE) || field[yCoord][xCoord] == null) {
            return;
        }

        Class clazz = field[yCoord][xCoord].getClass();
        if (!clazz.equals(Ball.class)) {
            return;
        }

        switch (direction) {
            case RIGHT:
                moveRight(xCoord, yCoord);
                break;

            case LEFT:
                moveLeft(xCoord, yCoord);
                break;

            case UP:
                moveUp(xCoord, yCoord);
                break;

            case DOWN:
                moveDown(xCoord, yCoord);
                break;
        }

    }

    private void moveRight(int xCoord, int yCoord) {
        try {
            while (field[yCoord][xCoord + 1] == null) {
                field[yCoord][xCoord + 1] = field[yCoord][xCoord];
                field[yCoord][xCoord++] = null;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }
    }

    private void moveLeft(int xCoord, int yCoord) {
        try {
            while (field[yCoord][xCoord - 1] == null) {
                field[yCoord][xCoord - 1] = field[yCoord][xCoord];
                field[yCoord][xCoord--] = null;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            Log.d("Moved", "Can't move edge element!");
        }
    }

    private void moveUp(int xCoord, int yCoord) {
        try {
            while (field[yCoord - 1][xCoord] == null) {
                field[yCoord - 1][xCoord] = field[yCoord][xCoord];
                field[yCoord--][xCoord] = null;
                Log.d("Next", "Next is null");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            Log.d("Moved", "Can't move edge element!");
        }
    }

    private void moveDown(int xCoord, int yCoord) {
        try {
            while (field[yCoord + 1][xCoord] == null) {
                field[yCoord + 1][xCoord] = field[yCoord][xCoord];
                field[yCoord++][xCoord] = null;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            Log.d("Moved", "Can't move edge element!");
        }
    }

}
