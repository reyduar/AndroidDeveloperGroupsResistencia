package ar.com.android.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import ar.com.android.menu.R.id;

public class MenusActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu mnu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.listmenu, mnu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case id.mnuagregar: mensajeDialog("Agregando","Presiono agregar..."); 
		break;

		case id.mnuacercade: mensajeDialog("Acerca de..", "Copyright Ariel Duarte ©2012\n Android Developer");
		break;

		case id.mnusalir: mensajeDialog("Saliendo", "Finanlizando la aplicación");
		finish();
		break;
		}
		return super.onOptionsItemSelected(item);

	}

	public void mensajeDialog(String titulo, String texto){
		AlertDialog.Builder alert = new AlertDialog.Builder(MenusActivity.this);
		alert.setMessage(texto);
		alert.setTitle(titulo);
		alert.setIcon(android.R.drawable.ic_menu_agenda);
		alert.setNeutralButton("Aceptar", null);
		alert.show();
	}
}