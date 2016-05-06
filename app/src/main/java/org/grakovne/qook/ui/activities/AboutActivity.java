package org.grakovne.qook.ui.activities;

import android.os.Bundle;

import org.grakovne.qook.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    public void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);

    }
}
