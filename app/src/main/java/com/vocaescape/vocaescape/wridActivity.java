package com.vocaescape.vocaescape;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class wridActivity extends AppCompatActivity {
    private ActivityManager am = ActivityManager.getInstance();
    @BindView(R.id.webView)  WebView webView;
    @BindView(R.id.adView_banner2)
    AdView banner2;
    int flg_ad =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        am.addActivity(this);
        ButterKnife.bind(this);
        banner2.loadAd(new AdRequest.Builder().build());


        SharedPreferences sf = getSharedPreferences("pf_flg",MODE_PRIVATE);
        flg_ad = sf.getInt("flg",0);
        int set_value = flg_ad+1;
        if(set_value==10)  set_value = 0;

        SharedPreferences sharedPreferences = getSharedPreferences("pf_flg",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("flg", set_value);
        editor.commit();

        String url = getIntent().getStringExtra("url");


        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.addJavascriptInterface(new WebviewJavainterface(),"Android");
        webView.setWebViewClient(new Viewmanager(this));
        webView.setWebChromeClient(new WebchromeClient(this, this));

        if(url.isEmpty() == false)
        webView.loadUrl(url);

        else {
            finish();
            overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        }
    }

    @Override
    public void onBackPressed() {
        if(flg_ad==9) {
            Intent i = new Intent(this, SplashadActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
            finish();
            overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
        }
        else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        }
    }

    private class WebviewJavainterface {
        @JavascriptInterface
        public void temp(String id, String table) {

        }
    }

}
