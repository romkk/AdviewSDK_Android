package com.adview.adapters;

import android.app.Activity;
import android.util.Log;


import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import com.baidu.AdType;
import com.baidu.AdView;
import com.baidu.AdViewListener;
import com.baidu.FailReason;

public class AdBaiduAdapter extends AdViewAdapter implements AdViewListener  {
	
	private AdView adView;
	public AdBaiduAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
	//	try{
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Baidu");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
	 	}
	 	Activity activity = adViewLayout.activityReference.get();
		  if(activity == null) {
			  return;
		  }
		  if(adViewLayout.baiduView==null){
			  if(AdViewTargeting.getRunMode()==RunMode.TEST)
				  Log.d(AdViewUtil.ADVIEW, "Baidu first into");
			  if((ration.key3).compareTo("1")==0)
				  adViewLayout.baiduView = new AdView(adViewLayout.getContext(),AdType.IMAGE );
			  else if((ration.key3).compareTo("2")==0)
				  adViewLayout.baiduView = new AdView(adViewLayout.getContext(),AdType.TEXT );
			  else{}
			  adView=(AdView)(adViewLayout.baiduView);
			  adView.setListener(this);
			  adViewLayout.adViewManager.resetRollover();
			  adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		  }
		
		  else
	  	{
		  if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Baidu no first into");
	  	  adView=(AdView)(adViewLayout.baiduView);
		  adViewLayout.adViewManager.resetRollover();
		  adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		  adViewLayout.rotateThreadedDelayed();
		  }
	}

	@Override
	public void onAdSwitch() {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Baidu switch");
		
	}

	@Override
	public void onReceiveFail(FailReason arg0) {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Baidu failure");
	    
		  adView.setListener(null);

		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			 return;
		  }
		 adViewLayout.adViewManager.resetRollover_pri();
		  adViewLayout.rotateThreadedPri();
		
	}

	@Override
	public void onReceiveSuccess() {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Baidu success");

		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			  return;
		  }

		  adViewLayout.rotateThreadedDelayed();
		
	}

}
