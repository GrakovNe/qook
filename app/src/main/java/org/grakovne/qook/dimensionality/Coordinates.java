package org.grakovne.qook.dimensionality;

public class Coordinates {
    private int horizontal;
    private int vertical;

    public Coordinates(int horizontal, int vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "horizontal=" + horizontal +
                "; vertical=" + vertical +
                '}';
    }
}
