package org.grakovne.reqook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import org.grakovne.reqook.R;
import org.grakovne.reqook.adapters.LevelGridAdapter;
import org.grakovne.reqook.managers.LevelManager;

import java.util.ArrayList;
import java.util.List;

public class LevelSelectorActivity extends BaseActivity {

    private GridView levelGrid;
    private LevelManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);

        levelGrid = findViewById(R.id.level_grid);
    }

    @Override
    public void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);

        manager = LevelManager.build(getBaseContext());

        View.OnClickListener levelClick = v -> {
            Intent intent = new Intent(getBaseContext(), LevelActivity.class);
            intent.putExtra(DESIRED_LEVEL, v.getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        };

        LevelGridAdapter adapter = new LevelGridAdapter(
                this,
                R.layout.level_item,
                getListOfLevelNumbers(),
                manager.getMaximalLevelNumber(),
                levelClick
        );

        adapter.setNotifyOnChange(false);
        levelGrid.setAdapter(adapter);
        levelGrid.setVerticalScrollBarEnabled(false);
    }

    private List<Integer> getListOfLevelNumbers() {
        List<Integer> integers = new ArrayList<>();
        int maximalLevel = manager.getLevelsNumber();

        for (int i = 1; i <= maximalLevel; i++) {
            integers.add(i);
        }
        return integers;
    }
}
