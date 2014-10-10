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

public class WomenHelplines extends ListActivity implements TextToSpeech.OnInitListener{
	TextToSpeech tts;
	String classes[]={"Women in Distress(Delhi Police)\n1091","M/s Govt.of NCT of Delhi for Chief Minister(Women Helpline )\n181","M/s All India Women's Conference\n10920","DCP CEL, NANAKPURA\n011-26883769","ACP NEW DELHI DISTRICT\n011-22322426","ACP OF CAW/CELL, NANAKPURA(i)\n011-26883650","ACP OF CAW/CELL(ii)\n011-26880393","ACP OF CAW/CELL(iii)\n011-24121777","ACP NORTH DISTRICT\n011-23697610","ACP EAST DISTRICT\n011-22091950","ACP CENTRE DISTRICT\n011-22365753","ACP WEST DISTRICT\n011-25915314","Sangini (India) Trust\n011-5567650","Naz Foundation\n011-24372229"}; 
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



		setListAdapter(new ArrayAdapter<String>(WomenHelplines .this, android.R.layout.simple_list_item_1, classes)); 		
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
if (selec.equals("back")) {
					
					finish();}
				

				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+num.toString()));
				callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(callIntent);


/*				
				try{
				Class ourClass= Class.forName("com.technovators.braillevoice"+selec);
				Intent ourIntent = new Intent(WomenHelplines .this, ourClass);
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
				}
				if(vibration==true){
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
		tts.speak("Womens Helpline Submenu",	TextToSpeech.QUEUE_FLUSH, null);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
