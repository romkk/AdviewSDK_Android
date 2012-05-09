package com.adview.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;


import cn.domob.android.ads.DomobAdListener;
import cn.domob.android.ads.DomobAdManager;
import cn.domob.android.ads.DomobAdView;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Extra;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;


public class DomobAdapter extends AdViewAdapter implements DomobAdListener{

	public DomobAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Domob");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
	 	}
	 	Extra extra = adViewLayout.extra;
	    int bgColor = Color.rgb(extra.bgRed, extra.bgGreen, extra.bgBlue);
	    int fgColor = Color.rgb(extra.fgRed, extra.fgGreen, extra.fgBlue);
	    Activity activity = adViewLayout.activityReference.get();
		  if(activity == null) {
			  return;
		  }  	     
	    // Instantiate an ad view and add it to the view
		
	    DomobAdView ad=new DomobAdView(activity);
	    DomobAdManager.setPublisherId(ration.key);
/*	    if(AdViewTargeting.getRunMode()==RunMode.TEST)
	    	DomobAdManager.setIsTestMode(true);
	    else if(AdViewTargeting.getRunMode()==RunMode.NORMAL)
	    	DomobAdManager.setIsTestMode(false);
	    else{
	    	DomobAdManager.setIsTestMode(false);
	    }*/
	    ad.setAdListener(this);
	    ad.setBackgroundColor(bgColor);
	    ad.setPrimaryTextColor(fgColor);
	
	    ad.requestFreshAd() ;
	    ad.setRequestInterval(0);
		
	   
	}

	@Override
	public void onFailedToReceiveFreshAd(DomobAdView arg0) {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Domob failure");
	    
		  arg0.setAdListener(null);

		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			 return;
		  }
		 adViewLayout.adViewManager.resetRollover_pri();
		  adViewLayout.rotateThreadedPri();
		
	}

	@Override
	public void onReceivedFreshAd(DomobAdView arg0) {
		// TODO Auto-generated method stub
	
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Domob success");

		arg0.setAdListener(null);
		
		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			  return;
		  }

		  adViewLayout.adViewManager.resetRollover();
		  adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, arg0));
		  adViewLayout.rotateThreadedDelayed();
		
	}

	@Override
	public void onLandingPageClose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLandingPageOpening() {
		// TODO Auto-generated method stub
		
	}

	
		
	

}
