package org.grakovne.qook.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.grakovne.qook.R;
import org.grakovne.qook.entity.Field;
import org.grakovne.qook.entity.Level;
import org.grakovne.qook.ui.views.FieldView;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Level level = new Level();
        Field field = new Field(level);

        FieldView fieldView = (FieldView) findViewById(R.id.field);
        fieldView.setField(field);

        fieldView.invalidate();

    }


}
