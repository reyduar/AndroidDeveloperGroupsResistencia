package ar.com.iariel.persistencia.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Persistencia con SQlite
 * 
 * @author reynaldo.duarte
 * 
 */
public class AdaptadorBD {
	public static final String KEY_IDFILA = "_id";
	public static final String KEY_NOMBRE = "nombre";
	public static final String KEY_EMAIL = "email";
	private static final String TAG = "AdaptadorBD";
	private static final String NOMBRE_BASEDATOS = "MiBD";
	private static final String TABLA_BASEDATOS = "contactos";
	private static final int VERSION_BASEDATOS = 1;
	private static final String CREAR_BASEDATOS = "create table contactos "
			+ "(_id integer primary key autoincrement, nombre text not null, email text not null);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public AdaptadorBD(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREAR_BASEDATOS);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Actualizando Base de Datos  de versión" + oldVersion
					+ "a" + newVersion
					+ ". Se destruiran todos los datos viejos");
			db.execSQL("DROP TABLE IF EXISTS contactos");
			onCreate(db);
		}
	}

	// -- Abrir la base de datos --//
	public AdaptadorBD abrir() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// --Cerrar la base de datos
	public void cerrar() {
		DBHelper.close();
	}

	// -- Inserta contactos en la Base de Datos --//
	public long insertarContacto(String nombre, String email) {
		ContentValues valoresIniciales = new ContentValues();
		valoresIniciales.put(KEY_NOMBRE, nombre);
		valoresIniciales.put(KEY_EMAIL, email);
		return db.insert(TABLA_BASEDATOS, null, valoresIniciales);
	}

	// -- Borrar un contacto en concreto --//
	public boolean borrarContacto(long idFila) {
		return db.delete(TABLA_BASEDATOS, KEY_IDFILA + "=" + idFila, null) > 0;
	}

	// -- Recuperar un contacto en concreto --//
	public Cursor obtenerTodosContactos() {
		return db.query(TABLA_BASEDATOS, new String[] { KEY_IDFILA, KEY_NOMBRE,
				KEY_EMAIL }, null, null, null, null, KEY_NOMBRE);
	}

	// -- Recuperar un contacto en concreto --//
	public Cursor obtenerContacto(long idFila) throws SQLException {
		Cursor mCursor = db.query(TABLA_BASEDATOS, new String[] { KEY_IDFILA,
				KEY_NOMBRE, KEY_EMAIL }, KEY_IDFILA + "=" + idFila, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	//-- Actualizar un Contacto --//
	public boolean actualizarContacto(long idFila, String nombreFila,
			String emailFila) {
		ContentValues args = new ContentValues();
		args.put(KEY_NOMBRE, nombreFila);
		args.put(KEY_EMAIL, emailFila);
		return db.update(TABLA_BASEDATOS, args, KEY_IDFILA + "=" + idFila, null) > 0;

	}
}
