package org.grakovne.qook.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import org.grakovne.qook.R;
import org.grakovne.qook.entity.Field;
import org.grakovne.qook.entity.elements.Ball;
import org.grakovne.qook.entity.elements.Block;
import org.grakovne.qook.entity.elements.Hole;
import org.grakovne.qook.entity.elements.Item;
import org.grakovne.qook.enums.Color;

public class FieldView extends View {
    private Field field;

    public void setField(Field field) {
        this.field = field;
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

        int elementXSize = canvas.getWidth() / field.getField()[0].length;
        int elementYSize = canvas.getHeight() / field.getField().length;

        for (int i = 0; i < field.getField().length; i++) {
            for (int j = 0; j < field.getField()[0].length; j++) {
                Drawable d = selectDrawable(field.getField()[j][i]);
                d.setBounds(i * elementXSize, j * elementYSize, (i + 1) * elementXSize, (j + 1) * elementYSize);
                d.draw(canvas);
            }
        }
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
            switch (color){
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
}
