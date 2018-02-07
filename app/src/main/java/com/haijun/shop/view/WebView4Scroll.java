package com.haijun.shop.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebView;

/**
 * Created by haijun on 2018/2/8.
 */

public class WebView4Scroll extends WebView {

    private SwipeRefreshLayout swipeRefreshLayout;

    public WebView4Scroll(Context context, SwipeRefreshLayout swipeRefreshLayout){
        super(context);
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.getScrollY() == 0){
            swipeRefreshLayout.setEnabled(true);
        }else {
            swipeRefreshLayout.setEnabled(false);
        }
    }
}