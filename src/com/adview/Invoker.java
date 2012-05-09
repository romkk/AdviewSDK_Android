package com.adview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;


//import com.adview.AdViewTargeting.Channel;
import com.adview.AdViewTargeting.RunMode;
//import com.adview.AdViewTargeting.UpdateMode;






public class Invoker extends Activity implements AdViewInterface {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LinearLayout layout = (LinearLayout)findViewById(R.id.layout_main);
        if (layout == null) 
            return; 
          
        AdViewTargeting.setRunMode(RunMode.TEST);
	 AdViewLayout adViewLayout = new AdViewLayout(this, "SDK20111812070129bb9oj4n571faaka");	
        adViewLayout.setAdViewInterface(this);      
        layout.addView(adViewLayout);
		
        layout.invalidate();
    }

	

	@Override
	public void onClickAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisplayAd() {
		// TODO Auto-generated method stub
		
	}
}
