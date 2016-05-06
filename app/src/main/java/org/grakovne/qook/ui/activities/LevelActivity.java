package org.grakovne.qook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import org.grakovne.qook.R;
import org.grakovne.qook.entity.Field;
import org.grakovne.qook.entity.Level;
import org.grakovne.qook.exceptions.GameException;
import org.grakovne.qook.managers.LevelManager;
import org.grakovne.qook.ui.views.FieldView;

import java.io.IOException;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.view.View.OnTouchListener;

public class LevelActivity extends BaseActivity {
    @InjectView(R.id.field)
    FieldView fieldView;
    @InjectView(R.id.reset_level_button)
    ImageButton resetLevelButton;
    @InjectView(R.id.level_counter)
    TextView levelCounter;
    @InjectView(R.id.back_level_button)
    ImageButton backLevelButton;

    private int currentLevelNumber;

    private Bundle savedData;

    private LevelManager levelManager = null;
    private Level level = null;

    private float downHorizontal;
    private float downVertical;
    private float upHorizontal;
    private float upVertical;

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
                        animateView(fieldView);

                        try {
                            levelManager.finishLevel();
                            openLevel(levelManager.getCurrentLevelNumber());
                            getIntent().putExtra(DESIRED_LEVEL, levelManager.getCurrentLevelNumber());
                            fieldView.layout(0,0,0,0);
                        } catch (GameException ex) {
                            onMenuClick();
                        }
                    }
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        ButterKnife.inject(this);

        levelManager = LevelManager.build(this);
        fieldView.setOnTouchListener(onFieldTouchListener);

        savedData = savedInstanceState;

        int levelNumber = getIntent().getIntExtra(DESIRED_LEVEL, 1);

        if (savedData != null && savedData.getInt(LEVEL_NUMBER) == levelNumber) {
            restoreLevel();
        } else {
            openLevel(levelNumber);
        }

        currentLevelNumber = levelNumber;
        Log.d("Current level", String.valueOf(currentLevelNumber));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void openLevel(int levelNumber) {
        try {
            level = levelManager.getLevel(levelNumber);
            fieldView.setField(new Field(level));
            setLevelCounterText(levelNumber);
            currentLevelNumber = levelNumber;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void restoreLevel() {
        Field field = (Field) savedData.getSerializable(FIELD);

        fieldView.setField(field);
        setLevelCounterText(savedData.getInt(LEVEL_NUMBER));
    }

    private void setLevelCounterText(int levelNumber) {
        StringBuilder builder = new StringBuilder();
        try {
            builder
                    .append(String.format(Locale.getDefault(), "%02d", levelNumber))
                    .append(" / ")
                    .append(String.format(Locale.getDefault(), "%02d", levelManager.getLevelsNumber()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        levelCounter.setText(builder.toString());
    }

    private void animateView(View view) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.field_changes_anim);
        view.startAnimation(animation);
    }

    @Override
    public void onResume() {
        super.onResume();

        overridePendingTransition(0, 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(FIELD, fieldView.getField());
        outState.putInt(LEVEL_NUMBER, currentLevelNumber);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }


    @OnClick(R.id.reset_level_button)
    public void onResetClick() {
        try {
            fieldView.setField(new Field(levelManager.resetLevel()));
            fieldView.invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.back_level_button)
    public void onMenuClick() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}