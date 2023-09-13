package com.vocaescape.vocaescape;

import static com.vocaescape.vocaescape.MenuWebviewActivity.VIEW_REFRESH;
import static com.vocaescape.vocaescape.setting.SettingTextActivity.viewtextSize;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.vocaescape.vocaescape.databinding.ActivityWebviewBinding;
import com.vocaescape.vocaescape.setting.SettingActivity;
import com.vocaescape.vocaescape.util.ActivityManager;

import butterknife.ButterKnife;

public class WridActivity extends AppCompatActivity {
    ActivityWebviewBinding binding;
    private ActivityManager am = ActivityManager.getInstance();

    WebView webView;
//    @BindView(R.id.searchwv_bt)    ImageButton searchwv_bt;

    int flg_ad =0;
    int adClick = 0;
    WebSettings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        am.addActivity(this);
        ButterKnife.bind(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        binding.setMain2(this);
        webView = binding.webView;



     //   searchwv_bt.setVisibility(View.GONE);

        SharedPreferences sf = getSharedPreferences("pf_flg",MODE_PRIVATE);
        flg_ad = sf.getInt("flg",0);
        int set_value = flg_ad+1;
        if(set_value==5)  set_value = 0;

        SharedPreferences sharedPreferences = getSharedPreferences("pf_flg",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("flg", set_value);
        editor.commit();

        String url = getIntent().getStringExtra("url");

        Log.d("url",url);


        settings = webView.getSettings();
        if(Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            //쿠키 생성
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(webView,true);
        }
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.addJavascriptInterface(new WebviewJavainterface(),"Android");
        webView.setWebViewClient(new Viewmanager(this));
        webView.setWebChromeClient(new WebchromeClient(this, this));
        settings.setTextZoom(viewtextSize*15+55);

        if(url.isEmpty() == false)
            webView.loadUrl(url);

        else {
            finish();
            overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        }
        bannerAd();
    }

    @Override
    public void onBackPressed() {
        /*if(flg_ad==4) {
            Intent i = new Intent(this, SplashadActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
            finish();
            overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
        }
        else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        }*/
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case VIEW_REFRESH:
                settings.setTextZoom(viewtextSize*15+55);
                break;
        }
    }

    private class WebviewJavainterface {
        @JavascriptInterface
        public void temp(String id, String table) {

        }
    }

    public void move_setting(View view) {
        Intent i  = new Intent(getApplicationContext(), SettingActivity.class);
        startActivityForResult(i,VIEW_REFRESH);
        //i.putExtra("notice",1);
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }

    public void move_search(View view) {
        Intent i  = new Intent(getApplicationContext(), WebviewActivity.class);
        i.putExtra("board_name", "search");
        startActivityForResult(i,VIEW_REFRESH);
        //i.putExtra("notice",1);
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }

    public void move_home(View view){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        finish();
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }

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
                }).withAdListener(new AdListener() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        bannerAd();
                        adClick++;
                    }
                })
                .build();
        if(adClick == 0) {
            adLoader.loadAd(new AdRequest.Builder().build());
        }else{
            adLoader.loadAds(new AdRequest.Builder().build(),2);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
