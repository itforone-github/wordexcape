package com.vocaescape.vocaescape.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.vocaescape.vocaescape.R;
import com.vocaescape.vocaescape.databinding.ActivitySettingtextBinding;
import com.vocaescape.vocaescape.util.ActivityManager;

import butterknife.BindView;

public class SettingTextActivity extends AppCompatActivity {
    ActivitySettingtextBinding binding;
    public static int viewtextSize = 3;
    private ActivityManager am = ActivityManager.getInstance();

    @BindView(R.id.settingseek)    SeekBar settingtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingtext);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settingtext);
        binding.setMain(this);


         binding.settingseek.setProgress(viewtextSize);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        binding.settingseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int now_progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("seek_value",String.valueOf(progress));
                //now_progress=progress;
                viewtextSize = progress;
                SharedPreferences sharedPreferences = getSharedPreferences("viewtxtsize",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("value", viewtextSize);
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });
        bannerAd();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
    }
    //광고 보여주기
    public void bannerAd(){
        MobileAds.initialize(this);
        AdLoader adLoader = new AdLoader.Builder(this, getString(R.string.nativead))
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();
                        TemplateView template = findViewById(R.id.adView_banner);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .build();

        adLoader.loadAds(new AdRequest.Builder().build(),1);
    }

}