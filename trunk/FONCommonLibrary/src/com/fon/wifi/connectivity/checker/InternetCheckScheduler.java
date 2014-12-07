package com.fon.wifi.connectivity.checker;

import com.fon.wifi.R;
import com.fon.wifi.util.IntentActionsFactory;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.Log;

public class InternetCheckScheduler {

	private static final String TAG = InternetCheckScheduler.class.getCanonicalName();
	
	private static final int PERIODIC_CHECK_ALARM_ID = 1;

	public static void removeCheckerAlarm(Context context) {
		Log.d(TAG, "INTERNET CHECK ALARM REMOVED");
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent();
		i.setAction(IntentActionsFactory.getInternetCheckAlarmAction());
		PendingIntent alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(),
				PERIODIC_CHECK_ALARM_ID, i, 0);
		am.cancel(alarmIntent);
	}

	public static void setCheckerAlarm(Context context) {
		if(shouldCheckFonConnection(context)){
		Log.d(TAG, "INTERNET CHECK ALARM SCHEDULED");
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent();
		i.setAction(IntentActionsFactory.getInternetCheckAlarmAction());
		PendingIntent alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(),
				PERIODIC_CHECK_ALARM_ID, i, PendingIntent.FLAG_UPDATE_CURRENT);
		
		am.setRepeating(
				AlarmManager.RTC, 
				System.currentTimeMillis(), 
				getCheckInterval(context), // every minute
				alarmIntent);
		
		}
	}

	private static boolean shouldCheckFonConnection(Context context) {
		TypedArray a = context.getTheme().obtainStyledAttributes(new int[] {R.attr.check_fon_connection_in_background});
		return a.getBoolean(0, true);
	}
	private static long getCheckInterval(Context context) {
		TypedArray a = context.getTheme().obtainStyledAttributes(new int[] {R.attr.check_fon_connection_minutes_interval});
		return a.getInt(0, 15)*60*1000;
	}

}
