package org.grakovne.qook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.grakovne.qook.R;
import org.grakovne.qook.managers.LevelManager;
import org.grakovne.qook.managers.SharedSettingsManager;

public class AdvancedActivity extends BaseActivity {

    private Button toggleAnimationButton;
    private Button purchaseUndoBuyButton;
    private Button openAllLevelsBuyButton;
    private Button helpButton;
    private TextView titleText;
    private LinearLayout menuList;
    private ScrollView menuButtonsPanel;

    private static final int OPEN_LEVELS_RESPONSE = 200;
    private static final int OPEN_UNDO_RESPONSE = 201;
    private static final int BOUGHT_ALREADY_CODE = 7;
    private static final String OPEN_LEVEL_ITEM = "open_all_levels";
    private static final String OPEN_UNDO_ITEM = "open_undo_steps";

    private final String base64EncodedPublicKey =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAha7b6VWXKrV4nGLs3lP38s9NJqc7vumuRELyMrp0Qv/8wQuFGWXL36uYmULDCN4YGHW0/u4RC3i1tUQoH2AflDGM/nun4idmoozrswIHwjI1dKep3NFMjHLpvgXm+4PnNRTEmRXEG42GLAgABn/47eIie/ODgXOwfmNhyMlPaieKjxbX462jXQ9/EaqntMMkhomBlfb57xi/2Vc+yGlAKO52sBj0xCR8tQT/67kkP4LkDR++07V4lbLQroiRb9p/TECQbr/UiZEXvPmARbqw6WjAeJBguJtrdR8OvCKKJdA0F/Mj6tBXEZQmePSChp1fb91QixH9eO5atkZZL6ueKwIDAQAB";

    private LevelManager levelManager;
    private SharedSettingsManager sharedSettingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        toggleAnimationButton = findViewById(R.id.toggle_animation_button);
        purchaseUndoBuyButton = findViewById(R.id.purchase_undo_buy_button);
        openAllLevelsBuyButton = findViewById(R.id.open_all_levels_buy_button);
        helpButton = findViewById(R.id.help_button);
        titleText = findViewById(R.id.title_text);
        menuList = findViewById(R.id.menu_list);
        menuButtonsPanel = findViewById(R.id.menu_buttons_panel);

        levelManager = LevelManager.build(this);
        sharedSettingsManager = SharedSettingsManager.build(this);

        setOpenLevelsButtonClickable(false);
        setUndoButtonClickable(false);

        premiumOpenUndo();
        premiumOpenLevels();

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

        if (levelManager.getMaximalLevelNumber() >= levelManager.getLevelsNumber()) {
            setOpenLevelsButtonClickable(false);
        }

        if (sharedSettingsManager.isUndoPurchased()) {
            setUndoButtonClickable(false);
        }

        boolean isAnimationNeed = sharedSettingsManager.isAnimationNeed();
        toggleAnimationButton.setText(isAnimationNeed
                ? getString(R.string.off_animation)
                : getString(R.string.on_animation));
    }

    private void setOpenLevelsButtonClickable(boolean state) {
        openAllLevelsBuyButton.setClickable(state);
        openAllLevelsBuyButton.setBackgroundResource(
                state ? R.drawable.button_normal : R.drawable.button_dark
        );
    }

    private void setUndoButtonClickable(boolean state) {
        purchaseUndoBuyButton.setClickable(state);
        purchaseUndoBuyButton.setBackgroundResource(
                state ? R.drawable.button_normal : R.drawable.button_dark
        );
    }

    private void premiumOpenLevels() {
        levelManager.openAllLevels();
        switchActivity(LevelSelectorActivity.class);
    }

    private void premiumOpenUndo() {
        sharedSettingsManager.makeUndoPurchased();
        Toast.makeText(this, getString(R.string.undo_purchased), Toast.LENGTH_LONG).show();
    }
}
