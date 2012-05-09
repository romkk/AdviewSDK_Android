package com.adview.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;


import com.fractalist.android.ads.ADView;
import com.fractalist.android.ads.AdStatusListener;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Extra;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;


public class FractalAdapter extends AdViewAdapter implements AdStatusListener{

	ADView view;
	
	public FractalAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Fractal");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
	 	}
	 	Extra extra = adViewLayout.extra;
		int bgColor = Color.rgb(extra.bgRed, extra.bgGreen, extra.bgBlue);
		//int fgColor = Color.rgb(extra.fgRed, extra.fgGreen, extra.fgBlue);
		
		Activity activity = adViewLayout.activityReference.get();
		if(activity == null) {
			return;
		}  	     
		// Instantiate an ad view and add it to the view

		ADView.setPublishId(ration.key);
		view=new ADView(activity);
		//view.setStateHander(hand);
		view.setFreshInterval(30);
		//view.setShowCloseButton(true);        
		view.setFadeImage(false);
		view.setBackgroundColor(bgColor);
		//view.setAdStatusListener(this);

		adViewLayout.adViewManager.resetRollover();
		adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, view));
		adViewLayout.rotateThreadedDelayed();
		
	}

	@Override
	public void onClick() {
		
	}

	@Override
	public void onFail() {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Fractal failure");

		view.setAdStatusListener(null);
		view = null;

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}
		adViewLayout.adViewManager.resetRollover_pri();
		adViewLayout.rotateThreadedPri();
	}

	@Override
	public void onReceiveAd() {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Fractal success");

		view.setAdStatusListener(null);
		
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}

		adViewLayout.adViewManager.resetRollover();
		//adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, view));
		adViewLayout.rotateThreadedDelayed();
	}

	@Override
	public void onRefreshAd() {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Fractal, onRefreshAd");
	}

	//@Override
	public void fullScreenAdClose(boolean arg0) {
		
	}

}
