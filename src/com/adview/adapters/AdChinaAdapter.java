package com.adview.adapters;

import android.util.Log;

import com.adchina.android.ads.*;
import com.adchina.android.ads.views.AdView;
import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import android.view.View;

public class AdChinaAdapter extends AdViewAdapter implements AdListener{

	public AdChinaAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into AdChina");
	 	AdViewLayout adViewLayout = adViewLayoutReference.get();
	 	if(adViewLayout == null) {
	 		return;
	 	}
	 	 
		// …Ë÷√π„∏Êº‡Ã˝
		AdEngine.setAdListener(this);	  
		AdManager.setRefershinterval(-1);
		AdView mAdView=new AdView(adViewLayout.getContext(), ration.key, true, false);
		mAdView.setVisibility(View.VISIBLE);	
		
	}
	
	public void onFailedToReceiveAd(AdView arg0) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "onFailedToReceiveAd");
	    
		AdEngine.setAdListener(null);

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}
		adViewLayout.adViewManager.resetRollover_pri();
		adViewLayout.rotateThreadedPri();
	}

	public void onReceiveAd(AdView adView) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "onReceiveAd");

		AdEngine.setAdListener(null);
		  
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
		return;
		}

		adViewLayout.adViewManager.resetRollover();
		adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		adViewLayout.rotateThreadedDelayed();
	}

	public void onReceiveVideoAd(){
		
	}

	public void onEndFullScreenLandpage() {

	}

	public void onStartFullScreenLandPage(){

	}
	
	/**
	 * Called when user clicks on an sms button on ad the promptText will be
	 * something like SMS:123 To:1000 You can pop up an AlertDialog here and
	 * show the promptText return true for OK, return false for Cancel or you
	 * may simply return true to allow sms
	 */
	public boolean OnRecvSms(AdView adView, String promptText) {
		return true;
	}

	public void onFailedToRefreshAd(AdView arg0) {

	}

	public void onRefreshAd(AdView arg0) {

	}

	public void onFailedToPlayVideoAd() {

	}

	public void onPlayVideoAd() {

	}

	public void onFailedToReceiveVideoAd() {

	}

	public void onReceiveFullScreenAd() {

	}

	public void onFailedToReceiveFullScreenAd() {

	}

	public void onDisplayFullScreenAd() {

	}
}
