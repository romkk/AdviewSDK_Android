package com.adview;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class AdViewTargeting {
	private static RunMode runMode;
	private static UpdateMode updateMode;
	private static AdArea adArea;
	private static Gender gender;
	private static GregorianCalendar birthDate;
	private static String postalCode;
	private static String keywords;
	private static Set<String> keywordSet;
	private static Channel channel;
	
	

	static {
		resetData();
	}
	
	private static void resetData() {
		AdViewTargeting.runMode = RunMode.NORMAL;
		AdViewTargeting.updateMode=UpdateMode.DEFAULT;
		AdViewTargeting.adArea=AdArea.BOTTOM;
		AdViewTargeting.gender = Gender.UNKNOWN;
		AdViewTargeting.birthDate = null;
		AdViewTargeting.postalCode = null;
		AdViewTargeting.keywords = null;
		AdViewTargeting.keywordSet = null;
		AdViewTargeting.channel=Channel.OTHER;
		
		
	}
	
	public static enum Channel{
		ADVIEW,EOE,GOOGLEMARKET,APPCHINA,HIAPK,GFAN,GOAPK,NDUOA,OTHER
	}
	public static enum AdArea{
		TOP,BOTTOM
	}
	
	public static enum Gender {
		UNKNOWN, MALE, FEMALE
	}
	public static enum UpdateMode{
		DEFAULT, EVERYTIME
	}
	public static enum RunMode{
		NORMAL,TEST
	}
	/**
	 * 获取当前渠道模式,渠道包括ADVIEW,EOE,GOOGLEMARKET,APPCHINA,HIAPK,GFAN,GOAPK,NDUOA,OTHER.
	 * 用法举例:Channel channel=AdViewTargeting.getChannel();
	 * 
	 */
	public static Channel getChannel() {
		return channel;
	}
	/**
	 * 设置版本发布渠道,方便您的统计,渠道包括ADVIEW,EOE,GOOGLEMARKET,APPCHINA,HIAPK,GFAN,GOAPK,NDUOA,OTHER.
	 * 用法举例:AdViewTargeting.setChannel(Channel.GOOGLEMARKET);
	 * 
	 */
	public static void setChannel(Channel cha) {
		if(cha==null)
			channel=Channel.OTHER;
		AdViewTargeting.channel=cha;
	}
	
	
	/**
	 * 获取当前运行模式:1.正常模式(RunMode.NORMAL) 2.调试模式(RunMode.TEST).
	 * 用法举例:RunMode runMode=AdViewTargeting.getRunMode();
	 * 
	 */
	public static RunMode getRunMode() {
		return runMode;
	}
	/**
	 * 设置当前运行模式:1.正常模式(RunMode.NORMAL) 2.调试模式(RunMode.TEST).
	 * 用法举例:AdViewTargeting.setRunMode(RunMode.TEST);
	 * 
	 */
	public static void setRunMode(RunMode runMode) {
		if(runMode==null)
			runMode=RunMode.NORMAL;
		AdViewTargeting.runMode=runMode;
	}
	
	/**
	 * 获取配置更新模式:1.每次都更新(UpdateMode.EVERYTIME) 2.系统默认时间段更新(UpdateMode.DEFAULT).
	 * 用法举例:UpdateMode updateMode=AdViewTargeting.getUpdateMode();
	 * 
	 */
	public static UpdateMode getUpdateMode(){
		return updateMode;
	}
	/**
	 * 设置配置更新模式:1.每次都更新(UpdateMode.EVERYTIME) 2.系统默认时间段更新(UpdateMode.DEFAULT).
	 * 用法举例:AdViewTargeting.setUpdateMode(UpdateMode.DEFAULT);
	 * 
	 */
	public static void setUpdateMode(UpdateMode updateMode){
		if(updateMode==null)
			updateMode=UpdateMode.DEFAULT;
		AdViewTargeting.updateMode=updateMode;
	}
	/**
	 * 获取部分公司的广告在手机的显示位置:1.上部(AdArea.TOP) 2.底部(AdArea.BOTTOM).
	 * 用法举例:AdViewTargeting.getAdArea();
	 * 该功能只对AdTOUCH有效,因为该类广告平台不支持广告任意位置设置
	 * 
	 */
	public static AdArea getAdArea() {
		return adArea;
	}
	/**
	 * 设置部分公司的广告在手机的显示位置:1.上部(AdArea.TOP) 2.底部(AdArea.BOTTOM).
	 * 用法举例:AdViewTargeting.setUpdateMode(AdArea.BOTTOM);
	 * 该功能只对AdTOUCH有效,因为该类广告平台不支持广告任意位置设置
	 * 
	 */
	public static void setAdArea(AdArea area) {
		if (area == null) {
			area = AdArea.TOP;
		}
		
		AdViewTargeting.adArea = area;
	}
	
	/**
	 * 获取性别信息 .
	 * 用法举例:AdViewTargeting.getGender();
	 * 该功能只对AdMob,Millennial有效
	 * 
	 */
	public static Gender getGender() {
		return gender;
	}
	/**
	 * 设置性别信息 .
	 * 用法举例:AdViewTargeting.setGender(Gender.MALE);
	 * 该功能只对AdMob,Millennial广告公司有效
	 * 
	 */
	public static void setGender(Gender gender) {
		if (gender == null) {
			gender = Gender.UNKNOWN;
		}
		
		AdViewTargeting.gender = gender;
	}
	/**
	 * 获取年龄信息 .
	 * 用法举例:AdViewTargeting.getAge();
	 * 该功能只对AdMob,Millennial广告公司有效
	 * 
	 */
	public static int getAge() {
		if (birthDate != null) {
			return GregorianCalendar.getInstance().get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
		}
		
		return -1;
	}
	/**
	 * 设置年龄信息 .
	 * 用法举例:AdViewTargeting.setAge(35);
	 * 该功能只对AdMob,Millennial广告公司有效
	 * 
	 */
	public static void setAge(int age) {
		AdViewTargeting.birthDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR) - age, 0, 1);
	}
	
	/**
	 * 该接口目前没有被启用
	 * 
	 * 
	 * 
	 */
	public static GregorianCalendar getBirthDate() {
		return birthDate;
	}
	
	/**
	 * 该接口目前没有被启用
	 * 
	 * 
	 * 
	 */
	public static void setBirthDate(GregorianCalendar birthDate) {
		AdViewTargeting.birthDate = birthDate;
	}

	
	/**
	 * 获取邮政编码 .
	 * 用法举例:AdViewTargeting.getPostalCode();
	 * 该功能只对AdMob,Millennial广告公司有效
	 * 
	 */
	public static String getPostalCode() {
		return postalCode;
	}
	/**
	 * 设置邮政编码 .
	 * 用法举例:AdViewTargeting.setPostalCode("100086");
	 * 该功能只对AdMob,Millennial广告公司有效
	 * 
	 */
	public static void setPostalCode(String postalCode) {
		AdViewTargeting.postalCode = postalCode;
	}
	
	
	/**
	 * 该接口目前没有被启用
	 * 
	 * 
	 * 
	 */
	 public static String getKeywords() {
		    return keywords;
	}
	 /**
		 * 该接口目前没有被启用
		 * 
		 * 
		 * 
		 */
	 public static void setKeywords(String keywords) {
		    AdViewTargeting.keywords = keywords;
	}
	 /**
		 * 获取关键字信息 .
		 * 用法举例:AdViewTargeting.getKeywordSet();
		 * 该功能只对AdMob,Millennial广告公司有效
		 * 
		 */
	public static Set<String> getKeywordSet() {
	    return keywordSet;
	}
	
	/**
	 * 设置关键字信息 .
	 * 用法举例:String keywords[] = { "online", "games", "gaming" };
     * AdViewTargeting.setKeywordSet(new HashSet<String>(Arrays.asList(keywords)));
	 * 该功能只对AdMob,Millennial广告公司有效
	 * 
	 */
	public static void setKeywordSet(Set<String> keywords) {
	    AdViewTargeting.keywordSet = keywords;
	}
	/**
	 * 该接口目前没有被启用
	 * 
	 * 
	 * 
	 */
	 public static void addKeyword(String keyword) {
		if (keywordSet == null) {
			AdViewTargeting.keywordSet = new HashSet<String>();
		}
		keywordSet.add(keyword);
	 }
	
}
