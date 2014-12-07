package ar.iariel.android.viewsevents;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// -- Button Abrir --//
		Button btnAbrir = (Button) findViewById(R.id.btnAbrir);
		btnAbrir.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MostrarToast("Se ha pulsado el boton Abrir");
			}
		});

		// -- Button Salva --//
		Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
		btnSalvar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MostrarToast("Se ha pulsado el boton Salvar");
			}
		});

		// -- Evento para el CheckBox --//
		CheckBox chkBox1 = (CheckBox) findViewById(R.id.chkAutosalvar);
		chkBox1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((CheckBox) v).isChecked())
					MostrarToast("La caja de chequeo ha sido marcado");
				else
					MostrarToast("La caja de chequeo ha sido desmarcada");
			}
		});
		// -- Evento para el RadioButton --//
		RadioGroup rgb1 = (RadioGroup) findViewById(R.id.rdbGp1);
		rgb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rbOp1 = (RadioButton) findViewById(R.id.rdb1);
				if (rbOp1.isChecked()) {
					MostrarToast("Marco la opcion 1");
				}else{
					MostrarToast("Marco la opcion 2");
				}
				
			}
		});
		
		// -- Evento para el ToggleButton --//
		ToggleButton tggButton = (ToggleButton) findViewById(R.id.toggle1);
		tggButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(((ToggleButton) v).isChecked())
					MostrarToast("El boton alternador esta ON");
				else
					MostrarToast("El boton alternador esta OFF");
				
			}
		});
	}

	private void MostrarToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();

	}

}
