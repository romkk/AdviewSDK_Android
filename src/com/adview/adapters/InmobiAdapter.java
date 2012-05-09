package com.adview.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import com.inmobi.androidsdk.IMAdListener;
import com.inmobi.androidsdk.IMAdRequest;
import com.inmobi.androidsdk.IMAdView;
import com.inmobi.androidsdk.IMAdRequest.ErrorCode;
import com.inmobi.androidsdk.impl.Constants;

public class InmobiAdapter extends AdViewAdapter {
	private IMAdView mIMAdView = null;
	private IMAdRequest mAdRequest;
	
	public InmobiAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Inmobi");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}

		Activity activity = adViewLayout.activityReference.get();
		if (activity == null) {
			return;
		}
		// set the test mode to true (Make sure you set the test mode to false
		// when distributing to the users)
		mIMAdView = new IMAdView(activity, IMAdView.INMOBI_AD_UNIT_320X50,
		ration.key);
		mAdRequest = new IMAdRequest();
	    if(AdViewTargeting.getRunMode()==RunMode.TEST)
	    	mAdRequest.setTestMode(true);
	    else if(AdViewTargeting.getRunMode()==RunMode.NORMAL)
	    	mAdRequest.setTestMode(false);
	    else{
	    	mAdRequest.setTestMode(false);
	    } 
		mIMAdView.setIMAdRequest(mAdRequest);
		mIMAdView.setIMAdListener(mIMAdListener);
		adViewLayout.addView(mIMAdView);
		mIMAdView.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));			
		//adViewLayout.adViewManager.resetRollover();
		//adViewLayout.rotateThreadedDelayed();

	}
	private IMAdListener mIMAdListener = new IMAdListener() {

		@Override
		public void onShowAdScreen(IMAdView adView) {
			Log.i(Constants.LOGGING_TAG,
					"InMobiAdActivity-> onShowAdScreen, adView: " + adView);
 
		}

		@Override
		public void onDismissAdScreen(IMAdView adView) {
			Log.i(Constants.LOGGING_TAG,
					"InMobiAdActivity-> onDismissAdScreen, adView: " + adView);
		}

		@Override
		public void onAdRequestFailed(IMAdView adView, ErrorCode errorCode) {
			if(AdViewTargeting.getRunMode()==RunMode.TEST)
				  Log.d(AdViewUtil.ADVIEW, "ImMobi failure");
		    
			adView.setIMAdListener(null);

			AdViewLayout adViewLayout = adViewLayoutReference.get();
			if(adViewLayout == null) {
				return; 
			}
			adViewLayout.adViewManager.resetRollover_pri();
			adViewLayout.rotateThreadedPri();

		} 

		@Override
		public void onAdRequestCompleted(IMAdView adView) {
			if(AdViewTargeting.getRunMode()==RunMode.TEST)
				  Log.d(AdViewUtil.ADVIEW, "InMobi success");

			adView.setIMAdListener(null);
			
			  AdViewLayout adViewLayout = adViewLayoutReference.get();
			  if(adViewLayout == null) {
				  return;
			  }	
			  mIMAdView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));				
			  adViewLayout.adViewManager.resetRollover();
			  adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
			  adViewLayout.rotateThreadedDelayed(); 
		}
	};


}
