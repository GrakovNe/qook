package org.grakovne.qook.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import org.grakovne.qook.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AboutActivity extends BaseActivity {

    @InjectView(R.id.version_text_about)
    TextView versionTextAbout;
    @InjectView(R.id.author_text_about)
    TextView authorTextAbout;
    @InjectView(R.id.author_name_text_about)
    TextView authorNameTextAbout;
    @InjectView(R.id.address_text_about)
    TextView addressTextAbout;
    @InjectView(R.id.year_text_about)
    TextView yearTextAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);

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
