package org.grakovne.qook.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import org.grakovne.qook.R;
import org.grakovne.qook.dimensionality.Coordinates;
import org.grakovne.qook.dimensionality.Size;
import org.grakovne.qook.entity.Field;
import org.grakovne.qook.entity.elements.Ball;
import org.grakovne.qook.entity.elements.Block;
import org.grakovne.qook.entity.elements.Hole;
import org.grakovne.qook.entity.elements.Item;
import org.grakovne.qook.enums.Color;
import org.grakovne.qook.enums.Direction;

public class FieldView extends View {
    int paddingSize = 0;
    private int elementSize;

    private Field field;
    private Size fieldSize;

    private final double ROUND_RECT_SIZE = 0.15;
    private final int PADDING_DIVIDER = 4;

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (field == null) {
            return;
        }

        if (fieldSize == null || !fieldSize.equals(countFieldSize())) {
            this.fieldSize = countFieldSize();
            setFieldSize(this.fieldSize);
            paddingSize = (int) (Math.sqrt(elementSize) / PADDING_DIVIDER);
        }

        for (int i = 0; i < field.getField().length; i++) {
            for (int j = 0; j < field.getField()[0].length; j++) {
                Drawable d = selectDrawable(field.getField()[i][j]);
                d.setBounds(j * elementSize + paddingSize, i * elementSize + paddingSize, (j + 1) * elementSize - paddingSize, (i + 1) * elementSize - paddingSize);
                d.draw(canvas);
            }
        }
    }

    public FieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private double getSwipeLength(float horizontalDistance, float verticalDistance) {
        return Math.sqrt(Math.pow(horizontalDistance, 2) + Math.pow(verticalDistance, 2));
    }

    public Direction getSwipeDirection(float downHorizontal, float upHorizontal, float downVertical, float upVertical) {
        float xDistance = Math.abs(upHorizontal - downHorizontal);
        float yDistance = Math.abs(upVertical - downVertical);
        double swipeLength = getSwipeLength(xDistance, yDistance);

        if (swipeLength < elementSize / 2) {
            return Direction.NOWHERE;
        }

        if (xDistance >= yDistance) {
            if (upHorizontal > downHorizontal) {
                return Direction.RIGHT;
            }
            return Direction.LEFT;
        }

        if (yDistance > xDistance) {
            if (upVertical > downVertical) {
                return Direction.DOWN;
            }
            return Direction.UP;
        }

        return Direction.DOWN;
    }

    public Coordinates getElementCoordinates(float horizontal, float vertical) {
        float xElCoordinate = horizontal / elementSize;
        float yElCoordinate = vertical / elementSize;

        return new Coordinates((int) xElCoordinate, (int) yElCoordinate);
    }

    private Drawable selectDrawable(Item item) {
        if (item == null) {
            GradientDrawable bgShape = new GradientDrawable();
            bgShape.setColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
            bgShape.setCornerRadius((float) (elementSize * ROUND_RECT_SIZE));
            return bgShape;
        }

        Class clazz = item.getClass();
        Color color = item.getColor();

        if (clazz.equals(Block.class)) {
            GradientDrawable bgShape = new GradientDrawable();
            bgShape.setColor(ContextCompat.getColor(getContext(), R.color.gray));
            bgShape.setCornerRadius((float) (elementSize * ROUND_RECT_SIZE));
            return bgShape;
        }

        if (clazz.equals(Hole.class)) {
            GradientDrawable bgShape = new GradientDrawable();
            bgShape.setCornerRadius((float) (elementSize * ROUND_RECT_SIZE));

            switch (color) {
                case GREEN:
                    bgShape.setColor(ContextCompat.getColor(getContext(), R.color.green));
                    return bgShape;

                case RED:
                    bgShape.setColor(ContextCompat.getColor(getContext(), R.color.red));
                    return bgShape;

                case BLUE:
                    bgShape.setColor(ContextCompat.getColor(getContext(), R.color.blue));
                    return bgShape;

                case YELLOW:
                    bgShape.setColor(ContextCompat.getColor(getContext(), R.color.yellow));
                    return bgShape;

                case PURPLE:
                    bgShape.setColor(ContextCompat.getColor(getContext(), R.color.purple));
                    return bgShape;

                case CYAN:
                    bgShape.setColor(ContextCompat.getColor(getContext(), R.color.cyan));
                    return bgShape;
            }
        }

        if (clazz.equals(Ball.class)) {
            GradientDrawable bgShape = new GradientDrawable();
            bgShape.setColor(ContextCompat.getColor(getContext(), R.color.gray));
            bgShape.setCornerRadius(elementSize);

            switch (color) {
                case GREEN:
                    bgShape.setColor(ContextCompat.getColor(getContext(), R.color.green));
                    return bgShape;

                case RED:
                    bgShape.setColor(ContextCompat.getColor(getContext(), R.color.red));
                    return bgShape;

                case BLUE:
                    bgShape.setColor(ContextCompat.getColor(getContext(), R.color.blue));
                    return bgShape;

                case YELLOW:
                    bgShape.setColor(ContextCompat.getColor(getContext(), R.color.yellow));
                    return bgShape;

                case PURPLE:
                    bgShape.setColor(ContextCompat.getColor(getContext(), R.color.purple));
                    return bgShape;

                case CYAN:
                    bgShape.setColor(ContextCompat.getColor(getContext(), R.color.cyan));
                    return bgShape;

            }
        }

        return new GradientDrawable();
    }

    public Field getField() {
        return field;
    }

    public void setFieldSize(Size size) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size.getWidth(), size.getHeight());
        params.gravity = Gravity.CENTER_HORIZONTAL;
        this.setLayoutParams(params);
    }

    public Size countFieldSize() {
        int horizontalElementsNum = field.getField()[0].length;
        int verticalElementsNum = field.getField().length;

        int maxHorizontalElSize = this.getWidth() / horizontalElementsNum;
        int maxVerticalElSize = this.getHeight() / verticalElementsNum;

        this.elementSize = (maxHorizontalElSize < maxVerticalElSize) ? maxHorizontalElSize : maxVerticalElSize;

        int newWidth = this.elementSize * horizontalElementsNum;
        int newHeight = this.elementSize * verticalElementsNum;

        return new Size(newWidth, newHeight);
    }

}
