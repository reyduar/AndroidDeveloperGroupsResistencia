package com.belgacom.fon.notification;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.belgacom.fon.FONStatus;
import com.belgacom.fon.activity.AccountActivity;
import com.belgacom.fon.activity.HomeActivity;
import com.belgacom.fon.blacklist.FonBlackListManager;
import com.belgacom.fon.connectivity.RouterConnectService;
import com.belgacom.fon.util.WISPrConstants;

public class NotificationHandlerService extends IntentService{

	private static final String TAG = NotificationHandlerService.class.getCanonicalName();

	public static final String ACTION_DISMISS_ERROR = "com.belgacom.fon.notification.ACTION_DISMISS_ERROR";
	public static final String ACTION_HANDLE_ERROR = "com.belgacom.fon.notification.ACTION_HANDLE_ERROR";
	public static final String ACTION_PAUSE_FON_SERVICE = "com.belgacom.fon.notification.ACTION_PAUSE_FON_SERVICE";
	public static final String ACTION_OPEN_BROWSER = "com.belgacom.fon.notification.ACTION_OPEN_BROWSER";

	public static final String EXTRA_SSID = "SSID";
	public static final String EXTRA_BSSID = "BSSID";

	public static final String ACTION_CONNECT_TO_FON_ROUTER = "com.belgacom.fon.notification.ACTION_CONNECT_TO_FON_ROUTER";

	public static final String EXTRA_ERROR = "EXTRA_ERROR";
	
	public NotificationHandlerService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if(ACTION_DISMISS_ERROR.equals(intent.getAction())){
			String ssid = intent.getStringExtra(EXTRA_SSID);
			String bssid = intent.getStringExtra(EXTRA_BSSID);
			dismissError(ssid,bssid);
		}
		else if(ACTION_PAUSE_FON_SERVICE.equals(intent.getAction())){
			pause();
		}
		else if(ACTION_HANDLE_ERROR.equals(intent.getAction())){
			int error = intent.getIntExtra(EXTRA_ERROR, WISPrConstants.UNKNOWN_ERROR);
			handleError(error);
		}
		else if(ACTION_OPEN_BROWSER.equals(intent.getAction())){
			connectToBrowser();
		}
		else if(ACTION_CONNECT_TO_FON_ROUTER.equals(intent.getAction())){
			connectToFonRouter();
		}
	}

	private void connectToFonRouter() {
		Intent connectServiceIntent = new Intent(this, RouterConnectService.class);
		connectServiceIntent.putExtra(RouterConnectService.EXTRA_AUTOCONNECT,true);
		startService(connectServiceIntent);		
		FONNotificationManager.clearNotifications(this);
	}

	private void connectToBrowser() {
		String packageName = "com.android.browser"; 
		String className = "com.android.browser.BrowserActivity"; 
		Intent internetIntent = new Intent(Intent.ACTION_VIEW);
		internetIntent.addCategory(Intent.CATEGORY_LAUNCHER); 
		internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		internetIntent.setClassName(packageName, className); 

		final PackageManager packageManager = getPackageManager();
		@SuppressWarnings("rawtypes")
		List list = packageManager.queryIntentActivities(internetIntent, PackageManager.MATCH_DEFAULT_ONLY);
		if(list.isEmpty()){
			internetIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
		}
		startActivity(internetIntent);
	}

	private void handleError(int error) {
		Intent appIntent;
		switch (error) {
		case WISPrConstants.NOT_AUTHORIZED:
		case WISPrConstants.NO_SUCH_USER:
		case WISPrConstants.USER_NOT_FOUND:
		case WISPrConstants.USER_IN_BLACK_LIST:
		case WISPrConstants.NOT_ENOUGH_CREDIT:
		case WISPrConstants.WISPR_RESPONSE_CODE_LOGIN_FAILED:
		case WISPrConstants.CREDENTIALS_ERROR:
			appIntent = new Intent(this,AccountActivity.class);
			appIntent.putExtra(AccountActivity.EXTRA_ERROR, error);
			appIntent.putExtra(AccountActivity.EXTRA_SHOW_ERROR, true);
			break;

		default:		
			appIntent = new Intent(this,HomeActivity.class);
		}
		appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(appIntent);
		FONStatus.disconnected(this);
		FONNotificationManager.clearNotifications(this);
	
	}

	private void dismissError(String ssid,String bssid) {
		FonBlackListManager.addToBlackList(this, ssid,bssid);
		FONStatus.disconnected(this);
		FONNotificationManager.clearNotifications(this);	
	}

	private void pause() {
		FONStatus.pause(this);
		FONNotificationManager.clearNotifications(this);		
	}

}
