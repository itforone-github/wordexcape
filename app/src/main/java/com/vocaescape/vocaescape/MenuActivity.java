package com.vocaescape.vocaescape;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {
    private ActivityManager am = ActivityManager.getInstance();
    @BindView(R.id.adView_banner3)    AdView banner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        am.addActivity(this);
        ButterKnife.bind(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        banner.loadAd(new AdRequest.Builder().build());

    }

//
//    public void move_detailmenu(View view){
//
//        Intent i  = new Intent(MenuActivity.this, MenuWebviewActivity.class);
//
//        switch(view.getId()){
//
//            case R.id.menu01 :
//                    i.putExtra("url",getString(R.string.menu1));
//                    break;
//            case R.id.menu02 :
//                    i.putExtra("url",getString(R.string.menu2));
//                    break;
//            case R.id.menu03 :
//                    i.putExtra("url",getString(R.string.menu3));
//                    break;
//            case R.id.menu04 :
//                    i.putExtra("url",getString(R.string.menu4));
//                    break;
//            case R.id.menu05 :
//                    i.putExtra("url",getString(R.string.menu5));
//                    break;
//            case R.id.menu06 :
//                    i.putExtra("url",getString(R.string.menu6));
//                    break;
//
//        }
//
//        startActivity(i);
//        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
//
//    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }
}