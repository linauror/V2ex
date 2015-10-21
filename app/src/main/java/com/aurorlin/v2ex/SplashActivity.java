package com.aurorlin.v2ex;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * Created by aurorlin on 2015/10/19.
 */
public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGHT = 4000;
    private TextView splash_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash_text = (TextView) findViewById(R.id.splash_text);
        //Animator animator = AnimatorInflater.loadAnimator(SplashActivity.this, R.anim.splash);
        //animator.setTarget(splash_text);
        //animator.start();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
