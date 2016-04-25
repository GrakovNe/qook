package org.grakovne.qook.entity.elements;

import org.grakovne.qook.enums.Color;

public class Hole extends Item {
    private boolean isFilled;

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public Hole(Color color) {
        super(color);
    }
}
