package com.adview.adapters;

import android.app.Activity;
//import android.graphics.Color;
import android.util.Log;


import com.umengAd.android.AdView;
import com.umengAd.controller.UmengAdListener;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;


public class UmengAdapter extends AdViewAdapter implements UmengAdListener{

	public UmengAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Umeng");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
	 	}
	 	//Extra extra = adViewLayout.extra;
		//int bgColor = Color.rgb(extra.bgRed, extra.bgGreen, extra.bgBlue);
		//int fgColor = Color.rgb(extra.fgRed, extra.fgGreen, extra.fgBlue);
		Activity activity = adViewLayout.activityReference.get();
		if(activity == null) {
			return;
		}  	     
		// Instantiate an ad view and add it to the view

		if(adViewLayout.umengView==null)
		{
			AdView adView = new AdView(activity);
			//adView.adInit("10013", "10050");
			//adView.adInit("fab1c58b2e9d5a7f", "c0bece60ff61ed90");
			adView.adInit(ration.key, ration.key2);
			//adView.setTextColor(fgColor);
			//adView.setBannerColor(bgColor);
			
			adViewLayout.umengView = adView;
		}
		
		//adView.setUmengAdListener(this);
		adViewLayout.adViewManager.resetRollover();
		adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adViewLayout.umengView));
		adViewLayout.rotateThreadedDelayed();

	}

	@Override
	public void onRequestFail(AdView paramAdView) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Umeng failure");

		paramAdView.setUmengAdListener(null);

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}
		adViewLayout.adViewManager.resetRollover_pri();
		adViewLayout.rotateThreadedPri();
	}

	@Override
	public void onRequestSuccess(AdView paramAdView) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Umeng success");

		paramAdView.setUmengAdListener(null);
		
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}

		adViewLayout.adViewManager.resetRollover();
		adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, paramAdView));
		adViewLayout.rotateThreadedDelayed();
	}

}
