欢迎使用AdView Android SDK，开发者可以在自己的程序中嵌入AdView，实现众多广告公司播放的聚合，优化！如果你愿意，还有激动人心的应用互推功能！

注意：
1.集成友盟(umeng)广告的时候，需要把umeng_res目录下的资源文件也放到项目中；

2.支持本地默认缓存功能，从adview后台点击“离线配置下载”下载配置文件，保存在<assets>文件夹中就可以了，确保任何情况下都能展示广告；

3.Adview SDK支持的android最小版本升级到1.6;

4.支持帷千,亿赞普的时候，需要把<uses-sdk android:minSdkVersion="4"/>给删除掉，否则广告显示的会缩小；如果去掉<uses-sdk android:minSdkVersion="4"/>的话，对于高分辨率的手机后台统计的就不是准了；

5.腾讯聚赢升级到1.3后，包括Tencent_MobWIN_BASIC_1.2.jar,Tencent_MobWIN_SDK_1.3.jar, libs\armeabi\liblbs.so, 为了更好的支持lbs可以加上<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>；

6.admob升级到4.3.1，需要android 3.2以上才能编译通过；AndroidManifest.xml中需要配置android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"/>, sample中这部分注释掉了，需要支持的时候打开; android:configChanges="orientation|keyboard|keyboardHidden"是为了在低版本编译用的，到时去掉；

7.开发者可以设置渠道，在Manifest文件中<meta-data android:name="AdView_CHANNEL" android:value="GFAN"></meta-data> (AdViewTargeting.setChannel接口已经作废); 目前支持的渠道包括下面列出的，移动广告观察(http://t.adview.cn/)中有各个市场的链接：
EOE(优亿市场)
GOOGLEMARKET(谷歌电子市场)
APPCHINA(应用汇)
HIAPK(安卓市场)
GFAN(机锋)
GOAPK(安智)
NDUOA(N多网)
91Store(手机91)
LIQUCN(历趣)
WAPTW(天网)
ANDROIDCN(安卓中国)
GGDWON(G友网)
ANDROIDAI(安卓之家)
STARANDROID(安卓星空)
ANDROIDD(安致)
YINGYONGSO(应用搜)
IMOBILE(手机之家)
SOUAPP(搜应用)
MUMAYI(木蚂蚁)
MOBIOMNI(欧米)
PAOJIAO(泡椒网)
AIBALA(爱扒拉市场)
COOLAPK(酷安网)
ANFONE(安丰)
APKOK(乐致网)

如果不配置，或配置其他的值，一律作为"OTHER"处理；



当前AdView更新版本:1.6.8
目录结构介绍：
libs：包括AdView的SDK和各个广告公司的SDK,其中：
	库名					广告平台				版本
	AdViewSDK_Android.jar:			AdView的SDK				1.6.8
	adchina_android_sdk.jar:		易传媒广告公司的SDK			2.5.1
	adlib_android.jar:			哇棒广告公司的SDK			2.2	
	adOn-3.2.4.jar:				Vpon广告公司的SDK			3.2.4
	adtouch-embed-sdk-1.1.0.jar: 		AdTouch公司的SDK			1.1.0
	aduu-sdk.jar: 				优友传媒的SDK				3.0.3
	adwosdk2.5.1.jar:  			AdWo广告公司的SDK			2.5.1	
	ad-fractalist-sdk-android.jar:		飞云广告公司的SDK			2.0
	airAD_basic_sdk.jar: 			AirAD广告公司的SDK			1.2.7
	android_api.jar: 			百度移动联盟的sdk			1.0
	AppMediaAdAndroidSdk-1.1.0.jar: 	AppMedia广告公司的sdk			1.1.0
	casee-ad-sdk-3.0.jar:			架势无线广告公司的SDK			3.0
	domob_android_sdk-2.0.2.jar:		多盟广告公司的SDK			2.2.0
	GoogleAdMobAdsSdk-4.3.1.jar:		AdMOB广告公司的SDK			4.3.1	
	gssdk_1.6.1.jar:			GreyStripe广告公司的SDK			1.6.1
	InMobiAndroidSDK.jar			InMobi广告公司的SDK			3.0.1
	IZPView.jar:				易赞普广告公司的SDK			1.0.3
	l_android_adsdk.jar:			百分通联的SDK				2.1.0.0
	LMMOB_SDK.jar:				力美广告公司的SDK			2.0
	mdotm-sdk-android.jar:			MdotM广告公司的SDK			3.0.0
	MMAdView.jar:				MillennialMedia 广告公司的SDK		4.2.6
	mobisageSDK.jar:			艾德斯奇广告公司的SDK			2.3.0
	mobplate-android-sdk-1.02.jar		随踪广告公司的SDK			1.02
	smartmad-sdk-android.jar:		亿动智道广告公司的SDK			2.0.1
	SOMAAndroidSDK2.5.4.jar:		SOMA广告公司的SDK			2.5.4

	Tencent_MobWIN_BASIC_1.2.jar:		腾讯广告公司的SDK			1.3
	Tencent_MobWIN_SDK_1.3.jar:		腾讯广告公司的SDK			1.3
	liblbs.so:				腾讯广告公司的SDK			1.3

	tinmoo-android-sdk_v3.1.0.jar:		天幕(Tinmoo)广告公司的sdk		2.0.2
	umeng_ad_android_sdk_v1.4.2.jar:	友盟广告公司的SDK			1.4.2
	WiAd.jar:				微云广告公司的SDK			1.2.3	
	winAd-android-sdk.jar:			赢告广告公司的SDK			2.0
	WQAndroidSDK_2.0.2.jar: 		惟千广告公司sdk				2.0.2
	youmi-android_v3.05.jar:		有米广告公司的SDK			3.05
	zestadz_sdk_androidv1.2.jar:		ZestADZ广告公司的SDK			1.2
	

