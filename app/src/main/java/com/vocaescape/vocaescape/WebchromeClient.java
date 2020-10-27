package com.vocaescape.vocaescape;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

class WebchromeClient extends WebChromeClient {
    Activity activity;
    WebviewActivity webviewActivity;
    WridActivity wridActivity;
    MenuWebviewActivity menuWebviewActivity;

    WebchromeClient(Activity activity, WebviewActivity webviewActivity){

        this.activity = activity;
        this.webviewActivity = webviewActivity;

    }
    WebchromeClient(Activity activity, WridActivity wridActivity){

        this.activity = activity;
        this.wridActivity = wridActivity;

    }
    WebchromeClient(Activity activity, MenuWebviewActivity menuWebviewActivity){

        this.activity = activity;
        this.menuWebviewActivity = menuWebviewActivity;

    }

    WebchromeClient(){}

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        new AlertDialog.Builder(view.getContext())
                .setTitle("title")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        })
                .setCancelable(false)
                .create()
                .show();
        return true;
    }
}
