package com.vocaescape.vocaescape;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.interstitial.InterstitialAd;
//import com.google.android.gms.ads.InterstitialAd;

public class SplashadActivity extends Activity {
    private InterstitialAd mInterstitialAd;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashad);
        mInterstitialAd = new InterstitialAd() {
            @NonNull
            @Override
            public String getAdUnitId() {
                return null;
            }

            @Override
            public void show(@NonNull Activity activity) {

            }

            @Override
            public void setFullScreenContentCallback(@Nullable FullScreenContentCallback fullScreenContentCallback) {

            }

            @Nullable
            @Override
            public FullScreenContentCallback getFullScreenContentCallback() {
                return null;
            }

            @Override
            public void setImmersiveMode(boolean b) {

            }

            @NonNull
            @Override
            public ResponseInfo getResponseInfo() {
                return null;
            }

            @Override
            public void setOnPaidEventListener(@Nullable OnPaidEventListener onPaidEventListener) {

            }

            @Nullable
            @Override
            public OnPaidEventListener getOnPaidEventListener() {
                return null;
            }
        };
        //mInterstitialAd.setAdUnitId(getString(R.string.full));
        //mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("F225B75A37119EE77E3DEAB3DC23EB31").build());
        //mInterstitialAd.loadAd(new AdRequest.Builder().build());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.");
//            Toast.makeText(getApplicationContext(), "로딩중입니다.", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                // Load the next interstitial.
//                //F225B75A37119EE77E3DEAB3DC23EB31 단어탈출아이디
//                //F325488412086766B1E28A777DE121D5 테스트아이디
//                //mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("F225B75A37119EE77E3DEAB3DC23EB31").build());
//                mInterstitialAd.loadAd(new AdRequest.Builder().build());
//            }
//        });
    }
}
