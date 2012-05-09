

package com.adview;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

//import com.adview.AdViewTargeting.Channel;
import com.adview.AdViewTargeting.RunMode;
import com.adview.AdViewTargeting.UpdateMode;
import com.adview.adapters.AdViewAdapter;
import com.adview.obj.Extra;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import java.io.UnsupportedEncodingException;

public class AdViewLayout extends RelativeLayout {
	public final WeakReference<Activity> activityReference;
	
	
	public final Handler handler;
	
	public final ScheduledExecutorService scheduler;
	
	private String keyAdView;
	public String keyDev=new String("000000000000000");
	public String typeDev=new String("SDK");
	public String osVer=new String("2.1-update1");
	public String resolution=new String("320*480");
	public String servicePro=new String("46000");
	public String netType=new String("2G/3G");
	public String channel=new String("unknown");
	public String platform=new String("android");
	
	public Extra extra;

	public RelativeLayout umengView=null;
	
	public RelativeLayout baiduView=null;
	public WeakReference<RelativeLayout> superViewReference;
	
	public Ration activeRation;
	public Ration nextRation;
	
	public AdViewInterface adViewInterface;  
	
	public AdViewManager adViewManager;
	
	private boolean hasWindow;
	private boolean isScheduled;

	private String mDefaultChannel[]=
		{"EOE",
		"GOOGLEMARKET",
		"APPCHINA",
		"HIAPK",
		"GFAN",
		"GOAPK",
		"NDUOA",
		"91Store",
		"LIQUCN",
		"WAPTW",
		"ANDROIDCN",
		"GGDWON",
		"ANDROIDAI",
		"STARANDROID",
		"ANDROIDD",
		"YINGYONGSO",
		"IMOBILE",
		"SOUAPP",
		"MUMAYI",
		"MOBIOMNI",
		"PAOJIAO",
		"AIBALA",
		"COOLAPK",
		"ANFONE",
		"APKOK",
		"OTHER"
		};
	
	private int maxWidth;
	public void setMaxWidth(int width) { maxWidth = width; }
	
	private int maxHeight;
	public void setMaxHeight(int height) { maxHeight = height; }
	
	public AdViewLayout(final Activity context, final String keyAdView) {
		super(context);
		this.activityReference = new WeakReference<Activity>(context);
		this.superViewReference = new WeakReference<RelativeLayout>(this);
		
		this.keyAdView = keyAdView;
		
		this.hasWindow = true;
		
		handler = new Handler();

		scheduler = Executors.newScheduledThreadPool(1);
		this.isScheduled = true;
		scheduler.schedule(new InitRunnable(this, keyAdView), 0, TimeUnit.SECONDS);

		setHorizontalScrollBarEnabled(false);
		setVerticalScrollBarEnabled(false);
		getAppInfo(context);
		//appReport();
		this.maxWidth = 0;
		this.maxHeight = 0;
	}

	public AdViewLayout(Context context, AttributeSet attrs) {
		super(context, attrs); 
			String key = getAdViewSDKKey(context);
			if(key == null){
				key = "";
			}
			Activity activity = (Activity)context;
			this.activityReference = new WeakReference<Activity>(activity);
			this.superViewReference = new WeakReference<RelativeLayout>(this);
			this.keyAdView = key;
			this.hasWindow = true;
			handler = new Handler();
			scheduler = Executors.newScheduledThreadPool(1);
			this.isScheduled = true;
			scheduler.schedule(new InitRunnable(this, keyAdView), 0, TimeUnit.SECONDS);
			setHorizontalScrollBarEnabled(false);
			setVerticalScrollBarEnabled(false);
			getAppInfo(context);
			//appReport();
			this.maxWidth = 0;
			this.maxHeight = 0;			

		}	
	
	private String getAdViewSDKKey(Context ctx){
		String packageName = ctx.getPackageName();
		String activityName = ctx.getClass().getName();
		PackageManager packageManager = ctx.getPackageManager();
		Bundle bd = null;
		String key = "";
		try
		{
			ActivityInfo info = packageManager.getActivityInfo(new ComponentName(packageName, activityName), PackageManager.GET_META_DATA);
			bd = info.metaData;
			if (bd != null){
				key = bd.getString("ADVIEW_SDK_KEY");
			}
		}catch(PackageManager.NameNotFoundException exception)
		{
		}
		try
		{
			ApplicationInfo info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
			bd = info.metaData;
			if (bd != null){
				key = bd.getString("ADVIEW_SDK_KEY");
			}
		}
		catch (PackageManager.NameNotFoundException exception)
		{
		}
		return key;		
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		if (maxWidth > 0 && widthSize > maxWidth){
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.AT_MOST);
		}
		
