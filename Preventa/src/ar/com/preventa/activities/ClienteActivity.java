package ar.com.preventa.activities;

import java.util.ArrayList;
import java.util.List;

import org.metawidget.android.widget.AndroidMetawidget;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import ar.com.preventa.PreventaApp;
import ar.com.preventa.R;
import ar.com.preventa.db.Cliente;
import ar.com.preventa.db.Db4oHelper;
import ar.com.preventa.db.DbManager;
import ar.com.preventa.db.Ruta;
import ar.com.preventa.db.PreventaDB.ClientesTable;

public class ClienteActivity extends Activity {

	private final static String TAG = "Preventa.Cliente";
	private Db4oHelper db4oHelper = null;
	
	private Db4oHelper dbHelper() {
		if (db4oHelper == null) {
			db4oHelper = new Db4oHelper(this);
			db4oHelper.db();
		}
		return db4oHelper;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.cliente_activity);
		
		final AndroidMetawidget metawidget = (AndroidMetawidget) findViewById( R.id.metawidget );
		metawidget.setToInspect(new Cliente());
				
		//Uri uri = getIntent().getData();
        //Cursor cursor = managedQuery(uri, null, null, null, null);
		PreventaApp preventaApp = ((PreventaApp)getApplicationContext());
		Bundle b = this.getIntent().getExtras();
		
		Cliente cliente = dbHelper().findCliente(b.getString("codigo"),b.getString("comercio"));
		
		
//		Cursor cursor = preventaApp.getDataHelper().getDatosClienteBySecuenciaId(b.getLong("id"));
//		startManagingCursor(cursor);
//
//        if (cursor == null) {
//            finish();
//        } else {
//            cursor.moveToFirst();
		
//		private String  codigo;
//		private String  comercio;
//		private String  nombre;
//		private String  domicilio;
//		private String  telefono;
//		private String  tipocontribuyente;
//		private String  cuit;
//		private int     condicionventa;
//		private double  saldo;
//		private int     vendedor;
//		private String  ciudad;
//		private String  provincia;	
//		private int     renglones;
//		private List<Ruta> rutas = new ArrayList<Ruta>();   
		
//            
            metawidget.setValue(cliente.getCodigo(), "codigo" );
            metawidget.setValue(cliente.getNombre(), "nombre" );
            metawidget.setValue(cliente.getDomicilio(), "domicilio" );
            //metawidget.setValue(cliente.getProvincia(), "provincia" );
            metawidget.setValue(cliente.getTelefono(), "telefono" );
            
            metawidget.setValue(cliente.getTipocontribuyente(), "tipocontribuyente" );
            metawidget.setValue(cliente.getCuit(), "cuit" );
            metawidget.setValue(cliente.getCondicionventa(), "condicionventa" );
            metawidget.setValue(cliente.getSaldo(), "saldo" );
           // metawidget.setValue(cliente.getCiudad(), "ciudad" );
 
//        }
	}
	
	//@Override
//    	protected void onPause() {
//	    	super.onDestroy();
//	    	dbHelper().close();
//	    	db4oHelper = null;
//    }
}