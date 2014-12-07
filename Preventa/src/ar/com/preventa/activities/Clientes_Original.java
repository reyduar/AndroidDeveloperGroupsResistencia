package ar.com.preventa.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
import ar.com.preventa.R;
import ar.com.preventa.data.entity.Notes;
import ar.com.preventa.data.utils.GenericDAO_Original;

public class Clientes_Original extends Activity {
	/** Called when the activity is first created. */
	private ListView listClientes;
	private Runnable viewOrders;
	public GenericDAO_Original dao = null;
	SimpleCursorAdapter mAdapter = null;
	ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		try {
			
			
			super.onCreate(savedInstanceState);
			
			
			
			setContentView(R.layout.clientes);
			/*
			dialog = ProgressDialog.show(this, "",
                    "Loading. Please wait...", true);
			
			dialog.setIndeterminate(false);
			*/
			Intent intent = getIntent();

		    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
		      String query = intent.getStringExtra(SearchManager.QUERY);
		      //doMySearch(query);
		    }
			

			dao = GenericDAO_Original.getInstance(this, Notes.DATABASE_NAME, "",
					Notes.DATABASE_TABLE, Notes.DATABASE_VERSION);

			String[] columns = new String[] { GenericDAO_Original.KEY_ID, "razonsocial",
					"direccion" };
			int[] to = new int[] { R.id.name_entry, R.id.address_entry };
			Cursor cursor = dao.get(Notes.DATABASE_TABLE, columns);
			startManagingCursor(cursor);

			mAdapter = new SimpleCursorAdapter(this,
					R.layout.clientes_grid_row, cursor, new String[] {
							"razonsocial", "direccion" }, to);

			listClientes = (ListView) findViewById(R.id.listClientes);
			listClientes.setAdapter(mAdapter);
			listClientes.setFastScrollEnabled(true);
			listClientes.setTextFilterEnabled(true);
			
			setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
			
			

			listClientes.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> av, View v,
						int position, long id) {
				}
			});
			
			//dialog.dismiss();
            /*
			EditText txtSearch = (EditText) findViewById(R.id.EditBuscarClientes);
			txtSearch.addTextChangedListener(new TextWatcher() {
				@Override
				public void afterTextChanged(Editable arg0) {
				}

				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
				}

				public void onTextChanged(CharSequence chars, int start,
						int before, int count) {
					mAdapter.getFilter().filter(chars,
							new Filter.FilterListener() {
								public void onFilterComplete(int count) {
									//Log.d(Config.LOG_TAG,
									//		"filter complete! count: " + count);
									mAdapter.notifyDataSetChanged();
								}
							});
				}
			});
         */

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.close();
		}
	}
}