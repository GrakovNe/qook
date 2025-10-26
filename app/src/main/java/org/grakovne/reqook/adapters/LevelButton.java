package org.grakovne.reqook.adapters;

import android.content.Context;
import android.util.AttributeSet;

public class LevelButton extends androidx.appcompat.widget.AppCompatButton {

    public LevelButton(Context context) {
        super(context);
    }

    public LevelButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LevelButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}