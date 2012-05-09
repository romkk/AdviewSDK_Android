package com.adview.adapters;
import java.text.SimpleDateFormat;
import android.app.Activity;
import android.util.Log;
import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewLayout.ViewAdRunnable;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.AdRequest.ErrorCode;

public class AdMobAdapter extends AdViewAdapter implements AdListener {
	public AdMobAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
	}
	protected String birthdayForAdViewTargeting() {
		return (AdViewTargeting.getBirthDate() != null) ?
		new SimpleDateFormat("yyyyMMdd").
	          format(AdViewTargeting.getBirthDate().getTime()) : null;
}
protected AdRequest.Gender genderForAdViewTargeting() {
	    switch (AdViewTargeting.getGender()) {
	      case MALE:   return AdRequest.Gender.MALE;
	      case FEMALE: return AdRequest.Gender.FEMALE;
	      default:     return null;
	    }
}
  @Override
  public void handle() {
	  if(AdViewTargeting.getRunMode()==RunMode.TEST)
		  Log.d(AdViewUtil.ADVIEW, "Into AdMob");
	  AdViewLayout adViewLayout = adViewLayoutReference.get();
	  if(adViewLayout == null) {
		  return;
	  }
	 	
	  Activity activity = adViewLayout.activityReference.get();
	  if(activity == null) {
		  return;
	  }
	  AdView adView = new AdView(activity, AdSize.BANNER, ration.key);
	  adView.setAdListener(this);

	  
	  adView.loadAd(requestForAdWhirlLayout(adViewLayout)); 
    
  }
  protected AdRequest requestForAdWhirlLayout(AdViewLayout layout) {
	    AdRequest result = new AdRequest();
/*	    if(AdViewTargeting.getRunMode()==RunMode.TEST)
	    	result.addTestDevice(AdRequest.TEST_EMULATOR);
	    else if(AdViewTargeting.getRunMode()==RunMode.NORMAL)
	    	;//result.setTesting(false);
	    else
	    	;//result.setTesting(false);
	    result.setGender(genderForAdViewTargeting());
	    result.setBirthday(birthdayForAdViewTargeting());
	    result.setKeywords(AdViewTargeting.getKeywordSet());
*/	    
	    return result;
	  }
@Override
public void onDismissScreen(Ad arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
	// TODO Auto-generated method stub
	if(AdViewTargeting.getRunMode()==RunMode.TEST)
		Log.d(AdViewUtil.ADVIEW, "AdMob fail");
	arg0.setAdListener(null);
	AdViewLayout adViewLayout = adViewLayoutReference.get();
	if (adViewLayout == null) {
		return;
	}
	adViewLayout.adViewManager.resetRollover_pri();
	adViewLayout.rotateThreadedPri();
}

@Override
public void onLeaveApplication(Ad arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onPresentScreen(Ad arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onReceiveAd(Ad arg0) {
	// TODO Auto-generated method stub
	if(AdViewTargeting.getRunMode()==RunMode.TEST)
		Log.d(AdViewUtil.ADVIEW, "AdMob success");

	arg0.setAdListener(null);	
	AdViewLayout adViewLayout = adViewLayoutReference.get();
	if (adViewLayout == null) {
		return;
	}
	if (!(arg0 instanceof AdView)) {
		return;
	}
	AdView adView = (AdView)arg0;
	adViewLayout.adViewManager.resetRollover();
	adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, adView));
	adViewLayout.rotateThreadedDelayed();
	
}

 

  /*******************************************************************/
  // End of AdMob listeners
}
