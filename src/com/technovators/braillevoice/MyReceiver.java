package com.technovators.braillevoice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
	int press_count = 0;
	Handler h;
	Runnable r;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		if (!(intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
				&& !(intent.getAction().equals(Intent.ACTION_SCREEN_ON))) {

			Log.i("if", "Called");
			return;

		} else {
			Log.i("else", "Called");

			if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

				Log.i("screen on", "Called");

				Intent i = new Intent(context, HomeActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(i);
			}

			// screenOff = false;

			press_count++;
			Log.i("pressed", "Called");
			if (press_count == 1) {
				h = new Handler();

				r = new Runnable() {
					final MyReceiver this$0 = MyReceiver.this;

					@Override
					public void run() {
						// TODO Auto-generated method stub
						press_count = 0;
						Log.i("running", "Called");

						h.removeCallbacks(r);
					}

				};

				h.postDelayed(r, 4000L);

				return;
			}
			if (press_count == 3) {
				Log.i("pressed thrice", "Called");
				Toast.makeText(context, "thrice pressed ", Toast.LENGTH_LONG)
						.show();
				Intent i = new Intent(context, LocationMenu.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(i);
				return;
			}

			if (press_count > 3) {
				Log.i("pressed more than thrice", "Called");
				press_count = 0;
				return;
			}
		}

	}
}
