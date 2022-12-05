package com.vocaescape.vocaescape;

import static com.vocaescape.vocaescape.setting.SettingTextActivity.viewtextSize;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.nativead.NativeAd;
import com.vocaescape.vocaescape.databinding.ActivityMainBinding;
import com.vocaescape.vocaescape.util.ActivityManager;
import com.vocaescape.vocaescape.util.Enddialog;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    private long backPrssedTime = 0;
//    private InterstitialAd mInterstitialAd;
    private UnifiedNativeAd nativeAd,nativeAd2;
    private ActivityManager am = ActivityManager.getInstance();
    private Enddialog mEndDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(this);



        am.addActivity(this);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences("pf_flg",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("flg", 0);
        editor.commit();

        SharedPreferences pref_txtsize = getSharedPreferences("viewtxtsize",MODE_PRIVATE);
        viewtextSize = pref_txtsize.getInt("value",3);





        String androidId =  Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = MD5(androidId).toUpperCase();

        bannerAd();


    }

    @Override
    public void onBackPressed(){

        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPrssedTime;
        if (0 <= intervalTime && 2000 >= intervalTime){
            Intent i = new Intent(MainActivity.this, SplashEndActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
            finish();
            am.finishAllActivity();
        } else {
            backPrssedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 뒤로가기 누를시 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }


        }
        //광고 보여주기
    public void bannerAd(){
        MobileAds.initialize(this);
        AdLoader adLoader = new AdLoader.Builder(this, getString(R.string.nativead_test))
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
        AdLoader adLoader2 = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();
                        TemplateView template = findViewById(R.id.adView_main);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());
        adLoader2.loadAd(new AdRequest.Builder().build());
    }
    public void click_dialogN_main(View view){
     //   Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_LONG).show();
        mEndDialog.dismiss();
    }

    public void click_dialogY_main(View view){
        //    Toast.makeText(mContext.getApplicationContext(),"test2",Toast.LENGTH_LONG).show();
        mEndDialog.dismiss();
        Intent i = new Intent(MainActivity.this, SplashEndActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        finish();
        am.finishAllActivity();

    }

  /*  public void exit_setnativead(){

        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.nativead_test));

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                // You must call destroy on old ads when you are done with them,
                // otherwise you will have a memory leak.
                if (nativeAd2 != null) {
                    nativeAd2.destroy();
                }
                nativeAd2 = unifiedNativeAd;

                mEndDialog.populateUnifiedNativeAdView(unifiedNativeAd);
                //binding.fl_adplace.removeAllViews();

            }
        });

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(MainActivity.this, "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();

        //adLoader.loadAd(new AdRequest.Builder().addTestDevice("F225B75A37119EE77E3DEAB3DC23EB31").build());
        adLoader.loadAd(new AdRequest.Builder().build());

    }*/

    public String MD5(String md5) {

        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;

    }


    public void move_event(View view){
        String board_url = "";
        Intent i;
        switch (view.getId()) {
            case R.id.menu_bt:
                i  = new Intent(getApplicationContext(), MenuWebviewActivity.class);
                i.putExtra("url",getString(R.string.all_menu));
                break;
            case R.id.search_bt:
                i  = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", "search");
                break;
            case R.id.bt_word1:
                board_url = "verb";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word2:
                board_url = "noun";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word3:
                board_url = "adjective";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word4:
                board_url = "adverb";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word5:
                board_url = "auxiliary_verb";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word6:
                board_url = "pronoun";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word7:
                board_url = "preposition";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word8:
                board_url = "conjunction";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.mywordBtn:
                board_url = "myword";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        startActivity(i);
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bannerAd();
       // binding.adViewBanner.loadAd(new AdRequest.Builder().build());
    }
}