		if (maxHeight > 0 && heightSize > maxHeight){
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
		}
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		 if(visibility == VISIBLE) {
			 this.hasWindow = true;
			 if(!this.isScheduled) {
				 this.isScheduled = true;
				 
				 if(this.extra != null) {
					 rotateThreadedNow();
				 }
				 else {
					scheduler.schedule(new InitRunnable(this, keyAdView), 0, TimeUnit.SECONDS);
				 }
			 }
		}
		else {
			this.hasWindow = false;
		}
	}

	@Override
	public void setVisibility(int visibility)
	{
	 if(visibility == VISIBLE) {
		 this.hasWindow = true;
		 if(!this.isScheduled) {
			 this.isScheduled = true;
			 
			 if(this.extra != null) {
				 rotateThreadedNow();
			 }
			 else {
				scheduler.schedule(new InitRunnable(this, keyAdView), 0, TimeUnit.SECONDS);
			 }
		 }
	}
	 else {
		 this.hasWindow = false;
	 }
	 super.setVisibility(visibility);
	}
	  
	private void rotateAd() {
		if(!this.hasWindow) {
			this.isScheduled = false;
			return;
		}
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.i(AdViewUtil.ADVIEW, "Rotating Ad");
		nextRation = adViewManager.getRation();
		
		handler.post(new HandleAdRunnable(this));
	}
	public void rotatePriAd() {
		if(!this.hasWindow) {
			this.isScheduled = false;
			return;
		}
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.i(AdViewUtil.ADVIEW, "Rotating Pri Ad");
		nextRation=adViewManager.getRollover();//adViewManager.getRollover_pri();
		handler.post(new HandleAdRunnable(this));
	}

    public boolean isConnectInternet() {
        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null)
            return ni.isAvailable ();
        else
            return false;
    }
	
	// Initialize the proper ad view from nextRation
	private void handleAd() {
		// We shouldn't ever get to a state where nextRation is null unless all networks fail
		if(nextRation == null) {
			if(AdViewTargeting.getRunMode()==RunMode.TEST)
				Log.e(AdViewUtil.ADVIEW, "nextRation is null!");
			rotateThreadedDelayed();
			return;
		}

		if (isConnectInternet()==false)
		{
			//Log.i(AdViewUtil.ADVIEW, "network is unavailable");
			scheduler.schedule(new RotatePriAdRunnable(this), 5, TimeUnit.SECONDS);
			return;
		}
		
		String rationInfo = String.format("Showing ad:\nname: %s", nextRation.name);
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, rationInfo);

		try {
		  AdViewAdapter.handle(this, nextRation);
		}
		catch(Throwable t) {
			if(AdViewTargeting.getRunMode()==RunMode.TEST)
				Log.w(AdViewUtil.ADVIEW, "Caught an exception in adapter:", t);
		    rollover();
		    return;
		}
	}
	
	// Rotate immediately
	public void rotateThreadedNow() {
		scheduler.schedule(new RotateAdRunnable(this), 0, TimeUnit.SECONDS);
	}
	public void rotateThreadedPri(){
		scheduler.schedule(new RotatePriAdRunnable(this), 1, TimeUnit.SECONDS);
	}
	
	// Rotate in extra.cycleTime seconds
	public void rotateThreadedDelayed() {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Will call rotateAd() in " + extra.cycleTime + " seconds");
		scheduler.schedule(new RotateAdRunnable(this), extra.cycleTime, TimeUnit.SECONDS);
	}
	
	// Fetch config from server after defined time
	public void fetchConfigThreadedDelayed(int seconds){
		if(AdViewTargeting.getUpdateMode() == UpdateMode.DEFAULT){
			scheduler.schedule(new GetConfigRunnable(this), seconds, TimeUnit.SECONDS);		
		}
	}
	
	// Remove old views and push the new one
	public void pushSubView(ViewGroup subView) {
		RelativeLayout superView = superViewReference.get();
		if(superView == null) {
			return;
		}
		superView.removeAllViews();
		RelativeLayout.LayoutParams layoutParams;
		if (nextRation.type == AdViewUtil.NETWORK_TYPE_ADWO || nextRation.type == AdViewUtil.NETWORK_TYPE_MOBWIN)
			layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		else
			layoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		superView.addView(subView, layoutParams);
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Added subview");
		this.activeRation = nextRation;
		countImpression();
	}

	public void pushSubViewForIzp(ViewGroup subView) {
		RelativeLayout superView = superViewReference.get();
		if(superView == null) {
			return;
		}
		superView.removeAllViews();
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(320, 48);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		superView.addView(subView, layoutParams);
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Added subview");
		this.activeRation = nextRation;
		countImpression();
	}

	public void AddSubView(RelativeLayout subView) {
		RelativeLayout superView = superViewReference.get();
		if(superView == null) {
			return;
		}
		superView.removeAllViews();
		RelativeLayout.LayoutParams layoutParams;
		layoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		superView.addView(subView, layoutParams);
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "AddSubView");
		this.activeRation = nextRation;
		//countImpression();
	}
	
	public void reportImpression() {
		//if(AdViewTargeting.getRunMode()==RunMode.TEST)
		//	Log.d(AdViewUtil.ADVIEW, "reportImpression");
		this.activeRation = nextRation;
		countImpression();
	}
	
	public void rollover() {
		nextRation = adViewManager.getRollover();
		handler.post(new HandleAdRunnable(this));
	}
    	
	private void countImpression() {	
		if(activeRation != null) {
			
			String url = String.format(AdViewUtil.urlImpression, adViewManager.keyAdView, activeRation.nid, activeRation.type, 0, "hello", AdViewUtil.VERSION, adViewManager.mSimulator);
			scheduler.schedule(new PingUrlRunnable(url), 0, TimeUnit.SECONDS);
			if(adViewInterface!=null)
				adViewInterface.onDisplayAd();
		}
	}
	
	private void countClick() {
		if(activeRation != null) {
			
			String url = String.format(AdViewUtil.urlClick, adViewManager.keyAdView, activeRation.nid, activeRation.type, 0, "hello", AdViewUtil.VERSION, adViewManager.mSimulator);
			scheduler.schedule(new PingUrlRunnable(url), 0, TimeUnit.SECONDS);
			if(adViewInterface!=null)
			adViewInterface.onClickAd();
		}
	}
	public void appReport() {
		
			
		String url = String.format(AdViewUtil.appReport, keyAdView, keyDev, typeDev, osVer, resolution,servicePro,netType,channel,platform);
		scheduler.schedule(new PingUrlRunnable(url), 0, TimeUnit.SECONDS);
		
			
	}

	public void kyAdviewReport(String url, String content) {
		
		scheduler.schedule(new PingKyAdviewRunnable(url, content), 1, TimeUnit.SECONDS);
	}

	private String getChannel(Context ctx){
		String packageName = ctx.getPackageName();
		String activityName = ctx.getClass().getName();
		PackageManager packageManager = ctx.getPackageManager();
		Bundle bd = null;
		String key = "";
		try
		{
			ActivityInfo info = packageManager.getActivityInfo(new ComponentName(packageName, activityName), PackageManager.GET_META_DATA);
			bd = info.metaData;
			if (bd != null){
				key = bd.getString("AdView_CHANNEL");
			}
		}catch(PackageManager.NameNotFoundException exception)
		{
		}
		try
		{
			ApplicationInfo info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
			bd = info.metaData;
			if (bd != null){
				key = bd.getString("AdView_CHANNEL");
			}
		}
		catch (PackageManager.NameNotFoundException exception)
		{
		}

		if (key==null || key.length()==0)
			key = "OTHER";
		else
		{
			int i=0;
			for (i=0; i<mDefaultChannel.length; i++)
			{
				if (key.compareTo(mDefaultChannel[i])==0)
				{
					break;
				}
			}
			if (i==mDefaultChannel.length)
			{
				key = "OTHER";
			}
		}

		return key;		
	}

	
	private void getAppInfo(Context context){
		if(context==null){
			return;
		}
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if(tm==null)
			return;
		//keyDev = new String(tm.getDeviceId());
		String devid=tm.getDeviceId();
		if (devid != null && devid.length()>0)
		{
			keyDev = new String(devid);
		}
		typeDev = new String(Build.MODEL);
		typeDev=typeDev.replaceAll(" ", "");
		osVer=new String(Build.VERSION.RELEASE);
		osVer=osVer.replaceAll(" ", "");
		DisplayMetrics dm = new DisplayMetrics();
		//dm = context.getApplicationContext().getResources().getDisplayMetrics();
		if(dm==null)
			return;
		activityReference.get().getWindowManager().getDefaultDisplay().getMetrics(dm);
/*		double density = dm.density;
		int screenWidth = (int)(density*(dm.widthPixels + 0.5f));
		int screenHeight = (int)(density*(dm.heightPixels + 0.5f));
		resolution=new String(Integer.toString(screenWidth)+"*"+Integer.toString(screenHeight));
*/
		resolution=new String(Integer.toString(dm.widthPixels)+"*"+Integer.toString(dm.heightPixels));
		//if(AdViewTargeting.getRunMode()==RunMode.TEST)
		//	Log.d(AdViewUtil.ADVIEW, "resolution="+resolution);
		
		if(tm.getSimState()==TelephonyManager.SIM_STATE_READY)
		{
			String service=tm.getSimOperator();
			if (service != null && service.length()>0)
			{
				servicePro = new String(service);
			}
			//servicePro=new String(tm.getSimOperator());
		}
		else
			;

		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        	NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null)
		{
			String networkType = ni.getTypeName();
			if (networkType.equalsIgnoreCase("mobile"))
			{
				netType=new String("2G/3G");
			}
			else if (networkType.equalsIgnoreCase("wifi"))
			{
				netType=new String("Wi-Fi");
			}
			else
			{
				netType=new String("Wi-Fi");
			}	
		}
		else
		{
			netType=new String("Wi-Fi");
		}
		//Log.d(AdViewUtil.ADVIEW, "netType="+netType);
		
