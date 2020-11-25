package com.vocaescape.vocaescape;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.vocaescape.vocaescape.setting.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vocaescape.vocaescape.setting.SettingActivity.VIEW_REFRESH;
import static com.vocaescape.vocaescape.setting.SettingTextActivity.viewtextSize;

public class WebviewActivity  extends AppCompatActivity {

    @BindView(R.id.webView)    WebView webView;
    @BindView(R.id.adView_banner2)   AdView banner2;
    //@BindView(R.id.xic)    ImageView xic;
    private ActivityManager am = ActivityManager.getInstance();
    String i_url="";
    WebSettings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        am.addActivity(this);
        ButterKnife.bind(this);

        i_url = getIntent().getStringExtra("board_name");

        if(i_url != null){
            i_url=getIntent().getStringExtra("board_name");
        }
        else
            i_url= "";


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        //banner2.loadAd(new AdRequest.Builder().addTestDevice("F225B75A37119EE77E3DEAB3DC23EB31").build());
        banner2.loadAd(new AdRequest.Builder().build());

        settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setTextZoom(viewtextSize*20+100);

        webView.addJavascriptInterface(new WebviewJavainterface(),"Android");
        webView.setWebViewClient(new Viewmanager(this));
        webView.setWebChromeClient(new WebchromeClient(this, this));


        if(!i_url.isEmpty() && i_url.equals("search")) {
            webView.loadUrl(getString(R.string.bbs) + "wordsearch.php");
        }
        else{
            webView.loadUrl(getString(R.string.board) + i_url);
        }
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
                settings.setTextZoom(viewtextSize*20+100);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            if(webView.getUrl().equals(getString(R.string.board) + i_url)){
                finish();
                overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
            }
           else if(webView.getUrl().contains("sca=")) {
                        webView.loadUrl(getString(R.string.board) + i_url);
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

    private class WebviewJavainterface {
        @JavascriptInterface
        public void temp(String id, String table) {

        }
    }
}
