package com.adview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Extra;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;
import android.telephony.TelephonyManager;
import java.util.Locale;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class AdViewManager {
	public String keyAdView;
	
	private Extra extra;
	private List<Ration> rationsList;
	//private List<Ration> rationsList_pri;
	private double totalWeight = 0;
	private WeakReference<Context> contextReference;
	
	// Default config expire timeout is 30 minutes.
	public static int configExpireTimeout = 1800;
	private boolean needFetchEveryTime = false;
	
	Iterator<Ration> rollovers;
	Iterator<Ration> rollover_pri;
	
	public int mSimulator=0;
	
	private final static String PREFS_STRING_TIMESTAMP = "timestamp";
	private final static String PREFS_STRING_CONFIG = "config";
	private  boolean youmiInit=true;
	public  boolean bGetConfig=false;

	public boolean bLocationForeign=false;
	public String mLocation="";
	
	public AdViewManager(WeakReference<Context> contextReference, String keyAdView) {
		Log.i("Android", "Creating weivda reganam...");
		this.contextReference = contextReference;
		this.keyAdView = keyAdView;
		
		if(AdViewTargeting.getUpdateMode()==AdViewTargeting.UpdateMode.DEFAULT){
			needFetchEveryTime = false;
		}
		else if(AdViewTargeting.getUpdateMode()==AdViewTargeting.UpdateMode.EVERYTIME)
			needFetchEveryTime = true;
		else
		{
		
		}

		mSimulator = isSimulator();

		bLocationForeign = isLocateForeign();
		if (bLocationForeign==false)
			mLocation = "china";
		else
			mLocation = "foreign";
	}
	
	
	public void setYoumiInit(boolean flag) {
		youmiInit=flag;
	}
	public boolean getYoumiInit() {
		return youmiInit;
	}
	/*
	public static void setConfigExpireTimeout(int expireTimeout) {
		configExpireTimeout = expireTimeout;
	}
	*/
	
	public Extra getExtra() {
		if(totalWeight <= 0) {
		//	Log.i(AdViewUtil.ADVIEW, "Sum of ration weights is 0 - no ads to be shown");
			return null;
		}
		else {
			return this.extra;
		}
	}
	
	public int getConfigExpiereTimeout(){
		return configExpireTimeout;
	}
	
	public Ration getRation() {
		Random random = new Random();
		
		double r = random.nextDouble() * totalWeight;
		double s = 0;
		
				
		Iterator<Ration> it = this.rationsList.iterator();
		Ration ration = null;
		while(it.hasNext()) {
			ration = it.next();
			s += ration.weight;
			
			if(s >= r) {
				break;
			}
		}
		
		return ration;
	}
	
	public Ration getRollover() {
		if(this.rollovers == null) {
			return null;
		}
		
		Ration ration = null;
		if(this.rollovers.hasNext()) {
			ration = this.rollovers.next();
		}
		else
		{
			ration = getRation();
		}
		
		return ration;
	}
	
	public Ration getRollover_pri() {
		int max=100000000;
		if(this.rollover_pri == null) {
			return null;
		}
		
		Ration ration = null;
		Ration ration_pri=null;
		while(this.rollover_pri.hasNext())
		{
		
			ration = this.rollover_pri.next();
			if(ration.priority<max)
			{
				ration_pri=ration;
				max=ration.priority;
			}
		
		}
		
		return ration_pri;
	}
	
	public void resetRollover_pri() {
		//this.rollovers = this.rationsList.iterator();
		//this.rollover_pri=this.rationsList_pri.iterator();
	}
	public void resetRollover() {
		this.rollovers = this.rationsList.iterator();
	}
	

	private String getLocalConfig(String sdkkey) {
		if (sdkkey == null || sdkkey.length() == 0)
		{
			return null;
		}

		Context context = contextReference.get();
		InputStream is;
		String filename=sdkkey+".txt";
		String localconfig=null;
		
		try {
			is = context.getAssets().open(filename);
			localconfig=convertStreamToString(is);
			is.close();
		} catch (Exception e) {
			if(AdViewTargeting.getRunMode()==RunMode.TEST)
				Log.i(AdViewUtil.ADVIEW, e.toString());

			return null;
		}

		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.i(AdViewUtil.ADVIEW, "localconfig="+localconfig);
		
		return localconfig;
	}
	
    public void fetchConfig() {
    	Context context = contextReference.get();
    	
		// If the context is null here something went wrong with initialization.
    	if(context == null) {
    		return;
    	}
    	
	    SharedPreferences adViewPrefs = context.getSharedPreferences(keyAdView, Context.MODE_PRIVATE);
	    String jsonString = adViewPrefs.getString(PREFS_STRING_CONFIG, null);

	    if((jsonString == null) || (needFetchEveryTime == true)) {
	
	        HttpClient httpClient = new DefaultHttpClient();
	        String url = String.format(AdViewUtil.urlConfig, this.keyAdView, AdViewUtil.VERSION, mSimulator, mLocation);
	        HttpGet httpGet = new HttpGet(url); 
	        HttpResponse httpResponse;
	        try {
	            httpResponse = httpClient.execute(httpGet);
	     
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			;//Log.i(AdViewUtil.ADVIEW, "getStatusCode="+httpResponse.getStatusLine().getStatusCode());
		
	            if(httpResponse.getStatusLine().getStatusCode()==200){
	            HttpEntity entity = httpResponse.getEntity();
	 
	            if (entity != null) {
	                InputStream inputStream = entity.getContent();
	                String jsonStringHttp = "";
	                jsonStringHttp = convertStreamToString(inputStream); 		
	                if(jsonStringHttp.length() > 0){
				if (checkConfigurationString(jsonStringHttp)==true)	
				{
					jsonString = jsonStringHttp;
					SharedPreferences.Editor editor = adViewPrefs.edit();
					editor.putString(PREFS_STRING_CONFIG, jsonString);
					editor.putLong(PREFS_STRING_TIMESTAMP, System.currentTimeMillis());
					editor.commit();
					bGetConfig=true;
				}
	                }
	                	
	            }
	            
	            }
	        } catch (ClientProtocolException e) {
			if(AdViewTargeting.getRunMode()==RunMode.TEST)
				Log.i(AdViewUtil.ADVIEW, e.toString());	  
	        } catch (IOException e) {
	    		if(AdViewTargeting.getRunMode()==RunMode.TEST)
				Log.i(AdViewUtil.ADVIEW, e.toString());
	        }

		httpClient.getConnectionManager().shutdown();

		if(jsonString == null)
	 	{
	 		jsonString = getLocalConfig(this.keyAdView);
			parseOfflineConfigurationString(jsonString);
			return;
	 	}
	    }
	    else {
	 
	    }
        
        parseConfigurationString(jsonString);
    }
    
    public void fetchConfigFromServer() {
    	Context context = contextReference.get();
	    SharedPreferences adViewPrefs = context.getSharedPreferences(keyAdView, Context.MODE_PRIVATE);    	
	    String jsonString = "";
        HttpClient httpClient = new DefaultHttpClient();
        
        String url = String.format(AdViewUtil.urlConfig, this.keyAdView, AdViewUtil.VERSION, mSimulator, mLocation);
        HttpGet httpGet = new HttpGet(url); 
 
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(httpGet);
     
       
            if(httpResponse.getStatusLine().getStatusCode()==200){
	            HttpEntity entity = httpResponse.getEntity();
	 
	            if (entity != null) {
	                InputStream inputStream = entity.getContent();
	                jsonString = convertStreamToString(inputStream); 
	            }
            }
        } catch (ClientProtocolException e) {
  		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.i(AdViewUtil.ADVIEW, e.toString());
        } catch (IOException e) {
 		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.i(AdViewUtil.ADVIEW, e.toString());   
        }finally{
        	httpClient.getConnectionManager().shutdown();	
        }
        if(jsonString.length() > 0 && checkConfigurationString(jsonString) == true){
        	//parseConfigurationString(jsonString);
        	//if(this.rationsList.size() > 0)
		{
                SharedPreferences.Editor editor = adViewPrefs.edit();
                editor.putString(PREFS_STRING_CONFIG, jsonString);
                editor.commit();       		
        	}
        }
    }
    
	private String convertStreamToString(InputStream is) {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8192);
	    StringBuilder sb = new StringBuilder();
	
	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } 
	    catch (IOException e) {
        
        	return null;
	    } 
	    finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	    
	            return null;
	        }
	    }
	    
	    return sb.toString();
	}

    private boolean checkConfigurationString(String jsonString) {
	boolean ret=false;
	
    	if(AdViewTargeting.getRunMode()==RunMode.TEST)
    		;//Log.d(AdViewUtil.ADVIEW, "checkConfigurationString, Received jsonString=" + jsonString);
    	
    	try {    
	        JSONObject json = new JSONObject(jsonString);
		 if (json.has("extra") && json.has("rations")) 
	        	ret=true;
    	}
    	catch (JSONException e) {
    		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.i(AdViewUtil.ADVIEW, e.toString());
			
    		ret=false;
    	}
    	catch (NullPointerException e) {
    		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.i(AdViewUtil.ADVIEW, e.toString());
			
    		ret=false;
    	}

	if(AdViewTargeting.getRunMode()==RunMode.TEST)
    		Log.d(AdViewUtil.ADVIEW, "ret=" + ret);
	
	return ret;	
    }
	
    private void parseConfigurationString(String jsonString) {
    	//*
    	if(AdViewTargeting.getRunMode()==RunMode.TEST)
    		Log.d(AdViewUtil.ADVIEW, "Received jsonString: " + jsonString);
    	//*/
    	try {    
	        JSONObject json = new JSONObject(jsonString);
	
	        parseExtraJson(json.getJSONObject("extra"));
	        parseRationsJson(json.getJSONArray("rations"));
    	}
    	catch (JSONException e) {
    	
    		this.extra = new Extra();
    	}
    	catch (NullPointerException e) {
    	
    		this.extra = new Extra();
    	}
    }

    private void parseOfflineConfigurationString(String jsonString) {
    	if(AdViewTargeting.getRunMode()==RunMode.TEST)
    		Log.d(AdViewUtil.ADVIEW, "Received jsonString: " + jsonString);
	
    	try {    
	        JSONObject json = new JSONObject(jsonString);

		if (bLocationForeign==false)
		{
			json = json.getJSONObject("china_cfg");
		}
		else
		{
			json = json.getJSONObject("foreign_cfg");
		}
		
	        parseExtraJson(json.getJSONObject("extra"));
	        parseRationsJson(json.getJSONArray("rations"));
    	}
    	catch (JSONException e) {

		//location is off
	    	try {    
		        JSONObject json = new JSONObject(jsonString);
			
		        parseExtraJson(json.getJSONObject("extra"));
		        parseRationsJson(json.getJSONArray("rations"));
	    	}
	    	catch (JSONException e2) {
	    	
	    		this.extra = new Extra();
	    	}
		catch (NullPointerException e2) {
    	
	    		this.extra = new Extra(); 
    		}	
    	}
    	catch (NullPointerException e) {
    	
    		this.extra = new Extra(); 
    	}
    }
	
    private void parseExtraJson(JSONObject json) {
    	Extra extra = new Extra();
    	
    	try {    
	        extra.cycleTime = json.getInt("cycle_time");
	        extra.locationOn = json.getInt("loacation_on");
	        extra.transition = json.getInt("transition");
	        extra.report=json.getString("report");
	        AdViewUtil.urlImpression="http://"+extra.report+"/agent/agent2.php?appid=%s&nid=%s&type=%d&uuid=%s&country_code=%s&appver=%d&client=0&simulator=%d";
	        AdViewUtil.urlClick="http://"+extra.report+"/agent/agent3.php?appid=%s&nid=%s&type=%d&uuid=%s&country_code=%s&appver=%d&client=0&simulator=%d";
		 AdViewUtil.appReport="http://"+extra.report+"/agent/appReport.php?keyAdView=%s&keyDev=%s&typeDev=%s&osVer=%s&resolution=%s&servicePro=%s&netType=%s&channel=%s&platform=%s";
		 
	        JSONObject backgroundColor = json.getJSONObject("background_color_rgb");
	        extra.bgRed = backgroundColor.getInt("red");
	        extra.bgGreen = backgroundColor.getInt("green");
	        extra.bgBlue = backgroundColor.getInt("blue");
	        extra.bgAlpha = backgroundColor.getInt("alpha") * 255;
	        
	        JSONObject textColor = json.getJSONObject("text_color_rgb");
	        extra.fgRed = textColor.getInt("red");
	        extra.fgGreen = textColor.getInt("green");
	        extra.fgBlue = textColor.getInt("blue");
	        extra.fgAlpha = textColor.getInt("alpha") * 255;
    	}
    	
    	catch (JSONException e) {
    	
    	}
      	this.extra = extra;
    }
    
    private void parseRationsJson(JSONArray json) {
    	List<Ration> rationsList = new ArrayList<Ration>();
    	//List<Ration> rationsList_pri =new ArrayList<Ration>();
    	List<Ration> rationsList_ex = new ArrayList<Ration>();
    	//List<Ration> rationsList_pri_ex =new ArrayList<Ration>();
    	double totalweight = 0;
    	double totalweight_ex = 0;
    	
    	//this.totalWeight = 0;
    	
    	
    	try {
	    	int i;
	    	for(i=0; i<json.length(); i++) {
				JSONObject jsonRation = json.getJSONObject(i); 
				if(jsonRation == null) {
					continue;
				}
				
				Ration ration = new Ration();

			    ration.nid = jsonRation.getString("nid");
			    ration.type = jsonRation.getInt("type");
			    ration.name = jsonRation.getString("nname");
			    ration.weight = jsonRation.getInt("weight");
			    ration.priority = jsonRation.getInt("priority");
			    
			    
			    switch(ration.type) {
			    case AdViewUtil.NETWORK_TYPE_YOUMI:
			    case AdViewUtil.NETWORK_TYPE_SMARTAD:
			    case AdViewUtil.NETWORK_TYPE_WQ:
			    case AdViewUtil.NETWORK_TYPE_SMAATO:
			    case AdViewUtil.NETWORK_TYPE_UMENG:	
			    case AdViewUtil.NETWORK_TYPE_ADUU:
			    	ration.key = jsonRation.getString("key");
			    	ration.key2 = jsonRation.getString("key2");
			    	break;
			    case AdViewUtil.NETWORK_TYPE_ADVIEWAD:
			    	
			    	ration.key = jsonRation.getString("key");
			    	ration.key2 = jsonRation.getString("key2");
			    	ration.type2=jsonRation.getInt("type2");
			    	ration.logo=jsonRation.getString("logo");
			    	break;
			    case AdViewUtil.NETWORK_TYPE_BAIDU:
			    	ration.key3=jsonRation.getString("key3");
			    default:
				    ration.key = jsonRation.getString("key");
				    break;
			    }
				
			    switch(ration.type) {
			    //china AD
			    case AdViewUtil.NETWORK_TYPE_WOOBOO:
			    case AdViewUtil.NETWORK_TYPE_YOUMI:	
			    case AdViewUtil.NETWORK_TYPE_KUAIYOU:
			    case AdViewUtil.NETWORK_TYPE_CASEE:	
			    case AdViewUtil.NETWORK_TYPE_WIYUN:
			    case AdViewUtil.NETWORK_TYPE_ADCHINA:	
			    case AdViewUtil.NETWORK_TYPE_ADVIEWAD:
			    case AdViewUtil.NETWORK_TYPE_SMARTAD:	
			    case AdViewUtil.NETWORK_TYPE_DOMOB:
			    case AdViewUtil.NETWORK_TYPE_VPON:	
			    case AdViewUtil.NETWORK_TYPE_ADTOUCH:
			    case AdViewUtil.NETWORK_TYPE_ADWO:	
			    case AdViewUtil.NETWORK_TYPE_AIRAD:
			    case AdViewUtil.NETWORK_TYPE_WQ:	
			    case AdViewUtil.NETWORK_TYPE_APPMEDIA:
			    case AdViewUtil.NETWORK_TYPE_TINMOO:	
			    case AdViewUtil.NETWORK_TYPE_BAIDU:
			    case AdViewUtil.NETWORK_TYPE_LSENSE:	
			    case AdViewUtil.NETWORK_TYPE_YINGGAO:
			    case AdViewUtil.NETWORK_TYPE_IZPTEC:	
			    case AdViewUtil.NETWORK_TYPE_ADSAGE:	
			    case AdViewUtil.NETWORK_TYPE_UMENG:
			    case AdViewUtil.NETWORK_TYPE_FRACTAL:
			    case AdViewUtil.NETWORK_TYPE_LMMOB:		
			    case AdViewUtil.NETWORK_TYPE_MOBWIN:
			    case AdViewUtil.NETWORK_TYPE_SUIZONG:	
			    case AdViewUtil.NETWORK_TYPE_ADUU:
			    {
			    	//if(ration.weight > 0)
				{
			    		rationsList.add(ration);
			    		totalweight += ration.weight;
			    	}
/*			    	if(ration.priority > 0){
			    		rationsList_pri.add(ration);
			    	}*/
			    	break;
			    }
			    //abroad ad
			    case AdViewUtil.NETWORK_TYPE_ADMOB:
			    case AdViewUtil.NETWORK_TYPE_GREYSTRIP:	
			    case AdViewUtil.NETWORK_TYPE_INMOBI:
			    case AdViewUtil.NETWORK_TYPE_MDOTM:	
			    case AdViewUtil.NETWORK_TYPE_ZESTADZ:
			    case AdViewUtil.NETWORK_TYPE_MILLENNIAL:	
			    case AdViewUtil.NETWORK_TYPE_SMAATO:
			    {
			    	//if(ration.weight > 0)
				{
			    		rationsList_ex.add(ration);
			    		totalweight_ex += ration.weight;
			    	}
/*			    	if(ration.priority > 0){
			    		rationsList_pri_ex.add(ration);
			    	}*/	
			    	break;
			    }  
			    default:
			    	break;
			    }
	    	}
    	}
    	catch (JSONException e) {
    	
		}
    	//if set the location optimizing 
  /*  	if(this.extra.locationOn == 0){
    		//if the location is in China
    		if(isLocateForeign() == false){
    			if(rationsList.size() <= 0){
    				rationsList = rationsList_ex;
    				totalweight = totalweight_ex;
    			}
//    			if(rationsList_pri.size() <= 0){
//    				rationsList_pri = rationsList_pri_ex;
 //   			}
    		}else{
    			if(rationsList_ex.size() > 0){
    				rationsList = rationsList_ex;
    				totalweight = totalweight_ex;
    				//rationsList_pri = rationsList_pri_ex;
    			}
    		}
    	}else
*/
    	{
    		rationsList.addAll(rationsList_ex);
    		totalweight += totalweight_ex;
    		//rationsList_pri.addAll(rationsList_pri_ex);
    	}
    	
    	Collections.sort(rationsList);
    	this.rationsList = rationsList;
    	this.rollovers = this.rationsList.iterator();
    	this.totalWeight = totalweight;
/*    	
    	Collections.sort(rationsList_pri);
    	this.rationsList_pri=rationsList_pri;
    	this.rollover_pri=rationsList_pri.iterator();
*/    	
    	
    }    

	public boolean isLocateForeign() {
		Context context = contextReference.get();
	    	if(context == null) {
	    		return false;
	    	}
	    
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		
		String imei = tm.getDeviceId();
		if ((imei == null) || (imei.equals("000000000000000"))) {
			if(AdViewTargeting.getRunMode()==RunMode.TEST)
				Log.d(AdViewUtil.ADVIEW, "There is no imei, or run in emulator");
			return false;
		}
		else {
			//Log.d(AdViewUtil.ADVIEW, "run in device, imei="+imei);
		}

		String countryCodeDefault = Locale.getDefault().getCountry().toLowerCase();
		String countryCodeNetwork = tm.getNetworkCountryIso().toLowerCase();
		String locale = Locale.getDefault().toString();

		if(AdViewTargeting.getRunMode()==RunMode.TEST)
		{
			Log.d(AdViewUtil.ADVIEW, "run in device, imei="+imei);
			Log.d(AdViewUtil.ADVIEW, "countryCodeDefault="+countryCodeDefault);
			Log.d(AdViewUtil.ADVIEW, "countryCodeNetwork="+countryCodeNetwork);
			Log.d(AdViewUtil.ADVIEW, "locale="+locale);
		}
		
		if (countryCodeNetwork != null && countryCodeNetwork.length()>0)
		{
			if (countryCodeNetwork.compareTo("cn")==0)
				return false;
			else
				return true;
		}

		if (countryCodeDefault != null && countryCodeDefault.length()>0)
		{
			if (countryCodeDefault.compareTo("cn")==0)
				return false;
			else
				return true;
		}

		try
		{
			String serviceName = "location";
			LocationManager locationManager = (LocationManager)context.getSystemService(serviceName);
			Criteria criteria = new Criteria();
			criteria.setAccuracy(1);
			criteria.setAltitudeRequired(false);
			criteria.setBearingRequired(false);
			criteria.setCostAllowed(true);
			criteria.setPowerRequirement(1);

			String provider = locationManager.getBestProvider(criteria, true);
			Location location=null;

			if (provider != null && provider.length()>0)
			{
				Log.d(AdViewUtil.ADVIEW, "provider="+provider);
				Log.d(AdViewUtil.ADVIEW, provider+" enable ="+locationManager.isProviderEnabled(provider));
				location = locationManager.getLastKnownLocation(provider);
			}
			
			if (location != null) {
				Log.d(AdViewUtil.ADVIEW, "location != null");
				
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				String locationString = (lat + "," + lng);
				Log.d(AdViewUtil.ADVIEW, "locationString="+locationString);
				
			} 
			else 
				Log.d(AdViewUtil.ADVIEW, "location == null");
		}
		catch (Exception e) {
			Log.i(AdViewUtil.ADVIEW, e.toString());
		}
		
		return false;
	}
	
	private int isSimulator() {
		Context context = contextReference.get();
	    	if(context == null) {
	    		return 0;
	    	}
			
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		int ret;
		
		String imei = tm.getDeviceId();
		if ((imei == null) || (imei.equals("000000000000000"))) {
			ret =  1;
		}
		else {
			ret = 0;
		}

		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "isSimulator, ret="+ret);
		
		return ret;
	}	
	
}
