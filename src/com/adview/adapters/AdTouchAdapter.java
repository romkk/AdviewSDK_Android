package com.adview.adapters;

import android.app.Activity;
import android.util.Log;



import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewTargeting.AdArea;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import com.energysource.szj.embeded.AdListener;
import com.energysource.szj.embeded.AdManager;
import com.energysource.szj.embeded.AdView;



public class AdTouchAdapter extends AdViewAdapter implements AdListener{
	
	private int area=AdManager.Bottom_Banner;
	
	public AdTouchAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into AdTouch");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}

		Activity activity = adViewLayout.activityReference.get();
		if (activity == null) {
			return;
		}
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			AdManager.openDebug();
		AdManager.openPermissionJudge();
		
		
		AdManager.initAd(activity, ration.key);
		
		if(AdViewTargeting.getAdArea()==AdArea.BOTTOM)
			area=AdManager.Bottom_Banner;
		else
			area=AdManager.Top_Banner;
		AdManager.addAd(101, AdManager.AD_FILL_PARENT, area, 0, 0);
		AdManager.setAdListener(this);
		AdManager.openAllAdView();
		adViewLayout.adViewManager.resetRollover();
		adViewLayout.rotateThreadedDelayed();
		
		
	}

	@Override
	public void failedReceiveAd(AdView arg0) {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "AdTouch fail");
		
		AdManager.setAdListener(null);
	}

	@Override
	public void receiveAd(AdView arg0) {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "AdTouch success");

		AdManager.setAdListener(null);

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}
		adViewLayout.reportImpression(); 
	}

	

}
