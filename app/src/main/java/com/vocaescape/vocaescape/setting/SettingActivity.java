package com.vocaescape.vocaescape.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.vocaescape.vocaescape.util.ActivityManager;
import com.vocaescape.vocaescape.MenuWebviewActivity;
import com.vocaescape.vocaescape.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vocaescape.vocaescape.setting.SettingTextActivity.viewtextSize;

public class SettingActivity extends AppCompatActivity {
    public static final int VIEW_REFRESH =4;
    private ActivityManager am = ActivityManager.getInstance();
    @BindView(R.id.adView_banner5)    AdView banner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        banner.loadAd(new AdRequest.Builder().build());


    }

    @Override
    public void onBackPressed() {
        if(viewtextSize>0){
            Intent intent = new Intent();
            setResult(RESULT_OK,intent);
        }
        finish();
        overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
    }







    public void move_settingdetail(View view) {

        switch (view.getId()){
            case  R.id.setting_menu01:{
                Intent i = new Intent(SettingActivity.this, SettingTextActivity.class);
                startActivityForResult(i,VIEW_REFRESH);
                overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
                break;
            }
            case R.id.setting_menu02:{
                Intent i = new Intent(SettingActivity.this, MenuWebviewActivity.class);
                i.putExtra("url","https://www.google.com");
                startActivity(i);
                overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
                break;
            }

        }


    }

}