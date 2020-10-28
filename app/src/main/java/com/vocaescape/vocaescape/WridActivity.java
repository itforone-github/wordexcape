package com.vocaescape.vocaescape;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.vocaescape.vocaescape.setting.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vocaescape.vocaescape.setting.SettingActivity.VIEW_REFRESH;
import static com.vocaescape.vocaescape.setting.SettingTextActivity.viewtextSize;

public class WridActivity extends AppCompatActivity {
    private ActivityManager am = ActivityManager.getInstance();
    @BindView(R.id.webView)  WebView webView;
    @BindView(R.id.adView_banner2)
    AdView banner2;
    int flg_ad =0;
    WebSettings settings;
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


        settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.addJavascriptInterface(new WebviewJavainterface(),"Android");
        webView.setWebViewClient(new Viewmanager(this));
        webView.setWebChromeClient(new WebchromeClient(this, this));
        settings.setTextZoom(viewtextSize*20+100);

        if(url.isEmpty() == false)
        webView.loadUrl(url);

        else {
            finish();
            overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        }
    }

    @Override
    public void onBackPressed() {
        if(flg_ad==4) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case VIEW_REFRESH:
                settings.setTextZoom(viewtextSize*20+100);
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

    public void move_home(View view){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        finish();
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }


}
