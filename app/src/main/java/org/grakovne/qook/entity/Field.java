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

    private Coordinates moveItem(Coordinates itemCoords, Direction direction) {

        int xCoord = itemCoords.getxCoord();
        int yCoord = itemCoords.getyCoord();

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

    public void makeTurn(Coordinates coordinates, Direction direction){
        Coordinates newCoords = moveItem(coordinates, direction);

        if (newCoords == null){
            return;
        }

        acceptHole(newCoords, direction);
    }

    private void acceptHole(Coordinates coordinates, Direction direction){

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

}
