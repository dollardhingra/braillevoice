package com.technovators.braillevoice;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.pdf.PdfDocument.Page;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class HelpButton extends Activity implements
TextToSpeech.OnInitListener, View.OnClickListener,
View.OnLongClickListener{
	Button Next;
	TextToSpeech tts;
	Vibrator vb;
	Button button_back,button_preferences,button_manual,button_about;
	Boolean vibration;
	Boolean tts_engine;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
	setContentView(R.layout.help);
	Log.i("help menu started", "menu");
	
	try {
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		vibration = getPrefs.getBoolean("checkbox1", true);
		SharedPreferences getTts = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		tts_engine = getTts.getBoolean("checkbox4", true);
		Log.i("preferences value done", "menu");
			
		
	} catch (Exception e) {
		e.printStackTrace();
	}

	initialize();

	button_back.setOnClickListener(this);
	button_back.setOnLongClickListener(this);
	Log.i("back listeners done", "menu");
	button_preferences.setOnClickListener(this);
	button_preferences.setOnLongClickListener(this);
	Log.i("preferenceslisteners done", "menu");
	button_manual.setOnClickListener(this);
	button_manual.setOnLongClickListener(this);
	Log.i("manual listeners done", "menu");
	button_about.setOnClickListener(this);
	button_about.setOnLongClickListener(this);
	Log.i("listeners done", "menu");
	
	}

	
private void initialize() {
		// TODO Auto-generated method stub
	
	Log.i("initialization started", "menu");
	
	button_back=(Button)findViewById(R.id.buttonBack);
	button_preferences=(Button)findViewById(R.id.buttonPreferences);
	button_manual=(Button)findViewById(R.id.buttonManual);
button_about=(Button)findViewById(R.id.buttonAbout);

	try {
		if (vibration == true) {
			vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		}
		if (tts_engine == true) {
			tts = new TextToSpeech(this, this);
			Log.i("done", "menu");
			
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
	
}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		try{
		tts.setSpeechRate((float) 0.9);
		tts.setLanguage(Locale.ENGLISH);
		Log.i("tts initialized", "menu");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case(R.id.buttonBack):
			 
			NavUtils.navigateUpFromSameTask(this);
		
			break;
		

		case(R.id.buttonPreferences):
			Intent openPreferencesActivity = new Intent(
					"com.technovators.braillevoice.PREFS");
			startActivity(openPreferencesActivity);
		if(vibration==true){
			vb.vibrate(200);
		}
			
			break;
		
		

		case(R.id.buttonManual):
			break;
		

		case(R.id.buttonAbout):
			
			Intent openAboutActivity= new Intent("com.technovators.braillevoice.ABOUT");
		startActivity(openAboutActivity); 
	break;
		
		}
		return true;
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case(R.id.buttonBack):
			if(tts_engine==true)

				tts.speak(button_back.getText()+"button", TextToSpeech.QUEUE_FLUSH, null);

		if(vibration==true){
			vb.vibrate(200);
		}
			break;


		case(R.id.buttonPreferences):

			if(tts_engine==true)

				tts.speak(button_preferences.getText()+"button", TextToSpeech.QUEUE_FLUSH, null);

		if(vibration==true){
			vb.vibrate(200);
		}
			break;
		
		

		case(R.id.buttonManual):
			if(tts_engine==true)

				tts.speak(button_manual.getText()+"button", TextToSpeech.QUEUE_FLUSH, null);

		if(vibration==true){
			vb.vibrate(200);
		}
			break;
		

		case(R.id.buttonAbout):
			if(tts_engine==true)

				tts.speak(button_about.getText()+"button", TextToSpeech.QUEUE_FLUSH, null);

		if(vibration==true){
			vb.vibrate(200);
		}
			break;
		}
	}

}
