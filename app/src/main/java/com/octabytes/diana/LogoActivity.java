package com.octabytes.diana;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LogoActivity extends BaseActivity {
    ImageView imgLogoView;
    Animation animationFadeIn, animationFadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        new LocalDatabase().loadInfo(this);

        imgLogoView = (ImageView) findViewById(R.id.imgLogo);
        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        animationFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation) {
                animationFadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {
                        if (LocalDatabase.firstTime) {
                            goToActivity(LogoActivity.this, FirstTimeOriginLangActivity.class);
                        } else {
                            goToActivity(LogoActivity.this, MainMenuActivity.class);
                        }
                    }
                });

                imgLogoView.startAnimation(animationFadeOut);
            }
        });

        imgLogoView.startAnimation(animationFadeIn);
    }
}
