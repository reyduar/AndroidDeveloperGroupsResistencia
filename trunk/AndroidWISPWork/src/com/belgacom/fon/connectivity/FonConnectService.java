package com.belgacom.fon.connectivity;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.belgacom.fon.FONLogin;
import com.belgacom.fon.FONStatus;
import com.belgacom.fon.logger.LoggerResult;
import com.belgacom.fon.util.FONPreferences;
import com.belgacom.fon.util.FONUtils;

public class FonConnectService extends IntentService {

	private static final String TAG = FonConnectService.class.getCanonicalName();

	public static final String ACTION_CONNECT_TO_ROUTER = "com.belgacom.fon.ACTION_CONNECT_TO_ROUTER";
	public static final String ACTION_DISCONNECT_FROM_ROUTER = "com.belgacom.fon.ACTION_DISCONNECT_FROM_ROUTER";

	public FonConnectService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (ACTION_CONNECT_TO_ROUTER.equals(intent.getAction())) {

			WifiInfo wi = wm.getConnectionInfo();
			String ssid = wi.getSSID();
			String bssid = wi.getBSSID();

			Log.d(TAG, "WIFI Info: " + wi);
			if (ssid != null && bssid != null) {
				if (FONUtils.isSupportedNetwork(ssid, bssid)) {
					FONStatus.connectToFon(this, ssid, bssid);

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

				} else {
					Log.d(TAG, "WIFI ROUTER CONNECTED");
					FONStatus.connectToWifi(this, ssid, bssid);
				}
			}
		} else if (ACTION_DISCONNECT_FROM_ROUTER.equals(intent.getAction())) {
			Log.d(TAG, "ROUTER DISCONNECTED");
			FONStatus.disconnected(this);
		}

	}

}
