package com.fon.wifi.zon;

import com.fon.wifi.connectivity.checker.AbstractInternetCheckReceiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class InternetCheckOnStandByReceiver extends AbstractInternetCheckReceiver {

	private static final String TAG = InternetCheckOnStandByReceiver.class.getCanonicalName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "INTERNET CHECK ON STAND BY RECEIVED");
		super.onReceive(context, intent);
	}

}
