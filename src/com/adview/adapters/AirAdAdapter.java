package com.adview.adapters;

import android.app.Activity;
import android.util.Log;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;

import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;


import com.mt.airad.AirAD;

public class AirAdAdapter extends AdViewAdapter implements AirAD.AirADListener {
	
	private AirAD airAD=null;
	public AirAdAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			AirAD.setGlobalParameter(ration.key, true);
		else
			AirAD.setGlobalParameter(ration.key, false);
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Into AirAD");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}

		Activity activity = adViewLayout.activityReference.get();
		if(activity == null) {
			return;
		}
		  
		airAD=new AirAD(activity);
		airAD.setAirADListener(this);
		
	}

	@Override
	public void onAdReceived() {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "AirAD success");

		airAD.setAirADListener(null);
		
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}

		adViewLayout.adViewManager.resetRollover();
		adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, airAD));
		adViewLayout.rotateThreadedDelayed();
		
	}

	@Override
	public void onAdReceivedFailed() {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "AirAD failure");

		airAD.setAirADListener(null);

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}
		adViewLayout.adViewManager.resetRollover_pri();
		adViewLayout.rotateThreadedPri();
		
	}

	public void onAdBannerClicked() 
	{
	}
	
	public void onAirADFailed()
	{
	}

	public void onAdContentWillShow()
	{
	}

	public void onAdContentWillDismiss()
	{
	}

	public void onAdContentLoadFinished()
	{
	}

	public void onAdContentDidShow()
	{
	}

	public void onAdContentDidDismiss()
	{
	}

	public void onAdBannerWillShow()
	{
	}

	public void onAdBannerWillDismiss()
	{
	}

	public void onAdBannerDidShow()
	{
	}

	public void onAdBannerDidDismiss()
	{
	}
	
}
