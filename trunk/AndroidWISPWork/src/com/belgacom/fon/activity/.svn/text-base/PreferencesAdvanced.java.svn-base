package com.belgacom.fon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.belgacom.fon.FONLogin;
import com.belgacom.fon.R;
import com.belgacom.fon.util.FONPreferences;

public class PreferencesAdvanced extends PreferenceActivity {

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
		switch (item.getItemId()) {
			case R.id.menu_log_off:
				logOff(item);
				break;
			case R.id.menu_about:
				String url = "file:///android_asset/html/" + getString(R.string.html_language_folder)+"/about.html";
				Intent i = new Intent(this,WebActivity.class);
				i.putExtra(WebActivity.EXTRA_URL, url);
				i.putExtra(WebActivity.EXTRA_TITLE, getString(R.string.label_about));
				startActivity(i);
				finish();
				break;
			default:
				res = super.onOptionsItemSelected(item);
		}

		return res;
	}

	private void logOff(MenuItem item) {
		FONLogin.LogOff(this);
		item.setEnabled(false);
	}
}