package com.adview.adapters;

import android.graphics.Color;
import android.util.Log;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Extra;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import com.wooboo.adlib_android.AdListener;
import com.wooboo.adlib_android.WoobooAdView;

public class WoobooAdapter extends AdViewAdapter implements AdListener {
	
	
	public WoobooAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
	}
	
	public void handle() {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Wooboo");
	 	 AdViewLayout adViewLayout = adViewLayoutReference.get();
	 	 if(adViewLayout == null) {
	 		 return;
	 	 }
	 	Extra extra = adViewLayout.extra;
	    int bgColor = Color.rgb(extra.bgRed, extra.bgGreen, extra.bgBlue);
	    int fgColor = Color.rgb(extra.fgRed, extra.fgGreen, extra.fgBlue);
	    WoobooAdView adView=null;
	    if(AdViewTargeting.getRunMode()==RunMode.TEST)
	    	adView=new WoobooAdView(adViewLayout.getContext(), ration.key,bgColor, fgColor,true,120,null);
	    else if(AdViewTargeting.getRunMode()==RunMode.NORMAL)
	    	adView=new WoobooAdView(adViewLayout.getContext(), ration.key,bgColor, fgColor,false,120,null);
	    else{
	    	adView=new WoobooAdView(adViewLayout.getContext(), ration.key,bgColor, fgColor,false,120,null);
	    }
	 
	    adView.setHorizontalScrollBarEnabled(false);
	    adView.setVerticalScrollBarEnabled(false);
	    adView.setAdListener(this);
	   // adViewLayout.adViewManager.resetRollover();
	 //	adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
	//	adViewLayout.rotateThreadedDelayed();
	   
	}

	@Override
	public void onFailedToReceiveAd(Object arg0) {
		// TODO Auto-generated method stub
		WoobooAdView adView=(WoobooAdView)arg0;
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Woboo failure");
	    
		  adView.setAdListener(null);
	
		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			 return;
		  }
		  
 		  adViewLayout.adViewManager.resetRollover_pri();
		  adViewLayout.rotateThreadedPri();
	}

	@Override
	public void onReceiveAd(Object arg0) {
		// TODO Auto-generated method stub
		WoobooAdView adView=(WoobooAdView)arg0;
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Wooboo success");
		adView.setAdListener(null);
		
		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			  return;
		  }

		  adViewLayout.adViewManager.resetRollover();
		  adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		  adViewLayout.rotateThreadedDelayed();
		
	}

	public void onPlayFinish() {

		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Wooboo onPlayFinish");
	}
	
}
