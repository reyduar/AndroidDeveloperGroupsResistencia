package com.fon.wifi;

import java.io.IOException;

import android.content.Context;
import android.util.Log;

import com.fon.wifi.logger.BTFonLogger;
import com.fon.wifi.logger.LivedoorLogger;
import com.fon.wifi.logger.LoggerResult;
import com.fon.wifi.logger.NeufLogger;
import com.fon.wifi.logger.WISPrLogger;
import com.fon.wifi.logger.WebLogger;
import com.fon.wifi.util.FONPreferences;
import com.fon.wifi.util.FONUtils;
import com.fon.wifi.util.HttpUtils;

public class FONLogin {
	private static String TAG = FONLogin.class.getName();

	static public LoggerResult login(Context context, String ssid, String bssid) {
		String username = FONPreferences.getUsername(context);
		String password = FONPreferences.getPassword(context);

		WebLogger logger = null;
		if (FONUtils.isNeufBox(ssid, bssid)) {
			logger = new NeufLogger();
		} else if (FONUtils.isBtHub(ssid, bssid)) {
			logger = new BTFonLogger();
		} else if (FONUtils.isLivedoor(ssid, bssid)) {
			logger = new LivedoorLogger();
		} else {
			logger = new WISPrLogger();
		}

		LoggerResult result = logger.login(username, password);
		Log.d(TAG, "LOGIN RESULT: " + result);
		return result;
	}
	
	static public void LogOff(Context context){
		String logOffUrl = FONPreferences.getLogOffUrl(context);
		try {
			if (logOffUrl != null && logOffUrl.trim().length() > 0) {
				HttpUtils.getUrl(logOffUrl);
				FONStatus.disconnected(context);
			}
		} catch (IOException e) {
			Log.e(TAG, "Error login off", e);
		}
	}
}