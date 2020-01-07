package com.vocaescape.vocaescape;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebviewActivity  extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    @BindView(R.id.webView)    WebView webView;
    @BindView(R.id.adView_banner2)   AdView banner2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        String i_url = getIntent().getStringExtra("board_name");

        if(i_url != null){
            i_url=getIntent().getStringExtra("board_name");
        }
        else
            i_url= "";

        int i_notice = getIntent().getIntExtra("notice",0);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getString(R.string.test));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        banner2.loadAd(new AdRequest.Builder().build());

        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);


        webView.addJavascriptInterface(new WebviewJavainterface(),"Android");
        webView.setWebViewClient(new Viewmanager(this, mInterstitialAd));
        webView.setWebChromeClient(new WebchromeClient(this, this));

        if(i_url.isEmpty() && i_notice == 0)
        webView.loadUrl(getString(R.string.index));
        else if(i_notice==1)
            webView.loadUrl(getString(R.string.index));

        else {
            webView.loadUrl(getString(R.string.board) + i_url);
        }

    }
    @Override
    public void onBackPressed(){
        long tempTime = System.currentTimeMillis();
        if (webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }

    public void move_notice(View view) {
        Intent i  = new Intent(getApplicationContext(),WebviewActivity.class);
        i.putExtra("notice",1);
        startActivity(i);
    }

    private class WebviewJavainterface {
        @JavascriptInterface
        public void temp(String id, String table) {

        }
    }
}
