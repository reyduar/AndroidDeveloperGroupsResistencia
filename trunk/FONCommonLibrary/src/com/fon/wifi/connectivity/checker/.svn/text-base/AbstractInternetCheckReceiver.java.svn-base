package com.fon.wifi.connectivity.checker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.fon.wifi.util.IntentActionsFactory;

public abstract class AbstractInternetCheckReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		switch(wm.getWifiState()){
		case WifiManager.WIFI_STATE_ENABLED:
			Intent connetcToRouter = new Intent();
			connetcToRouter.setAction(IntentActionsFactory.getInternetCheckAction());
			context.startService(connetcToRouter);
		}
	}

}
