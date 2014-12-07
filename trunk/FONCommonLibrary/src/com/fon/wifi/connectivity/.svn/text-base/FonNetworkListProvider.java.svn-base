package com.fon.wifi.connectivity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class FonNetworkListProvider extends ContentProvider {
	private static String AUTHORITY;
	private static Uri NEWORK_LIST_URI;

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "fon_network_list.db";

	private static final int MATCH_LIST_URI = 1;

	private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	public static void init(String packageName){
		AUTHORITY = packageName+".FonNetworkListProvider";
		uriMatcher.addURI(AUTHORITY, "list", MATCH_LIST_URI);
		NEWORK_LIST_URI = Uri.parse("content://" + AUTHORITY + "/list");
	}
	
	public static Uri getNetworkListUri(){
		return NEWORK_LIST_URI;
	}

	
	private SQLiteDatabase db;
	private DBHelper dbHelper;

	@Override
	public boolean onCreate() {
		dbHelper = new DBHelper(getContext(), DB_NAME, null, DB_VERSION);
		return false;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		db = dbHelper.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriMatcher.match(uri)) {
		case MATCH_LIST_URI:
			rowsDeleted = db.delete(NetworkListTable.TABLE, selection, selectionArgs);
			Log.d(DB_NAME, "DELETED:"+ rowsDeleted);
			break;
		}
		return rowsDeleted;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case MATCH_LIST_URI:
			db.insert(NetworkListTable.TABLE, null, values);
			Log.d(DB_NAME, "INSERTED:"+ values.get(NetworkListTable.COLUMN_SSID));
			break;
		}
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case MATCH_LIST_URI:
			return db.query(NetworkListTable.TABLE, projection, selection, selectionArgs, null, null, sortOrder);
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
			db = dbHelper.getWritableDatabase();
			int rowsUpdated = 0;
			switch (uriMatcher.match(uri)) {
			case MATCH_LIST_URI:
				rowsUpdated = db.update(NetworkListTable.TABLE, values,selection, selectionArgs);
				break;
			}
			return rowsUpdated;
	}

	public static class NetworkListTable implements BaseColumns {
		public static final String COLUMN_SSID = "ssid";
		
		public static final int INDEX_SSID = 1;

		public static final String TABLE = "network_list";

	}
	
	static private class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		    db.execSQL("DROP TABLE " + NetworkListTable.TABLE);
		    onCreate(db);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + NetworkListTable.TABLE + "("
					+ NetworkListTable._ID + " INTEGER PRIMARY KEY ASC,"
					+ NetworkListTable.COLUMN_SSID + " TEXT COLLATE NOCASE)");
		}
	}
}
