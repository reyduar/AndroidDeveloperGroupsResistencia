package com.fon.wifi.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.fon.wifi.FONLogin;
import com.fon.wifi.R;
import com.fon.wifi.util.FONPreferences;
import com.fon.wifi.util.IntentActionsFactory;

abstract public class FonActivity extends Activity {
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		String logOffUrl = FONPreferences.getLogOffUrl(this);
		MenuItem menuItem = menu.findItem(R.id.menu_log_off);
		menuItem.setEnabled(logOffUrl != null);

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
				quit();
		}else if(item.getItemId()==R.id.menu_about){
				String url = "file:///android_asset/html/" + getString(R.string.html_language_folder)+"/about.html";
				Intent i = new Intent(IntentActionsFactory.getWebActivityAction());
				i.putExtra(WebActivity.EXTRA_URL, url);
				i.putExtra(WebActivity.EXTRA_TITLE, getString(R.string.label_about));
				startActivity(i);
				quit();
		}else{
				res = super.onOptionsItemSelected(item);
		}

		return res;
	}

	protected void quit() {
		finish();
	}

	private void logOff(final MenuItem item) {
		Builder b = new Builder(this);
		b.setTitle(R.string.app_name);
		b.setMessage(R.string.label_confirm_log_off);
		b.setPositiveButton(R.string.btn_log_off, new  OnClickListener() {
			
			public void onClick(DialogInterface dialog, int id) {
				FONLogin.LogOff(FonActivity.this);
				item.setEnabled(false);
			}
		});
		b.setNegativeButton(R.string.btn_cancel, new  OnClickListener() {
			
			public void onClick(DialogInterface dialog, int id) {
			}
		});
		b.create().show();
	}

}