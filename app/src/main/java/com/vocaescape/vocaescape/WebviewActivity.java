package com.vocaescape.vocaescape;

import static com.vocaescape.vocaescape.MenuWebviewActivity.VIEW_REFRESH;
import static com.vocaescape.vocaescape.setting.SettingTextActivity.viewtextSize;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.vocaescape.vocaescape.databinding.ActivityWebviewBinding;
import com.vocaescape.vocaescape.util.ActivityManager;
import com.vocaescape.vocaescape.util.Common;

import java.util.ArrayList;
import java.util.Collections;

public class WebviewActivity  extends AppCompatActivity {
    ActivityWebviewBinding binding;
    WebView webView;


    //@BindView(R.id.xic)    ImageView xic;
    private ActivityManager am = ActivityManager.getInstance();
    String i_url="",deviceId="";
    WebSettings settings;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);
        am.addActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        binding.setMain(this);
        webView = binding.webView;
        deviceId= Common.getMyDeviceId(this);


        i_url = getIntent().getStringExtra("board_name");

        if(i_url != null){
            i_url=getIntent().getStringExtra("board_name");
        }
        else
            i_url= "";

        Log.d("i_url",i_url);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        //banner2.loadAd(new AdRequest.Builder().addTestDevice("F225B75A37119EE77E3DEAB3DC23EB31").build());


        settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        if(Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            //쿠키 생성
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(webView,true);
        }
        if(!i_url.contains("search"))
            settings.setTextZoom(viewtextSize*15+55);

        webView.addJavascriptInterface(new WebviewJavainterface(),"Android");
        webView.setWebViewClient(new Viewmanager(this));
        webView.setWebChromeClient(new WebchromeClient(this, this));

        if(!i_url.isEmpty() && i_url.equals("search")) {
            webView.loadUrl(getString(R.string.bbs) + "wordsearch.php");
        }else if(!i_url.isEmpty() && i_url.equals("myword")){
            webView.loadUrl(getString(R.string.bbs) + "my_word_list.php?deviceId="+Common.getMyDeviceId(this));
        }else{
            webView.loadUrl(getString(R.string.board) + i_url+"&deviceId="+Common.getMyDeviceId(this));
        }
        bannerAd();

    }

    public void move_main(View view) {

        Intent i  = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        finish();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            if(webView.getUrl().endsWith("sca=A")){
                finish();
                overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
            }
            else   webView.goBack();
        }
        else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        }
    }
    public void move_home(View view){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        finish();
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }


    private class WebviewJavainterface {
        @JavascriptInterface
        public void save_history(String word, String cate) {

            SharedPreferences sharedPreferences;
            ArrayList<String> words = new ArrayList<String>();
            ArrayList<String> cates = new ArrayList<String>();
            sharedPreferences = getSharedPreferences("sch_words",MODE_PRIVATE);
            String saved_words =sharedPreferences.getString("word","");
            String saved_cates =sharedPreferences.getString("cate","");

            if(!saved_words.isEmpty() && !saved_words.equals("")){

                String [] temp_word = saved_words.split(",");
                String [] temp_cate = saved_cates.split(",");

                Collections.addAll(words,temp_word);
                Collections.addAll(cates,temp_cate);

                Log.d("words_2_1", TextUtils.join(",",words));
                Log.d("words_2_2", TextUtils.join(",",cates));

                Log.d("words_0",words.toString());
                if(words.contains(word)){
                    int index = words.indexOf(word);
                    words.remove(word);
                    cates.remove(index);
                }
                Log.d("words_2_3", TextUtils.join(",",words));
                Log.d("words_2_4", TextUtils.join(",",cates));

                if(words.size()>=10){
                    words.remove(0);
                    cates.remove(0);
                }
                Log.d("words_2_5", TextUtils.join(",",words));
                Log.d("words_2_6", TextUtils.join(",",cates));

                words.add(word);
                cates.add(cate);
                Log.d("words_2_7", TextUtils.join(",",words));
                Log.d("words_2_8", TextUtils.join(",",cates));

                Log.d("words_1",words.toString());



                sharedPreferences = getSharedPreferences("sch_words",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("word",TextUtils.join(",",words));
                editor.putString("cate",TextUtils.join(",",cates));
                editor.commit();

            }
            else{
                sharedPreferences = getSharedPreferences("sch_words",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("word",word);
                editor.putString("cate",cate);
                editor.commit();
            }
            settings.setTextZoom(viewtextSize*15+55);

        }

        @JavascriptInterface
        public void clicked_schtxt() {

            SharedPreferences sharedPreferences = getSharedPreferences("sch_words",MODE_PRIVATE);
            String saved_words =sharedPreferences.getString("word","");
            String saved_cates =sharedPreferences.getString("cate","");
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:display_words('"+saved_words+"','"+saved_cates+"');");
                }
            });
            settings.setTextZoom(100);


        }
    }
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

        adLoader.loadAd(new AdRequest.Builder().build());

    }

    @Override
    protected void onResume() {
        super.onResume();
        bannerAd();
    }
}