/*		int type=tm.getNetworkType();
		switch(type){
			case TelephonyManager.NETWORK_TYPE_UNKNOWN :
				netType=new String("Wi-Fi");
				break;
			default:
				netType=new String("2G/3G");
				break;
		}*/
		/*
		Channel cha=AdViewTargeting.getChannel();
		if(cha==null)
			return;
		channel=new String(cha.toString());*/
		channel=getChannel(context);		
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {  
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(AdViewTargeting.getRunMode()==RunMode.TEST)
				Log.d(AdViewUtil.ADVIEW, "Intercepted ACTION_DOWN event");
		    if(activeRation != null) {
			countClick();
			
			
			break;
		    }
		}
	
		// Return false so subViews can process event normally.
		return false;
	}
	
	
	
	public void setAdViewInterface(AdViewInterface adViewInterface) {
		this.adViewInterface = adViewInterface;
	}
	
	private static class InitRunnable implements Runnable {
		private WeakReference<AdViewLayout> adViewLayoutReference;
		private String keyAdView;

		public InitRunnable(AdViewLayout adViewLayout, String keyAdView) {
			adViewLayoutReference = new WeakReference<AdViewLayout>(adViewLayout);
			this.keyAdView = keyAdView;
		}
		
		public void run() {
			AdViewLayout adViewLayout = adViewLayoutReference.get();
			
			if(adViewLayout != null) {
				Activity activity = adViewLayout.activityReference.get();
				if(activity == null) {
					return;
				}
			
				
				if(adViewLayout.adViewManager == null) {
					adViewLayout.adViewManager = new AdViewManager(new WeakReference<Context>(activity.getApplicationContext()), keyAdView);
				}
				
				if(!adViewLayout.hasWindow) {
					adViewLayout.isScheduled = false;
					return;
				}
				
				adViewLayout.adViewManager.fetchConfig();
				adViewLayout.extra = adViewLayout.adViewManager.getExtra();

				if(adViewLayout.extra == null) {
					adViewLayout.scheduler.schedule(this, 30, TimeUnit.SECONDS);
				}
				else {
					if (adViewLayout.adViewManager.bGetConfig==false)
						adViewLayout.fetchConfigThreadedDelayed(10);
					else
						adViewLayout.fetchConfigThreadedDelayed(adViewLayout.adViewManager.getConfigExpiereTimeout());	

					adViewLayout.appReport();
					adViewLayout.rotateAd();
				}
			}
		}
	}
	
	private static class HandleAdRunnable implements Runnable {
		private WeakReference<AdViewLayout> adViewLayoutReference;

		public HandleAdRunnable(AdViewLayout adViewLayout) {
			adViewLayoutReference = new WeakReference<AdViewLayout>(adViewLayout);
		}
		public void run() {
			AdViewLayout adViewLayout = adViewLayoutReference.get();
			if(adViewLayout != null) {
				adViewLayout.handleAd();
			}
		}
	}

	public static class ViewAdRunnable implements Runnable {
		private WeakReference<AdViewLayout> adViewLayoutReference;
		private ViewGroup nextView;

		public ViewAdRunnable(AdViewLayout adViewLayout, ViewGroup nextView) {
			adViewLayoutReference = new WeakReference<AdViewLayout>(adViewLayout);
			this.nextView = nextView;
		}
		public void run() {
			AdViewLayout adViewLayout = adViewLayoutReference.get();
			if(adViewLayout != null) {
				adViewLayout.pushSubView(nextView);
			}
		}
	}
	
	private static class RotateAdRunnable implements Runnable {
		private WeakReference<AdViewLayout> adViewLayoutReference;
		
		public RotateAdRunnable(AdViewLayout adViewLayout) {
			adViewLayoutReference = new WeakReference<AdViewLayout>(adViewLayout);
		}
		public void run() {
			AdViewLayout adViewLayout = adViewLayoutReference.get();
			if(adViewLayout != null) {
				adViewLayout.rotateAd();
			}
		}
	}
	private static class RotatePriAdRunnable implements Runnable {
		private WeakReference<AdViewLayout> adViewLayoutReference;
		
		public RotatePriAdRunnable(AdViewLayout adViewLayout) {
			adViewLayoutReference = new WeakReference<AdViewLayout>(adViewLayout);
		}
		public void run() {
			AdViewLayout adViewLayout = adViewLayoutReference.get();
			if(adViewLayout != null) {
				adViewLayout.rotatePriAd();
			}
		}
	}

	private static class GetConfigRunnable implements Runnable {
		private WeakReference<AdViewLayout> adViewLayoutReference;
		
		public GetConfigRunnable(AdViewLayout adViewLayout) {
			adViewLayoutReference = new WeakReference<AdViewLayout>(adViewLayout);
		}
		public void run() {
			if(AdViewTargeting.getRunMode()==RunMode.TEST)
				Log.i(AdViewUtil.ADVIEW, "GetConfigFromServer");
			AdViewLayout adViewLayout = adViewLayoutReference.get();
			if(adViewLayout != null) {
				if(adViewLayout.adViewManager != null){
					adViewLayout.adViewManager.fetchConfigFromServer();
				}
				adViewLayout.fetchConfigThreadedDelayed(adViewLayout.adViewManager.getConfigExpiereTimeout());
			}
		}
	}	
	
	private static class PingUrlRunnable implements Runnable {
		private String url;
		
		public PingUrlRunnable(String url) {
			this.url = url;
		}
		
		public void run() {
		
	        HttpClient httpClient = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(url); 

		 //Log.e(AdViewUtil.ADVIEW, "PingUrlRunnable, url="+url);
		
	        try {
	            httpClient.execute(httpGet);
	          
	        } catch (ClientProtocolException e) {
	        	if(AdViewTargeting.getRunMode()==RunMode.TEST)
	        		Log.e(AdViewUtil.ADVIEW, "Caught ClientProtocolException in PingUrlRunnable", e);
	        } catch (IOException e) {
	        	if(AdViewTargeting.getRunMode()==RunMode.TEST)
	        		Log.e(AdViewUtil.ADVIEW, "Caught IOException in PingUrlRunnable", e);
	        }
	        
	        httpClient.getConnectionManager().shutdown();
			
		}
	}

	private static class PingKyAdviewRunnable implements Runnable {
		private String url;
		private String content;
		
		public PingKyAdviewRunnable(String url, String content) {
			this.url = url;
			this.content = content;
		}
		
		public void run() {
		
	    	//String strResult=null;
	    	HttpPost httpRequest=new HttpPost(url);
	    	List<NameValuePair> params=new ArrayList<NameValuePair>();
	    	params.add(new BasicNameValuePair("name",content));
	    	HttpClient httpClient = new DefaultHttpClient();

		
	    	try {
	    	
	    		//����HTTP request
	    	     httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
	    
	    	     //ȡ��HTTP response
	    	     HttpResponse httpResponse=httpClient.execute(httpRequest);
	    	     if(httpResponse.getStatusLine().getStatusCode()==200){
	    	   
	    	    	 EntityUtils.toString(httpResponse.getEntity(),"UTF_8"); 
	    	        }else{
	    	        
	    	        }

	    	}catch(ClientProtocolException e){
	    	     	e.printStackTrace();
	    	} catch (UnsupportedEncodingException e) {
	    	    	e.printStackTrace();
	    	} catch (IOException e) {
	    	    	e.printStackTrace();
	    	}finally{
	    		httpClient.getConnectionManager().shutdown();
	    	}			
			
		}
	}		
}
