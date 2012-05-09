package com.adview.adapters;


import android.app.Activity;
import android.util.Log;


import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
//import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Extra;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import com.madhouse.android.ads.AdListener;
import com.madhouse.android.ads.AdManager;
import com.madhouse.android.ads.AdView;
import android.widget.RelativeLayout;


public class SmartAdAdapter extends AdViewAdapter implements AdListener{

	
	public SmartAdAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into SmartAd");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
	 	}
	 	Extra extra = adViewLayout.extra;
	   
	    Activity activity = adViewLayout.activityReference.get();
		  if(activity == null) {
			  return;
		  }  	         
	    // Instantiate an ad view and add it to the view
		AdManager.setApplicationId(activity, ration.key);
		AdView ad=null;
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			ad = new AdView(activity, null, 0, ration.key2, extra.cycleTime, 0, true);
		else if(AdViewTargeting.getRunMode()==RunMode.NORMAL)
			ad = new AdView(activity, null, 0, ration.key2, extra.cycleTime, 0, false);
		else{
			ad = new AdView(activity, null, 0, ration.key2, extra.cycleTime, 0, false);
		}
		ad.setListener(this);
		
		adViewLayout.removeAllViews();
		adViewLayout.addView(ad, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
	}

	@Override
	public void onAdEvent(AdView arg0, int arg1) {
		// TODO Auto-generated method stub
		
		switch(arg1){
		
			case AdView.EVENT_NEWAD:
			//case AdView.EVENT_EXISTAD:
			
			{
				if(AdViewTargeting.getRunMode()==RunMode.TEST)
				Log.d(AdViewUtil.ADVIEW, "SmartAd new Ad");

				AdViewLayout adViewLayout = adViewLayoutReference.get();
				if(adViewLayout == null) {
					return;
				}

				adViewLayout.adViewManager.resetRollover();
				//adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, arg0));
				adViewLayout.reportImpression();
				adViewLayout.rotateThreadedDelayed();
				arg0.setListener(null);
			}
				break;
/*			
			case AdView.EVENT_FINISHAD:
			{
				
				if(AdViewTargeting.getRunMode()==RunMode.TEST)
					Log.d(AdViewUtil.ADVIEW, "SmartAd success");
				arg0.setListener(null);
			}
				break;*/
			case AdView.EVENT_INVALIDAD:
			{
				
				if(AdViewTargeting.getRunMode()==RunMode.TEST)
					Log.d(AdViewUtil.ADVIEW, "SmartAd invalid ad");
				arg0.setListener(null);
				
				  AdViewLayout adViewLayout = adViewLayoutReference.get();
				  if(adViewLayout == null) {
					 return;
				  }
				 adViewLayout.adViewManager.resetRollover_pri();
				  adViewLayout.rotateThreadedPri();
			}
				break;
			default:
				break;
		}
	}

	@Override
	public void onAdFullScreenStatus(boolean isFullScreen)
	{
	}
		
/*
	@Override
	public void onAdStatus(int arg0) {
		// TODO Auto-generated method stub
	
		if(arg0!=AdView.RETRUNCODE_OK){
			if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "SmartAd fail ad");
			
			  AdViewLayout adViewLayout = adViewLayoutReference.get();
			  if(adViewLayout == null) {
				 return;
			  }
			 adViewLayout.adViewManager.resetRollover_pri();
			  adViewLayout.rotateThreadedPri();
		}
		
	}
*/
}
