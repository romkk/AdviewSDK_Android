package com.adview.adapters;

import android.app.Activity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import com.wqmobile.sdk.widget.ADView;

public class WqAdapter extends AdViewAdapter{

	public WqAdapter(AdViewLayout adViewLayout, Ration ration) {
		super(adViewLayout, ration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
 			Log.d(AdViewUtil.ADVIEW, "Into WQ");
 		AdViewLayout adViewLayout = adViewLayoutReference.get();
 		if(adViewLayout == null) {
 			return;
 		}
 		Activity activity = adViewLayout.activityReference.get();
		if(activity == null) {
			  return;
		}
		String key=new String(ration.key);
		String key2=new String(ration.key2);
		String mode=null;
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			mode=new String("Y");
		else
			mode=new String("N");
		ADView view = new ADView(activity);
		String settings=new String("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		settings=settings+"<AppSettings>"+"<AppID>"+key+"</AppID>"+"<PublisherID>"+key2+"</PublisherID>"+"<URL></URL>"+"<AppStoreURL></AppStoreURL>"+"<BackgroundColor></BackgroundColor>"+"<TextColor></TextColor>"+"<UseLocationInfo></UseLocationInfo>"+"<RefreshRate>60</RefreshRate>"+"<TestMode>"+mode+"</TestMode>"+"<NextADCount>0</NextADCount>"+"<LoopTimes>2</LoopTimes>"+"<AcceptsOfflineAD>N</AcceptsOfflineAD>"+"<OfflineADCount>0</OfflineADCount>"+"<OfflineADSurvivalDays>0</OfflineADSurvivalDays>"+"<Width></Width>"+"<Height></Height>"+"<UseInternalBrowser>false</UseInternalBrowser>"+"<FittingStrategy>stretch</FittingStrategy>"+"</AppSettings>";
		view.Init(settings);
		adViewLayout.removeAllViews();
		adViewLayout.addView(view, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
		adViewLayout.adViewManager.resetRollover();
		adViewLayout.rotateThreadedDelayed();
		adViewLayout.reportImpression();
		view=null;
		
	}

}
