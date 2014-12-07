package com.fon.wifi.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;

import com.fon.wifi.R;
import com.fon.wifi.notification.FONNotificationManager;
import com.fon.wifi.util.IntentActionsFactory;

public abstract class HomeActivity extends FonActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		if(!shouldCheckFonConnectionOnStandBy()){
			ComponentName receiver=new ComponentName(this, getOnStandByReceiverClass());
			getPackageManager().setComponentEnabledSetting(
					receiver, 
					PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
					PackageManager.DONT_KILL_APP);
		}
	}

	public void openAccount(View view){
		Intent i = new Intent();
		i.setAction(IntentActionsFactory.getAccountActivityAction());
		startActivity(i);
	}

	public void openHelp(View view){
		Intent i = new Intent();
		i.setAction(IntentActionsFactory.getHelpListActivityAction());
		startActivity(i);
	}

	@Override
	protected void quit() {
	}
	

	private boolean shouldCheckFonConnectionOnStandBy() {
		TypedArray a = getTheme().obtainStyledAttributes(new int[] {R.attr.check_fon_connection_on_stand_by});
		return a.getBoolean(0, true);
	}
	
	abstract protected  Class<?> getOnStandByReceiverClass();
}