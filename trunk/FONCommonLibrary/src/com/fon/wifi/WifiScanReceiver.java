package com.fon.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fon.wifi.util.IntentActionsFactory;

abstract public class WifiScanReceiver extends BroadcastReceiver {
	private static String TAG = WifiScanReceiver.class.getName();

	private static long lastCalled = -1;

	private static final int MIN_PERIOD_BTW_CALLS = 10 * 1000;// 10 Seconds

	@Override
	public void onReceive(Context context, Intent intent) {
		long now = System.currentTimeMillis();
		if (lastCalled == -1 || (now - lastCalled > MIN_PERIOD_BTW_CALLS)) {
			lastCalled = now;
			Log.d(TAG, "SCAN PROCESSED");
			Intent handlerService = new Intent(IntentActionsFactory.getRouterConnectServiceAction());
			context.startService(handlerService);
		}
	}

}