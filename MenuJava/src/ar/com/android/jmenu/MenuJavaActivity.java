package ar.com.android.jmenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;



public class MenuJavaActivity extends Activity {
    /** Called when the activity is first created. */
 final int ACERCA = 1, SALIR = 2, AGREGAR = 3, ELIMINAR = 4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
	
    	boolean resultado = super.onCreateOptionsMenu(menu);
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, ACERCA, 0, "Acerca de").setIcon(R.drawable.ic_menu_king);
    	menu.add(0, SALIR, 0, "Salir").setIcon(R.drawable.ic_menu_cancel);
    	
    	SubMenu mantener = menu.addSubMenu("Mantener").setIcon(R.drawable.ic_menu_myspace);
    	mantener.add(0, AGREGAR, 0, "Agregar");
    	mantener.add(0, ELIMINAR, 0, "Eliminar");
    	
    	return resultado;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case ACERCA: mensajeDialog("Acerca de..", "Copyright Ariel Duarte ©2012\n Android Developer");
		break;

		case SALIR: mensajeDialog("Saliendo", "Finanlizando la aplicación");
		finish();
		break;
		
		case AGREGAR: mensajeDialog("Agregando", "Agregando");
		break;
		
		case ELIMINAR: mensajeDialog("Eliminando", "Eliminando");
		break;
		}
		return super.onOptionsItemSelected(item);

	}
    
    public void mensajeDialog(String titulo, String texto){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage(texto);
		alert.setTitle(titulo);
		alert.setIcon(android.R.drawable.ic_menu_agenda);
		alert.setNeutralButton("Aceptar", null);
		alert.show();
	}
}