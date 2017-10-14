package com.andryyu.smack.ui.splash;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class WelcomeScrollView extends HorizontalScrollView {

	public WelcomeScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return true;
	}
}
