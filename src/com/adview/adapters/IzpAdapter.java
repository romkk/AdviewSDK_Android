package com.adview.adapters;

import android.app.Activity;
import android.util.Log;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;

import com.izp.views.IZPDelegate;
import com.izp.views.IZPView;



public class IzpAdapter extends AdViewAdapter implements IZPDelegate{
	
	public IzpAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Izp");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}

		Activity activity = adViewLayout.activityReference.get();
		if (activity == null) {
			return;
		}

		IZPView adView=new IZPView(activity);
		adView.productID=ration.key;
		adView.adType="1";
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			adView.isDev=true;
		else
			adView.isDev=false;
		adView.delegate=this;
		adView.startAdExchange();

		adViewLayout.adViewManager.resetRollover();
		adViewLayout.pushSubViewForIzp(adView);
		//adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		
	}

	//@Override
	public boolean shouldRequestFreshAd(IZPView view) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.i(AdViewUtil.ADVIEW,"shouldRequestFreshAd");
		
		return true;
	}

	//@Override
	public void didReceiveFreshAd(IZPView view, int adCount)
	{
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "didReceiveFreshAd, adCount="+adCount);
		  
	}

	//@Override
	public boolean shouldShowFreshAd(IZPView view)
	{
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "shouldShowFreshAd");

		return true;
	}

	//@Override
	public void didShowFreshAd(IZPView view)
	{
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "didShowFreshAd");

		view.stopAdExchange();
		view.delegate=null;
		
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			  return;
		  }
		adViewLayout.adViewManager.resetRollover();  
		adViewLayout.rotateThreadedDelayed();
	}	

	//@Override
	public void willLeaveApplication(IZPView view)
	{
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "willLeaveApplication");
	}

	//@Override
	public void errorReport(IZPView view, int errorCode,String errorInfo)
	{
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "errorReport");

		view.stopAdExchange();
		view.delegate=null;
		
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			 return;
		  }
		 adViewLayout.adViewManager.resetRollover_pri();
		 adViewLayout.rotateThreadedPri();
	}	

	//@Override
	public void didStopFullScreenAd(IZPView view)
	{
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "didStopFullScreenAd");
	}

}
