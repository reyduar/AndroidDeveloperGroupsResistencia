package com.iariel.android.timepickerview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimePickerViewActivity extends Activity {

	TimePicker timePicker;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		//-- Por defecto el horario es am/pm pero si queremos que muestre en formato 24hs se hace asi --//
		timePicker.setIs24HourView(true);
		// -- Con el boton vamos a recuperar el horario mostralo en un Toast --//
		Button btnOpen = (Button) findViewById(R.id.btnSet);
		btnOpen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//-- Tener encuenta el getCurrentHour recupera el formato en 24hs. --//
				Toast.makeText(
						getBaseContext(),
						"Hora seleccionada:" + timePicker.getCurrentHour() + ":"
								+ timePicker.getCurrentMinute(),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

  

    
}
