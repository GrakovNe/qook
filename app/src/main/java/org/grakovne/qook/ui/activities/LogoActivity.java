package org.grakovne.qook.ui.activities;

import android.content.res.Configuration;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.grakovne.qook.R;
import org.grakovne.qook.entity.Coordinates;
import org.grakovne.qook.entity.Field;
import org.grakovne.qook.entity.Level;
import org.grakovne.qook.enums.Direction;
import org.grakovne.qook.ui.views.FieldView;

public class LogoActivity extends AppCompatActivity {
    private FieldView fieldView;

    float downXCoord;
    float downYCoord;
    float upXCoord;
    float upYCoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Level level = new Level();
        Field field = new Field(level);

        fieldView = (FieldView) findViewById(R.id.field);

        setFieldSize(fieldView, calcFieldSize());

        fieldView.setField(field);
        fieldView.setOnTouchListener(onFieldTouchListener);
    }

    private View.OnTouchListener onFieldTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downXCoord = event.getX();
                    downYCoord = event.getY();
                    break;

                case MotionEvent.ACTION_UP:
                    upXCoord = event.getX();
                    upYCoord = event.getY();

                    Direction direction = fieldView.getSwipeDirection(downXCoord, upXCoord, downYCoord, upYCoord);
                    Log.d("Direction", direction.toString());
                    Coordinates coordinates = fieldView.getElementCoords(downXCoord, downYCoord);
                    Log.d("Element", coordinates.toString());
            }
            return true;
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private int calcFieldSize() {
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        int fieldSize;

        if (screenHeight <= screenWidth) {
            fieldSize = screenHeight;
        } else {
            fieldSize = screenWidth;
        }

        fieldSize -= (fieldSize / 24) * 2;

        return fieldSize;
    }

    private void setFieldSize(FieldView fieldView, int size) {
        ViewGroup.LayoutParams viewParams = fieldView.getLayoutParams();
        viewParams.width = size;
        viewParams.height = size;
        fieldView.setLayoutParams(viewParams);
    }
}
