package com.belgacom.fon.connectivity;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.belgacom.fon.FONStatus;
import com.belgacom.fon.util.FONPreferences;

public class RouterConnectService extends IntentService {

	private static final String TAG = RouterConnectService.class.getCanonicalName();

	public static final String EXTRA_AUTOCONNECT = "EXTRA_AUTOCONNECT";

	public RouterConnectService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (!FONStatus.isPaused(this)){
			if(!ConnectivityUtis.isAlreadyConnected(this)) {
			if (!ConnectivityUtis.isAnyPreferedNetworkAvailable(wm)) {
				String fonSSID = ConnectivityUtis.getFonNetworkSSID(this);
				if (fonSSID != null) {
					boolean autoConnect = intent.getBooleanExtra(EXTRA_AUTOCONNECT, false);
					if (autoConnect || FONPreferences.isAutoConnectEnabled(this)) {
						Log.d(TAG, "AUTOCONNECT TO FON WIFI " + fonSSID);
						ConnectivityUtis.connectToRouter(this,fonSSID);
					} else {
						Log.d(TAG, "FON WIFI AVAILABLE " + fonSSID);
						FONStatus.fonAvailable(this);
					}
				}else{
					Log.d(TAG, "NOT FON WIFI AVAILABLE");
				}
			} else {
				Log.d(TAG, "PREFERRED WIFI AVAILABLE");
			}
		} else {
			Log.d(TAG, "ROUTER WIFI ALREADY CONNECTED");
		}
	} else {
		Log.d(TAG, "FON PAUSED");
	}
	}

}
