package org.grakovne.qook.entity;

import android.util.Log;

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

    public Field(Level level){
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

    public void moveItem(Coordinates itemCoords, Direction direction){
        Log.d("Moved!", itemCoords.toString() + " " + direction.toString());
    }

}
