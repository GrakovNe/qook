package org.grakovne.qook.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.grakovne.qook.R;
import org.grakovne.qook.managers.SharedSettingsManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MenuActivity extends BaseActivity {

    @InjectView(R.id.title_text)
    TextView titleText;
    @InjectView(R.id.continue_game_button)
    Button continueGameButton;
    @InjectView(R.id.select_level_button)
    Button selectLevelButton;
    @InjectView(R.id.about_button)
    Button aboutButton;
    @InjectView(R.id.menu_list)
    LinearLayout menuList;
    @InjectView(R.id.menu_buttons_panel)
    ScrollView menuButtonsPanel;
    @InjectView(R.id.additional_button)
    Button additionalButton;
    private SharedSettingsManager sharedSettingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedSettingsManager = SharedSettingsManager.build(this);

        setContentView(R.layout.activity_menu);
        ButterKnife.inject(this);

        menuButtonsPanel.setVerticalScrollBarEnabled(false);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.menu_coming_anim);
        animation.start();
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

    @OnClick(R.id.continue_game_button)
    public void onContinueClick() {
        int currentLevel = sharedSettingsManager.getCurrentLevel();
        Intent intent = new Intent(this, LevelActivity.class);
        intent.putExtra(DESIRED_LEVEL, currentLevel);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @OnClick(R.id.select_level_button)
    public void onSelectLevelButton() {
        switchActivity(LevelSelectorActivity.class);
    }

    @OnClick(R.id.about_button)
    public void onAboutButton() {
        switchActivity(AboutActivity.class);
    }

    private void switchActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @OnClick(R.id.additional_button)
    public void onAdditionalButton() {
    }
}
