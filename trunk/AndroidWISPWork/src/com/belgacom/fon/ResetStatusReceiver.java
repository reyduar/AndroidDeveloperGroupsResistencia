package com.belgacom.fon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

public class ResetStatusReceiver extends BroadcastReceiver {

	static private final String TAG = ResetStatusReceiver.class.getCanonicalName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())){
			switch(intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED)){
			case WifiManager.WIFI_STATE_DISABLED:
			case WifiManager.WIFI_STATE_ENABLED:
				Log.d(TAG, "WIFI STATUS CHANGED");
				context.startService(new Intent(context,ResetStatusService.class));
			}
		}
	}
}