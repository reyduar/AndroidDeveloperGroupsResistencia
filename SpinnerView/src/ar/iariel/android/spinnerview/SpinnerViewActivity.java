package ar.iariel.android.spinnerview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SpinnerViewActivity extends Activity {

	String[] provincias;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//-- No permite recuperar los valores del array_provincias del string.xml --//
		provincias = getResources().getStringArray(R.array.array_provincias);
		Spinner s1 = (Spinner) findViewById(R.id.spinner1);
		//-- simple_spinner_dropdown_item: es para que muestre la lista con radio botones --//
		//-- simple_spinner_item: muestra una lista simple --//
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, provincias);//Indicamos el aspecto con el que qieremos que aparesca
		s1.setAdapter(adapter);
		s1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int index = arg0.getSelectedItemPosition();
				Toast.makeText(getBaseContext(),
						"Has seleccionado el item: " + provincias[index],
						Toast.LENGTH_SHORT).show();
			}

			//-- Se usa para cuando el usuario preciona el boton volver para que no recupere nada y simplemente vuelva --//
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

    
}
