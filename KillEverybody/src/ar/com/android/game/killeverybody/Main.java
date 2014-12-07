package ar.com.android.game.killeverybody;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.support.v4.app.NavUtils;

public class Main extends Activity {
	final int ACERCA = 1, SALIR = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//-- Permite ocultar los titulos de la pantalla --//
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//-- Permite poner pantalla completa sin barra de titulo y notificaciones--//
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//--Hacemos que la clase RenderView se defina en el contexto de la aplicacion--//
		setContentView(new GameView(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		boolean resultado = super.onCreateOptionsMenu(menu);
		super.onCreateOptionsMenu(menu);
		menu.add(0, ACERCA, 0, "Acerca de").setIcon(R.drawable.ic_menu_king);
		menu.add(0, SALIR, 0, "Salir").setIcon(R.drawable.ic_menu_cancel);

		return resultado;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ACERCA:
			mensajeDialog("Autor",
					"Copyright Ariel Duarte ©2012\n Android Game Developer\n Versión 1.0.0 alfa");
			break;

		case SALIR:
			mensajeDialog("Saliendo", "Finanlizando la aplicación");
			finish();
			break;

		}
		return super.onOptionsItemSelected(item);

	}

	public void mensajeDialog(String titulo, String texto) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage(texto);
		alert.setTitle(titulo);
		alert.setIcon(android.R.drawable.ic_menu_agenda);
		alert.setNeutralButton("Aceptar", null);
		alert.show();
	}

}
