package ar.com.preventa.activities;

import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import ar.com.preventa.PreventaApp;
import ar.com.preventa.R;
import ar.com.preventa.db.PreventaDB.ClientesTable;
import ar.com.preventa.db.PreventaDB.SecuenciaTable;

public class ClientesActivity extends Activity {

	private final static String TAG = "Preventa.Clientes";
	
	private static final HashMap<String, String> CLIENTES_PROJECTION_MAP;
    static {
    	CLIENTES_PROJECTION_MAP = new HashMap<String, String>();
        CLIENTES_PROJECTION_MAP.put(ClientesTable._ID, "Clientes._id");
        CLIENTES_PROJECTION_MAP.put(SecuenciaTable._ID, "Secuencia._id");
        CLIENTES_PROJECTION_MAP.put(ClientesTable.RAZON_SOCIAL, "Clientes.razonsocial");
        CLIENTES_PROJECTION_MAP.put(ClientesTable.DOMICILIO, "Clientes.domicilio");
        CLIENTES_PROJECTION_MAP.put(SecuenciaTable.PEDIDOS, "Secuencia.pedidos");
    }
    
	//consantes para guardar el estado de la pantalla
	private static final String DIA_KEY_ID = "dia";
	private static final String VISITA_KEY_ID = "visita";
	private static final String QUERYSTRING_KEY_ID = "query";
	//contantes para el menu contextual
	private static final int MENU_ATENDER = 0;
	private static final int MENU_NO_ATENDER = 1;
	private static final int MENU_DATOS_CLIENTE = 2;

	private static final int DIA_MENU_GROUP = 1;
	private static final int VISITAS_MENU_GROUP = 2;
	
	private static final int DIA_DOMINGO_ID = 1;
	private static final int DIA_LUNES_ID = 2;
	private static final int DIA_MARTES_ID = 3;
	private static final int DIA_MIERCOLES_ID = 4;
	private static final int DIA_JUEVES_ID = 5;
	private static final int DIA_VIERNES_ID = 6;
	private static final int DIA_SABADO_ID = 7;
	private static final int DIA_TODOS_ID = 8;
	
	private static final int VISITAS_TODOS_ID = 10;
	private static final int VISITAS_CON_VENTAS_ID = 11;
	private static final int VISITAS_SIN_VENTAS_ID = 12;
	private static final int VISITAS_SIN_VISITAR_ID = 13;
	
	private ListView listClientes;
	private TextView mTextView;
	private Integer  mSelectedDay;
	private Integer  mSelectedVisita;
	private String   mQueryString;
	private EditText filterText;
	private Button clearButton;
	private ProgressDialog mProgressDialog;	
	private PreventaApp preventaApp;
		
	private class ClientesAdapter extends SimpleCursorAdapter {

        public ClientesAdapter(Context context, Cursor cursor) {
                super(context, R.layout.clientes_row_relative, cursor, new String[] {}, new int[] {});
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView != null) {
                        view = convertView;
                } else {
                        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflater.inflate(R.layout.clientes_row_relative, parent, false);
                }

                AccountViewHolder holder = (AccountViewHolder) view.getTag();
                if (holder == null) {
                        holder = new AccountViewHolder();
                        holder.name = (TextView) view.findViewById(R.id.name_entry);
                        holder.address = (TextView) view.findViewById(R.id.address_entry);
                        holder.pedidos = (TextView) view.findViewById(R.id.pedidos);
                        holder.icon = (ImageView) view.findViewById(R.id.icon);
                        view.setTag(holder);
                }

                Cursor cursor = (Cursor) getItem(position);
                
                String name = cursor.getString(cursor.getColumnIndex(ClientesTable.RAZON_SOCIAL));
                String domicilio = cursor.getString(cursor.getColumnIndex(ClientesTable.DOMICILIO));
                Integer pedidos = cursor.getInt(cursor.getColumnIndex(SecuenciaTable.PEDIDOS));               
                
                holder.icon.setImageResource(R.drawable.preventa);
                                
