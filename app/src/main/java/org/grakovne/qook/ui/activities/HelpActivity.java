package org.grakovne.qook.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.uncopt.android.widget.text.justify.JustifiedTextView;

import org.grakovne.qook.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HelpActivity extends AppCompatActivity {

    @InjectView(R.id.help_text)
    JustifiedTextView helpText;
    @InjectView(R.id.task_text)
    TextView taskText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.inject(this);

        Typeface fontFace = Typeface.SERIF;
        taskText.setTypeface(fontFace);
        helpText.setTypeface(fontFace);
    }

    @Override
    public void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
    }
}
