package com.belgacom.fon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.belgacom.fon.R;
import com.belgacom.fon.notification.FONNotificationManager;

public class HomeActivity extends FonActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
	}

	public void openAccount(View view){
		startActivity(new Intent(this, AccountActivity.class));
	}

	public void openHelp(View view){
		startActivity(new Intent(this, HelpListActivity.class));
	}

	@Override
	protected void quit() {
	}
}