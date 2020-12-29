package com.vocaescape.vocaescape;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.vocaescape.vocaescape.util.ActivityManager;

import org.w3c.dom.Text;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.StringJoiner;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vocaescape.vocaescape.MenuWebviewActivity.VIEW_REFRESH;
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

        if(!i_url.contains("search"))
            settings.setTextZoom(viewtextSize*15+55);

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
                settings.setTextZoom(viewtextSize*15+55);
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

/*    public void move_setting(View view) {
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
    }*/

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

        }
    }
}
