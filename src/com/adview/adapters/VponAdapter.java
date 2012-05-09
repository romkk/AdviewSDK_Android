package com.adview.adapters;

import android.app.Activity;
import android.util.Log;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
//import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import com.vpon.adon.android.AdListener;
import com.vpon.adon.android.AdOnPlatform;
import com.vpon.adon.android.AdView;

public class VponAdapter extends AdViewAdapter implements AdListener{
	
	public VponAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Vpon");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}

		Activity activity = adViewLayout.activityReference.get();
		if (activity == null) {
			return;
		}
		try {
			AdView adView = new AdView(activity);
			boolean autoRefreshAd = false;
			adView.setLicenseKey(ration.key, AdOnPlatform.CN, autoRefreshAd, false);
			adView.setAdListener(this);
			
			
			//adViewLayout.addView(adView, new LayoutParams(
			//		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			//adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView)); 
			adViewLayout.AddSubView(adView);
			
		} catch (IllegalArgumentException e) {
			adViewLayout.rollover();
			return;
		}
	}

	@Override
	public void onFailedToRecevieAd(AdView arg0) {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Vpon fail");
		arg0.setAdListener(null);

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}

		adViewLayout.adViewManager.resetRollover_pri();
		  adViewLayout.rotateThreadedPri();
		
	}

	@Override
	public void onRecevieAd(AdView arg0) {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Vpon success");

		arg0.setAdListener(null);
		
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}

		adViewLayout.reportImpression();	
		adViewLayout.adViewManager.resetRollover();
		//adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, arg0));
		adViewLayout.rotateThreadedDelayed();
		
	}

}
