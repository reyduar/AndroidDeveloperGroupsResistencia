package com.belgacom.fon.util;

import android.content.Context;
import android.widget.Toast;

public class DisplayToast implements Runnable {
	private String msg;
	private Context context;

	public DisplayToast(Context context, String msg) {
		this.msg = msg;
		this.context = context;
	}

	public void run() {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}