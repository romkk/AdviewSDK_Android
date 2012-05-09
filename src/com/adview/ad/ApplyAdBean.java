package com.adview.ad;


public class ApplyAdBean {
	private String appId= null;
	
	private int system;
	private int isTestMode = 0;
	
	
	
	public ApplyAdBean(){
		setSystem(1);
	}
	
	public String getAppId(){
		return appId;
	}
	public void setAppId(String str){
		appId=new String(str);
	}
	
	public int getTestMode(){
		return isTestMode;
	}
	public void setTestMode(int val){
		isTestMode =val;
	}	
	
	public int getSystem(){
		return system;
	}
	public void setSystem(int val){
		system=val;
	}
	
		
	

}
