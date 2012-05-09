package com.adview.ad;

public class RetAdBean {
	private String idApp="";   
    private String idAd="";
    private int adShowType;    
    private String adShowText="";
    private String adShowPic="";
    private int adLinkType;
    private String adLink="";
    
    
    public String getIdApp(){
    	return idApp;
    }
    public void setIdApp(String s) {   
        idApp = new String(s);   
    }   
    
    
    
    public String getIdAd(){
    	return idAd;
    }
    public void setIdAd(String s){
    	idAd=new String(s);
    }
    public int getAdShowType(){
    	return adShowType;
    }
    public void setAdShowType(int a){
    	adShowType=a;
    }
    
    public String getAdShowText(){
    	return adShowText;
    }
    public void setAdShowText(String s){
    	adShowText=new String(s);
    }
    
    public String getAdShowPic(){
    	return adShowPic;
    }
    public void setAdShowPic(String s){
    	adShowPic=new String(s);
    }
    
    public int getAdLinkType(){
    	return adLinkType;
    }
    public void setAdLinkType(int a){
    	adLinkType=a;
    }
    
    public String getAdLink(){
    	return adLink;
    }
    public void setAdLink(String s){
    	adLink=new String(s);
    }
    
    
   

}
