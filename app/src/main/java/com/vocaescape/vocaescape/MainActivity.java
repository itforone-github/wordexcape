package com.vocaescape.vocaescape;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.vocaescape.vocaescape.util.ActivityManager;
import static com.vocaescape.vocaescape.setting.SettingTextActivity.viewtextSize;
import com.vocaescape.vocaescape.util.Enddialog;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    //@BindView(R.id.webView)   WebView webView;
    @BindView(R.id.adView_banner)   AdView banner;
    @BindView(R.id.fl_adplace)    FrameLayout frameLayout;
   // @BindView(R.id.refreshlayout)   SwipeRefreshLayout refreshlayout;
//    @BindView(R.id.fullbt)   Button fulladbt;
    private long backPrssedTime = 0;
//    private InterstitialAd mInterstitialAd;
    private UnifiedNativeAd nativeAd,nativeAd2;
    private ActivityManager am = ActivityManager.getInstance();
    private Enddialog mEndDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        am.addActivity(this);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences("pf_flg",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("flg", 0);
        editor.commit();

        SharedPreferences pref_txtsize = getSharedPreferences("viewtxtsize",MODE_PRIVATE);
        viewtextSize = pref_txtsize.getInt("value",0);


      /*  Intent splash = new Intent(MainActivity.this,SplashActivity.class);
        startActivity(splash);*/

        //애드몹
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        String androidId =  Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = MD5(androidId).toUpperCase();
        //Toast.makeText(getApplicationContext(),deviceId,Toast.LENGTH_LONG).show();
/*
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.clearCache(true);
                webView.reload();
                refreshlayout.setRefreshing(false);
            }
        });

        refreshlayout.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(webView.getScrollY() == 0){
                    refreshlayout.setEnabled(true);
                }
                else{
                    refreshlayout.setEnabled(false);
                }
            }
        });*/

      /*  mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getString(R.string.test));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.test));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //버튼 이벤트시 광고 노출
        fulladbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                    Toast.makeText(getApplicationContext(),"로딩중입니다.",Toast.LENGTH_LONG).show();
                }
            }
        });


        .setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });*/

        //배너 광고 로드
        //banner.loadAd(new AdRequest.Builder().addTestDevice("F225B75A37119EE77E3DEAB3DC23EB31").build());
        banner.loadAd(new AdRequest.Builder().build());

        MobileAds.initialize(this,getString(R.string.app_id));
        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.nativead_test));

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                // You must call destroy on old ads when you are done with them,
                // otherwise you will have a memory leak.
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                        .inflate(R.layout.ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);

            }
        });

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(MainActivity.this, "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();


        //adLoader.loadAd(new AdRequest.Builder().addTestDevice("F225B75A37119EE77E3DEAB3DC23EB31").build());
        adLoader.loadAd(new AdRequest.Builder().build());

/*        AdLoader.Builder builder2 = new AdLoader.Builder(this, getString(R.string.nativead_test));

        builder2.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                Log.d("init_onunified","true");
                // You must call destroy on old ads when you are done with them,
                // otherwise you will have a memory leak.
                if (nativeAd2 != null) {
                    nativeAd2.destroy();
                }
                nativeAd2 = unifiedNativeAd;
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                        .inflate(R.layout.ad_unified_dialog, null);
                mEndDialog.populateUnifiedNativeAdView(unifiedNativeAd, adView);

            }
        });

        AdLoader adLoader2 = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(MainActivity.this, "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();


        //adLoader.loadAd(new AdRequest.Builder().addTestDevice("F225B75A37119EE77E3DEAB3DC23EB31").build());
        adLoader2.loadAd(new AdRequest.Builder().build());*/

        mEndDialog = new Enddialog(MainActivity.this);
        mEndDialog.setCancelable(true);





       //exit_setnativead();

    }

    @Override
    public void onBackPressed(){

            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - backPrssedTime;

/*            if(nativeAd2!=null) {
//                mEndDialog.show();
//                /*Display display = getWindowManager().getDefaultDisplay();
//                Point size = new Point();
//                display.getSize(size);
//                Window window = mEndDialog.getWindow();
//                int x = (int) (size.x * 0.8f);
//                int y = (int) (size.y * 0.6f);
//                window.setLayout(x, y);

            }
            else{

                if (0 <= intervalTime && 2000 >= intervalTime){
                    Intent i = new Intent(MainActivity.this, SplashEndActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
                    finish();
                    am.finishAllActivity();
                } else {
                    backPrssedTime = tempTime;
                    Toast.makeText(getApplicationContext(), "한번 더 뒤로가기 누를시 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
                }

            }*/


        mEndDialog.show();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Window window = mEndDialog.getWindow();
        int x = (int) (size.x * 0.8f);
        int y = (int) (size.y * 0.6f);
        window.setLayout(x, y);
        mEndDialog.close_btN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEndDialog.dismiss();
            }
        });
        mEndDialog.close_btY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEndDialog.dismiss();
                Intent i = new Intent(MainActivity.this, SplashEndActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
                finish();
                am.finishAllActivity();
            }
        });

        }

    public void click_dialogN_main(View view){
     //   Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_LONG).show();
        mEndDialog.dismiss();
    }

    public void click_dialogY_main(View view){
        //    Toast.makeText(mContext.getApplicationContext(),"test2",Toast.LENGTH_LONG).show();
        mEndDialog.dismiss();
        Intent i = new Intent(MainActivity.this, SplashEndActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
        finish();
        am.finishAllActivity();

    }

  /*  public void exit_setnativead(){

        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.nativead_test));

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                // You must call destroy on old ads when you are done with them,
                // otherwise you will have a memory leak.
                if (nativeAd2 != null) {
                    nativeAd2.destroy();
                }
                nativeAd2 = unifiedNativeAd;

                mEndDialog.populateUnifiedNativeAdView(unifiedNativeAd);
                //frameLayout.removeAllViews();

            }
        });

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(MainActivity.this, "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();

        //adLoader.loadAd(new AdRequest.Builder().addTestDevice("F225B75A37119EE77E3DEAB3DC23EB31").build());
        adLoader.loadAd(new AdRequest.Builder().build());

    }*/

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }


    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);
        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        //adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        //adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        //adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        // The headline is guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

     /*   if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }*/

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
/*
        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }*/

     /*   if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }
*/
        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd);
    }

    /*public void move_notice(View view) {
        Intent i  = new Intent(getApplicationContext(), MenuActivity.class);
        //i.putExtra("notice",1);
        startActivity(i);
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }*/

    public void move_event(View view){

        String board_url = "";
        Intent i;
        switch (view.getId()) {
            case R.id.menu_bt:
                i  = new Intent(getApplicationContext(), MenuWebviewActivity.class);
                i.putExtra("url",getString(R.string.all_menu));
                break;
            case R.id.search_bt:
                i  = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", "search");
                break;
            case R.id.bt_word1:
                board_url = "verb";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word2:
                board_url = "noun";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word3:
                board_url = "adjective";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word4:
                board_url = "adverb";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word5:
                board_url = "auxiliary_verb";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word6:
                board_url = "pronoun";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word7:
                board_url = "preposition";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            case R.id.bt_word8:
                board_url = "conjunction";
                i = new Intent(getApplicationContext(), WebviewActivity.class);
                i.putExtra("board_name", board_url);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        startActivity(i);
        overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
    }
}
