package ar.iariel.android.listview;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewActivity extends ListActivity {

	String[] provincias = { "La Coruña", "Lugo",
			"Orense", "Pontevedra", "Oviedo",
			"Las Palmas", "León", "Sevilla",
			"Málaga", "Córdoba", "Granada" };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, provincias));
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		Toast.makeText(this, "Has seleccionado " + provincias[position],
				Toast.LENGTH_SHORT).show();
	}
    
}
