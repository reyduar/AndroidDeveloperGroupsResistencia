package com.belgacom.fon;

import android.app.IntentService;
import android.content.Intent;

public class ResetStatusService extends IntentService {

	private static final String TAG = ResetStatusService.class.getCanonicalName();
	
	public ResetStatusService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		FONStatus.reset(this);
	}

}
