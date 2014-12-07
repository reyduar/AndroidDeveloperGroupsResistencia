package com.belgacom.fon.connectivity.checker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

public class InternetCheckAlarmReceiver extends BroadcastReceiver {

	private static final String TAG = InternetCheckAlarmReceiver.class.getCanonicalName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "INTERNET CHECK ALARM RECEIVED");
		WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		switch(wm.getWifiState()){
		case WifiManager.WIFI_STATE_ENABLED:
			Intent connetcToRouter = new Intent(context,FonCheckConnectService.class);
			context.startService(connetcToRouter);
		}
	}

}
