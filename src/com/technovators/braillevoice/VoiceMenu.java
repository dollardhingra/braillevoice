package com.technovators.braillevoice;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;;

public class VoiceMenu extends Activity implements OnClickListener{
	ListView ListView_Result;
	Button Button_Speak;
	static final int check= 1111;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//fullscreen view
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.voice_menu);
		
		ListView_Result = (ListView)findViewById(R.id.listViewVoiceReturn);
		Button_Speak=(Button) findViewById(R.id.buttonSpeak);
		Button_Speak.setOnClickListener(this);

		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please Speak Now !");
		startActivityForResult(i, check);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode== check && resultCode== RESULT_OK){
			ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			ListView_Result.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,results));

			
		}
		super.onActivityResult(requestCode, resultCode, data);
		
	}

}
