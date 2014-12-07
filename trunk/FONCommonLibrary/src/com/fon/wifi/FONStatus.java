package com.fon.wifi;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fon.wifi.connectivity.ConnectivityUtis;
import com.fon.wifi.connectivity.FonNetworkListProvider;
import com.fon.wifi.connectivity.FonNetworkListProvider.NetworkListTable;
import com.fon.wifi.connectivity.checker.InternetCheckScheduler;
import com.fon.wifi.notification.FONNotificationManager;

public class FONStatus {

	private static final String TAG = FONStatus.class.getCanonicalName();

	private static final String FILE_STATUS = "FON_APP_STATUS";
	
	private static final String KEY_STATUS = "STATUS";
	private static final String KEY_PAUSED_WHEN = "PAUSED_WHEN";
	private static final String KEY_SSID = "SSID";
	private static final String KEY_BSSID = "BSSID";

	private static final long PAUSED_WINDOW = 300000;//5 minutes

	private static final int DISCONNECTED = 0;
	private static final int WIFI_CONNECTED = 1;
	private static final int FON_AVAILABLE = 2;
	private static final int FON_CONNECTED = 3;
	private static final int FON_LOGUED_IN = 4;
	private static final int FON_ERROR = 5;
	private static final int FON_PAUSED = 6;

	synchronized public static void reset(Context context) {
		Cursor networks = context.getContentResolver().query(FonNetworkListProvider.getNetworkListUri(), null, null, null, null);
		if(networks.moveToFirst()){
			do{
				String ssid = networks.getString(NetworkListTable.INDEX_SSID);
				if(ConnectivityUtis.removeFONRouter(context,ssid)){
					context.getContentResolver().delete(
							FonNetworkListProvider.getNetworkListUri(), 
							NetworkListTable.COLUMN_SSID + "= ?", 
							new String[]{ssid});
				}
			}while(networks.moveToNext());
		}
		networks.close();
		clearSSID(context);
		clearBSSID(context);
		FONNotificationManager.clearNotifications(context);
		saveStatus(context, DISCONNECTED);
	}
	
	synchronized public static void disconnected(Context context) {
		String ssid = getSSID(context);
		String bssid = getBSSID(context);
		Log.d(TAG, "DISCONECTED WIFI: " + ssid + "-" +bssid);
		clearSSID(context);
		clearBSSID(context);
		switch (getStatus(context)) {
		case FON_CONNECTED:
			ConnectivityUtis.removeFONRouter(context,ssid);
			saveStatus(context, DISCONNECTED);
			break;
		case FON_LOGUED_IN:
			ConnectivityUtis.removeFONRouter(context,ssid);
			InternetCheckScheduler.removeCheckerAlarm(context);
			FONNotificationManager.notifyFONDisconnected(context, ssid);
			saveStatus(context, DISCONNECTED);
			break;
		case FON_ERROR:
			break;
		default:
			saveStatus(context, DISCONNECTED);
			break;
		}
	}

	synchronized public static void connectToWifi(Context context,String ssid, String bssid) {	
		switch (getStatus(context)) {
		case DISCONNECTED:
		case FON_AVAILABLE:
		case FON_ERROR:
			saveSSID(context, ssid);
			saveBSSID(context, bssid);
			FONNotificationManager.clearNotifications(context);
			saveStatus(context, WIFI_CONNECTED);
		}
	}

	synchronized public static void fonAvailable(Context context) {
		switch (getStatus(context)) {
		case DISCONNECTED:
			FONNotificationManager.notifyFONAvailable(context);
			saveStatus(context, FON_AVAILABLE);
			break;
		}
	}
	
	synchronized public static void connectToFon(Context context,String ssid, String bssid) {		
		switch (getStatus(context)) {
		case DISCONNECTED:
		case FON_AVAILABLE:
		case FON_ERROR:
			saveSSID(context, ssid);
			saveBSSID(context, bssid);
			ContentValues values = new ContentValues();
			values.put(NetworkListTable.COLUMN_SSID, ssid);
			context.getContentResolver().insert(FonNetworkListProvider.getNetworkListUri(), values);
			saveStatus(context, FON_CONNECTED);
			break;
		}
	}

