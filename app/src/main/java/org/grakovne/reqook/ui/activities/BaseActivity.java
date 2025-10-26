package org.grakovne.reqook.ui.activities;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String DESIRED_LEVEL = "Continue game";
    public static final String LEVEL_NUMBER = "Level number";
    public static final String FIELD = "Level object";

    protected void switchActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
