package org.grakovne.qook.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.grakovne.qook.R;
import org.grakovne.qook.entity.Field;
import org.grakovne.qook.entity.Level;
import org.grakovne.qook.entity.elements.Ball;
import org.grakovne.qook.enums.Color;
import org.grakovne.qook.managers.LevelManager;
import org.grakovne.qook.ui.views.FieldView;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.view.View.OnTouchListener;

public class LogoActivity extends AppCompatActivity {
    @InjectView(R.id.field)
    FieldView fieldView;
    @InjectView(R.id.reset_level_button)
    Button resetLevelButton;

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
        ButterKnife.inject(this);

        levelManager = LevelManager.build(this);
        fieldView.setOnTouchListener(onFieldTouchListener);
        openLevel();
    }


    private void openLevel() {
        try {
            level = levelManager.getCurrentLevel();
            Field field = new Field(level);
            fieldView.setField(field);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OnTouchListener onFieldTouchListener = new OnTouchListener() {
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
                            fieldView.getElementCoordinates(downHorizontal, downVertical),
                            fieldView.getSwipeDirection(downHorizontal, upHorizontal, downVertical, upVertical)
                    );

                    fieldView.invalidate();

                    if (isWin) {
                        Toast.makeText(getApplicationContext(), "You Win!", Toast.LENGTH_LONG).show();
                        levelManager.finishLevel();
                        openLevel();
                    }

            }
            return true;
        }
    };

    @OnClick(R.id.reset_level_button)
    public void onResetClick() {
        try {
            fieldView.setField(new Field(levelManager.resetLevel()));
            fieldView.invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
