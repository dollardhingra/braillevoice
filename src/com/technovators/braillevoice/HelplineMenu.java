package com.technovators.braillevoice;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HelplineMenu extends ListActivity implements
		TextToSpeech.OnInitListener {

	TextToSpeech tts;
	String classes[] = { "back", "PoliceHelplines", "Ambulances",
			"FireStations", "WomenHelplines", "AntiRagging", "AntiTeasing",
			"OtherImportantNumbers" };
	ListView lv;
	Vibrator vb;
	Boolean vibration;
	Boolean tts_engine;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			SharedPreferences getPrefs = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			vibration = getPrefs.getBoolean("checkbox1", true);
			SharedPreferences getTts = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			
			tts_engine = getTts.getBoolean("checkbox4", true);
			SharedPreferences getEdit = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			
			
			
			if (vibration == true) {
				vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				//vb.vibrate(400);Log.i("vibration initialized", "vib");
				
			}
			if (vibration == true) {
				vb.vibrate(400);Log.i("vibration performed", "vib");
				
			}
			if (tts_engine == true) {
				tts = new TextToSpeech(this, this);
				//vb.vibrate(400);Log.i("tts initialized", "vib");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * tts = new TextToSpeech(this, this); vb = (Vibrator)
		 * getSystemService(Context.VIBRATOR_SERVICE);
		 */
		Log.i("1", "nn");
		setListAdapter(new ArrayAdapter<String>(HelplineMenu.this,
				android.R.layout.simple_list_item_1, classes));

		Log.i("2", "nn");
		lv = getListView();

		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				Toast.makeText(getApplicationContext(), "bool",
						Toast.LENGTH_LONG).show();
				String selec1 = classes[position];
				String selec = selec1.trim();
				if (tts_engine == true) {
					tts.speak(" " + selec, TextToSpeech.QUEUE_FLUSH, null);
				}
				if (selec.equals("back")) {
					
					KeyEvent k = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
					dispatchKeyEvent(k);
					finish();

					
					// vb.vibrate(200);

				} else {
					try {
						Class ourClass = Class
								.forName("com.technovators.braillevoice."
										+ selec);

						Intent ourIntent = new Intent(HelplineMenu.this,
								ourClass);

						if (vibration == true) {
							vb.vibrate(400);
						}
						startActivity(ourIntent);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				return true;
			}
		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String selec = classes[position];

		if (tts_engine == true) {

			tts.speak("" + selec, TextToSpeech.QUEUE_FLUSH, null);
		}

		if (vibration == true) {
			vb.vibrate(200);
		}
	}/*
	 * try{ Class ourClass= Class.forName("com.example.calc."+selec); Intent
	 * ourIntent = new Intent(Menu.this, ourClass); startActivity(ourIntent);
	 * }catch(ClassNotFoundException e) { e.printStackTrace(); }
	 */

	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub

		tts.setSpeechRate((float) 0.9);
		tts.speak("Welcome to the Emergency numbers menu",
				TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
