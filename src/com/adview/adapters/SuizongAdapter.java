package com.adview.adapters;

import android.app.Activity;
//import android.graphics.Color;
import android.util.Log;


import com.suizong.mobplate.ads.AdRequest;
import com.suizong.mobplate.ads.AdSize;
import com.suizong.mobplate.ads.AdView;
import com.suizong.mobplate.ads.AdListener;
import com.suizong.mobplate.ads.AdRequestError;
import com.suizong.mobplate.ads.Ad;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
//import com.adview.obj.Extra;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;

public class SuizongAdapter extends AdViewAdapter implements AdListener{

	private AdView adView;
	
	public SuizongAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Suizong");
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
		
	    adView = new AdView(activity, AdSize.BANNER, ration.key);
		adView.setAdListener(this);
		
	  	 AdRequest adRequest = new AdRequest();
		  adRequest.setRefreshTime(-1L);
		boolean testmode;
	    if(AdViewTargeting.getRunMode()==RunMode.TEST)
	    	testmode = true;
	    else if(AdViewTargeting.getRunMode()==RunMode.NORMAL)
	    	testmode = false;
	    else{
	    	testmode = false;
	    }
		
		adRequest.setTesting(testmode); 
		adView.loadAd(adRequest);

	//adViewLayout.addView(adView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
	
	    //adViewLayout.adViewManager.resetRollover();
	    //adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
	    //adViewLayout.rotateThreadedDelayed();		
	}

	  public void onReceiveAd(Ad paramAd)
	  {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Suizong success");

		adView.setAdListener(null);
		
		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			  return;
		  }

		  adViewLayout.adViewManager.resetRollover();
		  adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		  adViewLayout.rotateThreadedDelayed();
		  
	  }

	  public void onFailedToReceiveAd(Ad paramAd, AdRequestError paramAdRequestError)
	  {
		  if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Suizong failure");
	    
		  adView.setAdListener(null);

		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			 return;
		  }
		 adViewLayout.adViewManager.resetRollover_pri();
		  adViewLayout.rotateThreadedPri();
	  }
	  

	  public void onPresentScreen(Ad paramAd)
	  {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "onPresentScreen");
	  }

	  public void onDismissScreen(Ad paramAd)
	  {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "onDismissScreen");
	  }

	  public void onLeaveApplication(Ad paramAd)
	  {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "onLeaveApplication");
	  }

}
