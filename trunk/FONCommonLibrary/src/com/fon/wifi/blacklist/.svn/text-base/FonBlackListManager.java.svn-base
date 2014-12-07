package com.fon.wifi.blacklist;

import static com.fon.wifi.blacklist.FonBlackListProvider.BlackListTable.COLUMN_BSSID;
import static com.fon.wifi.blacklist.FonBlackListProvider.BlackListTable.COLUMN_SSID;
import static com.fon.wifi.blacklist.FonBlackListProvider.BlackListTable.COLUMN_TIME;
import static com.fon.wifi.blacklist.FonBlackListProvider.BlackListTable.INDEX_TIME;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class FonBlackListManager {

	private static final long BLACK_LIST_WINDOW_TIME = 300000; // 5 Minutos

	public static boolean isBlackListed(Context context, String ssid, String bssid) {
		Cursor fonBlackListed = context.getContentResolver().query(
				FonBlackListProvider.getBlackListUri(), 
				null,
				COLUMN_SSID + "=? and " + COLUMN_BSSID + "=? ", 
				new String[] { ssid, bssid }, 
				null);
		try{
			if (fonBlackListed.moveToFirst()) {
				long since = fonBlackListed.getLong(INDEX_TIME);
				if (since + BLACK_LIST_WINDOW_TIME < System.currentTimeMillis()) {
					context.getContentResolver().delete(
							FonBlackListProvider.getBlackListUri(), 
							COLUMN_SSID + "=? and " + COLUMN_BSSID + "=? ",
							new String[] { ssid, bssid });
					return false;
				}
				return true;
			}
		}
		finally{
			fonBlackListed.close();
		}
		return false;
	}

	public static void addToBlackList(Context context, String ssid, String bssid) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_SSID, ssid);
		values.put(COLUMN_BSSID, bssid);
		values.put(COLUMN_TIME, System.currentTimeMillis());
		
		Cursor fonBlackListed = context.getContentResolver().query(
				FonBlackListProvider.getBlackListUri(), 
				null,
				COLUMN_SSID + "=? and " + COLUMN_BSSID + "=? ", 
				new String[] { ssid, bssid }, 
				null);
		if (fonBlackListed.moveToFirst()) {
			context.getContentResolver().update(
					FonBlackListProvider.getBlackListUri(),
					values,
					COLUMN_SSID + "=? and " + COLUMN_BSSID + "=? ", 
					new String[] { ssid, bssid });
		}
		else{
			context.getContentResolver().insert(FonBlackListProvider.getBlackListUri(),values);
		}
	}
}
