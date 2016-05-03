package org.grakovne.qook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import org.grakovne.qook.adapters.LevelGridAdapter;
import org.grakovne.qook.R;
import org.grakovne.qook.managers.LevelManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LevelSelectorActivity extends BaseActivity {

    @InjectView(R.id.level_grid)
    GridView levelGrid;

    private LevelManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);
        ButterKnife.inject(this);

        manager = LevelManager.build(getBaseContext());

        View.OnClickListener levelClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LevelActivity.class);
                intent.putExtra(DESIRED_LEVEL, v.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        };

        LevelGridAdapter adapter = new LevelGridAdapter(this, R.layout.level_item, getListOfLevelNumbers(), manager.getMaximalLevelNumber(), levelClick);
        adapter.setNotifyOnChange(false);
        levelGrid.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
    }

    private List<Integer> getListOfLevelNumbers() {
        List<Integer> integers = new ArrayList<>();
        int maximalLevel = 0;
        try {
            maximalLevel = manager.getLevelsNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < maximalLevel + 1; i++) {
            integers.add(i);
        }
        return integers;
    }
}
