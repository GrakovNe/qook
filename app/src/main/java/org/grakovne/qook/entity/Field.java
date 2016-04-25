package org.grakovne.qook.entity;

import org.grakovne.qook.entity.elements.Hole;
import org.grakovne.qook.entity.elements.Item;

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

    public Boolean isFinished(){
        for (Item[] aField : field) {
            for (int j = 0; j < field[0].length; j++) {
                if (aField[j].getClass().equals(Hole.class) && !((Hole) aField[j]).isFilled()) {
                    return false;
                }
            }
        }
        return true;
    }
}
