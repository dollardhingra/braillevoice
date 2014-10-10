package com.technovators.braillevoice;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.speech.tts.TextToSpeech;
import android.view.View;

public class Prefs extends PreferenceActivity{

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	
	}
}


