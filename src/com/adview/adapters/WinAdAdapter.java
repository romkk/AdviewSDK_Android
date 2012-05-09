package com.adview.adapters;

import android.app.Activity;
import android.util.Log;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;

import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;


import com.winad.android.ads.AdView; 
//import com.winad.android.ads.AdManager; 
import com.winad.android.ads.ADListener; 
import com.adview.obj.Extra;
import android.graphics.Color;

public class WinAdAdapter extends AdViewAdapter implements ADListener {
	
	//private AdView adView=null;
	public WinAdAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into WinAD");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}
		//adViewLayout.removeAllViews();

		Activity activity = adViewLayout.activityReference.get();
		if(activity == null) {
			return;
		}
		Extra extra = adViewLayout.extra;
		int bgColor = Color.rgb(extra.bgRed, extra.bgGreen, extra.bgBlue);
		int fgColor = Color.rgb(extra.fgRed, extra.fgGreen, extra.fgBlue); 

		//AdManager.setPublisherId(ration.key);
		//AdManager.setInTestMode(true);

		boolean isTestmode=false;
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			isTestmode = true;
		
		AdView adView = new AdView(activity,null, 0, 100,bgColor,fgColor,300,isTestmode);
		adView.setPublishId(ration.key);
		//adView.setBackgroundColor(bgColor);
		//adView.setTextColor(fgColor);
		//
		adView.setListener(this);
		//adView.setRequestInterval(0);
		adView.requestFreshAd();

		//adViewLayout.adViewManager.resetRollover();
		//adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		//adViewLayout.rotateThreadedDelayed();
	}

	@Override
	public void onReceiveAd(AdView adView) {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "WinAD success");

		adView.setListener(null);
		
		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			  return;
		  }
		 // adViewLayout.removeAllViews();

		  adViewLayout.adViewManager.resetRollover();
		  adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		  adViewLayout.rotateThreadedDelayed();
		
		
	}

	@Override
	public void onFailedToReceiveAd(AdView adView) {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "WinAD failure");
	    
		  adView.setListener(null);

		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			 return;
		  } 
		 adViewLayout.adViewManager.resetRollover_pri();
		 adViewLayout.rotateThreadedPri();
		
	}

	//@Override
	public void onNewAd() {
	if(AdViewTargeting.getRunMode()==RunMode.TEST)
		Log.d(AdViewUtil.ADVIEW, "onNewAd");
		
	}

	
}
