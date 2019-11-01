package dreamforone.com.wordexcape;

import android.app.Activity;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class Viewmanager extends WebViewClient {

    MainActivity mainActivity;
    Viewmanager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    Viewmanager(){}

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
