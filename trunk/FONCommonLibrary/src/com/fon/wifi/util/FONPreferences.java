package com.fon.wifi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fon.wifi.R;

public class FONPreferences {

	private static final String TAG = FONPreferences.class.getCanonicalName();
	
	private static final String KEY_LOG_OFF_URL = "LOG_OFF_URL";

	static public void saveLogOffUrl(Context context, String logOffUrl) {
		Log.d(TAG, "saveLogOffUrl "+logOffUrl);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putString(KEY_LOG_OFF_URL, logOffUrl);
		editor.commit();
	}
	static public String getLogOffUrl(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getString(KEY_LOG_OFF_URL,null);
	}
	static public void clearLogOffUrl(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.remove(KEY_LOG_OFF_URL);
		editor.commit();
	}

	public static boolean isNotificationEnabled(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getBoolean(context.getString(R.string.pref_notif_enabled),true);
	}
	public static boolean isNotificationVibrateEnabled(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getBoolean(context.getString(R.string.pref_notif_vibrate),false);
	}
	static public String getNotificationRingtone(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getString(context.getString(R.string.pref_notif_ringtone),null);
	}
	public static boolean isAutoConnectEnabled(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getBoolean(context.getString(R.string.pref_auto_connect),true);
	}
	
	public static String getUsername(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getString(context.getString(R.string.pref_username),"");
	}
	public static String getPassword(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getString(context.getString(R.string.pref_password),"");
	}
	

	static public void saveUsername(Context context, String username) {
		Log.d(TAG, "saveUsername "+username);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putString(context.getString(R.string.pref_username), username);
		editor.commit();
	}
	static public void savePassword(Context context, String username) {
		Log.d(TAG, "saveUsername "+username);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putString(context.getString(R.string.pref_password), username);
		editor.commit();
	}

	public static boolean areCredentialsConfigured(Context context) {
		String username = Utils.getStringPreference(context, R.string.pref_username, "");
		String password = Utils.getStringPreference(context, R.string.pref_password, "");

		return (username.trim().length() > 0 && password.trim().length() > 0);
	}
}
