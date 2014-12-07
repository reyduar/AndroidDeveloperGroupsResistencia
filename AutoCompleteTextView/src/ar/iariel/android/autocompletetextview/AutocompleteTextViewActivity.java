package ar.iariel.android.autocompletetextview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class AutocompleteTextViewActivity extends Activity {

	String[] presidentes = { "Argentina", "Alemania",
			"Adganiftan", "Brasil", "Belgica", "Bolivia","Colombia",
			"Checoslovaquia", "Cuba", "Costa Rica", "EEUU", "Ecuador",
			"Mexico", "Inglaterra", "Irlanda" };

	/** Llamada cuando la activity es creada por primera vez. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, presidentes);
		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.txtPaises);
		textView.setThreshold(1);// Numero minimo de caracteres que el usuario tiene que teclear
		textView.setAdapter(adapter);//Despliege de la lista de sugerencuias
	}

    
}
