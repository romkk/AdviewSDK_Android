package com.adview.adapters;

import android.app.Activity;
import android.util.Log;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import com.greystripe.android.sdk.BannerListener;
import com.greystripe.android.sdk.BannerView;
import com.greystripe.android.sdk.GSSDK;


public class GreystripeAdapter extends AdViewAdapter implements BannerListener
{

	public GreystripeAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		 if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Into Greystripe");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
	 	}
		try {
			Activity activity = adViewLayout.activityReference.get();
			if(activity == null) {
				return;
			}
		GSSDK.initialize(activity.getApplicationContext(), ration.key);	
		BannerView banner=new BannerView(activity);
		
		banner.addListener(this);
		
		//adViewLayout.addView(banner, new LayoutParams(
		//		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		banner.refresh();
		}
		catch (Exception e) {               
			adViewLayout.rotatePriAd();                       
		}
		
	}

	@Override
	public void onFailedToReceiveAd(BannerView arg0) {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Greystripe fail");
		arg0.removeListener(this);
	    AdViewLayout adViewLayout = adViewLayoutReference.get();
	    if (adViewLayout == null) {
	    	return;
	    }
	    adViewLayout.adViewManager.resetRollover_pri();
		adViewLayout.rotateThreadedPri();
		
	}

	@Override
	public void onReceivedAd(BannerView arg0) {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Greystripe success");
		arg0.removeListener(this);
		
	    AdViewLayout adViewLayout = adViewLayoutReference.get();
	    if (adViewLayout == null) {
	      return;
	    }
	    adViewLayout.adViewManager.resetRollover();
	    adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, arg0));
	    adViewLayout.rotateThreadedDelayed();
		
	}

}
