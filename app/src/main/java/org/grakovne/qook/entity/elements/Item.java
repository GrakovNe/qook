package org.grakovne.qook.entity.elements;


import org.grakovne.qook.enums.Color;

import java.io.Serializable;
import java.util.UUID;

public abstract class Item implements Serializable {
    private UUID id;
    private Color color;

    public Color getColor() {
        return color;
    }


    private void setColor(Color color){
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public Item(Color color) {
        this();
        setColor(color);
    }

    public Item() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "Item{" +
                "color=" + color +
                ", id=" + id +
                '}';
    }
}
