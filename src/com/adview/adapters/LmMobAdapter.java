package com.adview.adapters;

import android.app.Activity;
import android.util.Log;


import com.lmmob.ad.sdk.LmMobAdRequestListener;
import com.lmmob.ad.sdk.LmMobAdView;
import com.lmmob.ad.sdk.LmMobEngine;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;


public class LmMobAdapter extends AdViewAdapter implements LmMobAdRequestListener{

	private LmMobAdView adView;
	
	public LmMobAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into LmMob");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
	 	}
		
		Activity activity = adViewLayout.activityReference.get();
		if(activity == null) {
			return;
		}
		
		// Instantiate an ad view and add it to the view

		LmMobEngine.init(ration.key);
		adView = new LmMobAdView(activity);
		adView.setAdRequestListener(this);
		adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
	   
	}

	@Override
	public void adRecieveFailure() {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "LmMob failure");
	    
		adView.setAdRequestListener(null);

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}
		adViewLayout.adViewManager.resetRollover_pri();
		adViewLayout.rotateThreadedPri();
		
	}

	@Override
	public void adRecieveSuccess() {
		// TODO Auto-generated method stub
	
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "LmMob success");

		adView.setAdRequestListener(null);
		
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}

		adViewLayout.adViewManager.resetRollover();
		//adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		adViewLayout.rotateThreadedDelayed();
		
	}

	public int setAdSpecWidth() {
		// TODO Auto-generated method stub
		return 0;
	}	

}
