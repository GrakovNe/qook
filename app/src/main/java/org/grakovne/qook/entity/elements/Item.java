package org.grakovne.qook.entity.elements;


import org.grakovne.qook.enums.Color;

import java.io.Serializable;

public abstract class Item implements Serializable {
    private Color color;

    public Item(Color color) {
        setColor(color);
    }

    public Color getColor() {
        return color;
    }

    private void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Item{" +
                "color=" + color +
                '}';
    }
}
