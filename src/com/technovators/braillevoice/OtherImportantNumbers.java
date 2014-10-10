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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class OtherImportantNumbers extends ListActivity implements TextToSpeech.OnInitListener{

	TextToSpeech tts;
	String classes[]={"Eye Donation\n1919","Child Helpline \n1098","Delhi Metro Rail Corporation Ltd.\n155370","Delhi University Helpline\n155215","Dial-A-Cab\n1920","Railway Central Enquiry and Reservations\n139"}; 
	ListView lv;
	Vibrator vb;
	Boolean vibration;
	Boolean tts_engine;

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
			}
			if (tts_engine == true) {
				tts = new TextToSpeech(this, this);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		setListAdapter(new ArrayAdapter<String>(OtherImportantNumbers.this, android.R.layout.simple_list_item_1, classes)); 
		
		lv =  getListView();
		
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				String selec= classes[position];
				String num;
				num = selec.replaceAll("\\D+", "");
				if (tts_engine == true) {
					tts.speak(" "+selec,	TextToSpeech.QUEUE_FLUSH, null);
				}
				if (vibration == true)
					vb.vibrate(400);

				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+num.toString()));
				callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(callIntent);

				
			/*	try{
				Class ourClass= Class.forName("com.Bhardwaj.emergencyproject"+selec);
				Intent ourIntent = new Intent(OtherImportantNumbers.this, ourClass);
				vb.vibrate(400);
				startActivity(ourIntent);
				}catch(ClassNotFoundException e)
				{
					e.printStackTrace();
				}

*/
				return true;
			}
		} );
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Log.i("new", "!");

				String selec = classes[position];
				if (tts_engine == true) {
					tts.speak(" " + selec, TextToSpeech.QUEUE_FLUSH, null);
				}if(vibration==true){
					vb.vibrate(70);
				}

			}
		});

	}
	
	
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
		
	
	

	

	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub
		tts.setSpeechRate((float) 0.9);
		tts.speak("Other Important Numbers Submenu",	TextToSpeech.QUEUE_FLUSH, null);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}


