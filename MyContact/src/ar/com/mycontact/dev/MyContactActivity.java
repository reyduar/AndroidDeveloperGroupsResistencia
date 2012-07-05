package ar.com.mycontact.dev;

import android.app.Activity;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.sqlite.*; //Para conexion  la Base de Datos
import android.widget.EditText; //Para trabajar con campos de textos
import android.widget.Button; //Para trabajar con botones
import android.widget.Toast; //Para mostrar mensajes emergentes
import android.util.Log; //Para el servicio de logging
import android.view.View;
import android.content.Intent;
import android.net.Uri; //Para llamar a un n�mero de tel�fono 
import android.app.AlertDialog; //para mostrar mensaje de confirmaci�n 
import android.content.DialogInterface;

public class MyContactActivity extends Activity {
	/**
	 * Declaramos variables que usaremos y ejecutaremos el layout
	 */
	EditText etNombre, etTelefono;
	private Button btnGuardar;
	private Button btnLlamar;
	private Button btnEliminar;
	private Button btnCerrar;

	private SQLiteDatabase baseDatos;
	private static final String TAG = "bdagenda";   
	private static final String nombreBD = "agenda";   
	private static final String tablaContacto = "contacto";

	//guardamos en un String toda la creaci�n de la tabla        
	private static final String crearTablaContacto = "create table if not exists "  
			+ " contacto (codigo integer primary key autoincrement, "  
			+ " nombre text not null, telefono text not null unique);";   

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//Asignamos a cada objeto visual creado a su respectivo elemento de main.xml 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		etNombre = (EditText) findViewById(R.id.txtnombre);
		etTelefono = (EditText) findViewById(R.id.txttelefono);
		btnGuardar = (Button) findViewById(R.id.btnguardar);
		btnLlamar = (Button) findViewById(R.id.btnllamar);
		btnEliminar = (Button) findViewById(R.id.btneliminar);
		btnCerrar = (Button) findViewById(R.id.btncerrar);

		/**
		 * C�digo asignado al bot�n Guarda Contacto:
		 */
		btnGuardar.setOnClickListener(new View.OnClickListener() {
			//Guardamos el contacto actual en la agenda	
			@Override
			public void onClick(View v) {
				// Abrir la Base de Datos, se creara si no existe
				abrirBasedatos();

				//Insertar una fila o registro en la tabla "contacto"
				//si la inserci�n es correcta devolver� true  
				boolean resultado = insertarFila(etNombre.getText().toString(),etTelefono.getText().toString());
				if(resultado)
					Toast.makeText(getApplicationContext(), "Exito al guardar el contacto", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getApplicationContext(), "Error al guardar el contacto", Toast.LENGTH_LONG).show();
			}
		});
		/**
		 * C�digo para llamar al contacto actual por tel�fono:
		 */
		btnLlamar.setOnClickListener(new View.OnClickListener() {
			//Llamar al contacto actual por tel�fono
			@Override
			public void onClick(View v) {
				//Mostrar un mensaje de confirmaci�n antes de realizar la llamada
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyContactActivity.this);
				alertDialog.setMessage("�Desea realizar la llamada al contacto?");
				alertDialog.setTitle("Llamar a contacto...");
				alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
				alertDialog.setCancelable(false);
				//En el caso de que el evento sea afirmativo
				alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							EditText num = (EditText) findViewById(R.id.txttelefono);
							String number = "Telef.:"+ num.getText().toString().trim();
							Toast.makeText(getApplicationContext(), "Llamando al.."+num.getText().toString().trim(), Toast.LENGTH_LONG).show();
							Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
							startActivity(callIntent);
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(), "No se ha podido realizar la llamada", Toast.LENGTH_LONG).show();
						}
					}
				});
				alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						Toast.makeText(getApplicationContext(), 
								"Llamada cancelada", Toast.LENGTH_LONG).show();
					} 
				}); 
				alertDialog.show();

			}
		});
		/**
		 * C�digo del bot�n eliminar base de datos:
		 */
		btnEliminar.setOnClickListener(new View.OnClickListener() {
			//Eliminar la base de datos de la agenda
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyContactActivity.this);
				alertDialog.setMessage("�Desea eliminar la base de datos por completo?");
				alertDialog.setTitle("Eliminar agenda...");
				alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
				alertDialog.setCancelable(false);
				alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							Toast.makeText(getApplicationContext(), "Eliminando Base de Datos", Toast.LENGTH_LONG).show();
							boolean  resultado = deleteDatabase(nombreBD);
							if(resultado) 
								Toast.makeText(getApplicationContext(), "Base de datos eliminada correctamente", Toast.LENGTH_LONG).show();
							else 
								Toast.makeText(getApplicationContext(), "No se ha podido eliminar la base de datos", Toast.LENGTH_LONG).show(); 
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(), "No se ha podido eliminar la base de datos", Toast.LENGTH_LONG).show();
						}

					}
				});
				alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(), "Eliminaci�n de la Base de Datos Cancelada", Toast.LENGTH_LONG).show();
					}
				});
				alertDialog.show();
			}
		});
		/**
		 * C�digo para bot�n de cerrar la aplicaci�n:
		 */
		btnCerrar.setOnClickListener(new View.OnClickListener() {
			//Cerrar aplicaci�n Android
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	/**
	 * C�digo para abrir la base de datos (crearla si no existe, crear la tabla):
	 */
	//Procedimiento para abrir la base de datos
	//si no existe se crear�, tambi�n se crear� la tabla contacto
	private void abrirBasedatos(){
		try {
			baseDatos = openOrCreateDatabase(nombreBD, MODE_WORLD_WRITEABLE, null);
			baseDatos.execSQL(crearTablaContacto);
		} catch (Exception e) {
			 Log.i(TAG, "Error al abrir o crear la base de datos" + e);  
		}
	}
	
	/**
	 * C�digo para insertar un registro en la tabla de la base de datos SQLite:
	 */
	//M�todo que realiza la inserci�n de los datos en nuestra tabla contacto 
	private boolean insertarFila(String nombre, String telefono){
		ContentValues values = new ContentValues();
		values.put("nombre", nombre);
		values.put("telefono", telefono);
		Toast.makeText(getApplicationContext(), "Nombre: "+nombre+", "+"Telefono: "+telefono, Toast.LENGTH_LONG).show();
		
		return (baseDatos.insert(tablaContacto, null, values)>0);
	}
	
	
}