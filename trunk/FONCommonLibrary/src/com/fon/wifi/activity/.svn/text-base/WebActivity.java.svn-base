package com.fon.wifi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.fon.wifi.R;
import com.fon.wifi.util.IntentActionsFactory;

abstract public class WebActivity extends FonActivity {

	static public final String EXTRA_URL = "EXTRA_URL";
	static public final String EXTRA_TITLE = "EXTRA_TITLE";
	static public final String EXTRA_IS_HELP = "EXTRA_IS_HELP";
	
	private boolean isHelp = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		TextView title = (TextView)findViewById(R.id.web_title);
		title.setText(getIntent().getStringExtra(EXTRA_TITLE));
		WebView web = (WebView)findViewById(R.id.web);
		web.loadUrl(getIntent().getStringExtra(EXTRA_URL));
		isHelp = getIntent().getBooleanExtra(EXTRA_IS_HELP,false);
	}

	@Override
	public void onBackPressed() {
		if(isHelp){
			Intent i = new Intent();
			i.setAction(IntentActionsFactory.getHelpListActivityAction());
			startActivity(i);
			finish();
		}
		else{
			super.onBackPressed();
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem menuItem = menu.findItem(R.id.menu_about);
		menuItem.setEnabled(isHelp);

		return super.onPrepareOptionsMenu(menu);
	}
}