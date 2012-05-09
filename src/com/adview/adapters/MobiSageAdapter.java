package com.adview.adapters;

import android.app.Activity;
import android.util.Log;
import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
//import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import com.mobisage.android.MobiSageAdBanner;
import com.mobisage.android.MobiSageAnimeType;
import com.mobisage.android.MobiSageEnviroment;
import com.mobisage.android.IMobiSageAdViewListener;
import android.widget.RelativeLayout;


public class MobiSageAdapter extends AdViewAdapter implements IMobiSageAdViewListener{
	private MobiSageAdBanner adv;
	
	public MobiSageAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into MobiSage");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
	 	}

		Activity activity = adViewLayout.activityReference.get();
		  if(activity == null) {
			  return;
		  }  	     

		adv = new MobiSageAdBanner(activity,ration.key,null,null);//MobiSageAdSize.Size_540X80
		adv.setAdRefreshInterval(MobiSageEnviroment.AdRefreshInterval.Ad_No_Refresh);//Ad_Refresh_15//Ad_No_Refresh
		adv.setAnimeType(MobiSageAnimeType.Anime_LeftToRight);
		adv.setMobiSageAdViewListener(this);
		//adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adv));
		adViewLayout.removeAllViews();
		adViewLayout.addView(adv, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
		adViewLayout.rotateThreadedDelayed();
	}

	public void onMobiSageAdViewShow(Object adView) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "onMobiSageAdViewShow");

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}
		adViewLayout.reportImpression();
        }

	public void onMobiSageAdViewUpdate(Object adView) { 
		if(AdViewTargeting.getRunMode()==RunMode.TEST)	
			Log.d(AdViewUtil.ADVIEW, "onMobiSageAdViewUpdate");
        }
	
	public void onMobiSageAdViewHide(Object adView) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)		
			Log.d(AdViewUtil.ADVIEW, "onMobiSageAdViewHide");
       }

}
