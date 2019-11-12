package dreamforone.com.wordexcape;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

class Viewmanager extends WebViewClient {
    private InterstitialAd mInterstitialAd;
    MainActivity mainActivity;
    Viewmanager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    Viewmanager(MainActivity mainActivity , InterstitialAd mInterstitialAd){
        this.mainActivity = mainActivity;
        this.mInterstitialAd = mInterstitialAd;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if(url.contains("wr_id")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
                Toast.makeText(mainActivity.getApplicationContext(), "로딩중입니다.", Toast.LENGTH_LONG).show();
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
        if(!url.equals(mainActivity.getString(R.string.index))) {
            FrameLayout nativead =  (FrameLayout)mainActivity.findViewById(R.id.fl_adpalce);
            nativead.setVisibility(View.GONE);
        }
        else{
            FrameLayout nativead =  (FrameLayout)mainActivity.findViewById(R.id.fl_adpalce);
            nativead.setVisibility(View.VISIBLE);
        }
    }
}
