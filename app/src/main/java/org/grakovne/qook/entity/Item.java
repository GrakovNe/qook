package org.grakovne.qook.entity;


import org.grakovne.qook.enums.Color;

import java.util.UUID;

public abstract class Item {
    private UUID id;
    private int xCoord;
    private int yCoord;
    private Color color;

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public Color getColor() {
        return color;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public void setCoords(int x, int y){
        setxCoord(x);
        setyCoord(y);
    }

    private void setColor(Color color){
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public Item(int xCoord, int yCoord, Color color) {
        this();
        setCoords(xCoord, yCoord);
        setColor(color);
    }

    public Item() {
        this.id = UUID.randomUUID();
    }

}
