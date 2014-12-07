package ar.com.adroidbasico.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends Activity {

	private String handset[]={"Google","Intel","Motorola","HTC","Sony","Sansung","Lenovo","Sprint"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		//-- Para generar la lista con los elementos --//
		ListView lvHandSet = (ListView) findViewById(R.id.lista);
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, handset);
		lvHandSet.setAdapter(adaptador);
		
		//-- Capturar eventos de la lista --//
		lvHandSet.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> padre, View v, int posicionSeleccionada,
					long id) {
				switch (posicionSeleccionada) {
				case 0:
					Toast.makeText(getApplicationContext(), "Google", Toast.LENGTH_LONG).show();
					break;
				case 1:
					Toast.makeText(getApplicationContext(), "Intel", Toast.LENGTH_LONG).show();
					break;
				case 2:
					Toast.makeText(getApplicationContext(), "Motorola", Toast.LENGTH_LONG).show();
					break;
				case 3:
					Toast.makeText(getApplicationContext(), "HTC", Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
				
			}
		});//-- Fin del onItemClick
	}
}//-- Fin de la clase
