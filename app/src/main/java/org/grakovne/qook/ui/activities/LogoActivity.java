package org.grakovne.qook.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.grakovne.qook.managers.LevelManager;
import org.grakovne.qook.R;
import org.grakovne.qook.entity.Field;
import org.grakovne.qook.entity.Level;
import org.grakovne.qook.ui.views.FieldView;

import java.io.IOException;

public class LogoActivity extends AppCompatActivity {
    private FieldView fieldView = null;
    private LevelManager levelManager = null;
    private Level level = null;

    private float downHorizontal;
    private float downVertical;
    private float upHorizontal;
    private float upVertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        levelManager = LevelManager.build(this);

        fieldView = (FieldView) findViewById(R.id.field);

        if (fieldView != null) {
            fieldView.setOnTouchListener(onFieldTouchListener);
            openLevel();
        }
    }


    private void openLevel(){
        try {
            level = levelManager.getCurrentLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Field field = new Field(level);
        fieldView.setField(field);
    }

    private View.OnTouchListener onFieldTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downHorizontal = event.getX();
                    downVertical = event.getY();
                    break;

                case MotionEvent.ACTION_UP:
                    upHorizontal = event.getX();
                    upVertical = event.getY();

                    boolean isWin = fieldView.getField().makeTurn(
                            fieldView.getElementCoord(downHorizontal, downVertical),
                            fieldView.getSwipeDirection(downHorizontal, upHorizontal, downVertical, upVertical)
                    );

                    fieldView.invalidate();

                    if (isWin){
                        Toast.makeText(getApplicationContext(), "You Win!", Toast.LENGTH_LONG).show();
                        levelManager.finishLevel();
                        openLevel();
                    }
            }
            return true;
        }
    };
}
