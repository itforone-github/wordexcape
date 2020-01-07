package com.vocaescape.vocaescape;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

class Viewmanager extends WebViewClient {
    private InterstitialAd mInterstitialAd;
    WebviewActivity webviewActivity;
    Viewmanager(WebviewActivity webviewActivity) {
        this.webviewActivity = webviewActivity;
    }
    Viewmanager(WebviewActivity webviewActivity , InterstitialAd mInterstitialAd){
        this.webviewActivity = webviewActivity;
        this.mInterstitialAd = mInterstitialAd;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if(url.contains("wr_id")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
                Toast.makeText(webviewActivity.getApplicationContext(), "로딩중입니다.", Toast.LENGTH_LONG).show();
                return true;
            }

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    // Load the next interstitial.
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            });
        }
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }
}
