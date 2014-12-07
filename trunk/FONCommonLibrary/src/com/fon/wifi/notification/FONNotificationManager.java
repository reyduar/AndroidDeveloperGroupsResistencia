package com.fon.wifi.notification;

import static com.fon.wifi.util.WISPrConstants.CREDENTIALS_ERROR;
import static com.fon.wifi.util.WISPrConstants.NOT_AUTHORIZED;
import static com.fon.wifi.util.WISPrConstants.NOT_ENOUGH_CREDIT;
import static com.fon.wifi.util.WISPrConstants.NO_SUCH_USER;
import static com.fon.wifi.util.WISPrConstants.USER_IN_BLACK_LIST;
import static com.fon.wifi.util.WISPrConstants.USER_NOT_FOUND;
import static com.fon.wifi.util.WISPrConstants.WISPR_RESPONSE_CODE_LOGIN_FAILED;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.fon.wifi.R;
import com.fon.wifi.util.FONPreferences;
import com.fon.wifi.util.IntentActionsFactory;
import com.fon.wifi.util.WISPrConstants;

public class FONNotificationManager {

	private static final String TAG = FONNotificationManager.class.getCanonicalName();
	private static int FON_NOTIFICATION_ID = 100;


	private static int FON_CONNECTED_ICON_ID;
	private static int FON_ERROR_ICON_ID;
	private static int FON_AVAILABLE_ICON_ID;
	public static void init(int connectedIconId,int errorIconId,int availableIconId){
		FON_CONNECTED_ICON_ID = connectedIconId;
		FON_ERROR_ICON_ID = errorIconId;
		FON_AVAILABLE_ICON_ID = availableIconId;
	}
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

			Notification notification = new Notification(FON_AVAILABLE_ICON_ID, notificationTitle,
					System.currentTimeMillis());
			
			/*
			RemoteViews contentView = new RemoteViews(context.getPackageName(), NOTIFICATION_LAYOUT_ID);

			contentView.setImageViewResource(NOTIFICATION_ICON_ID, getDrawableFonAvailable(context));
			contentView.setTextViewText(NOTIFICATION_TITLE_ID, notificationTitle);
			contentView.setTextColor(NOTIFICATION_TITLE_ID, NotificationUtils.getNotificationTitleColor(context));
			contentView.setTextViewText(NOTIFICATION_TEXT_ID, notificationText);
			contentView.setTextColor(NOTIFICATION_TEXT_ID, NotificationUtils.getNotificationContentColor(context));
			*/
			Intent dismissServiceIntent = new Intent(IntentActionsFactory.getNotificationHandlerServiceAction());
			dismissServiceIntent.putExtra(NotificationHandlerService.EXTRA_ACTION,NotificationHandlerService.ACTION_PAUSE_FON_SERVICE);
			PendingIntent dismissIntent = PendingIntent.getService(context, 0, dismissServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			notification.deleteIntent = dismissIntent;
			//contentView.setOnClickPendingIntent(NOTIFICATION_BTN_ID, dismissIntent);
			
			//notification.contentView = contentView;
			
			Intent handlerIntent = new Intent(IntentActionsFactory.getNotificationHandlerServiceAction());
			handlerIntent.putExtra(NotificationHandlerService.EXTRA_ACTION,NotificationHandlerService.ACTION_CONNECT_TO_FON_ROUTER);
			PendingIntent connectIntent = PendingIntent.getService(context, 0, handlerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			//contentView.setOnClickPendingIntent(NOTIFICATION_BODY_ID, connectIntent);


			notification.setLatestEventInfo(context, notificationTitle, notificationText, connectIntent);
			/*PendingIntent contentIntent = PendingIntent.getActivity(context, 0,  new Intent(), 0);
			notification.contentIntent = contentIntent;*/

			postNotification(context, notification);
		}
	}

