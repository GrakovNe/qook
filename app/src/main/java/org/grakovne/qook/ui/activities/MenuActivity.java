package org.grakovne.qook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ScrollView;

import org.grakovne.qook.R;
import org.grakovne.qook.managers.SharedSettingsManager;

public class MenuActivity extends BaseActivity {

    private Button continueGameButton;
    private Button selectLevelButton;
    private Button aboutButton;
    private ScrollView menuButtonsPanel;
    private Button additionalButton;

    private SharedSettingsManager sharedSettingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedSettingsManager = SharedSettingsManager.build(this);

        setContentView(R.layout.activity_menu);

        continueGameButton = findViewById(R.id.continue_game_button);
        selectLevelButton = findViewById(R.id.select_level_button);
        aboutButton = findViewById(R.id.about_button);
        menuButtonsPanel = findViewById(R.id.menu_buttons_panel);
        additionalButton = findViewById(R.id.additional_button);

        menuButtonsPanel.setVerticalScrollBarEnabled(false);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.menu_coming_anim);
        animation.start();

        continueGameButton.setOnClickListener(v -> onContinueClick());
        selectLevelButton.setOnClickListener(v -> onSelectLevelButton());
        aboutButton.setOnClickListener(v -> onAboutButton());
        additionalButton.setOnClickListener(v -> onAdditionalButton());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedSettingsManager.isRanBefore()) {
            continueGameButton.setText(getString(R.string.continue_game_button_text));
        } else {
            continueGameButton.setText(getString(R.string.new_game_button_text));
        }

        sharedSettingsManager.setRanBefore();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    private void onContinueClick() {
        int currentLevel = sharedSettingsManager.getCurrentLevel();
        Intent intent = new Intent(this, LevelActivity.class);
        intent.putExtra(DESIRED_LEVEL, currentLevel);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void onSelectLevelButton() {
        switchActivity(LevelSelectorActivity.class);
    }

    private void onAboutButton() {
        switchActivity(AboutActivity.class);
    }

    private void onAdditionalButton() {
        switchActivity(AdvancedActivity.class);
    }
}
