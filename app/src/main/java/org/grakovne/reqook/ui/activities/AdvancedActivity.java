package org.grakovne.reqook.ui.activities;

import android.os.Bundle;
import android.widget.Button;

import org.grakovne.reqook.R;
import org.grakovne.reqook.managers.SharedSettingsManager;

public class AdvancedActivity extends BaseActivity {

    private Button toggleAnimationButton;

    private SharedSettingsManager sharedSettingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        toggleAnimationButton = findViewById(R.id.toggle_animation_button);
        Button helpButton = findViewById(R.id.help_button);

        sharedSettingsManager = SharedSettingsManager.build(this);

        helpButton.setOnClickListener(v -> switchActivity(HelpActivity.class));

        toggleAnimationButton.setOnClickListener(v -> {
            boolean isAnimationNeed = sharedSettingsManager.isAnimationNeed();
            if (isAnimationNeed) {
                sharedSettingsManager.setIsAnimationNeed(false);
                toggleAnimationButton.setText(getString(R.string.on_animation));
            } else {
                sharedSettingsManager.setIsAnimationNeed(true);
                toggleAnimationButton.setText(getString(R.string.off_animation));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);

        boolean isAnimationNeed = sharedSettingsManager.isAnimationNeed();
        toggleAnimationButton.setText(isAnimationNeed
                ? getString(R.string.off_animation)
                : getString(R.string.on_animation));
    }
}
