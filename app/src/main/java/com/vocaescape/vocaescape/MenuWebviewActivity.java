package com.vocaescape.vocaescape;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.vocaescape.vocaescape.setting.SettingTextActivity;
import com.vocaescape.vocaescape.util.ActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuWebviewActivity extends AppCompatActivity {
    public int flg_ad =0;
    @BindView(R.id.menu_webView)    WebView webView;
    @BindView(R.id.adView_banner4)   AdView banner;
    //@BindView(R.id.xic)    ImageView xic;
    private ActivityManager am = ActivityManager.getInstance();
    String i_url="";
    public static final int VIEW_REFRESH =4;
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
        i_url = "";

        if(i!=null) {
            i_url = i.getStringExtra("url");
        //    Toast.makeText(this, i_url, Toast.LENGTH_SHORT).show();
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
        WebBackForwardList list = null;
        String backurl="";
        try{

            list = webView.copyBackForwardList();
            if (list.getSize() >1){
                backurl = list.getItemAtIndex(list.getCurrentIndex() - 1).getUrl();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if(!backurl.isEmpty() && (backurl.contains(getString(R.string.all_menu)) || backurl.contains(getString(R.string.setting)))){
          if(webView.canGoBack())
              webView.goBack();
          else {
              finish();
              overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
          }
        }
        else {
            finish();
            overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
        }
    }

    private class WebviewJavainterface {
        @JavascriptInterface
        public void move_txtsize() {
            Intent i = new Intent(MenuWebviewActivity.this, SettingTextActivity.class);
            startActivityForResult(i,VIEW_REFRESH);
            overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
        }
    }
}
