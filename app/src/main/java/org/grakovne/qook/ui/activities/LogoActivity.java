package org.grakovne.qook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import org.grakovne.qook.R;
import org.grakovne.qook.entity.Level;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LogoActivity extends AppCompatActivity {

    @InjectView(R.id.author_icon)
    ImageView authorIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        ButterKnife.inject(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo_picture_show);
        authorIcon.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                authorIcon.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getApplicationContext(), LevelActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
