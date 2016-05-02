package org.grakovne.qook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.grakovne.qook.R;

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
    @InjectView(R.id.settings_button)
    Button settingsButton;
    @InjectView(R.id.help_button)
    Button helpButton;
    @InjectView(R.id.about_button)
    Button aboutButton;
    @InjectView(R.id.exit_button)
    Button exitButton;
    @InjectView(R.id.menu_list)
    LinearLayout menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        overridePendingTransition(0,0);
    }

    @OnClick(R.id.continue_game_button)
    public void onContinueClick() {
        Intent intent = new Intent(this, LevelActivity.class);
        intent.putExtra(CONTINUE_GAME_INTENT, 1);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
