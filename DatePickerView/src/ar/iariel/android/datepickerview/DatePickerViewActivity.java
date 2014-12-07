package ar.iariel.android.datepickerview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.DatePicker;

public class DatePickerViewActivity extends Activity {

	TimePicker timePicker;
	DatePicker datePicker;
	int hora, minuto;
	static final int TIME_DIALOG_ID = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// showDialog(TIME_DIALOG_ID);
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		timePicker.setIs24HourView(true);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		// ---Button view---
		Button btnOpen = (Button) findViewById(R.id.btnSet);
		btnOpen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//-- Recuperamos los valores del calendario --//
				//--No olvidar que Enenro = 0...  y asi sucesivamente por eso se le suma + 1 a getMonth--//
				Toast.makeText(
						getBaseContext(),
						"Fecha Seleccionada:" + datePicker.getDayOfMonth()  + "/"
								+ (datePicker.getMonth() + 1)+ "/"
								+ datePicker.getYear() + "\n"
								+ "Hora Seleccionada:"
								+ timePicker.getCurrentHour() + ":"
								+ timePicker.getCurrentMinute(),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, hora, minuto,
					false);
		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
			hora = hourOfDay;
			minuto = minuteOfHour;
			Toast.makeText(getBaseContext(),
					"Has seleccionado : " + hora + ":" + minuto,
					Toast.LENGTH_SHORT).show();
		}
	};
    
}
