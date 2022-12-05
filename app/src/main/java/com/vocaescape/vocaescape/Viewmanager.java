package com.vocaescape.vocaescape;

import static com.vocaescape.vocaescape.setting.SettingActivity.VIEW_REFRESH;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import com.vocaescape.vocaescape.util.Common;


class Viewmanager extends WebViewClient {

//    private InterstitialAd mInterstitialAd;

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
                Log.d("url",url);
                view.loadUrl(url);
            }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onPageFinished(WebView view, String url) {

        super.onPageFinished(view, url);
        view.loadUrl("javascript:initDeviceId('"+ Common.getMyDeviceId(viewActivity.getApplicationContext()) +"')");
        CookieManager.getInstance().flush();
    }
}
