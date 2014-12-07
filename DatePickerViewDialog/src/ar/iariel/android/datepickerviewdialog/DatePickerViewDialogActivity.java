package ar.iariel.android.datepickerviewdialog;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import java.util.Calendar;

public class DatePickerViewDialogActivity extends Activity {

	TimePicker timePicker;
	DatePicker datePicker;
	int hora, minuto;
	int an, mes, dia;
	static final int TIME_DIALOG_ID = 0;
	static final int DATE_DIALOG_ID = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// showDialog(TIME_DIALOG_ID);
		// ---get the current date---
		Calendar today = Calendar.getInstance();
		an = today.get(Calendar.YEAR);
		mes = today.get(Calendar.MONTH);
		dia = today.get(Calendar.DAY_OF_MONTH);
		showDialog(DATE_DIALOG_ID);
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		timePicker.setIs24HourView(true);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		// ---Button view---
		Button btnOpen = (Button) findViewById(R.id.btnSet);
		btnOpen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(
						getBaseContext(),
						"Date selected:" + datePicker.getMonth() + "/"
								+ datePicker.getDayOfMonth() + "/"
								+ datePicker.getYear() + "\n"
								+ "Time selected:"
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
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, an, mes, dia);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			an= year;
			mes = monthOfYear;
			dia = dayOfMonth;
			Toast.makeText(
					getBaseContext(),
					"Has seleccionado : " + (mes + 1) + "/" + dia + "/"
							+ year, Toast.LENGTH_SHORT).show();
		}
	};
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
