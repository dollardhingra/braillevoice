package com.technovators.braillevoice;
import java.io.FileNotFoundException;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class NewsMenu extends Activity implements TextToSpeech.OnInitListener, OnClickListener, OnLongClickListener{

	private SitesAdapter mAdapter;
	private ListView sitesList;
	TextToSpeech tts;
	Vibrator vb;
	Button buttonRefresh, button_backnews;
	Boolean vibration, tts_engine;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("StackSites", "OnCreate()");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.news_menu);

		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		vibration = getPrefs.getBoolean("checkbox1", true);
		SharedPreferences getTts = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		tts_engine = getTts.getBoolean("checkbox4", true);

		Log.i("StackSite", "boolean recieved");
		//Get reference to our ListView
		sitesList = (ListView)findViewById(R.id.sitesList);
		if(tts_engine==true)
		tts = new TextToSpeech(this, this);
		buttonRefresh = (Button)findViewById(R.id.buttonResult);

		
		button_backnews=(Button)findViewById(R.id.buttonbacknews);
if(vibration==true)
		vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		//Set the click listener to launch the browser when a row is clicked.
		buttonRefresh.setOnClickListener(this);
		buttonRefresh.setOnLongClickListener(this);

		button_backnews.setOnLongClickListener(this);
		button_backnews.setOnClickListener(this);
	
		sitesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				
				String url = mAdapter.getItem(pos).getLink();
				String ttl= mAdapter.getItem(pos).getTitle();
				String des= mAdapter.getItem(pos).getDescription();
				String pD= mAdapter.getItem(pos).getPubDate();
				int count=pos+1;
				tts.speak(" Headline"+count+":"+ttl+des, TextToSpeech.QUEUE_FLUSH, null);
				if(vibration==true)
				vb.vibrate(200);
				
			}
			
		});
		
		/*
		 * If network is available download the xml from the Internet.
		 * If not then try to use the local file from last time.
		 */
		refresh();

		
	}
	
	//Helper method to determine if Internet connection is available.
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	} 
	
	/*
	 * AsyncTask that will download the xml file for us and store it locally.
	 * After the download is done we'll parse the local file.
	 */
	private class SitesDownloadTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			//Download the file
			try {
				Downloader.DownloadFromUrl("http://www.rediff.com/rss/inrss.xml", openFileOutput("StackSites.xml", Context.MODE_PRIVATE));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result){
			//setup our Adapter and set it to the ListView.
			mAdapter = new SitesAdapter(NewsMenu.this, -1, SitesXmlPullParser.getStackSitesFromFile(NewsMenu.this));
			sitesList.setAdapter(mAdapter);
			Log.i("StackSites", "adapter size = "+ mAdapter.getCount());
		}
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub

		tts.setSpeechRate((float) 0.9);
		tts.setLanguage(Locale.ENGLISH);
		if(tts_engine==true)
		tts.speak(
				"News Menu ",
				TextToSpeech.QUEUE_FLUSH, null);
		
		
	}
	@Override
	public void onDestroy() {
		// Don't forget to shutdown tts!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}

		super.onDestroy();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case(R.id.buttonResult):
			if(tts_engine==true)
			tts.speak("Refresh button", TextToSpeech.QUEUE_FLUSH, null);
		if(vibration==true)
		vb.vibrate(200);
		
		case(R.id.buttonbacknews):
			if(tts_engine==true)
			tts.speak("back", TextToSpeech.QUEUE_FLUSH, null);
		if(vibration==true)
		vb.vibrate(200);
		

		}
	}
	

	private void refresh() 
	{
		// TODO Auto-generated method stub
		if(isNetworkAvailable()){
			Log.i("StackSites", "starting download Task");
			SitesDownloadTask download = new SitesDownloadTask();
			download.execute();
			if(tts_engine==true)
			tts.speak("News refreshed", TextToSpeech.QUEUE_FLUSH, null);

		}else{
			mAdapter = new SitesAdapter(getApplicationContext(), -1, SitesXmlPullParser.getStackSitesFromFile(NewsMenu.this));
			sitesList.setAdapter(mAdapter);
			if(tts_engine==true)
			tts.speak("Internet currently no available, you can read the already cached headlines.", TextToSpeech.QUEUE_FLUSH, null);

		}
	}

	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case(R.id.buttonResult):
			refresh();
		if(vibration==true)
		vb.vibrate(400);
		break;
		
		case(R.id.buttonbacknews):
		
		if(vibration==true)
		vb.vibrate(200);
		finish();
		
		}
		
		return true;
		
		
		
	}
}
