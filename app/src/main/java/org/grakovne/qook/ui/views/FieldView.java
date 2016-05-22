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
import org.grakovne.qook.engine.Field;
import org.grakovne.qook.entity.Ball;
import org.grakovne.qook.entity.Block;
import org.grakovne.qook.entity.Hole;
import org.grakovne.qook.entity.Item;
import org.grakovne.qook.enums.Color;
import org.grakovne.qook.enums.Direction;

public class FieldView extends View {
    private final double ROUND_RECT_SIZE = 0.15;
    private final int PADDING_DIVIDER = 4;
    int paddingSize = 0;
    private int elementSize;
    private Field field;
    private Size fieldSize;
    private Size maxViewSize;

    private GradientDrawable holeDrawableHolder;
    private GradientDrawable ballDrawableHolder;
    private GradientDrawable blockDrawableHolder;
    private GradientDrawable nullDrawableHolder;

    public FieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        Size countedFieldSize = countFieldSize();
        if (fieldSize == null || !fieldSize.equals(countedFieldSize)) {
            this.fieldSize = countedFieldSize;
            setFieldSize(this.fieldSize);
            paddingSize = (int) (Math.sqrt(elementSize) / PADDING_DIVIDER);
        }

        deleteDrawableHolders();

    }

    private void deleteDrawableHolders() {
        nullDrawableHolder = null;
        blockDrawableHolder = null;
        holeDrawableHolder = null;
        ballDrawableHolder = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (field == null) {
            return;
        }

        for (int i = 0; i < field.getField().length; i++) {
            for (int j = 0; j < field.getField()[0].length; j++) {
                Drawable d = selectDrawable(field.getField()[i][j]);
                d.setBounds(j * elementSize + paddingSize, i * elementSize + paddingSize, (j + 1) * elementSize - paddingSize, (i + 1) * elementSize - paddingSize);
                d.draw(canvas);
            }
        }
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
            if (nullDrawableHolder == null) {
                GradientDrawable bgShape = new GradientDrawable();
                bgShape.setColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
                bgShape.setCornerRadius((float) (elementSize * ROUND_RECT_SIZE));
                nullDrawableHolder = bgShape;
            }
            return nullDrawableHolder;
        }

        Class clazz = item.getClass();
        Color color = item.getColor();

        if (clazz.equals(Block.class)) {
            if (blockDrawableHolder == null) {
                GradientDrawable bgShape = new GradientDrawable();
                bgShape.setColor(ContextCompat.getColor(getContext(), R.color.gray));
                bgShape.setCornerRadius((float) (elementSize * ROUND_RECT_SIZE));
                blockDrawableHolder = bgShape;
            }
            return blockDrawableHolder;
        }

        if (clazz.equals(Hole.class)) {
            if (holeDrawableHolder == null) {
                GradientDrawable bgShape = new GradientDrawable();
                bgShape.setCornerRadius((float) (elementSize * ROUND_RECT_SIZE));
                holeDrawableHolder = bgShape;
            }

            switch (color) {
                case GREEN:
                    holeDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.green));
                    return holeDrawableHolder;

                case RED:
                    holeDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.red));
                    return holeDrawableHolder;

                case BLUE:
                    holeDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.blue));
                    return holeDrawableHolder;

                case YELLOW:
                    holeDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.yellow));
                    return holeDrawableHolder;

                case PURPLE:
                    holeDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.purple));
                    return holeDrawableHolder;

                case CYAN:
                    holeDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.cyan));
                    return holeDrawableHolder;
            }
        }

        if (clazz.equals(Ball.class)) {
            if (ballDrawableHolder == null) {
                GradientDrawable bgShape = new GradientDrawable();
                bgShape.setColor(ContextCompat.getColor(getContext(), R.color.gray));
                bgShape.setCornerRadius(elementSize);
                ballDrawableHolder = bgShape;
            }

            switch (color) {
                case GREEN:
                    ballDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.green));
                    return ballDrawableHolder;

                case RED:
                    ballDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.red));
                    return ballDrawableHolder;

                case BLUE:
                    ballDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.blue));
                    return ballDrawableHolder;

                case YELLOW:
                    ballDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.yellow));
                    return ballDrawableHolder;

                case PURPLE:
                    ballDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.purple));
                    return ballDrawableHolder;

                case CYAN:
                    ballDrawableHolder.setColor(ContextCompat.getColor(getContext(), R.color.cyan));
                    return ballDrawableHolder;

            }
        }

        return new GradientDrawable();
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setFieldSize(Size size) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size.getWidth(), size.getHeight());
        params.gravity = Gravity.CENTER_HORIZONTAL;
        this.setLayoutParams(params);
    }

    public Size countFieldSize() {
        if (maxViewSize == null) {
            maxViewSize = new Size(this.getWidth(), this.getHeight());
        }

        int horizontalElementsNum = field.getField()[0].length;
        int verticalElementsNum = field.getField().length;

        int maxHorizontalElSize = maxViewSize.getWidth() / horizontalElementsNum;
        int maxVerticalElSize = maxViewSize.getHeight() / verticalElementsNum;

        this.elementSize = (maxHorizontalElSize < maxVerticalElSize) ? maxHorizontalElSize : maxVerticalElSize;

        int newWidth = this.elementSize * horizontalElementsNum;
        int newHeight = this.elementSize * verticalElementsNum;

        return new Size(newWidth, newHeight);
    }

}