	public static void notifyFONConnected(Context context, String ssid) {
		Log.d(TAG, "notifyFONConnected");
		if (FONPreferences.isNotificationEnabled(context)) {
			String notificationTitle = context.getString(R.string.notif_connected_title);
			String notificationText = context.getString(R.string.notif_connected_text, ssid);

			Intent intent = new Intent(IntentActionsFactory.getNotificationHandlerServiceAction());

			intent.putExtra(NotificationHandlerService.EXTRA_ACTION,NotificationHandlerService.ACTION_OPEN_BROWSER);
			PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			Notification notification = new Notification(FON_CONNECTED_ICON_ID, notificationTitle,
					System.currentTimeMillis());
			notification.setLatestEventInfo(context, notificationTitle, notificationText, pendingIntent);
			notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;

			notification.contentIntent = pendingIntent;
			postNotification(context, notification);
		}
	}

	public static void notifyFONDisconnected(Context context, String ssid) {
		Log.d(TAG, "notifyFONDisconnected");
		if (FONPreferences.isNotificationEnabled(context)) {
			String notificationTitle = context.getString(R.string.notif_disconnected_title);
			String notificationText = context.getString(R.string.notif_disconnected_text, ssid);

			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

			Notification notification = new Notification(FON_ERROR_ICON_ID, notificationTitle,
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

			Notification notification = new Notification(FON_ERROR_ICON_ID, notificationTitle,
					System.currentTimeMillis());

			/*
			RemoteViews contentView = new RemoteViews("com.fon.wifi", NOTIFICATION_LAYOUT_ID);
			contentView.setImageViewResource(NOTIFICATION_ICON_ID, getDrawableFonError(context));
			contentView.setTextViewText(NOTIFICATION_TITLE_ID, notificationTitle);
			contentView.setTextColor(NOTIFICATION_TITLE_ID, NotificationUtils.getNotificationTitleColor(context));
			contentView.setTextViewText(NOTIFICATION_TEXT_ID, notificationText);
			contentView.setTextColor(NOTIFICATION_TEXT_ID, NotificationUtils.getNotificationContentColor(context));
			*/
			Intent dismissServiceIntent = new Intent(IntentActionsFactory.getNotificationHandlerServiceAction());
			dismissServiceIntent.putExtra(NotificationHandlerService.EXTRA_ACTION, NotificationHandlerService.ACTION_DISMISS_ERROR);
			dismissServiceIntent.putExtra(NotificationHandlerService.EXTRA_SSID,ssid);
			dismissServiceIntent.putExtra(NotificationHandlerService.EXTRA_BSSID,bssid);
			PendingIntent dismissIntent = PendingIntent.getService(context, 0, dismissServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			notification.deleteIntent = dismissIntent;
			//contentView.setOnClickPendingIntent(NOTIFICATION_BTN_ID, dismissIntent);
			
			//notification.contentView = contentView;

			Intent handlerIntent = new Intent(IntentActionsFactory.getNotificationHandlerServiceAction());
			handlerIntent.putExtra(NotificationHandlerService.EXTRA_ACTION, NotificationHandlerService.ACTION_HANDLE_ERROR);
			handlerIntent.putExtra(NotificationHandlerService.EXTRA_ERROR, error);
			PendingIntent bodyIntent = PendingIntent.getService(context, 0, handlerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			notification.setLatestEventInfo(context, notificationTitle, notificationText, bodyIntent);
			/*contentView.setOnClickPendingIntent(NOTIFICATION_BODY_ID, bodyIntent);

			PendingIntent contentIntent = PendingIntent.getService(context, 0,  new Intent(), 0);
			notification.contentIntent = contentIntent;
			*/

			postNotification(context, notification);
		}
	}
	
	public static void notifyFONMissingCredentials(Context context) {
		Log.d(TAG, "notifyFONDisconnected");
		if (FONPreferences.isNotificationEnabled(context)) {
			String notificationTitle = context.getString(R.string.notif_no_credentials_title);
			String notificationText = context.getString(R.string.notif_no_credentials_text);
			
			Intent handlerIntent = new Intent(IntentActionsFactory.getNotificationHandlerServiceAction());
			handlerIntent.putExtra(NotificationHandlerService.EXTRA_ACTION, NotificationHandlerService.ACTION_HANDLE_ERROR);
			handlerIntent.putExtra(NotificationHandlerService.EXTRA_ERROR, WISPrConstants.CREDENTIALS_ERROR);
			PendingIntent pendingIntent = PendingIntent.getService(context, 0, handlerIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			Notification notification = new Notification(FON_ERROR_ICON_ID, notificationTitle,
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