                //con ventas
                if (pedidos > 0 && pedidos < 100) {
                        holder.pedidos.setVisibility(View.VISIBLE);
                        holder.pedidos.setText(pedidos.toString());
                        holder.icon.setVisibility(View.VISIBLE);
                //sin ventas
                } else if (pedidos >= 100) {
                         //holder.pedidos.setVisibility(View.GONE);
                	     holder.icon.setVisibility(View.GONE);
                	     holder.pedidos.setVisibility(View.VISIBLE);
                	     holder.pedidos.setText(preventaApp.getDataHelper().getStringById("Mensajes", pedidos));          	     
                //sin visitar
                } else {
                	     holder.pedidos.setVisibility(View.GONE);
           	             holder.icon.setVisibility(View.GONE);
                }
                holder.name.setText(name);
                holder.address.setText(domicilio);

                return view;
        }

        class AccountViewHolder {
                public TextView name;
                public TextView address;
                public TextView pedidos;
                public ImageView icon;
        }
	}
	
	
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle state) {

		super.onCreate(state);
		this.setContentView(R.layout.clientes_original);
		
		preventaApp = ((PreventaApp)getApplicationContext());
		
		if (state != null){
			if (state.containsKey(DIA_KEY_ID)) {
				mSelectedDay = state.getInt(DIA_KEY_ID);
			}
			if (state.containsKey(VISITA_KEY_ID)) {
				mSelectedVisita = state.getInt("VISITA_KEY_ID");
			}
		} else {
			mSelectedDay = Calendar.getInstance ().get (Calendar.DAY_OF_WEEK);  //dia actual
			mSelectedVisita = VISITAS_TODOS_ID;
		}
		
		atachControls();
		initControls();
        
		new FillClientesTask().execute("");
	}

	
	private void atachControls() {
		filterText = (EditText) findViewById(R.id.filterText);
		mTextView = (TextView) findViewById(R.id.text);
		listClientes = (ListView) findViewById(R.id.listClientes);
		
		this.clearButton = (Button)this.findViewById(R.id.clearButton);
		  this.clearButton.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	if (filterText.getText().length() > 0)
		    		filterText.setText("");
		    }
		  });
	}
	
	private void initControls() {
		filterText.addTextChangedListener(filterTextWatcher);
		listClientes.setFastScrollEnabled(true);
		registerForContextMenu(listClientes);
	}


	class FillClientesTask extends AsyncTask<String, String, Cursor> {
		
		@Override
		protected void onPreExecute (){
			showProgressDialog();
		}
		
		@Override
		protected Cursor doInBackground(String... filterstring) {		
			try {
				return doQuery();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(String... item) {
		}
		
		@Override
		protected void onPostExecute(Cursor cursor) {
			displayCursor(cursor);
    		displayTotalRecords(cursor);
    		dismissProgressDialog();
		}
		
		@Override
        protected void onCancelled() {
            dismissProgressDialog();
        }
	}
    
	private Cursor doQuery() {
		
		Boolean compuesto = false;
		
		String msg = "dia: " + mSelectedDay.toString() + "visita: " + mSelectedVisita.toString();
		Log.d(TAG, msg);
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(ClientesTable.TABLE + "," + SecuenciaTable.TABLE);
        qb.appendWhere("Clientes._id = Secuencia.id_Clientes");
        if (mSelectedDay != DIA_TODOS_ID){
        	qb.appendWhere(" AND ");
        	qb.appendWhere(SecuenciaTable.DIA + "=" + mSelectedDay.toString());
        	compuesto = true;
        }
        if (mSelectedVisita != VISITAS_TODOS_ID){
	    	if (compuesto)
	    		qb.appendWhere(" AND ");
	    	if (mSelectedVisita == VISITAS_CON_VENTAS_ID){
	    		qb.appendWhere(SecuenciaTable.PEDIDOS + "> 0");
	    		qb.appendWhere(" AND ");
	    		qb.appendWhere(SecuenciaTable.PEDIDOS + "< 100");
	         } else if (mSelectedVisita == VISITAS_SIN_VISITAR_ID){
		    	qb.appendWhere(SecuenciaTable.PEDIDOS + "= 0");
	         }else if (mSelectedVisita == VISITAS_SIN_VENTAS_ID){
		    	qb.appendWhere(SecuenciaTable.PEDIDOS + ">= 100"); 
	         }
	         compuesto = true;
	    }
        
        if ((mQueryString != null) && (mQueryString.length() > 0)){
	    	if (compuesto)
	    		qb.appendWhere(" AND ");
	    	qb.appendWhere(ClientesTable.RAZON_SOCIAL + " LIKE ");
            qb.appendWhereEscapeString('%' + mQueryString + '%');
	    }
        
        qb.setProjectionMap(CLIENTES_PROJECTION_MAP);
        Cursor cursor = qb.query(preventaApp.getDataHelper().getReadableDatabase(), 
        		                 new String[] { ClientesTable._ID,ClientesTable.RAZON_SOCIAL, ClientesTable.DOMICILIO, SecuenciaTable.PEDIDOS }, 
        				 	     null, 
        				 	     null, 
        				 	     null, 
        				 	     null, 
        				 	     SecuenciaTable.DEFAULT_SORT_ORDER);

        startManagingCursor(cursor);
        return cursor;                                                         
	}
	
	private void displayTotalRecords(Cursor cursor){
		
		if (cursor != null){
			if (cursor.getCount() == 0) {
					// There are no results
					mTextView.setText(R.string.no_results);
			} else {
					// Display the number of results
					int count = cursor.getCount();
					String countString = getResources().getQuantityString(R.plurals.search_results, count, new Object[] { count });
					mTextView.setText(countString);
			}
		}
	}
	
	private TextWatcher filterTextWatcher = new TextWatcher() {
        
		
		@Override
		public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        	    mQueryString = filterText.getText().toString();
                Log.d("list", "Text is " + mQueryString);
                
                if (mQueryString.length() == 0 || mQueryString.length() >= 3){
                	new FillClientesTask().execute();
                }
        }	
	};
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.setHeaderTitle("Clientes");     
		menu.add(Menu.NONE, MENU_ATENDER, Menu.NONE, "Atender");
		menu.add(Menu.NONE, MENU_NO_ATENDER, Menu.NONE, "No Atender");
		menu.add(Menu.NONE, MENU_DATOS_CLIENTE, Menu.NONE, "Ver Datos");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		boolean handled = false;
		AdapterView.AdapterContextMenuInfo info;
		try {
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		} catch (ClassCastException e) {
			Log.e(TAG, "Bad menuInfo", e);
			return handled;
		}

		Cursor cursor = (Cursor) listClientes.getAdapter().getItem(info.position);
		//Uri mDialogUri = ContentUris.withAppendedId( SecuenciaTable.CONTENT_URI, cursor.getLong(0));
		//String mDialogCurrentName = cursor.getString(1);
		
		switch (item.getItemId()) {
		 case MENU_ATENDER:
			 handled = true;
			 break;
		 case MENU_NO_ATENDER: 
			 handled = true;
			 break;
		 case MENU_DATOS_CLIENTE:
			 Intent actionIntent = new Intent( this, ClienteActivity.class );
			 Bundle b = new Bundle();
			 b.putLong("id", cursor.getLong(0));
			 actionIntent.putExtras(b);
			 startActivity( actionIntent );
		     break;
		default:
			handled = super.onContextItemSelected(item);
			break;
		}
		return handled;
	}

	private void displayCursor(Cursor tracksCursor) {
		ClientesAdapter clientesAdapter = new ClientesAdapter(this,tracksCursor);
		listClientes.setAdapter(clientesAdapter);
	}
  
	public boolean onPrepareOptionsMenu( Menu menu)
    {
        super.onPrepareOptionsMenu( menu);
       
        menu.findItem( mSelectedDay).setChecked(true);
        menu.findItem( mSelectedVisita).setChecked(true);
        
        return true;
    }
	
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);

		SubMenu subMenuDia = menu.addSubMenu("Dia");
		subMenuDia.setIcon(android.R.drawable.ic_menu_week);
		
		subMenuDia.add(DIA_MENU_GROUP, DIA_DOMINGO_ID, 0, "Domingo");
		subMenuDia.add(DIA_MENU_GROUP, DIA_LUNES_ID, 0, "Lunes");
		subMenuDia.add(DIA_MENU_GROUP, DIA_MARTES_ID, 0, "Martes");
		subMenuDia.add(DIA_MENU_GROUP, DIA_MIERCOLES_ID, 0, "Miercoles");
		subMenuDia.add(DIA_MENU_GROUP, DIA_JUEVES_ID, 0, "Jueves");
		subMenuDia.add(DIA_MENU_GROUP, DIA_VIERNES_ID, 0, "Viernes");
		subMenuDia.add(DIA_MENU_GROUP, DIA_SABADO_ID, 0, "Sabado");
		subMenuDia.add(DIA_MENU_GROUP, DIA_TODOS_ID, 0, "Todos");
		subMenuDia.setGroupCheckable(DIA_MENU_GROUP, true, true);
		
		
		SubMenu subMenuVisitas = menu.addSubMenu("Visitas");
		subMenuVisitas.setIcon(android.R.drawable.ic_menu_agenda);
		
		subMenuVisitas.add(VISITAS_MENU_GROUP, VISITAS_TODOS_ID, 0, "Todos");
		subMenuVisitas.add(VISITAS_MENU_GROUP, VISITAS_CON_VENTAS_ID, 0, "Con Ventas");
		subMenuVisitas.add(VISITAS_MENU_GROUP, VISITAS_SIN_VENTAS_ID, 0, "Sin Ventas");
		subMenuVisitas.add(VISITAS_MENU_GROUP, VISITAS_SIN_VISITAR_ID, 0, "Sin Visitar");
		subMenuVisitas.setGroupCheckable(VISITAS_MENU_GROUP, true, true);
		
		
		return result;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean handled = false;

		if (item.isChecked()) {
			item.setChecked(false);
		} else {
			item.setChecked(true);
		}	
         
		if (item.getItemId() >= DIA_DOMINGO_ID && item.getItemId() <= DIA_TODOS_ID){
			
			mSelectedDay = item.getItemId();
			new FillClientesTask().execute("");
			handled = true;
		
		} else if (item.getItemId() >= VISITAS_TODOS_ID && item.getItemId() <= VISITAS_SIN_VISITAR_ID){
			
			mSelectedVisita = item.getItemId();
			new FillClientesTask().execute("");
			handled = true;
		
		} else {
			handled = super.onOptionsItemSelected(item);
		}
		return handled;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		filterText.removeTextChangedListener(filterTextWatcher);
		dismissProgressDialog();
	}
	
	private ProgressDialog showProgressDialog() {
        if (mProgressDialog == null) {
            ProgressDialog dialog = new ProgressDialog(this);
            //dialog.setTitle("Cargando...");
            dialog.setMessage("Cargando...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            mProgressDialog = dialog;
        }
        mProgressDialog.show();
        return mProgressDialog;
    }

    private void dismissProgressDialog() {
        try {
            mProgressDialog.dismiss();
        } catch (IllegalArgumentException e) {
            // We don't mind. android cleared it for us.
        }
    }
	
   @Override
   protected void onRestoreInstanceState( Bundle state )
   {
      mSelectedDay = state.getInt(DIA_KEY_ID);
      mSelectedVisita = state.getInt(VISITA_KEY_ID);
      mQueryString = state.getString(QUERYSTRING_KEY_ID);
      super.onRestoreInstanceState( state );
   }

   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      outState.putInt(DIA_KEY_ID, mSelectedDay);
      outState.putInt(VISITA_KEY_ID, mSelectedVisita );
      outState.putString(QUERYSTRING_KEY_ID, mQueryString );
      super.onSaveInstanceState( outState );
   }

}