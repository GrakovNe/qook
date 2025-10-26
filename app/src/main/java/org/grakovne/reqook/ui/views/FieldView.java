package org.grakovne.reqook.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import org.grakovne.reqook.R;
import org.grakovne.reqook.dimensionality.Coordinates;
import org.grakovne.reqook.engine.Field;
import org.grakovne.reqook.entity.Ball;
import org.grakovne.reqook.entity.Block;
import org.grakovne.reqook.entity.Hole;
import org.grakovne.reqook.entity.Item;
import org.grakovne.reqook.enums.Color;
import org.grakovne.reqook.enums.Direction;

public class FieldView extends View {
    private final double ROUND_RECT_SIZE = 0.15;
    private final int PADDING_DIVIDER = 4;

    private int paddingSize = 0;
    private int elementSize;
    private Field field;

    private GradientDrawable holeDrawableHolder;
    private GradientDrawable ballDrawableHolder;
    private GradientDrawable blockDrawableHolder;
    private GradientDrawable nullDrawableHolder;

    public FieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_HARDWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (field == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int availableWidth = MeasureSpec.getSize(widthMeasureSpec);
        int availableHeight = MeasureSpec.getSize(heightMeasureSpec);

        int horizontalElements = field.getField()[0].length;
        int verticalElements = field.getField().length;

        elementSize = Math.min(availableWidth / horizontalElements, availableHeight / verticalElements);
        int desiredWidth = elementSize * horizontalElements;
        int desiredHeight = elementSize * verticalElements;

        paddingSize = (int) (Math.sqrt(elementSize) / PADDING_DIVIDER);

        setMeasuredDimension(desiredWidth, desiredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (field == null) return;

        for (int i = 0; i < field.getField().length; i++) {
            for (int j = 0; j < field.getField()[0].length; j++) {
                Drawable d = selectDrawable(field.getField()[i][j]);
                d.setBounds(
                        j * elementSize + paddingSize,
                        i * elementSize + paddingSize,
                        (j + 1) * elementSize - paddingSize,
                        (i + 1) * elementSize - paddingSize
                );
                d.draw(canvas);
            }
        }
    }

    private Drawable selectDrawable(Item item) {
        if (item == null) {
            if (nullDrawableHolder == null) {
                GradientDrawable bg = new GradientDrawable();
                bg.setColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
                bg.setCornerRadius((float) (elementSize * ROUND_RECT_SIZE));
                nullDrawableHolder = bg;
            }
            return nullDrawableHolder;
        }

        Class<?> clazz = item.getClass();
        Color color = item.getColor();

        if (clazz.equals(Block.class)) {
            if (blockDrawableHolder == null) {
                GradientDrawable bg = new GradientDrawable();
                bg.setColor(ContextCompat.getColor(getContext(), R.color.gray));
                bg.setCornerRadius((float) (elementSize * ROUND_RECT_SIZE));
                blockDrawableHolder = bg;
            }
            return blockDrawableHolder;
        }

        if (clazz.equals(Hole.class)) {
            if (holeDrawableHolder == null) {
                GradientDrawable bg = new GradientDrawable();
                bg.setCornerRadius((float) (elementSize * ROUND_RECT_SIZE));
                holeDrawableHolder = bg;
            }
            switch (color) {
                case GREEN: holeDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.green)); break;
                case RED: holeDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.red)); break;
                case BLUE: holeDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.blue)); break;
                case YELLOW: holeDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.yellow)); break;
                case PURPLE: holeDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.purple)); break;
                case CYAN: holeDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.cyan)); break;
            }
            return holeDrawableHolder;
        }

        if (clazz.equals(Ball.class)) {
            if (ballDrawableHolder == null) {
                GradientDrawable bg = new GradientDrawable();
                bg.setColor(ContextCompat.getColor(getContext(), R.color.gray));
                bg.setCornerRadius(elementSize);
                ballDrawableHolder = bg;
            }
            switch (color) {
                case GREEN: ballDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.green)); break;
                case RED: ballDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.red)); break;
                case BLUE: ballDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.blue)); break;
                case YELLOW: ballDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.yellow)); break;
                case PURPLE: ballDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.purple)); break;
                case CYAN: ballDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.cyan)); break;
            }
            return ballDrawableHolder;
        }

        return new GradientDrawable();
    }

    public Direction getSwipeDirection(float downX, float upX, float downY, float upY) {
        float dx = Math.abs(upX - downX);
        float dy = Math.abs(upY - downY);
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length < (double) elementSize / 2) return Direction.NOWHERE;
        if (dx >= dy) return (upX > downX) ? Direction.RIGHT : Direction.LEFT;
        else return (upY > downY) ? Direction.DOWN : Direction.UP;
    }

    public Coordinates getElementCoordinates(float x, float y) {
        return new Coordinates((int) (x / elementSize), (int) (y / elementSize));
    }

    public Field getField() { return field; }

    public void setField(Field field) {
        this.field = field;
        this.post(() -> {
            requestLayout();
            invalidate();
        });
    }
}
