package com.technovators.braillevoice;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FireStations extends ListActivity implements TextToSpeech.OnInitListener {
	TextToSpeech tts;
	String classes[]={"back","Fire Station NCR\n101","Bhikaji Cama Place\n011-26173583","Safdarjung Enclave\n011-24611111", "Chanakya Puri \n011-26112226","Naraina\n011-25798798","Hari Nagar\n011-25141433","Parliament Street \n011-23719479"}; 
	ListView lv;
	Vibrator vb;
	Boolean vibration=true;
	Boolean tts_engine=true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		vibration = getPrefs.getBoolean("checkbox1", true);
		SharedPreferences getTts = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		
		tts_engine = getTts.getBoolean("checkbox4", true);
		setListAdapter(new ArrayAdapter<String>(FireStations.this, android.R.layout.simple_list_item_1, classes)); 
		if(tts_engine==true)
		tts = new TextToSpeech(this, this);
		if(vibration==true)
		vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		lv =  getListView();
		
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				String selec= classes[position];
if (selec.equals("back")) {
					
					finish();}
				
				String num;
				num = selec.replaceAll("\\D+", "");
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+num.toString()));
				callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(callIntent);
				if(tts_engine==true)
		tts.speak(" "+selec,	TextToSpeech.QUEUE_FLUSH, null);

				
				try{
				Class ourClass= Class.forName("com.technovators.braillevoice"+selec);
				Intent ourIntent = new Intent(FireStations.this, ourClass);
				if(vibration==true)
				vb.vibrate(400);
				startActivity(ourIntent);
				}catch(ClassNotFoundException e)
				{
					e.printStackTrace();
				}


				return true;
			}
		} );
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		if(vibration==true)
		vb.vibrate(200);
		String selec= classes[position];
		if(tts_engine==true)
		tts.speak(""+selec,	TextToSpeech.QUEUE_FLUSH, null);
		
/*
		try{
		Class ourClass= Class.forName("com.example.calc."+selec);
		Intent ourIntent = new Intent(Menu.this, ourClass);
		startActivity(ourIntent);
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		*/
		
	}
	

	

	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub
		tts.setSpeechRate((float) 0.9);
		if(tts_engine==true)
		tts.speak("Fire stations helpline sub menu",	TextToSpeech.QUEUE_FLUSH, null);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
