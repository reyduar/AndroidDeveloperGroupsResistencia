package com.example.menusoptionscontext;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;

public class MenuActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button btn = (Button) findViewById(R.id.btn1);
		btn.setOnCreateContextMenuListener(this);//se crea un listener para el menu contextual
		
	}

	//-- Para que se muestren el menu de opciones se sobrescribe este metodo pasandole como argumento el menu --//
	//-- Es menu es llamado cuando el boton del menu del telefono es pulsado --//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		CrearMenu(menu);// Le llamamos al metodo helper y que crea el menu
		return true;
	}

	//-- Para que se muestren el menu Item se sobrescribe este metodo pasandole como argumento el menu --//
	//-- Es menu se activa atravez de otras view manteniendo pulsado la view --//	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return MenuEleccion(item);//llama al metodo que crea este menu
	}
	
	//-- Metodo que se sobrescribe para llamar al menu contextual creado--//
	//-- Se llama solo cuando es presionado el boton al cual se hace referencia con setOnCreateContextMenuListener --//
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		CrearMenu(menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return MenuEleccion(item);
	}

	//-- Menu de opciones para que se activa con el boton de menu del teclado del telefono --//
	private void CrearMenu (Menu menu) {
		menu.setQwertyMode(true);// Para que teclas de atajos funcionen tambien en el menu contextual debemos de pasarle a true a este metodo "setQwertyMode"
		MenuItem mnu1 = menu.add(0, 0, 0, "Item 1");//Se le pasa 4 argumentos (idGrupo, idItem, idOrden, textoMenu)
		{
			mnu1.setAlphabeticShortcut('a');//Se le configura una tecla de atajo
			mnu1.setIcon(R.drawable.pic1);//Permite mostrar una imagen para el menu
		}
		MenuItem mnu2 = menu.add(0, 1, 1, "Item 2");//Se le pasa 4 argumentos (idGrupo, idItem, idOrden, textoMenu
		{
			mnu2.setAlphabeticShortcut('b');//Se le configura una tecla de atajo
			mnu2.setIcon(R.drawable.pic2);//Permite mostrar una imagen para el menu
		}
		MenuItem mnu3 = menu.add(0, 2, 2, "Item 3");//Se le pasa 4 argumentos (idGrupo, idItem, idOrden, textoMenu
		{
			mnu3.setAlphabeticShortcut('c');//Se le configura una tecla de atajo
			mnu3.setIcon(R.drawable.pic3);//Permite mostrar una imagen para el menu
		}
		MenuItem mnu4 = menu.add(0, 3, 3, "Item 4");//Se le pasa 4 argumentos (idGrupo, idItem, idOrden, textoMenu
		{
			mnu4.setAlphabeticShortcut('d');//Se le configura una tecla de atajo
		}
		menu.add(0, 3, 3, "Item 5");
		menu.add(0, 3, 3, "Item 6");
		menu.add(0, 3, 3, "Item 7");
	}
	//-- Menu de Contextual para que se activa con un view programada en la aplicacion --//
	private boolean MenuEleccion(MenuItem item) {
		switch (item.getItemId()) {//Comprueba la id por medio de getItemId
		case 0:
			Toast.makeText(this, "Has pulsado en Item 1", Toast.LENGTH_LONG)
					.show();
			return true;
		case 1:
			Toast.makeText(this, "Has pulsado en Item 2", Toast.LENGTH_LONG)
					.show();
			return true;
		case 2:
			Toast.makeText(this, "Has pulsado en Item 3", Toast.LENGTH_LONG)
					.show();
			return true;
		case 3:
			Toast.makeText(this, "Has pulsado en Item 4", Toast.LENGTH_LONG)
					.show();
			return true;
		case 4:
			Toast.makeText(this, "Has pulsado en Item 5", Toast.LENGTH_LONG)
					.show();
			return true;
		case 5:
			Toast.makeText(this, "You clicked on Item 6", Toast.LENGTH_LONG)
					.show();
			return true;
		case 6:
			Toast.makeText(this, "Has pulsado en Item 7", Toast.LENGTH_LONG)
					.show();
			return true;
		}
		return false;
	}

    
}
