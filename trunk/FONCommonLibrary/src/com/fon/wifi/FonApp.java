package com.fon.wifi;

import com.fon.wifi.blacklist.FonBlackListProvider;
import com.fon.wifi.connectivity.FonNetworkListProvider;
import com.fon.wifi.util.IntentActionsFactory;

import android.app.Application;

public class FonApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		FonBlackListProvider.init(getPackageName());
		FonNetworkListProvider.init(getPackageName());
		IntentActionsFactory.init(getPackageName());
	}

}
