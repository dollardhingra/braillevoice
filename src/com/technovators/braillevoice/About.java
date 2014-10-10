package com.technovators.braillevoice;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.Window;
import android.view.WindowManager;

public class About extends Activity implements
TextToSpeech.OnInitListener {

	TextToSpeech tts;
	Boolean tts_engine;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		setContentView(R.layout.about);

		try {SharedPreferences getTts = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			tts_engine = getTts.getBoolean("checkbox4", true);
			SharedPreferences getEdit = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			//vb.vibrate(50);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread timer= new Thread(){
		public void run(){
			try{
				sleep(5000); 
			}
			catch(InterruptedException e){
				e.printStackTrace();
				
}
			finally{
				Intent openStartingPoint= new Intent("com.technovators.braillevoice.HELPBUTTON");
				startActivity(openStartingPoint); 
			}
 }
  
	};
		timer.start();
	
	}

	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub

		tts.setSpeechRate((float) 0.9);
		
		tts.setLanguage(Locale.ENGLISH);
		if(tts_engine==true)
		tts.speak("Contact us at technovators24@gmail.com",
				TextToSpeech.QUEUE_FLUSH, null);

	}

}
