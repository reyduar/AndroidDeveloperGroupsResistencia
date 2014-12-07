package com.belgacom.fon;

import java.io.IOException;

import android.content.Context;
import android.util.Log;

import com.belgacom.fon.logger.BTFonLogger;
import com.belgacom.fon.logger.LivedoorLogger;
import com.belgacom.fon.logger.LoggerResult;
import com.belgacom.fon.logger.NeufLogger;
import com.belgacom.fon.logger.WISPrLogger;
import com.belgacom.fon.logger.WebLogger;
import com.belgacom.fon.util.FONPreferences;
import com.belgacom.fon.util.FONUtils;
import com.belgacom.fon.util.HttpUtils;

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