package org.grakovne.qook.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.grakovne.qook.R;
import org.grakovne.qook.managers.LevelManager;
import org.grakovne.qook.managers.SharedSettingsManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AdvancedActivity extends BaseActivity {

    @InjectView(R.id.title_text)
    TextView titleText;
    @InjectView(R.id.open_all_levels_buy_button)
    Button openAllLevelsBuyButton;
    @InjectView(R.id.help_button)
    Button helpButton;
    @InjectView(R.id.menu_list)
    LinearLayout menuList;
    @InjectView(R.id.menu_buttons_panel)
    ScrollView menuButtonsPanel;

    private LevelManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        ButterKnife.inject(this);

        manager = LevelManager.build(this);
    }

    @OnClick(R.id.help_button)
    public void onHelpButtonClick() {
        switchActivity(HelpActivity.class);
    }

    @OnClick(R.id.open_all_levels_buy_button)
    public void onOpenAllLevelsClock(){
        manager.openAllLevels();
        Toast.makeText(this, "Levels are opened", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);

        if (manager.getMaximalLevelNumber() == manager.getLevelsNumber()){
            openAllLevelsBuyButton.setClickable(false);
            openAllLevelsBuyButton.setBackgroundResource(R.drawable.button_dark);
        }
    }
}
