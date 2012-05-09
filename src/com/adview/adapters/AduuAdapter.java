package com.adview.adapters;

import android.app.Activity;
import android.util.Log;

//import cn.aduu.adsdk.AdActivityListener;
import cn.aduu.adsdk.AdManager;
import cn.aduu.adsdk.AdView;
//import cn.aduu.adsdk.AdViewListener;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;


public class AduuAdapter extends AdViewAdapter implements cn.aduu.adsdk.AdViewListener{
	private AdView adView = null;

	public AduuAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub	
		

		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Aduu"); 
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}

		Activity activity = adViewLayout.activityReference.get();
		if (activity == null) {
			return;
		}
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			AdManager.init(ration.key, ration.key2, -9, 0, 2, 61, true);
		else
			AdManager.init(ration.key, ration.key2, -9, 0, 2, 61, false);
		adView = new AdView((Activity)adViewLayout.getContext(), null);
		//adView.setReceiveAdListener(this);
		adView.setAdViewListener(this);
		//adViewLayout.removeAllViews();
		//adViewLayout.addView(adView); 
		adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		//adViewLayout.rotateThreadedDelayed();
	}

	public void onReceiveSuccess() {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "aduu--onReceiveAd");

		adView.setAdViewListener(null);
		  //adView.finalize();
		  
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}
		adViewLayout.adViewManager.resetRollover();
		//adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		adViewLayout.rotateThreadedDelayed();
	}

	//@Override
	public void onReceiveFail(int code) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "aduu--onFailedToReceiveAd");
	    
		adView.setAdViewListener(null);

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}
		adViewLayout.adViewManager.resetRollover_pri();
		adViewLayout.rotateThreadedPri();
	}	
	public void onAdSwitch() {

	}	

}
