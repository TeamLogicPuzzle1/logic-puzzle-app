/*
* */
package com.cookandroid.test_ui.mainPage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;


public class CustomViewPager extends ViewPager {
    private boolean isPagingEnabled = false;    // 슬라이드 기능 비활성화

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 슬라이드 기능을 막기 위해 터치 이벤트 사용
        return isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // 슬라이드 방지
        return isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean enabled) {
        this.isPagingEnabled = enabled;
    }
}
