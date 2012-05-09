package com.adview.adapters;

import android.app.Activity;
import android.util.Log;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import com.smaato.SOMA.AdDownloader;
import com.smaato.SOMA.AdListener;
import com.smaato.SOMA.ErrorCode;
import com.smaato.SOMA.SOMABanner;
import com.smaato.SOMA.SOMAReceivedBanner;



public class SmaatoAdapter extends AdViewAdapter implements AdListener{
	private SOMABanner banner=null;
	public SmaatoAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
	 	}
		try {
			Activity activity = adViewLayout.activityReference.get();
			if(activity == null) {
				return;
			}
		banner=new SOMABanner (activity);
		banner.addAdListener(this);
		banner.setPublisherId(Integer.valueOf(ration.key).intValue());
		banner.setAdSpaceId(Integer.valueOf(ration.key2).intValue());
		banner.asyncLoadNewBanner();
		
		     
		}
		catch (Exception e) {               
			adViewLayout.rollover();                       
		}
		
		
	}

	@Override
	public void onFailedToReceiveAd(AdDownloader arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Smaato fail");
		banner.removeAdListener(this);
	    AdViewLayout adViewLayout = adViewLayoutReference.get();
	    if (adViewLayout == null) {
	    	return;
	    }
	    adViewLayout.adViewManager.resetRollover_pri();
		adViewLayout.rotateThreadedPri();
		
	}

	@Override
	public void onReceiveAd(AdDownloader arg0, SOMAReceivedBanner arg1) {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Smaato success");
		banner.removeAdListener(this);
		
	    AdViewLayout adViewLayout = adViewLayoutReference.get();
	    if (adViewLayout == null) {
	      return;
	    }
	    adViewLayout.adViewManager.resetRollover();
	    adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, banner));
	    adViewLayout.rotateThreadedDelayed();
		
	}

}
