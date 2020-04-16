package com.vocaescape.vocaescape;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static android.content.Context.LAUNCHER_APPS_SERVICE;
import static android.content.Context.MODE_PRIVATE;

class Viewmanager extends WebViewClient {
    private InterstitialAd mInterstitialAd;
    Activity viewActivity;
    Viewmanager(Activity viewActivity) {
        this.viewActivity = viewActivity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.contains("wr_id=")) {
                Intent i = new Intent(viewActivity.getApplicationContext(),wridActivity.class);
                i.putExtra("url",url);
                viewActivity.startActivity(i);
                viewActivity.overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
            }
            else {
                view.loadUrl(url);
            }
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {

        super.onPageFinished(view, url);
    }
}
