package com.vocaescape.vocaescape;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuWebviewActivity extends AppCompatActivity {
    public int flg_ad =0;
    @BindView(R.id.menu_webView)    WebView webView;
    @BindView(R.id.adView_banner4)   AdView banner;
    //@BindView(R.id.xic)    ImageView xic;
    private ActivityManager am = ActivityManager.getInstance();
    String i_url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuwebview);
        am.addActivity(this);
        ButterKnife.bind(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        banner.loadAd(new AdRequest.Builder().build());

        Intent i = getIntent();
        String i_url = "";

        if(i!=null) {
            i_url = i.getStringExtra("url");
            Toast.makeText(this, i_url, Toast.LENGTH_SHORT).show();
        }
        else{
           // Toast.makeText(this, "else", Toast.LENGTH_SHORT).show();
        }

        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setTextZoom(100);

        webView.addJavascriptInterface(new WebviewJavainterface(),"Android");
        webView.setWebViewClient(new Viewmanager(this));
        webView.setWebChromeClient(new WebchromeClient(this, this));

        webView.loadUrl(i_url);

    }

    public void move_home(View view){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        finish();
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }

    private class WebviewJavainterface {
        @JavascriptInterface
        public void temp(String id, String table) {

        }
    }
}
