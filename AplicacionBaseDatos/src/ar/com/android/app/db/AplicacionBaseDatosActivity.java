package ar.com.android.app.db;

import java.sql.SQLDataException;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.Cursor; //Permite manipular los datos
import android.util.Log; //Para el servicio de logging
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AplicacionBaseDatosActivity extends Activity {
	/**
	 * @author Ariel Duarte Definicion de variables
	 */
	 final int ACERCA = 1, SALIR = 2;
	EditText etNombre, etTelefono, etEmail;
	TextView tvNombre, tvTelefono, tvEmail;
	String cNombre, cTelefono, cEmail;
	int contadorRegistros = 0, posicion = 0;
	
	private SQLiteDatabase appBdSQLite = null; //Definimos el objeto de Base de Datos
	private static final String TAG = "bdStock";   
	private static final String nombreBD = "stock";   
	private static final String tablaPersonas = "personas";
	private Cursor cursorDatos; // Definimos un objeto para visualizar los datos
	//guardamos en un String toda la creación de la tabla        
	private static final String crearTablaPersona = "CREATE TABLE if not exists "  
			+ " PERSONAS (codigo INTEGER PRIMARY KEY autoincrement, "  
			+ " nombre TEXT NOT NULL, telefono TEXT NOT NULL unique, " 
			+ " email TEXT NOT NULL unique);";   
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		callInicio();
        crearOabrirBaseDatos();
    }
    /**
     * Permite definir el menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
	
    	boolean resultado = super.onCreateOptionsMenu(menu);
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, ACERCA, 0, "Acerca de").setIcon(R.drawable.ic_menu_king);
    	menu.add(0, SALIR, 0, "Salir").setIcon(R.drawable.ic_menu_cancel);
    	
    	
    	return resultado;
    }
    
    @Override
   	public boolean onOptionsItemSelected(MenuItem item){
   		switch (item.getItemId()) {
   		case ACERCA: alertMenssage("Acerca de..", "Copyright Ariel Duarte ©2012\n Android Developer");
   		break;

   		case SALIR: alertMenssage("Saliendo", "Finanlizando la aplicación");
   		finish();
   		break;
   		
   		}
   		return super.onOptionsItemSelected(item);

   	}
    
    
    /**
	 * @author Ariel Duarte Metodo para invocar al layout de registros de datos
	 */
	public void callRegistro() {
		setContentView(R.layout.registro);
		crearOabrirBaseDatos();
//		oRegistro = new Registro();
		etNombre = (EditText) findViewById(R.id.ednombre);
		etTelefono = (EditText) findViewById(R.id.edtelefono);
		etEmail = (EditText) findViewById(R.id.edemail);
		etNombre.requestFocus();
		Button btFinalizar = (Button) findViewById(R.id.btnfinalizar);
		
		//Definimos el evento para el boton guardar registros que se llama Finalizar
		btFinalizar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Validamos los campos
				if (etNombre.getText().toString().equals("")) {
					alertMenssage("Campo obligatorio!", "Ingrese el nombre porfavor.");
					etNombre.requestFocus();
				} else if(etTelefono.getText().toString().equals("")){
					alertMenssage("Campo obligatorio!", "Ingrese el teléfono porfavor.");
					etTelefono.requestFocus();	 
				} else {
					try {
						
						guardarDatos();
						callInicio();
					} catch (Exception e) {
						alertMenssage(
								"Error!",
								"Ocurrio un error al guardar los datos"
										+ e.getMessage()
										+ ". \nPorfavor intente de nuevo.");
					}
				}

			}
		});
		//Definimos el evento para el boton de Volver al menu principal
		Button btVolverRegistro = (Button) findViewById(R.id.btnvolverregistro);
		btVolverRegistro.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//cursorDatos.close();
				callInicio();
			}
		});
	}

	
    /**
	 * @author Ariel Duarte Metodo para invocar al layout de consulta de
	 *         registros
	 */
	public void callConsulta() {
		setContentView(R.layout.consulta);
		if (buscarDatos()) {
			buscarDatos();
			mostrarDatos(); 
		} else {
			alertMenssage("Atención","No existen datos para consultar");
			callInicio();
			return;
		}
		
		Button btVolverConsulta = (Button) findViewById(R.id.btnvolverconsulta);
		//Define el evento del boton volver al menu principal
		btVolverConsulta.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//cursorDatos.close();
				callInicio();

			}
		});
		//Define el evento del boton mostrar proximo registro
		Button btProximo = (Button) findViewById(R.id.btnproximo);
		btProximo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mostrarProximoRegistro();	
			}
		});
		
		//Define el evento del boton del mostrar registro anterior
		Button btAnterior = (Button) findViewById(R.id.btnanterior);
		btAnterior.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mostrarRegistroAnterior();
			}
		});
	}
	
    /**
	 * @author Ariel Duarte Metodo para invocar al layout del menu principal
	 */
	public void callInicio() {
		setContentView(R.layout.main);

		Button btRegistro = (Button) findViewById(R.id.btnregistro);
		Button btConsulta = (Button) findViewById(R.id.btnconsulta);
		Button btnEliminar = (Button) findViewById(R.id.btneliminar);

		btRegistro.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				callRegistro();
			}
		});

		btConsulta.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				callConsulta();

			}
		});
		
		/**
		 * Código del botón eliminar base de datos:
		 */
		btnEliminar.setOnClickListener(new View.OnClickListener() {
			//Eliminar la base de datos de la agenda
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(AplicacionBaseDatosActivity.this);
				alertDialog.setMessage("¿Desea eliminar la base de datos por completo?");
				alertDialog.setTitle("Eliminar Personas...");
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
						  crearOabrirBaseDatos();
					}
				});
				alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(), "Eliminación de la Base de Datos Cancelada", Toast.LENGTH_LONG).show();
					}
				});
				alertDialog.show();
			}
		});
	}
	
	/**
     * Permite crear y Abrir la base de datos
     * @author Ariel Duarte
     */
    public void crearOabrirBaseDatos() {
    	try {
    		appBdSQLite = openOrCreateDatabase(nombreBD, MODE_WORLD_WRITEABLE, null);
    		appBdSQLite.execSQL(crearTablaPersona);
		} catch (Exception e) {
			 Log.i(TAG, "Error al abrir o crear la base de datos" + e); 
			 alertMenssage("Error Base de Datos","Erro al abrir la Base de Datos :"+e.getMessage());
		}
    }
    
    /**
     * Permite cerrar la base de datos
     * @author Ariel Duarte
     */
    public void cerrarBaseDatos(){
    	try {
    		appBdSQLite.close(); 
    		alertMenssage("Success!","Success to create Data Base");
		} catch (Exception e) {
			 Log.i(TAG, "Error al cerrar la base de datos" + e); 
			 alertMenssage("Error Base de Datos","Erro al cerrar la Base de Datos :"+e.getMessage());
		}
    }
    
    public void alertMenssage(String titulo, String texto) {
		AlertDialog.Builder msg = new AlertDialog.Builder(AplicacionBaseDatosActivity.this);
		msg.setTitle(titulo);
		msg.setMessage(texto);
		msg.setIcon(android.R.drawable.ic_dialog_alert);
		msg.setNeutralButton("Aceptar", null);
		msg.show();
	}
    
    public void mostrarDatos(){
    	TextView tvNombre = (TextView) findViewById(R.id.tvnombre);
		TextView tvTelefono = (TextView) findViewById(R.id.tvtelefono);
		TextView tvEmail = (TextView) findViewById(R.id.tvemail);
    	tvNombre.setText(cursorDatos.getString(cursorDatos.getColumnIndex("nombre")));
    	tvTelefono.setText(cursorDatos.getString(cursorDatos.getColumnIndex("telefono")));
    	tvEmail.setText(cursorDatos.getString(cursorDatos.getColumnIndex("email")));
    }
    
    /**
	 * Permite buscar registros
	 */
	private boolean buscarDatos(){
		
		try {
			String columnas[] =  {"nombre","telefono","email"};
			//query(tabla, columnas ,seleccion, seleccionArg, groupBy, having, orderBy, limiteRegistro)
			cursorDatos = appBdSQLite.query(tablaPersonas, columnas, null, null, null, null, null); 
			contadorRegistros = cursorDatos.getCount();
			
			if(contadorRegistros!=0){
				cursorDatos.moveToFirst();
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
			alertMenssage("Error Base de Datos","Error buscando datos :"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * Metodos para recorrer los registros
	 */
	public void mostrarRegistroAnterior(){
		try {
			if (cursorDatos.isFirst()) {
				cursorDatos.moveToLast();
			} else {
				cursorDatos.moveToPrevious();
			}
			mostrarDatos();
		} catch (Exception e) {
			alertMenssage("Error Base de Datos","No es posible ir para el anterior registro :"+e.getMessage());
		}
	}
	
	public void mostrarProximoRegistro(){
		try {
			if(cursorDatos.isLast()){
			cursorDatos.moveToFirst();
			}else{
			cursorDatos.moveToNext();
			}
			mostrarDatos();
		} catch (Exception e) {
			alertMenssage("Error Base de Datos","No es posible ir para el proximo registro :"+e.getMessage());
		}
	}
	
	/**
	 * Permite guardar los registros en la base de datos
	 */
	public void guardarDatos(){
		
		try {
			//Hacemo el insert para la tabla personas y los asignamos una variable
			cNombre = etNombre.getText().toString();
			cTelefono = etTelefono.getText().toString();
			cEmail = etEmail.getText().toString();
			if (cEmail.equals(null)) 
				cEmail = "";
			String sqlComando = "INSERT INTO PERSONAS (nombre, telefono, email) VALUES ('"+cNombre+"','"
					+cTelefono+"','"
					+cEmail+"')";
					
//			String sqlComando = "INSERT INTO PERSONAS (nombre, telefono, email) VALUES ('"+etNombre.getText().toString()+"','"
//					+etTelefono.getText().toString()+"','"
//					+etEmail.getText().toString()+"')";
			appBdSQLite.execSQL(sqlComando);
			//alertMenssage("Exito!","Los datos se guardaron con exito");
			Toast.makeText(getApplicationContext(), "Los datos se guardaron con exito", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			alertMenssage("Error Base de Datos","No es posible guardar los datos :"+e.getMessage());
		}
	}
}