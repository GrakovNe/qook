package org.grakovne.qook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.grakovne.qook.R;
import org.grakovne.qook.entity.Field;
import org.grakovne.qook.entity.Level;
import org.grakovne.qook.managers.LevelManager;
import org.grakovne.qook.ui.views.FieldView;

import java.io.IOException;
import java.text.DecimalFormat;
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

    private LevelManager levelManager = null;
    private Level level = null;

    private float downHorizontal;
    private float downVertical;
    private float upHorizontal;
    private float upVertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        ButterKnife.inject(this);

        if (savedInstanceState == null) {
            levelManager = LevelManager.build(this);
            fieldView.setOnTouchListener(onFieldTouchListener);
            openLevel();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void openLevel() {
        try {
            level = levelManager.getCurrentLevel();
            Field field = new Field(level);
            fieldView.setField(field);


            StringBuilder builder = new StringBuilder();
            builder
                    .append(String.format(Locale.getDefault(), "%02d", levelManager.getCurrentLevelNumber()))
                    .append(" / ")
                    .append(String.format(Locale.getDefault(), "%02d", levelManager.getLevelsNumber()));
            levelCounter.setText(builder.toString());

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

    @Override
    public void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
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
        onBackPressed();
    }
}
