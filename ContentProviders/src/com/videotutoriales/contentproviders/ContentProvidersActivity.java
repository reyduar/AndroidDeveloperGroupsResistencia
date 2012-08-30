package com.videotutoriales.contentproviders;

import android.app.Activity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class ContentProvidersActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// ---añadir un libro---
				ContentValues values = new ContentValues();
				values.put(LibrosProvider.TITULO,
						((EditText) findViewById(R.id.txtTitle)).getText()
								.toString());
				values.put(LibrosProvider.ISBN,
						((EditText) findViewById(R.id.txtISBN)).getText()
								.toString());
				Uri uri = getContentResolver().insert(
						LibrosProvider.CONTENT_URI, values);
				Toast.makeText(getBaseContext(), uri.toString(),
						Toast.LENGTH_LONG).show();
				
				//Añadir libros en un paquete distino
				/*ContentValues values = new ContentValues();
                  values.put("titulo", ((EditText)
                  findViewById(R.id.txtTitle)).getText().toString());
                  values.put("isbn", ((EditText)
                  findViewById(R.id.txtISBN)).getText().toString());
                  Uri uri = getContentResolver().insert(
                  Uri.parse(“content://com.videotutoriales.contentproviders2.Libros/libros”),
                  values);
                  Toast.makeText(getBaseContext(),uri.toString(),
                  Toast.LENGTH_LONG).show();*/
				
				//Actualizar libros
				/*ContentValues editedValues = new ContentValues();
				editedValues.put(LibrosProvider.TITULO, "Otro ejemplo de libro");
				getContentResolver().update(
				Uri.parse(
				"content://com.videotutoriales.provider.Libros/libros/2"),
				editedValues,
				null,
				null);*/
				
				//Borrar un libro
				       /* getContentResolver().delete(
						Uri.parse("content://com.videotutoriales.provider.Libros/libros/2"),
						null, null);*/
			}
		});
		Button btnRetrieve = (Button) findViewById(R.id.btnRetrieve);
		btnRetrieve.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// ---recuperar los títulos---
				Uri allTitles = Uri
						.parse("content://com.videotutoriales.provider.Libros/libros");
				Cursor c = managedQuery(allTitles, null, null, null,
						"titulo desc");
				if (c.moveToFirst()) {
					do {
						Log.v("ContentProviders",
								c.getString(c
										.getColumnIndex(LibrosProvider._ID))
										+ ", "
										+ c.getString(c
												.getColumnIndex(LibrosProvider.TITULO))
										+ ", "
										+ c.getString(c
												.getColumnIndex(LibrosProvider.ISBN)));
					} while (c.moveToNext());
				}
			}
		});
	}
}