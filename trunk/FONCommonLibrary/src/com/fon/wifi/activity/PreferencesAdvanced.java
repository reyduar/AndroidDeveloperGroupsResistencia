package com.fon.wifi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fon.wifi.FONLogin;
import com.fon.wifi.R;
import com.fon.wifi.util.FONPreferences;
import com.fon.wifi.util.IntentActionsFactory;

abstract public class PreferencesAdvanced extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.preferences_advanced);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		String logOffUrl = FONPreferences.getLogOffUrl(this);
		MenuItem menuItem = menu.findItem(R.id.menu_log_off);
		menuItem.setEnabled(logOffUrl != null);

		menuItem = menu.findItem(R.id.menu_settings);
		menuItem.setEnabled(false);
		
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.app_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean res = true;
		if (item.getItemId()==R.id.menu_log_off) {
			logOff(item);
		}else if(item.getItemId()==R.id.menu_settings){
				startActivity(new Intent(IntentActionsFactory.getPreferencesActivityAction()));
				finish();
		}else if(item.getItemId()==R.id.menu_about){
				String url = "file:///android_asset/html/" + getString(R.string.html_language_folder)+"/about.html";
				Intent i = new Intent(IntentActionsFactory.getWebActivityAction());
				i.putExtra(WebActivity.EXTRA_URL, url);
				i.putExtra(WebActivity.EXTRA_TITLE, getString(R.string.label_about));
				startActivity(i);
				finish();
		}else{
				res = super.onOptionsItemSelected(item);
		}

		return res;
	}

	private void logOff(MenuItem item) {
		FONLogin.LogOff(this);
		item.setEnabled(false);
	}
}