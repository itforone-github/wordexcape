package com.vocaescape.vocaescape;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
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
import com.vocaescape.vocaescape.databinding.ActivityMenuwebviewBinding;
import com.vocaescape.vocaescape.setting.SettingTextActivity;
import com.vocaescape.vocaescape.util.ActivityManager;
import com.vocaescape.vocaescape.util.Common;

import mc.kr.cross.sdk.CrossSdk;

public class MenuWebviewActivity extends AppCompatActivity {
    ActivityMenuwebviewBinding binding;
    public int flg_ad =0;



    //@BindView(R.id.xic)    ImageView xic;
    private ActivityManager am = ActivityManager.getInstance();
    String i_url="";
    public static final int VIEW_REFRESH =4;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuwebview);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menuwebview);
        binding.setMain(this);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        bannerAd();


        Intent i = getIntent();
        i_url = "";

        if(i!=null){
            i_url = i.getStringExtra("url");
        //    Toast.makeText(this, i_url, Toast.LENGTH_SHORT).show();
        }

        else{
           // Toast.makeText(this, "else", Toast.LENGTH_SHORT).show();
        }

        WebSettings settings = binding.menuWebView.getSettings();
        if(Build.VERSION.SDK_INT >= 21) {
            binding.menuWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            //쿠키 생성
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(binding.menuWebView,true);
        }
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setTextZoom(100);

        binding.menuWebView.addJavascriptInterface(new WebviewJavainterface(),"Android");
        binding.menuWebView.setWebViewClient(new Viewmanager(this));
        binding.menuWebView.setWebChromeClient(new WebchromeClient(this, this));
        if(0 < i_url.indexOf("menu")){
            i_url+="?mb_id="+Common.getPref(getApplicationContext(),"mb_id","");
        }else{

        }

        binding.menuWebView.loadUrl(i_url);

    }

    /*public void move_home(View view){

        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        finish();
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);

    }*/


    @Override
    public void onBackPressed() {

        Log.d("tranoption","back");
        finish();
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);

    }

    private class WebviewJavainterface {
        @JavascriptInterface
        public void move_txtsize() {
            Intent i = new Intent(MenuWebviewActivity.this, SettingTextActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
        }
        @JavascriptInterface
        public void move_detailmenu(String url) {
            Intent i = new Intent(MenuWebviewActivity.this, MenuWebviewActivity.class);
            i.putExtra("url",url);
            Log.d("tranoption","load");
            launcher.launch(i);
            //startActivity(i);

            overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
        }
        @JavascriptInterface
        public void setSession(String mb_id,String mb_name){
            Common.savePref(getApplicationContext(),"mb_id",mb_id);
            Common.savePref(getApplicationContext(),"mb_name",mb_name);
            if(!mb_id.equals("")) {
                Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            }
            Intent i = new Intent(MenuWebviewActivity.this, MenuWebviewActivity.class);
            i.putExtra("mb_id",mb_id);
            setResult(RESULT_OK,i);
            finish();
        }
        //멀티앱크로스 회원가입 여부 확인하기
        @JavascriptInterface
        public void setSdkJoin(String mb_id){
            try {
                Log.d("sdk",mb_id);
                CrossSdk.INSTANCE.join(mb_id);
            }catch (Exception e){
                Toast.makeText(MenuWebviewActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == RESULT_OK)
                    {
                        String i_url2 = binding.menuWebView.getUrl().substring(0,binding.menuWebView.getUrl().indexOf("?"));
                        Intent data = result.getData();
                        String mb_id = data.getStringExtra("mb_id");
                        finish();

                    }
                }
            });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            binding.menuWebView.reload();
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

        adLoader.loadAd(new AdRequest.Builder().build());;
    }
}
