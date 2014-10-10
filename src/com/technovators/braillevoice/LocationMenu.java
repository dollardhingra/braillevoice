package com.technovators.braillevoice;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.technovators.braillevoice.GPSTracker;
import com.technovators.braillevoice.LocationMenu;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LocationMenu extends Activity implements LocationListener {

	double latitude;
	double longitude;
	String towers;
	GPSTracker gps;
	EditText editText_PhoneNumber;
	TextToSpeech tts;
	Vibrator vb;
	Boolean vibration;

	String edit_contact1;
	String edit_contact2;
	String edit_contact3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);


		setContentView(R.layout.location_menu);
		SharedPreferences getContact= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		vibration = getPrefs.getBoolean("checkbox1", true);
		
		edit_contact1= getContact.getString("name1", null);
		edit_contact2= getContact.getString("name2", null);
		edit_contact3= getContact.getString("name3", null);
	
		intialize();
		gps = new GPSTracker(LocationMenu.this);


		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();}
		getLocation();

	}

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
//					tts.speak(""+ display, TextToSpeech.QUEUE_FLUSH, null);

					try{
					sendLocaionSMS(edit_contact1,gps);

					sendLocaionSMS(edit_contact2,gps);

					sendLocaionSMS(edit_contact3,gps);}
					catch(Exception e){
						e.printStackTrace();
					}
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

		//	editText_LatLongLink.setText(" "+latitude+","+longitude);
			
			//editText_LatLongLink.setText("http");
			
		//editText_LatLongLink.setText("http://maps.google.com/?q=+"+latitude+","+longitude);
			// \n is for new line
		/*	Toast.makeText(
					getApplicationContext(),
					"Your Location is - \nLat: " + latitude + "\nLong: "
							+ longitude, Toast.LENGTH_LONG).show();*/
		}
		else
		{
			Toast.makeText(
					getApplicationContext(),"not available", Toast.LENGTH_LONG).show();
			
		}

	}

	private void intialize() {
		// TODO Auto-generated method stub	
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

	public void sendLocaionSMS(String phoneNumber, GPSTracker gps){
		SmsManager smsManager = SmsManager.getDefault();
		StringBuffer smsBody= new StringBuffer();
		Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
		String display = "";
		latitude = gps.getLatitude();
		longitude = gps.getLongitude();
		try{

				List<Address> address= geocoder.getFromLocation(latitude, longitude, 1);
			if (address.size() > 0)
			{
				for (int i=0; i<address.get(0).getMaxAddressLineIndex();i++)
				{
					display+= address.get(0).getAddressLine(i)+"\n";
				}
				Toast.makeText(
						getBaseContext(),display, Toast.LENGTH_LONG).show();
	//			tts.speak(""+ display, TextToSpeech.QUEUE_FLUSH, null);

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
		
		smsBody.append("http://www.maps.google.com?q=");
		smsBody.append(gps.getLatitude());
		smsBody.append(",");
		smsBody.append(gps.getLongitude());
	//	smsBody.append(" I am at "+display);
		
		smsManager.sendTextMessage(phoneNumber, null, smsBody.toString(), null, null);
		//vb.vibrate(2000);
		Toast.makeText(
				getApplicationContext(),"sms has been send!", Toast.LENGTH_LONG).show();
		
	}


}