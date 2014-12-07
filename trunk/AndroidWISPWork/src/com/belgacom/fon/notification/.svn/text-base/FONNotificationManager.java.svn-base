package com.belgacom.fon.notification;

import static com.belgacom.fon.util.WISPrConstants.*;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.belgacom.fon.R;
import com.belgacom.fon.util.FONPreferences;
import com.belgacom.fon.util.WISPrConstants;

public class FONNotificationManager {

	private static final String TAG = FONNotificationManager.class.getCanonicalName();
	private static int FON_NOTIFICATION_ID = 100;

	final static long[] VIBRATE_PATTERN = new long[] { 100, 250 };

	public static void clearNotifications(Context context) {
		Log.d(TAG, "clearNotifications");
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(FON_NOTIFICATION_ID);
	}

	public static void notifyFONAvailable(Context context) {
		Log.d(TAG, "notifyFONAvailable");
		if (FONPreferences.isNotificationEnabled(context)) {
			String notificationTitle = context.getString(R.string.notif_available_title);
			String notificationText = context.getString(R.string.notif_available_text);

			Notification notification = new Notification(R.drawable.notif_fon_connected, notificationTitle,
					System.currentTimeMillis());

			RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification);
			contentView.setImageViewResource(R.id.notif_icon, R.drawable.notif_fon_available);
			contentView.setTextViewText(R.id.notif_title, notificationTitle);
			contentView.setTextColor(R.id.notif_title, NotificationUtils.getNotificationTitleColor(context));
			contentView.setTextViewText(R.id.notif_text, notificationText);
			contentView.setTextColor(R.id.notif_text, NotificationUtils.getNotificationContentColor(context));
			
			Intent dismissServiceIntent = new Intent(context, NotificationHandlerService.class);
			dismissServiceIntent.setAction(NotificationHandlerService.ACTION_PAUSE_FON_SERVICE);
			PendingIntent dismissIntent = PendingIntent.getService(context, 0, dismissServiceIntent, 0);
			contentView.setOnClickPendingIntent(R.id.notif_dismiss_btn, dismissIntent);
			
			notification.contentView = contentView;
			
			Intent handlerIntent = new Intent(context, NotificationHandlerService.class);
			handlerIntent.setAction(NotificationHandlerService.ACTION_CONNECT_TO_FON_ROUTER);
			PendingIntent connectIntent = PendingIntent.getService(context, 0, handlerIntent, 0);
			contentView.setOnClickPendingIntent(R.id.notif_body, connectIntent);


			PendingIntent contentIntent = PendingIntent.getActivity(context, 0,  new Intent(), 0);
			notification.contentIntent = contentIntent;

			postNotification(context, notification);
		}
	}

	public static void notifyFONConnected(Context context, String ssid) {
		Log.d(TAG, "notifyFONConnected");
		if (FONPreferences.isNotificationEnabled(context)) {
			String notificationTitle = context.getString(R.string.notif_connected_title);
			String notificationText = context.getString(R.string.notif_connected_text, ssid);

			Intent intent = new Intent(context, NotificationHandlerService.class);
			intent.setAction(NotificationHandlerService.ACTION_OPEN_BROWSER);
			PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

			Notification notification = new Notification(R.drawable.notif_fon_connected, notificationTitle,
					System.currentTimeMillis());
			notification.setLatestEventInfo(context, notificationTitle, notificationText, pendingIntent);
			notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
			
			postNotification(context, notification);
		}
	}

	public static void notifyFONDisconnected(Context context, String ssid) {
		Log.d(TAG, "notifyFONDisconnected");
		if (FONPreferences.isNotificationEnabled(context)) {
			String notificationTitle = context.getString(R.string.notif_disconnected_title);
			String notificationText = context.getString(R.string.notif_disconnected_text, ssid);

			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);

			Notification notification = new Notification(R.drawable.notif_fon_error, notificationTitle,
					System.currentTimeMillis());
			notification.setLatestEventInfo(context, notificationTitle, notificationText, pendingIntent);
			
			notification.flags = notification.flags | Notification.FLAG_AUTO_CANCEL;
			
			postNotification(context, notification);
		}
	}

	public static void notifyFONError(Context context, int error, String ssid, String bssid) {
		Log.d(TAG, "notifyFONError");
		if (FONPreferences.isNotificationEnabled(context)) {
			String notificationText = context.getString(R.string.notif_error_unknown);
			String notificationTitle = context.getString(R.string.notif_error_title);
			switch(error){
			case WISPR_RESPONSE_CODE_LOGIN_FAILED:
				notificationText = context.getString(R.string.notif_error_100);
				break;
			case CREDENTIALS_ERROR:
				notificationText = context.getString(R.string.notif_no_credentials_text);
				break;
			case NOT_AUTHORIZED:
				notificationText = context.getString(R.string.notif_error_not_authorized);
				break;
			case NO_SUCH_USER:
				notificationText = context.getString(R.string.notif_error_not_such_user);
				break;
			case NOT_ENOUGH_CREDIT:
				notificationText = context.getString(R.string.notif_error_not_enough_credit);
				break;
			case USER_IN_BLACK_LIST:
				notificationText = context.getString(R.string.notif_error_user_in_black_list);
				break;
			case USER_NOT_FOUND:
				notificationText = context.getString(R.string.notif_error_user_not_found);
				break;		
			default:
				notificationText = context.getString(R.string.notif_error_unknown);
				break;		
			}

			Notification notification = new Notification(R.drawable.notif_fon_error, notificationTitle,
					System.currentTimeMillis());

			RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification);
			contentView.setImageViewResource(R.id.notif_icon, R.drawable.notif_fon_error);
			contentView.setTextViewText(R.id.notif_title, notificationTitle);
			contentView.setTextColor(R.id.notif_title, NotificationUtils.getNotificationTitleColor(context));
			contentView.setTextViewText(R.id.notif_text, notificationText);
			contentView.setTextColor(R.id.notif_text, NotificationUtils.getNotificationContentColor(context));
			
			Intent dismissServiceIntent = new Intent(context, NotificationHandlerService.class);
			dismissServiceIntent.setAction(NotificationHandlerService.ACTION_DISMISS_ERROR);
			dismissServiceIntent.putExtra(NotificationHandlerService.EXTRA_SSID,ssid);
			dismissServiceIntent.putExtra(NotificationHandlerService.EXTRA_BSSID,bssid);
			PendingIntent dismissIntent = PendingIntent.getService(context, 0, dismissServiceIntent, 0);
			contentView.setOnClickPendingIntent(R.id.notif_dismiss_btn, dismissIntent);
			
			notification.contentView = contentView;

			Intent handlerIntent = new Intent(context, NotificationHandlerService.class);
			handlerIntent.setAction(NotificationHandlerService.ACTION_HANDLE_ERROR);
			handlerIntent.putExtra(NotificationHandlerService.EXTRA_ERROR, error);
			PendingIntent bodyIntent = PendingIntent.getService(context, 0, handlerIntent, 0);
			contentView.setOnClickPendingIntent(R.id.notif_body, bodyIntent);

			PendingIntent contentIntent = PendingIntent.getService(context, 0,  new Intent(), 0);
			notification.contentIntent = contentIntent;

			postNotification(context, notification);
		}
	}
	
	public static void notifyFONMissingCredentials(Context context) {
		Log.d(TAG, "notifyFONDisconnected");
		if (FONPreferences.isNotificationEnabled(context)) {
			String notificationTitle = context.getString(R.string.notif_no_credentials_title);
			String notificationText = context.getString(R.string.notif_no_credentials_text);

			Intent handlerIntent = new Intent(context, NotificationHandlerService.class);
			handlerIntent.setAction(NotificationHandlerService.ACTION_HANDLE_ERROR);
			handlerIntent.putExtra(NotificationHandlerService.EXTRA_ERROR, WISPrConstants.CREDENTIALS_ERROR);
			PendingIntent pendingIntent = PendingIntent.getService(context, 0, 	handlerIntent, 0);

			Notification notification = new Notification(R.drawable.notif_fon_error, notificationTitle,
					System.currentTimeMillis());
			notification.setLatestEventInfo(context, notificationTitle, notificationText, pendingIntent);

			notification.flags = notification.flags | Notification.FLAG_AUTO_CANCEL;

			postNotification(context, notification);
		}
	}

	private static void postNotification(Context context, Notification notification) {


		if (FONPreferences.isNotificationVibrateEnabled(context)) {
			notification.vibrate = VIBRATE_PATTERN;
		}

		String ringtone = FONPreferences.getNotificationRingtone(context);
		if (ringtone == null) {
			notification.defaults |= Notification.DEFAULT_SOUND;
		} else {
			notification.sound = Uri.parse(ringtone);
		}

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(FON_NOTIFICATION_ID, notification);
	}
}
