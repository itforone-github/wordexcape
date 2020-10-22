package com.vocaescape.vocaescape.menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.vocaescape.vocaescape.ActivityManager;
import com.vocaescape.vocaescape.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {
    private ActivityManager am = ActivityManager.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        am.addActivity(this);
        ButterKnife.bind(this);

    }


    public void move_detailmenu(View view) {
        Intent i;
        switch(view.getId()){
            case R.id.menu01 :
                    i=new Intent(MenuActivity.this, MenuintroduceActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
                    break;
            case R.id.menu02 :
                    i=new Intent(MenuActivity.this, MenueffectActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
                    break;
            case R.id.menu03 :
                    i=new Intent(MenuActivity.this, MenuadActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
                    break;
            case R.id.menu04 :
                    i=new Intent(MenuActivity.this, MenuUpdateActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
                    break;
            case R.id.menu05 :
                    i=new Intent(MenuActivity.this, MenuPolicyActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
                    break;
            case R.id.menu06 :
                    i=new Intent(MenuActivity.this, MenuRightActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
                    break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }
}