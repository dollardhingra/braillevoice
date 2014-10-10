package com.technovators.braillevoice;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity implements
		TextToSpeech.OnInitListener, View.OnClickListener,
		View.OnLongClickListener, LocationListener {

	TextView textView_BatteryInfo, textView_Time,textView_Mainmenu;
	TextToSpeech tts;
	Vibrator vb;
	WifiManager wifi;
	Button button_HelpLineNumbers, button_Wifi,
			button_CheckNet, button_LocationMenu, button_News,
			button_Data, button_HelplineNumbers, 
			button_help;
	Method dataConnSwitchmethod;
	Class telephonyManagerClass;
	Object ITelephonyStub;
	Class ITelephonyClass;
	Boolean isEnabled = true;
	Boolean vibration;
	Boolean tts_engine;
	double latitude;
	double longitude;
	String towers;
	GPSTracker gps;
	

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// fullscreen code

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_home);
		try {
			SharedPreferences getPrefs = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			vibration = getPrefs.getBoolean("checkbox1", true);
			SharedPreferences getTts = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			tts_engine = getTts.getBoolean("checkbox4", true);
			SharedPreferences getEdit = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			//vb.vibrate(50);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
		// disabling key guard
		try {
			KeyguardManager _guard = (KeyguardManager) getApplicationContext()
					.getSystemService(Context.KEYGUARD_SERVICE);
			KeyguardLock _keyguardLock = _guard
					.newKeyguardLock("KeyguardLockWrapper");
			_keyguardLock.disableKeyguard();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Toast.makeText(getApplicationContext(),
				"For Help long press Help Button", Toast.LENGTH_LONG).show();
		textView_BatteryInfo.setOnClickListener(this);
		textView_Time.setOnClickListener(this);
		button_HelpLineNumbers.setOnClickListener(this);
		button_Wifi.setOnClickListener(this);
		button_Wifi.setOnLongClickListener(this);
		button_CheckNet.setOnLongClickListener(this);
		button_News.setOnLongClickListener(this);
		button_News.setOnClickListener(this);
		button_LocationMenu.setOnLongClickListener(this);
		button_LocationMenu.setOnClickListener(this);
		button_Data.setOnClickListener(this);
		button_Data.setOnLongClickListener(this);
		button_HelplineNumbers.setOnLongClickListener(this);
		button_HelplineNumbers.setOnClickListener(this);
		button_help.setOnClickListener(this);
		button_help.setOnLongClickListener(this);
		textView_Mainmenu.setOnClickListener(this);
		
	}

	BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

			textView_BatteryInfo.setText(" " + level + " %");

		}

	};


	private void getLocation() {
		// TODO Auto-generated method stub
		
		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
			try{

				String display = "";
				List<Address> address= geocoder.getFromLocation(latitude, longitude, 1);
				if (address.size() > 0)
				{
					for (int i=0; i<address.get(0).getMaxAddressLineIndex();i++)
					{
						display+= address.get(0).getAddressLine(i)+"\n";
					}
					
					Toast.makeText(
							getBaseContext(),display, Toast.LENGTH_LONG).show();
					
					if(tts_engine==true)
					tts.speak(""+ display, TextToSpeech.QUEUE_FLUSH, null);

				}	
				else if(address.size() == 0)
				{
					
					
				}
				else
				{
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
			}

		}
		else
		{
			Toast.makeText(
					getApplicationContext(),"not available", Toast.LENGTH_LONG).show();

			if(tts_engine==true)
			tts.speak("location not available", TextToSpeech.QUEUE_FLUSH, null);

		}

	}

	private void initialize() {
		// TODO Auto-generated method stub

		try {
			if (vibration == true) {
				vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			}
			if (tts_engine == true) {
				tts = new TextToSpeech(this, this);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		gps = new GPSTracker(HomeActivity.this);
		
		textView_BatteryInfo = (TextView) findViewById(R.id.textViewBatteryInfo);
		button_HelpLineNumbers = (Button) findViewById(R.id.buttonHelplineNumbers);
		button_Wifi = (Button) findViewById(R.id.buttonWifi);
		button_CheckNet = (Button) findViewById(R.id.buttonCheckNet);
		button_News = (Button) findViewById(R.id.buttonNews);
		button_LocationMenu = (Button) findViewById(R.id.buttonLocation);
		button_Data = (Button) findViewById(R.id.buttonData);
		button_HelplineNumbers = (Button) findViewById(R.id.buttonHelplineNumbers);
		textView_Mainmenu=(TextView)findViewById(R.id.textViewMainMenu);
		button_help = (Button) findViewById(R.id.buttonHelpMenu);
		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

		if (wifi.isWifiEnabled()) {
			button_Wifi.setText("Disable Wifi");
		} else {
			button_Wifi.setText("Enable Wifi");
		}
		SimpleDateFormat df;
		df = new SimpleDateFormat("HH:mm");
		Calendar c = Calendar.getInstance();
		String formattedDate = df.format(c.getTime());

		textView_Time = (TextView) findViewById(R.id.textViewTime);
		textView_Time.setText(formattedDate);
		this.registerReceiver(this.batteryInfoReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		try {
			switch (v.getId()) {
			case (R.id.textViewBatteryInfo):
				tts.speak("Battery is "
						+ textView_BatteryInfo.getText(),
						TextToSpeech.QUEUE_FLUSH, null);

				vb.vibrate(100);
				break;

			case (R.id.textViewTime):

				SimpleDateFormat df;
				df = new SimpleDateFormat("HH:mm");
				Calendar c = Calendar.getInstance();
				String formattedDate = df.format(c.getTime());

				textView_Time.setText(formattedDate);
				tts.speak("Time is. " + textView_Time.getText(),
						TextToSpeech.QUEUE_FLUSH, null);

				vb.vibrate(100);
				break;

			case (R.id.buttonHelplineNumbers):
				tts.speak("helplines Menu Button. ", TextToSpeech.QUEUE_FLUSH,
						null);

				vb.vibrate(100);

				break;
					case (R.id.buttonWifi):
				tts.speak(button_Wifi.getText() + ". button ",
						TextToSpeech.QUEUE_FLUSH, null);

				vb.vibrate(100);

				break;

		
			case (R.id.buttonNews):
				tts.speak("News Menu Button", TextToSpeech.QUEUE_FLUSH, null);

				vb.vibrate(100);

				break;

			case (R.id.buttonData):
				tts.speak(button_Data.getText() + " ",
						TextToSpeech.QUEUE_FLUSH, null);
				vb.vibrate(100);
				break;


			case (R.id.buttonHelpMenu):
				Log.i("help", "menu");
			
				tts.speak("Help Menu Button", TextToSpeech.QUEUE_FLUSH, null);
				vb.vibrate(100);
				break;
			case (R.id.textViewMainMenu):
				tts.speak(""+textView_Mainmenu.getText(),
						TextToSpeech.QUEUE_FLUSH, null);

				vb.vibrate(100);
				break;

			case (R.id.buttonLocation):
				tts.speak("location button",
						TextToSpeech.QUEUE_FLUSH, null);

				vb.vibrate(100);
				break;
				

			case (R.id.buttonCheckNet):
				tts.speak("check internet button",
						TextToSpeech.QUEUE_FLUSH, null);

				vb.vibrate(100);
				
				break;
				

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onLongClick(View vl) {
		// TODO Auto-generated method stub
		try {
			switch (vl.getId()) {

					case (R.id.buttonHelplineNumbers):
				Intent openHelplineActivity = new Intent(
						"com.technovators.braillevoice.HELPLINEMENU");
				startActivity(openHelplineActivity);
				vb.vibrate(200);

				break;
						case (R.id.buttonWifi):
				if (wifi.isWifiEnabled()) {
					wifi.setWifiEnabled(false);
					button_Wifi.setText("Enable Wifi");

					tts.speak("Wifi disabled", TextToSpeech.QUEUE_FLUSH, null);

				} else {
					wifi.setWifiEnabled(true);
					button_Wifi.setText("disable Wifi");

					tts.speak("Wifi enabled", TextToSpeech.QUEUE_FLUSH, null);

				}

				vb.vibrate(200);

				break;
			case (R.id.buttonCheckNet):
				if (isOnline()) {
					tts.speak("connected to internet",
							TextToSpeech.QUEUE_FLUSH, null);

				} else {
					tts.speak("connection not available",
							TextToSpeech.QUEUE_FLUSH, null);

				}

				// Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
				// Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);

				vb.vibrate(200);

				break;

			case (R.id.buttonLocation):

				if (gps.canGetLocation()) {

					latitude = gps.getLatitude();
					longitude = gps.getLongitude();
					}
				getLocation();
			
				if(vibration==true)
					vb.vibrate(200);
				break;


			case (R.id.buttonNews):

				if(vibration==true)
					vb.vibrate(200);

				Intent openNewsActivity = new Intent(
						"com.technovators.braillevoice.NEWSMENU");
				startActivity(openNewsActivity);

				break;

			case (R.id.buttonHelpMenu):
				Log.i("help", "menu");

			if(vibration==true)
				vb.vibrate(200);

			Intent openHelpMenuActivity = new Intent(
						"com.technovators.braillevoice.HELPBUTTON");
				startActivity(openHelpMenuActivity);
				break;

			case (R.id.buttonData):
				Context context = getApplicationContext();
				boolean enabled = true;
				boolean disabled = false;
				final ConnectivityManager conman = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				try {

					if(vibration==true)
						vb.vibrate(200);

					final Class conmanClass = Class.forName(conman.getClass()
							.getName());
					final Field iConnectivityManagerField = conmanClass
							.getDeclaredField("mService");
					iConnectivityManagerField.setAccessible(true);
					final Object iConnectivityManager = iConnectivityManagerField
							.get(conman);
					final Class iConnectivityManagerClass = Class
							.forName(iConnectivityManager.getClass().getName());
					final Method setMobileDataEnabledMethod = iConnectivityManagerClass
							.getDeclaredMethod("setMobileDataEnabled",
									Boolean.TYPE);
					setMobileDataEnabledMethod.setAccessible(true);
					if (isOnline()) {

						setMobileDataEnabledMethod.invoke(iConnectivityManager,
								disabled);

						tts.speak("data disabled",
								TextToSpeech.QUEUE_FLUSH, null);
						button_Data.setText("Enable Data");

					} else {
						setMobileDataEnabledMethod.invoke(iConnectivityManager,
								enabled);

						button_Data.setText("Disable Data");

						tts.speak("data enabled",
								TextToSpeech.QUEUE_FLUSH, null);

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean isOnline() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub

		tts.setSpeechRate((float) 0.9);

		tts.speak("Main Menu",
				TextToSpeech.QUEUE_FLUSH, null);

		if(vibration==true)
			vb.vibrate(50);

		
		tts.setLanguage(Locale.ENGLISH);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) { // Back key pressed
			// Things to Do
			Toast.makeText(getApplicationContext(), "back button disabled",
					Toast.LENGTH_LONG).show();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		startService(new Intent(this, MyUpdateService.class).putExtra("KEY1",
				"111"));
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown tts!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		unregisterReceiver(batteryInfoReceiver);
		super.onDestroy();
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}