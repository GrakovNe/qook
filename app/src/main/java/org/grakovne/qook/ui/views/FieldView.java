package org.grakovne.qook.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.grakovne.qook.R;
import org.grakovne.qook.entity.Coordinates;
import org.grakovne.qook.entity.Field;
import org.grakovne.qook.entity.elements.Ball;
import org.grakovne.qook.entity.elements.Block;
import org.grakovne.qook.entity.elements.Hole;
import org.grakovne.qook.entity.elements.Item;
import org.grakovne.qook.enums.Color;
import org.grakovne.qook.enums.Direction;

public class FieldView extends View {
    private Field field;
    private int elementSize;

    public void setField(Field field) {
        this.field = field;
        setFieldSize(calcFieldSize());
    }

    public FieldView(Context context) {
        super(context);
    }

    public FieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FieldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (field == null) {
            return;
        }

        int elementXSize = canvas.getWidth() / field.getField()[0].length;
        int elementYSize = canvas.getHeight() / field.getField().length;

        elementSize = elementXSize;

        for (int i = 0; i < field.getField().length; i++) {
            for (int j = 0; j < field.getField()[0].length; j++) {
                Drawable d = selectDrawable(field.getField()[j][i]);
                d.setBounds(i * elementXSize, j * elementYSize, (i + 1) * elementXSize, (j + 1) * elementYSize);
                d.draw(canvas);
            }
        }
    }

    private double getSwipeLength(float xDistance, float yDistance) {
        return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
    }

    public Direction getSwipeDirection(float downXCoord, float upXCoord, float downYCoord, float upYCoord) {
        float xDistance = Math.abs(upXCoord - downXCoord);
        float yDistance = Math.abs(upYCoord - downYCoord);
        double swipeLength = getSwipeLength(xDistance, yDistance);

        if (swipeLength < elementSize / 2) {
            return Direction.NOWHERE;
        }

        if (xDistance >= yDistance) {
            if (upXCoord > downXCoord) {
                return Direction.RIGHT;
            }
            return Direction.LEFT;
        }

        if (yDistance > xDistance) {
            if (upYCoord > downYCoord) {
                return Direction.DOWN;
            }
            return Direction.UP;
        }

        return Direction.DOWN;
    }

    public Coordinates getElementCoords(float xCoords, float yCoords) {
        float xElCoord = xCoords / elementSize;
        float yElCoord = yCoords / elementSize;
        return new Coordinates((int) xElCoord, (int) yElCoord);
    }

    private Drawable selectDrawable(Item item) {
        if (item == null) {
            return ContextCompat.getDrawable(getContext(), R.drawable.none);
        }

        Class clazz = item.getClass();
        Color color = item.getColor();

        if (clazz.equals(Block.class)) {
            return ContextCompat.getDrawable(getContext(), R.drawable.block_cell);
        }

        if (clazz.equals(Hole.class)) {
            switch (color) {
                case GREEN:
                    return ContextCompat.getDrawable(getContext(), R.drawable.hole_green);

                case RED:
                    return ContextCompat.getDrawable(getContext(), R.drawable.hole_red);

                case BLUE:
                    return ContextCompat.getDrawable(getContext(), R.drawable.hole_blue);

                case YELLOW:
                    return ContextCompat.getDrawable(getContext(), R.drawable.hole_yellow);

                case PURPLE:
                    return ContextCompat.getDrawable(getContext(), R.drawable.hole_purple);

                case CYAN:
                    return ContextCompat.getDrawable(getContext(), R.drawable.hole_cyan);
            }
        }

        if (clazz.equals(Ball.class)) {
            switch (color) {
                case GREEN:
                    return ContextCompat.getDrawable(getContext(), R.drawable.ball_green);

                case RED:
                    return ContextCompat.getDrawable(getContext(), R.drawable.ball_red);

                case BLUE:
                    return ContextCompat.getDrawable(getContext(), R.drawable.ball_blue);

                case YELLOW:
                    return ContextCompat.getDrawable(getContext(), R.drawable.ball_yellow);

                case PURPLE:
                    return ContextCompat.getDrawable(getContext(), R.drawable.ball_purple);

                case CYAN:
                    return ContextCompat.getDrawable(getContext(), R.drawable.ball_cyan);

            }
        }

        return ContextCompat.getDrawable(getContext(), R.drawable.ball_red);

    }

    public Field getField() {
        return field;
    }

    public int calcFieldSize() {
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        int fieldSize;

        if (screenHeight <= screenWidth) {
            fieldSize = screenHeight;
        } else {
            fieldSize = screenWidth;
        }

        fieldSize -= (fieldSize / 20) * 2;

        return fieldSize;
    }

    public void setFieldSize(int size) {
        ViewGroup.LayoutParams viewParams = this.getLayoutParams();
        viewParams.width = size;
        viewParams.height = size;
        this.setLayoutParams(viewParams);
    }
}
