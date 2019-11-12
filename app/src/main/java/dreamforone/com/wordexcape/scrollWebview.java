package dreamforone.com.wordexcape;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class scrollWebview extends WebView {
    public scrollWebview(Context context) {
        super(context);
    }

    public scrollWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public scrollWebview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }

}
