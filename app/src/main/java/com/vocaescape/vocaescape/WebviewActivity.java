package com.vocaescape.vocaescape;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.nio.channels.InterruptedByTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebviewActivity  extends AppCompatActivity {
    private int flg_ad =0;
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
        //banner2.loadAd(new AdRequest.Builder().addTestDevice("F225B75A37119EE77E3DEAB3DC23EB31").build());
        banner2.loadAd(new AdRequest.Builder().build());

        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);


        webView.addJavascriptInterface(new WebviewJavainterface(),"Android");
        webView.setWebViewClient(new Viewmanager(this));
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
    public void onBackPressed() {
        if (webView.canGoBack() && flg_ad < 9) {
            webView.goBack();
        }
        else if (webView.getUrl().contains("wr_id") && flg_ad == 9) {
                Intent i = new Intent(WebviewActivity.this, SplashadActivity.class);
                startActivity(i);
            }
        else {
            super.onBackPressed();
        }
        if (flg_ad < 9) {
            flg_ad++;
        } else {
            flg_ad = 0;
        }
    }
    public void move_home(View view){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
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