	synchronized public static void logIn(Context context, String ssid) {		
		switch (getStatus(context)) {
		case FON_CONNECTED:
			saveStatus(context, FON_LOGUED_IN);
			InternetCheckScheduler.setCheckerAlarm(context);
			FONNotificationManager.notifyFONConnected(context, ssid);
			break;
		}
	}

	synchronized public static void error(Context context,int error, String ssid, String bssid) {		
		switch (getStatus(context)) {
		case FON_CONNECTED:
			ConnectivityUtis.removeFONRouter(context,ssid);
			FONNotificationManager.notifyFONError(context, error, ssid, bssid);
			saveStatus(context, FON_ERROR);
			break;
		}
	}
	synchronized public static void errorFONMissingCredentials(Context context, String ssid) {
		switch (getStatus(context)) {
		case FON_CONNECTED:
			ConnectivityUtis.removeFONRouter(context,ssid);
			FONNotificationManager.notifyFONMissingCredentials(context);
			saveStatus(context, FON_ERROR);
			break;
		}
	}
	
	synchronized public static void pause(Context context) {		
		switch (getStatus(context)) {
		case FON_AVAILABLE:
			saveStatus(context, FON_PAUSED);
			saveWhen(context);
			break;
		}
	}

	synchronized static public boolean isPaused(Context context) {
		boolean paused = false;
		switch (getStatus(context)) {
		case FON_PAUSED:
			SharedPreferences prefs = context.getSharedPreferences(FILE_STATUS, Context.MODE_PRIVATE);
			paused = prefs.getLong(KEY_PAUSED_WHEN,0)+PAUSED_WINDOW > System.currentTimeMillis();
			if(!paused){
				saveStatus(context, DISCONNECTED);
			}
			break;
		}
		return paused;
	}

	private static int getStatus(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(FILE_STATUS, Context.MODE_PRIVATE);
		return prefs.getInt(KEY_STATUS, DISCONNECTED);
	}
	private static void saveStatus(Context context,int status) {
		SharedPreferences prefs = context.getSharedPreferences(FILE_STATUS, Context.MODE_PRIVATE);
		Editor edit = prefs.edit();
		edit.putInt(KEY_STATUS, status);
		edit.commit();

		Log.d(TAG, "FON APP STATUS:" + printStatus(status));
	}

	private static void saveWhen(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(FILE_STATUS, Context.MODE_PRIVATE);
		Editor edit = prefs.edit();
		edit.putLong(KEY_PAUSED_WHEN, System.currentTimeMillis());
		edit.commit();

		Log.d(TAG, "FON PAUSED:" + new Date(System.currentTimeMillis()));
	}
	
	static private void saveSSID(Context context, String ssid) {
		Log.d(TAG, "saveSSID "+ssid);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putString(KEY_SSID, ssid);
		editor.commit();
	}
	static private String getSSID(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getString(KEY_SSID,null);
	}
	static private void clearSSID(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.remove(KEY_SSID);
		editor.commit();
	}

	static private void saveBSSID(Context context, String bssid) {
		Log.d(TAG, "saveBSSID "+bssid);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putString(KEY_BSSID, bssid);
		editor.commit();
	}
	static private String getBSSID(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getString(KEY_BSSID,null);
	}
	static private void clearBSSID(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.remove(KEY_BSSID);
		editor.commit();
	}
	
	private static String printStatus(int status) {
		switch (status) {
		case DISCONNECTED:
			return "DISCONNECTED";
		case FON_AVAILABLE:
			return "FON_AVAILABLE";
		case WIFI_CONNECTED:
			return "WIFI_CONNECTED";
		case FON_CONNECTED:
			return "FON_CONNECTED";
		case FON_LOGUED_IN:
			return "FON_LOGUED_IN";
		case FON_PAUSED:
			return "FON_PAUSED";
		case FON_ERROR:
			return "FON_ERROR";
		default:
			return "UNKNOWN";
		}
	}


}
