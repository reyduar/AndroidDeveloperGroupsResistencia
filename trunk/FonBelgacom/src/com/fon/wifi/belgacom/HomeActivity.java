package com.fon.wifi.belgacom;

public class HomeActivity extends com.fon.wifi.activity.HomeActivity{

	@Override
	protected Class<?> getOnStandByReceiverClass() {
		return InternetCheckOnStandByReceiver.class;
	}

}
