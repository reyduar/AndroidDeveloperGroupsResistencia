package com.fon.wifi.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.fon.wifi.R;
import com.fon.wifi.util.IntentActionsFactory;
import com.fon.wifi.util.Utils;

abstract public class RouterConnectionStateChangeReceiver extends BroadcastReceiver {

	private static final String TAG = RouterConnectionStateChangeReceiver.class.getCanonicalName();

	@Override
	public void onReceive(Context context, Intent intent) {
		boolean active = Utils.getBooleanPreference(context, R.string.pref_active, true);
		if (active) {
			if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
				NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

				Intent handlerService = new Intent(IntentActionsFactory.getFonConnectServiceAction());
				Log.d(TAG, networkInfo.getState().name());
				switch (networkInfo.getState()) {
				case CONNECTED:
					Log.d(TAG, "ROUTER CONNECTED");
					handlerService.putExtra(FonConnectService.ACTION_EXTRA,FonConnectService.ACTION_CONNECT_TO_ROUTER);
					break;
				case DISCONNECTED:
					Log.d(TAG, "ROUTER DISCONECTED");
					handlerService.putExtra(FonConnectService.ACTION_EXTRA,FonConnectService.ACTION_DISCONNECT_FROM_ROUTER);
					break;
				}
				context.startService(handlerService);
			}
		}
	}
}
