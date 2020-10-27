package com.vocaescape.vocaescape;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingTextActivity extends AppCompatActivity {
    public static int viewtextSize = 0;
    private ActivityManager am = ActivityManager.getInstance();
    @BindView(R.id.adView_banner5)    AdView banner;
    @BindView(R.id.settingseek)    SeekBar settingtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingtext);
        ButterKnife.bind(this);


        if(viewtextSize>0) {
            settingtext.setProgress(viewtextSize);
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        banner.loadAd(new AdRequest.Builder().build());

        settingtext.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int now_progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("seek_value",String.valueOf(progress));
                //now_progress=progress;
                viewtextSize = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
    }


}