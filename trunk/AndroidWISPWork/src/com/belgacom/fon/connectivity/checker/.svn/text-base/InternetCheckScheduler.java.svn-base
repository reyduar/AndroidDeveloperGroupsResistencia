package com.belgacom.fon.connectivity.checker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class InternetCheckScheduler {

	private static final String TAG = InternetCheckScheduler.class.getCanonicalName();
	
	private static final int PERIODIC_CHECK_ALARM_ID = 1;

	public static void removeCheckerAlarm(Context context) {
		Log.d(TAG, "INTERNET CHECK ALARM REMOVED");
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context.getApplicationContext(), InternetCheckAlarmReceiver.class);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(),
				PERIODIC_CHECK_ALARM_ID, i, 0);
		am.cancel(alarmIntent);
	}

	public static void setCheckerAlarm(Context context) {
		Log.d(TAG, "INTERNET CHECK ALARM SCHEDULED");
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context.getApplicationContext(), InternetCheckAlarmReceiver.class);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(),
				PERIODIC_CHECK_ALARM_ID, i, PendingIntent.FLAG_UPDATE_CURRENT);
		
		am.setRepeating(
				AlarmManager.RTC, 
				System.currentTimeMillis(), 
				1000*60, // every minute
				alarmIntent);
		
		
//		am.setInexactRepeating(
//				AlarmManager.RTC, 
//				System.currentTimeMillis(), 
//				AlarmManager.INTERVAL_FIFTEEN_MINUTES, 
//				alarmIntent);
	}

}
