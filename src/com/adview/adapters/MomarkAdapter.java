package com.adview.adapters;

import android.app.Activity;
//import android.graphics.Color;
import android.util.Log;


import com.donson.momark.AdManager;
import com.donson.momark.view.view.AdView;
import com.donson.momark.util.AdViewListener;


import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
//import com.adview.obj.Extra;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;


public class MomarkAdapter extends AdViewAdapter implements AdViewListener{
	AdView adView;
		
	public MomarkAdapter(AdViewLayout adViewLayout, Ration ration) {
		// TODO Auto-generated constructor stub

		super(adViewLayout, ration);
		String key=new String(ration.key);
		String key2=new String(ration.key2);
		
		AdManager.init(key2, key);
		adView = null;
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Momark");
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
		
	    adView = new AdView(activity, "adview");		
		adView.setAdViewListener(this);
	}

	@Override
	public void onConnectFailed(AdView paramAdView) {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Momark failure");
	    
		 paramAdView.setAdViewListener(null);

		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			 return;
		  }
		 adViewLayout.adViewManager.resetRollover_pri();
		  adViewLayout.rotateThreadedPri();
		
	}

	@Override
	public void onAdViewSwitchedAd(AdView paramAdView) {
		// TODO Auto-generated method stub
	
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Momark success");

		paramAdView.setAdViewListener(null);
		
		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			  return;
		  }

		  adViewLayout.adViewManager.resetRollover();
		  adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, paramAdView));
		  adViewLayout.rotateThreadedDelayed();
		
	}


}
