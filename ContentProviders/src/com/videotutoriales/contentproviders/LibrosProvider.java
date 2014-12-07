package com.videotutoriales.contentproviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class LibrosProvider extends ContentProvider {
	public static final String PROVIDER_NAME = "com.videotutoriales.provider.Libros";//Definimos la ruta al provider
	public static final Uri CONTENT_URI = Uri.parse("content://" +PROVIDER_NAME + "/libros");//Definimos el Uri dependiendo de nuestro contenedor
	public static final String _ID = "_id";// Definimos la constante para el campo id de la tabla
	public static final String TITULO = "titulo";// Definimos la constante para el campo titulo de la tabla
	public static final String ISBN = "isbn";// Definimos la constante para el campo isbn de la tabla
	private static final int LIBROS = 1;
	private static final int LIBRO_ID = 2;
	private static final UriMatcher uriMatcher;//Sera el encargado de parsear el contenido Uri que se le pase 
	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "libros", LIBROS);
		uriMatcher.addURI(PROVIDER_NAME, "libros/#", LIBRO_ID);
	}
	// ---para uso base datos---
	private SQLiteDatabase librosDB;
	private static final String NOMBRE_BASEDATOS = "Libros";
	private static final String TABLA_BASEDATOS = "titulos";
	private static final int VERSION_BASEDATOS = 1;
	private static final String CREAR_BASEDATOS = "create table "
			+ TABLA_BASEDATOS + " (_id integer primary key autoincrement, "
			+ "titulo text not null, isbn text not null);";

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREAR_BASEDATOS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Content provider basedatos",
					"Actualizando basedatos de versión " + oldVersion + " a "
							+ newVersion
							+ ", lo que destruirá todos los viejos datos");
			db.execSQL("DROP TABLE IF EXISTS titulos");
			onCreate(db);
		}
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// arg0 = uri
		// arg1 = selection
		// arg2 = selectionArgs
		int count = 0;
		switch (uriMatcher.match(arg0)) {
		case LIBROS:
			count = librosDB.delete(TABLA_BASEDATOS, arg1, arg2);
			break;
		case LIBRO_ID:
			String id = arg0.getPathSegments().get(1);
			count = librosDB.delete(TABLA_BASEDATOS, _ID + " = " + id
					+ (!TextUtils.isEmpty(arg1) ? " AND (" + arg1 + ')' : ""),
					arg2);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + arg0);
		}
		getContext().getContentResolver().notifyChange(arg0, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		// ---obtener todos los libros---
		case LIBROS:
			return "vnd.android.cursor.dir/vnd.videotutoriales.libros ";
			// ---obtener un libro concreto---
		case LIBRO_ID:
			return "vnd.android.cursor.item/vnd.videotutoriales.libros ";
		default:
			throw new IllegalArgumentException("URI no admitida: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// ---añadir un nuevo libro---
		long rowID = librosDB.insert(TABLA_BASEDATOS, "", values);
		// ---si añadido de modo correcto---
		if (rowID > 0) {
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
		}
		throw new SQLException("error al insertar fila en " + uri);
	}

	@Override
	public boolean onCreate() {
		Context context = getContext();
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		librosDB = dbHelper.getWritableDatabase();
		return (librosDB == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
		sqlBuilder.setTables(TABLA_BASEDATOS);
		if (uriMatcher.match(uri) == LIBRO_ID)
			// ---si obtenemos un libro concreto---
			sqlBuilder.appendWhere(_ID + " = " + uri.getPathSegments().get(1));
		if (sortOrder == null || sortOrder == "")
			sortOrder = TITULO;
		Cursor c = sqlBuilder.query(librosDB, projection, selection,
				selectionArgs, null, null, sortOrder);
		// ---registro para ver un content URI para cambios---
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case LIBROS:
			count = librosDB.update(TABLA_BASEDATOS, values, selection,
					selectionArgs);
			break;
		case LIBRO_ID:
			count = librosDB.update(
					TABLA_BASEDATOS,
					values,
					_ID
							+ " = "
							+ uri.getPathSegments().get(1)
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}