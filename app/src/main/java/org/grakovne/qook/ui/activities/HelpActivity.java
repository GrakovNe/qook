package org.grakovne.qook.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.uncopt.android.widget.text.justify.JustifiedTextView;

import org.grakovne.qook.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        JustifiedTextView helpText = findViewById(R.id.help_text);
        TextView taskText = findViewById(R.id.task_text);

        Typeface fontFace = Typeface.SERIF;
        taskText.setTypeface(fontFace);
        helpText.setTypeface(fontFace);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
    }
}
