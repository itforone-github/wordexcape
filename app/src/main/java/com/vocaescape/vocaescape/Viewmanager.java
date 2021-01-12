package com.vocaescape.vocaescape;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;

import static com.vocaescape.vocaescape.setting.SettingActivity.VIEW_REFRESH;


class Viewmanager extends WebViewClient {

    private InterstitialAd mInterstitialAd;

    Activity viewActivity;
    Viewmanager(Activity viewActivity) {
        this.viewActivity = viewActivity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.contains("wr_id=")) {
                Log.d("tranoption","new");
                Intent i = new Intent(viewActivity.getApplicationContext(), WridActivity.class);
                i.putExtra("url",url);
                viewActivity.startActivityForResult(i,VIEW_REFRESH);
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
