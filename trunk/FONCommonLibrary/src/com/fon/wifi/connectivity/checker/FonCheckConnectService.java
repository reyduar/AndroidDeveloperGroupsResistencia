package com.fon.wifi.connectivity.checker;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.fon.wifi.FONLogin;
import com.fon.wifi.FONStatus;
import com.fon.wifi.logger.LoggerResult;
import com.fon.wifi.util.FONPreferences;
import com.fon.wifi.util.FONUtils;

abstract public class FonCheckConnectService extends IntentService {

	private static final String TAG = FonCheckConnectService.class.getCanonicalName();

	public FonCheckConnectService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo wi = wm.getConnectionInfo();
		String ssid = wi.getSSID();
		String bssid = wi.getBSSID();

		Log.d(TAG, "WIFI Info: " + wi);
		if (ssid != null && bssid != null) {
			if (FONUtils.isSupportedNetwork(ssid, bssid)) {
				if (!FONUtils.isConnected()) {
					boolean credentialsConfigured = FONPreferences.areCredentialsConfigured(this);

					if (credentialsConfigured) {
						Log.d(TAG, "FON LOGIN");
						LoggerResult result = FONLogin.login(this, ssid, bssid);
						wi = wm.getConnectionInfo();
						if (result.hasSucceded()) {
							Log.d(TAG, "WIFI Info: " + wi);
							if (FONUtils.isSupportedNetwork(wi.getSSID(), wi.getBSSID())) {
								FONPreferences.saveLogOffUrl(this, result.getLogOffUrl());
								FONStatus.logIn(this, ssid);
							}
						} else if (result.hasFailed()) {
							FONStatus.error(this, result.getResult(), ssid, bssid);
							wm.disconnect();
						}
					} else {
						Log.d(TAG, "FON MISSING CREDENTIALS");
						// TODO Unify Error Managing
						FONStatus.errorFONMissingCredentials(this, ssid);
						wm.disconnect();
					}
				} else {
					Log.d(TAG, "FON ALREADY CONNECTED");
					FONStatus.logIn(this, ssid);
				}

			}
		}

	}

}