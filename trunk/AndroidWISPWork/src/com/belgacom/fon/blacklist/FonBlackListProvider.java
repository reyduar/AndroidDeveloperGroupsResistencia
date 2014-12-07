package com.belgacom.fon.blacklist;

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

public class FonBlackListProvider extends ContentProvider {
	private static String AUTHORITY = "com.belgacom.fon.blacklist.FonBlackListProvider";

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "black_list.db";

	private static final int MATCH_BLACK_LIST_URI = 1;

	private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		uriMatcher.addURI(AUTHORITY, "black_list", MATCH_BLACK_LIST_URI);
	}
	

	public static final Uri BLACK_LIST_URI = Uri.parse("content://" + AUTHORITY + "/black_list");
	
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
		case MATCH_BLACK_LIST_URI:
			rowsDeleted = db.delete(BlackListTable.TABLE, selection, selectionArgs);
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
		case MATCH_BLACK_LIST_URI:
			db.insert(BlackListTable.TABLE, null, values);
			break;
		}
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case MATCH_BLACK_LIST_URI:
			return db.query(BlackListTable.TABLE, projection, selection, selectionArgs, null, null, sortOrder);
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
			db = dbHelper.getWritableDatabase();
			int rowsUpdated = 0;
			switch (uriMatcher.match(uri)) {
			case MATCH_BLACK_LIST_URI:
				rowsUpdated = db.update(BlackListTable.TABLE, values,selection, selectionArgs);
				break;
			}
			return rowsUpdated;
	}

	public static class BlackListTable implements BaseColumns {
		public static final String COLUMN_SSID = "ssid";
		public static final String COLUMN_BSSID = "bssid";
		public static final String COLUMN_TIME = "time";
		
		public static final int INDEX_SSID = 1;
		public static final int INDEX_BSSID = 2;
		public static final int INDEX_TIME = 3;

		public static final String TABLE = "black_list";

	}
	
	static private class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		    db.execSQL("DROP TABLE " + BlackListTable.TABLE);
		    onCreate(db);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + BlackListTable.TABLE + "("
					+ BlackListTable._ID + " INTEGER PRIMARY KEY ASC,"
					+ BlackListTable.COLUMN_SSID + " TEXT COLLATE NOCASE,"
					+ BlackListTable.COLUMN_BSSID + " TEXT COLLATE NOCASE," 
					+ BlackListTable.COLUMN_TIME + " LONG)");
		}
	}
}
