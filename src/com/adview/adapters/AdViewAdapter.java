

package com.adview.adapters;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import android.util.Log;

import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;

import com.adview.AdViewTargeting.RunMode;
import com.adview.obj.Ration;
import com.adview.util.AdViewUtil;

public abstract class AdViewAdapter {
	protected final WeakReference<AdViewLayout> adViewLayoutReference;
	protected Ration ration;
	
	
	public AdViewAdapter(AdViewLayout adViewLayout, Ration ration) {
		this.adViewLayoutReference = new WeakReference<AdViewLayout>(adViewLayout);
		this.ration = ration;
		
	}
	
	private static AdViewAdapter getAdapter(AdViewLayout adViewLayout, Ration ration) {	
		try {
			switch(ration.type) {
				case AdViewUtil.NETWORK_TYPE_ADMOB:
					if(Class.forName("com.google.ads.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.AdMobAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}  
				
					
				case AdViewUtil.NETWORK_TYPE_MILLENNIAL:
					if(Class.forName("com.millennialmedia.android.MMAdView") != null) {
                        return getNetworkAdapter("com.adview.adapters.MillennialAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				
				case AdViewUtil.NETWORK_TYPE_WOOBOO:
					if(Class.forName("com.wooboo.adlib_android.WoobooAdView") != null) {
						return getNetworkAdapter("com.adview.adapters.WoobooAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_YOUMI:
					if(Class.forName("net.youmi.android.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.YoumiAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
			
				case AdViewUtil.NETWORK_TYPE_CASEE:
					if(Class.forName("cn.casee.adsdk.CaseeAdView") != null) {
						return getNetworkAdapter("com.adview.adapters.CaseeAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_WIYUN:
					if(Class.forName("com.wiyun.ad.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.WiyunAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_ADCHINA:
					if(Class.forName("com.adchina.android.ads.views.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.AdChinaAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_ADVIEWAD:
					if(Class.forName("com.adview.ad.KyAdView") != null) {
						return getNetworkAdapter("com.adview.adapters.AdViewHouseAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_DOMOB:
					if(Class.forName("cn.domob.android.ads.DomobAdView") != null) {
						return getNetworkAdapter("com.adview.adapters.DomobAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_SMARTAD:
					if(Class.forName("com.madhouse.android.ads.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.SmartAdAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_VPON:
					if(Class.forName("com.vpon.adon.android.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.VponAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_ADTOUCH:
					if(Class.forName("com.energysource.szj.embeded.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.AdTouchAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_ADWO:
					if(Class.forName("com.adwo.adsdk.AdwoAdView") != null) {
						return getNetworkAdapter("com.adview.adapters.AdwoAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_AIRAD:
					if(Class.forName("com.mt.airad.AirAD") != null) {
						return getNetworkAdapter("com.adview.adapters.AirAdAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_WQ:
					if(Class.forName("com.wqmobile.sdk.widget.ADView") != null) {
						return getNetworkAdapter("com.adview.adapters.WqAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_APPMEDIA:
					if(Class.forName("cn.appmedia.ad.BannerAdView") != null) {
						return getNetworkAdapter("com.adview.adapters.AppMediaAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_TINMOO:
					if(Class.forName("com.ignitevision.android.ads.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.TinmooAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_BAIDU:
					if(Class.forName("com.baidu.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.AdBaiduAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_LSENSE:
					if(Class.forName("com.l.adlib_android.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.LsenseAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_YINGGAO:
					if(Class.forName("com.winad.android.ads.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.WinAdAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_ZESTADZ:
					if(Class.forName("com.zestadz.android.ZestADZAdView") != null) {
						return getNetworkAdapter("com.adview.adapters.ZestADZAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_SMAATO:
					if(Class.forName("com.smaato.SOMA.SOMABanner") != null) {
						return getNetworkAdapter("com.adview.adapters.SmaatoAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_GREYSTRIP:
					if(Class.forName("com.greystripe.android.sdk.BannerView") != null) {
						return getNetworkAdapter("com.adview.adapters.GreystripeAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_MDOTM:
					if(Class.forName("com.mdotm.android.ads.MdotMView") != null) {
						return getNetworkAdapter("com.adview.adapters.MdotMAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}		
				case AdViewUtil.NETWORK_TYPE_INMOBI:
					if(Class.forName("com.inmobi.androidsdk.IMAdView") != null) {
						return getNetworkAdapter("com.adview.adapters.InmobiAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}	
				case AdViewUtil.NETWORK_TYPE_ADSAGE:
					if(Class.forName("com.mobisage.android.MobiSageAdBanner") != null) {
						return getNetworkAdapter("com.adview.adapters.MobiSageAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_IZPTEC:
					if(Class.forName("com.izp.views.IZPView") != null) {
						return getNetworkAdapter("com.adview.adapters.IzpAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}	
				case AdViewUtil.NETWORK_TYPE_UMENG:
					if(Class.forName("com.umengAd.android.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.UmengAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}	
				case AdViewUtil.NETWORK_TYPE_FRACTAL:
					if(Class.forName("com.fractalist.android.ads.ADView") != null) {
						return getNetworkAdapter("com.adview.adapters.FractalAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_LMMOB:
					if(Class.forName("com.lmmob.ad.sdk.LmMobAdView") != null) {
						return getNetworkAdapter("com.adview.adapters.LmMobAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}	
				case AdViewUtil.NETWORK_TYPE_MOBWIN:
					if(Class.forName("com.tencent.mobwin.AdView") != null) {
                        			return getNetworkAdapter("com.adview.adapters.MobWinAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_SUIZONG:
					if(Class.forName("com.suizong.mobplate.ads.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.SuizongAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				case AdViewUtil.NETWORK_TYPE_ADUU:
					if(Class.forName("cn.aduu.adsdk.AdView") != null) {
						return getNetworkAdapter("com.adview.adapters.AduuAdapter", adViewLayout, ration);
					}
					else {
						return unknownAdNetwork(adViewLayout, ration);
					}
				default:
					return unknownAdNetwork(adViewLayout, ration);
			}
		}
		catch(ClassNotFoundException e) {
			return unknownAdNetwork(adViewLayout, ration);
		}
		catch(VerifyError e) {
			if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.e("AdView", "YYY - Caught VerifyError", e);
          return unknownAdNetwork(adViewLayout, ration);
		}
	}
	
  private static AdViewAdapter getNetworkAdapter(String networkAdapter, AdViewLayout adViewLayout, Ration ration) {
	  AdViewAdapter adViewAdapter = null;

	  try {
    	  @SuppressWarnings("unchecked")
    	  Class<? extends AdViewAdapter> adapterClass = (Class<? extends AdViewAdapter>) Class.forName(networkAdapter);
    	  
    	  Class<?>[] parameterTypes = new Class[2];
    	  parameterTypes[0] = AdViewLayout.class;
    	  parameterTypes[1] = Ration.class;
    	  
    	  Constructor<? extends AdViewAdapter> constructor = adapterClass.getConstructor(parameterTypes);
    	 
    	  Object[] args = new Object[2];
    	  args[0] = adViewLayout;
    	  args[1] = ration;
    	  
    	  adViewAdapter = constructor.newInstance(args);
	  }
	  catch(ClassNotFoundException e) {}
	  catch(SecurityException e) {}
	  catch(NoSuchMethodException e) {}
	  catch(InvocationTargetException e) {}
	  catch(IllegalAccessException e) {}
	  catch(InstantiationException e) {}
	  
	  return adViewAdapter;
	}
	
	private static AdViewAdapter unknownAdNetwork(AdViewLayout adViewLayout, Ration ration) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.w(AdViewUtil.ADVIEW, "Unsupported ration type: " + ration.type);
		return null;
	}
	
	public static void handle(AdViewLayout adViewLayout, Ration ration)  {
      AdViewAdapter adapter = AdViewAdapter.getAdapter(adViewLayout, ration);
      if(adapter != null) {
    	  if(AdViewTargeting.getRunMode()==RunMode.TEST)
    		  Log.d(AdViewUtil.ADVIEW, "Valid adapter, calling handle()");
         adapter.handle();
      }
      else {
    	adViewLayout.adViewManager.resetRollover();
    	adViewLayout.rotateThreadedNow();
       
        
      }
	}
	
	public abstract void handle();

	
  }
