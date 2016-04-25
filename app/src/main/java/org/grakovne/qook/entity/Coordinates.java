package org.grakovne.qook.entity;

public class Coordinates {
    private int xCoord;
    private int yCoord;

    public Coordinates(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "xCoord=" + xCoord +
                "; yCoord=" + yCoord +
                '}';
    }
}
