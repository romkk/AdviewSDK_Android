package com.adview.adapters;

import android.app.Activity;
import android.graphics.Color;
//import android.util.DisplayMetrics;
import android.util.Log;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import com.l.adlib_android.AdListenerEx;
import com.l.adlib_android.AdView;
import com.adview.obj.Extra;

public class LsenseAdapter extends AdViewAdapter implements AdListenerEx{
	//static private AdView adView = null;
	private AdView adView = null;
	public LsenseAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Lsense");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}

		Extra extra = adViewLayout.extra;
	    int bgColor = Color.rgb(extra.bgRed, extra.bgGreen, extra.bgBlue);
	    int fgColor = Color.rgb(extra.fgRed, extra.fgGreen, extra.fgBlue);
		Activity activity = adViewLayout.activityReference.get();
		if (activity == null) {
			return;
		}
		try {
			//adView = new AdView(activity);
			adView = null;
			adView = new AdView(
					activity, 
					Integer.valueOf(ration.key), 
					Color.rgb(65, 65, 65), 
					bgColor, 
					fgColor, 
					255,5,
					true);	
		/*	
	        DisplayMetrics dm = new DisplayMetrics();
	        dm = adViewLayout.getContext().getApplicationContext().getResources().getDisplayMetrics();
	        int screenHeight = dm.heightPixels;	
	        if(screenHeight > 320){
	        	adView.setBannerSize(640, 96); 
	        }else{
	        	adView.setBannerSize(640, 76); 	        	
	        }
	   */     
			adView.setOnAdListenerEx(this);		
			adViewLayout.adViewManager.resetRollover();
			adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
			adViewLayout.rotateThreadedDelayed();
			 
		} catch (IllegalArgumentException e) {
			adViewLayout.rollover();
			return; 
		}
	}
	@Override
	public void OnConnectFailed(String arg0) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Lsense failure");
	    
		adView.setOnAdListenerEx(null);

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			 return;
		}
		adViewLayout.adViewManager.resetRollover_pri();
		//adViewLayout.rotateThreadedPri();
	}
	
	@Override
	public void OnAcceptAd(int arg0) {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Lsense success");

		adView.setOnAdListenerEx(null);
		
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}
		
		//adViewLayout.adViewManager.resetRollover();
		//adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		//adViewLayout.rotateThreadedDelayed();
		
	}

}
