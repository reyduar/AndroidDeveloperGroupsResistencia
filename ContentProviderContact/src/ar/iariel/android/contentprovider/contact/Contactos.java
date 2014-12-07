package ar.iariel.android.contentprovider.contact;



import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.SimpleCursorAdapter;

public class Contactos extends ListActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.main_principal);
		 
		 
		 //-- Aqui utilizamos la query Uri para consulta el ContectProvider que necesitamos --//
		 Uri allContacts = Uri.parse("content://contacts/people");
		 
		 //-- Podemos ademas usar en vez de Uri una
		 //-- Hacemos una llamada al metodo que realiza la consulta del Content Provider de la agenda del dispositivo
		 //-- usamos el metodo de la clase activity  managedQuery()
		 Cursor c = managedQuery(allContacts, null, null, null, null);
		 String[] columns = new String[] {ContactsContract.Contacts.DISPLAY_NAME,
				 ContactsContract.Contacts._ID};
		 int[] views = new int[]{R.id.contactName, R.id.contactID};
		 SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.main_principal,
				 c, columns, views);
		 this.setListAdapter(adapter);
		
	}
}
