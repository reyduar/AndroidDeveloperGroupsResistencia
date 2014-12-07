package com.videotutoriales.contactos;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.SimpleCursorAdapter;
import android.content.ContentUris;

public class ContactosActivity extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//Uri allContacts = Uri.parse("content://contacts/people/1");
		//Uri allContacts = ContactsContract.Contacts.CONTENT_URI;
		Uri allContacts = ContentUris.withAppendedId(
				ContactsContract.Contacts.CONTENT_URI, 1);		
				Cursor c = managedQuery(allContacts, null, null, null, null);
		String[] columns = new String[] {
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts._ID };
		int[] views = new int[] { R.id., R.id.contactID };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.main, c, columns, views);
		this.setListAdapter(adapter);
	}
}