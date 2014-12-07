package com.fon.wifi.zon;

import com.fon.wifi.connectivity.checker.AbstractInternetCheckReceiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class InternetCheckAlarmReceiver extends AbstractInternetCheckReceiver {

	private static final String TAG = InternetCheckAlarmReceiver.class.getCanonicalName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "INTERNET CHECK ALARM RECEIVED");
		super.onReceive(context, intent);
	}

}
