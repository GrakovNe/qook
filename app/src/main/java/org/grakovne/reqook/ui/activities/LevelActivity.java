package org.grakovne.reqook.ui.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.grakovne.reqook.R;
import org.grakovne.reqook.engine.Field;
import org.grakovne.reqook.engine.Level;
import org.grakovne.reqook.engine.listeners.FieldUpdatingListener;
import org.grakovne.reqook.engine.listeners.HistoryStatesListener;
import org.grakovne.reqook.exceptions.GameException;
import org.grakovne.reqook.managers.HistoryManager;
import org.grakovne.reqook.managers.LevelManager;
import org.grakovne.reqook.managers.SharedSettingsManager;
import org.grakovne.reqook.ui.views.FieldView;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class LevelActivity extends BaseActivity {

    private FieldView fieldView;
    private TextView levelCounter;
    private ImageButton undoStepButton;

    private int currentLevelNumber;
    private Bundle savedData;

    private LevelManager levelManager = null;

    private float downHorizontal;
    private float downVertical;

    private Handler handler;
    private Timer timer;
    private TimerTask task;

    private Animation animation;
    private HistoryManager historyManager;

    private final SharedSettingsManager sharedSettingsManager = SharedSettingsManager.build(this);
    private static final int FRAME_DELAY = 1;

    private final Runnable invalidateView = () -> fieldView.invalidate();

    private final View.OnTouchListener onFieldTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downHorizontal = event.getX();
                    downVertical = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    float upHorizontal = event.getX();
                    float upVertical = event.getY();

                    fieldView.setClickable(false);
                    fieldView
                            .getField()
                            .makeTurn(
                                    fieldView.getElementCoordinates(downHorizontal, downVertical),
                                    fieldView.getSwipeDirection(downHorizontal, upHorizontal, downVertical, upVertical)
                            );
                    fieldView.setClickable(false);
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        fieldView = findViewById(R.id.field);
        ImageButton resetLevelButton = findViewById(R.id.reset_level_button);
        levelCounter = findViewById(R.id.level_counter);
        ImageButton backLevelButton = findViewById(R.id.back_level_button);
        undoStepButton = findViewById(R.id.undo_step_button);

        handler = new Handler();
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.field_changes_anim);
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

        // Listeners for buttons
        resetLevelButton.setOnClickListener(v -> {
            fieldView.layout(0, 0, 0, 0);
            openLevel(levelManager.getCurrentLevelNumber());
        });

        backLevelButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        undoStepButton.setOnClickListener(v -> {
            Level level = historyManager.getFromHistory();
            fieldView.getField().loadFromHistory(level);
            fieldView.invalidate();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void setListeners(final Field field) {
        field.setUpdatingListener(new FieldUpdatingListener() {
            @Override
            public void startUpdate() {
                timer = new Timer();
                task = new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(invalidateView);
                    }
                };
                timer.scheduleAtFixedRate(task, 0, FRAME_DELAY);
                historyManager.saveToHistory(fieldView.getField().getLevel());
            }

            @Override
            public void finishUpdate() {
                timer.cancel();
                handler.post(invalidateView);
            }
        });

        field.setCompleteListener(() -> handler.post(() -> {
            fieldView.invalidate();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            animateView(fieldView);
            try {
                levelManager.finishLevel();
                openLevel(levelManager.getCurrentLevelNumber());
                getIntent().putExtra(DESIRED_LEVEL, levelManager.getCurrentLevelNumber());
                fieldView.layout(0, 0, 0, 0);
            } catch (GameException ex) {
                onMenuClick();
            }
        }));
    }

    private void openLevel(int levelNumber) {
        try {
            Level level = levelManager.getLevel(levelNumber);
            final Field field = new Field(level, sharedSettingsManager.isAnimationNeed());
            setListeners(field);
            fieldView.setField(field);
            setLevelCounterText(levelNumber);
            currentLevelNumber = levelNumber;
            if (historyManager != null) historyManager.clearHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void restoreLevel() {
        Field field = (Field) savedData.getSerializable(FIELD);
        setListeners(field);
        fieldView.setField(field);
        setLevelCounterText(savedData.getInt(LEVEL_NUMBER));
    }

    private void setLevelCounterText(int levelNumber) {
        String text = String.format(Locale.getDefault(), "%02d / %02d",
                levelNumber, levelManager.getLevelsNumber());
        levelCounter.setText(text);
    }

    private void animateView(View view) {
        view.startAnimation(animation);
    }

    @Override
    public void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);

        if (fieldView.getField() != null) {
            fieldView.getField().setIsAnimation(sharedSettingsManager.isAnimationNeed());
        }

        boolean isUndoAvailable = sharedSettingsManager.isUndoPurchased();
        undoStepButton.setVisibility(isUndoAvailable ? View.VISIBLE : View.GONE);

        historyManager = HistoryManager.build(new HistoryStatesListener() {
            @Override
            public void historyIsEmpty() {
                handler.post(() -> {
                    undoStepButton.setEnabled(false);
                    undoStepButton.getBackground().setColorFilter(
                            ContextCompat.getColor(getApplicationContext(), R.color.dark_button),
                            PorterDuff.Mode.MULTIPLY
                    );
                });
            }

            @Override
            public void historyIsContainsSomething() {
                handler.post(() -> {
                    undoStepButton.setEnabled(true);
                    undoStepButton.getBackground().setColorFilter(
                            ContextCompat.getColor(getApplicationContext(), R.color.normal_button),
                            PorterDuff.Mode.MULTIPLY
                    );
                });
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(FIELD, fieldView.getField());
        outState.putInt(LEVEL_NUMBER, currentLevelNumber);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void onMenuClick() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
