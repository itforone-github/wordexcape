package com.vocaescape.vocaescape;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashEndActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endsplash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                        finish();
        }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onBackPressed() {
    }
}
