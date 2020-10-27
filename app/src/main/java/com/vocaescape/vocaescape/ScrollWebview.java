package com.vocaescape.vocaescape;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class ScrollWebview extends WebView {
    public ScrollWebview(Context context) {
        super(context);
    }

    public ScrollWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWebview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }

}
