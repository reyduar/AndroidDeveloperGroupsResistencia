package com.fon.wifi.zon;
import com.fon.wifi.notification.FONNotificationManager;

public class FonApp extends com.fon.wifi.FonApp {	
	@Override
	public void onCreate() {
	super.onCreate();
	FONNotificationManager.init(R.drawable.notif_fon_connected,R.drawable.notif_fon_error,R.drawable.notif_fon_available);
}
}
