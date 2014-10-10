package com.technovators.braillevoice;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class MyUpdateService extends Service {
	BroadcastReceiver mReceiver;
	
	public MyUpdateService()
    {
    }
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
	    super.onCreate();
	   Log.i("screenON", "Called");
	   Context context=getApplicationContext();
	    // register receiver that handles screen on and screen off logic
	    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
	    filter.addAction(Intent.ACTION_SCREEN_OFF);
	    filter.addAction(Intent.ACTION_USER_PRESENT);

	    mReceiver = new MyReceiver();
	    registerReceiver(mReceiver, filter);
	   
	    /*********code for disabling keyguard***********/
        KeyguardManager _guard = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        @SuppressWarnings("deprecation")
		KeyguardLock _keyguardLock = _guard
                .newKeyguardLock("KeyguardLockWrapper");
        _keyguardLock.disableKeyguard();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		Log.i("low memory", "Called");
	}

	

	@Override
	public void onDestroy() {

	    unregisterReceiver(mReceiver);
	    Log.i("onDestroy Reciever", "Called");
	   
	    super.onDestroy();
	}

	
	@Override
	public int onStartCommand(Intent intent,int flags, int startId) {
	/*    boolean screenOn = intent.getBooleanExtra("screen_state", false);
	    if (!screenOn) {
	        Log.i("screenON", "Called");
	     /*   Intent dialogIntent = new Intent(getBaseContext(), MainActivity.class);
	        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        getApplication().startActivity(dialogIntent);*/
	     /*   Toast.makeText(getApplicationContext(), "Awake", Toast.LENGTH_LONG)
	                .show();
	    } else {
	        Log.i("screenOFF", "Called");
	        // Toast.makeText(getApplicationContext(), "Sleep",
	        // Toast.LENGTH_LONG)
	        // .show();
	    }*/
		return super.onStartCommand(intent, flags, startId);
	}
	@SuppressLint("NewApi")
	public void onTaskRemoved(Intent intent)
    {
        super.onTaskRemoved(intent);
        if (true)
        {
            Log.d("MyService", "onTaskRemoved");
        }
        Intent intent1 = new Intent(getApplicationContext(), getClass());
        intent1.setPackage(getPackageName());
        PendingIntent pendingintent = PendingIntent.getService(getApplicationContext(), 1, intent1, 0x40000000);
        ((AlarmManager)getApplicationContext().getSystemService("alarm")).set(3, 2000L + SystemClock.elapsedRealtime(), pendingintent);
    }

	
}
