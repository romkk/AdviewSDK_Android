

package com.adview.adapters;

import java.util.Hashtable;

import android.app.Activity;

import android.text.TextUtils;
import android.util.Log;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;

import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.Gender;
import com.adview.AdViewTargeting.RunMode;

import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import com.millennialmedia.android.MMAdView;
import com.millennialmedia.android.MMAdViewSDK;
import com.millennialmedia.android.MMAdView.MMAdListener;

public class MillennialAdapter extends AdViewAdapter implements MMAdListener {
	public MillennialAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
	}

	@Override
	public void handle() {
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
	 	}
	 	Hashtable<String, String> map = new Hashtable<String, String>();
	 	final AdViewTargeting.Gender gender = AdViewTargeting.getGender();
	    if (gender == Gender.MALE) {
	      map.put("gender", "male");
	    } else if (gender == Gender.FEMALE) {
	      map.put("gender", "female");
	    }
	    final int age = AdViewTargeting.getAge();
	    if (age != -1) {
	      map.put("age", String.valueOf(age));
	    }
	    final String postalCode = AdViewTargeting.getPostalCode();
	    if (!TextUtils.isEmpty(postalCode)) {
	      map.put("zip", postalCode);
	    }
	    final String keywords = AdViewTargeting.getKeywordSet() != null ? TextUtils
	            .join(",", AdViewTargeting.getKeywordSet())
	            : AdViewTargeting.getKeywords();
	        if (!TextUtils.isEmpty(keywords)) {
	          map.put("keywords", keywords);
	    }
	    map.put("vendor", "adwhirl");
	 //   Extra extra = adViewLayout.extra;
        // Instantiate an ad view and add it to the view
	    MMAdView adView=null;
	    adView = new MMAdView((Activity)adViewLayout.getContext(), ration.key, MMAdView.BANNER_AD_TOP, MMAdView.REFRESH_INTERVAL_OFF,map);
	    adView.setId(MMAdViewSDK.DEFAULT_VIEWID);
	    adView.setListener(this);
	    adView.callForAd();
	    adView.setHorizontalScrollBarEnabled(false);
	    adView.setVerticalScrollBarEnabled(false);
	}

	public void MMAdReturned(MMAdView adView) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Millennial success");
 		adView.setListener(null);

	 	AdViewLayout adViewLayout = adViewLayoutReference.get();
	 	if(adViewLayout == null) {
	 		return;
	 	}
	 	 
 		adViewLayout.adViewManager.resetRollover();
 		adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
		adViewLayout.rotateThreadedDelayed();
	}
	
	public void MMAdFailed(MMAdView adView) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Millennial failure");
		adView.setListener(null);
		AdViewLayout adViewLayout = adViewLayoutReference.get();
	 	if(adViewLayout == null) {
	 		return;
	 	}
	 	 
	 	adViewLayout.adViewManager.resetRollover_pri();
		adViewLayout.rotateThreadedPri();
	}		
	
	public void MMAdClickedToNewBrowser(MMAdView adview) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
		Log.d(AdViewUtil.ADVIEW, "Millennial Ad clicked, new browser launched" );
	}
	
	public void MMAdClickedToOverlay(MMAdView adview) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
		Log.d(AdViewUtil.ADVIEW, "Millennial Ad Clicked to overlay" );
	}
	
	public void MMAdOverlayLaunched(MMAdView adview) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
		Log.d(AdViewUtil.ADVIEW, "Millennial Ad Overlay Launched" );
	}

	@Override
	public void MMAdRequestIsCaching(MMAdView arg0) {
		// TODO Auto-generated method stub
		
	}	
}
