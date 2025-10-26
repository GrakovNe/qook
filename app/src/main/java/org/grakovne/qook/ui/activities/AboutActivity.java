package org.grakovne.qook.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import org.grakovne.qook.R;

public class AboutActivity extends BaseActivity {

    private TextView versionTextAbout;
    private TextView authorTextAbout;
    private TextView authorNameTextAbout;
    private TextView addressTextAbout;
    private TextView yearTextAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        versionTextAbout = findViewById(R.id.version_text_about);
        authorTextAbout = findViewById(R.id.author_text_about);
        authorNameTextAbout = findViewById(R.id.author_name_text_about);
        addressTextAbout = findViewById(R.id.address_text_about);
        yearTextAbout = findViewById(R.id.year_text_about);

        Typeface face = Typeface.create("serif-monospace", Typeface.NORMAL);
        yearTextAbout.setTypeface(face);
        addressTextAbout.setTypeface(face);
        authorNameTextAbout.setTypeface(face);
        authorTextAbout.setTypeface(face);
        versionTextAbout.setTypeface(face);
    }

    @Override
    public void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
    }
}
