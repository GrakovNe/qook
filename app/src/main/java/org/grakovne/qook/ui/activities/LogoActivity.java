package org.grakovne.qook.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import org.grakovne.qook.R;
import org.grakovne.qook.entity.Field;
import org.grakovne.qook.entity.Level;
import org.grakovne.qook.ui.views.FieldView;

public class LogoActivity extends AppCompatActivity {
    private FieldView fieldView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Level level = new Level();
        Field field = new Field(level);

        fieldView = (FieldView) findViewById(R.id.field);
        setFieldSize(fieldView, calcFieldSize());

        fieldView.setField(field);
    }

    private int calcFieldSize(){
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        int fieldSize;

        if (screenHeight <= screenWidth){
            fieldSize = screenHeight;
        } else {
            fieldSize = screenWidth;
        }

        fieldSize -= (fieldSize / 24) * 2;

        return fieldSize;
    }

    private void setFieldSize(FieldView fieldView, int size){
        ViewGroup.LayoutParams viewParams = fieldView.getLayoutParams();
        viewParams.width = size;
        viewParams.height = size;
        fieldView.setLayoutParams(viewParams);
    }
}
