

package com.adview.util;

public class AdViewUtil {
	public static final String urlConfig = "http://www.adview.cn/agent/agent1_android.php?appid=%s&appver=%d&client=0&simulator=%d&location=%s";
	public static  String urlImpression = "http://www.adview.cn/agent/agent2.php?appid=%s&nid=%s&type=%d&uuid=%s&country_code=%s&appver=%d&client=0&simulator=%d";
	
	public static  String urlClick = "http://www.adview.cn/agent/agent3.php?appid=%s&nid=%s&type=%d&uuid=%s&country_code=%s&appver=%d&client=0&simulator=%d";
	public static  String appReport = "http://www.adview.cn/agent/appReport.php?keyAdView=%s&keyDev=%s&typeDev=%s&osVer=%s&resolution=%s&servicePro=%s&netType=%s&channel=%s&platform=%s";
		
	// Don't change anything below this line
	/***********************************************/ 
	 
	public static final int VERSION = 168;

	public static final String ADVIEW = "AdView SDK v1.6.8";
	
	// Could be an enum, but this gives us a slight performance improvement
	//abroad
	public static final int NETWORK_TYPE_ADMOB = 1;
	public static final int NETWORK_TYPE_GREYSTRIP = 2;
	public static final int NETWORK_TYPE_INMOBI = 3;
	public static final int NETWORK_TYPE_MDOTM = 4;
	public static final int NETWORK_TYPE_ZESTADZ = 5;
	public static final int NETWORK_TYPE_MILLENNIAL = 6;
	public static final int NETWORK_TYPE_SMAATO = 7;
	
	//the below is china
	public static final int NETWORK_TYPE_WOOBOO=21;
	public static final int NETWORK_TYPE_YOUMI=22;
	public static final int NETWORK_TYPE_KUAIYOU=23;
	public static final int NETWORK_TYPE_CASEE=24;
	public static final int NETWORK_TYPE_WIYUN=25;
	public static final int NETWORK_TYPE_ADCHINA=26;
	public static final int NETWORK_TYPE_ADVIEWAD=28;
	public static final int NETWORK_TYPE_SMARTAD=29;
	public static final int NETWORK_TYPE_DOMOB=30;
	public static final int NETWORK_TYPE_VPON=31;
	public static final int NETWORK_TYPE_ADTOUCH=32;
	public static final int NETWORK_TYPE_ADWO=33;
	public static final int NETWORK_TYPE_AIRAD=34;
	public static final int NETWORK_TYPE_WQ=35;
	public static final int NETWORK_TYPE_APPMEDIA=36;
	public static final int NETWORK_TYPE_TINMOO=37;
	public static final int NETWORK_TYPE_BAIDU=38;
	public static final int NETWORK_TYPE_LSENSE=39;
	public static final int NETWORK_TYPE_YINGGAO=40;
	public static final int NETWORK_TYPE_IZPTEC=41;
	public static final int NETWORK_TYPE_ADSAGE=42;
	public static final int NETWORK_TYPE_UMENG=43;
	public static final int NETWORK_TYPE_FRACTAL=44;
	public static final int NETWORK_TYPE_LMMOB=45;	
	public static final int NETWORK_TYPE_MOBWIN=46;
	public static final int NETWORK_TYPE_SUIZONG=47;	
	public static final int NETWORK_TYPE_ADUU=48;
	
	public static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;	
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }
}
