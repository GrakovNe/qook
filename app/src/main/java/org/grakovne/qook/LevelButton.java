package org.grakovne.qook;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class LevelButton extends Button {

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
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }
}
