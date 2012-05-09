package com.adview.adapters;

import net.youmi.android.AdManager;
import net.youmi.android.AdView;
import net.youmi.android.AdViewListener;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;


import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Extra;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;


public class YoumiAdapter extends AdViewAdapter implements AdViewListener{
	
	public YoumiAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		String key=new String(ration.key);
		String key2=new String(ration.key2);
		Extra extra = adViewLayout.extra;
		int internal=extra.cycleTime;
		Activity activity = adViewLayout.activityReference.get();
		  if(activity == null) {
			  return;
		  }
		if(AdViewTargeting.getRunMode()==RunMode.TEST){
			if(adViewLayout.adViewManager.getYoumiInit()){
				AdManager.init(activity, key, key2, internal, true);
				AdManager.disableUpdateApp();
				adViewLayout.adViewManager.setYoumiInit(false);
			}
		}
		else{
			if(adViewLayout.adViewManager.getYoumiInit()){
				AdManager.init(activity, key, key2, internal, false);
				AdManager.disableUpdateApp();
				adViewLayout.adViewManager.setYoumiInit(false);
			}
		}
	}
	
	 	public void handle() {
	 		if(AdViewTargeting.getRunMode()==RunMode.TEST)
	 			Log.d(AdViewUtil.ADVIEW, "Into Youmi");
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
	    AdView adView=new AdView(activity,bgColor, fgColor,255);  
	    //adView.setAdViewListener(this);
	   
	    adViewLayout.adViewManager.resetRollover();
	    adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
	    adViewLayout.rotateThreadedDelayed();

	}

	@Override
	public void onAdViewSwitchedAd(AdView adView)
	{
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
	 		Log.d(AdViewUtil.ADVIEW, "onAdViewSwitchedAd");

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			  return;
		  }

		  adViewLayout.adViewManager.resetRollover();
		  //adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		  adViewLayout.rotateThreadedDelayed();
	}

	@Override
	public void onConnectFailed(AdView adView)
	{
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
	 		Log.d(AdViewUtil.ADVIEW, "onConnectFailed");

		adView.setAdViewListener(null);
	
		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			 return;
		  }
		  
		  adViewLayout.adViewManager.resetRollover_pri();
		  adViewLayout.rotateThreadedPri();
	}
	
}
