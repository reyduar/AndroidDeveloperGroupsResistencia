package ar.com.preventa.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import ar.com.preventa.db.PreventaDB.AuxiliaryTableColumns;
import ar.com.preventa.db.PreventaDB.ClientesTable;

public class DbManager extends SQLiteOpenHelper{
	private static SQLiteDatabase sqliteDb;  
	private static DbManager instance;
	private static final int DATABASE_VERSION = 1;  
	// the default database path is : /data/data/pkgNameOfYourApplication/databases/
	private static String DB_PATH_PREFIX = "/data/data/";
	private static String DB_PATH_SUFFIX = "/databases/";
	private static final String TAG = "DbManager";  
	private Context context;
 
	/***
	 * Contructor
	 * 
	 * @param context	: app context
	 * @param name		: database name
	 * @param factory	: cursor Factory
	 * @param version	: DB version
	 */
	private DbManager(Context context, String name, CursorFactory factory, int version) {  
		super(context, name, factory, version);  
		this.context = context;
		Log.i(TAG, "Create or Open database : "+name);
	} 
 
	/***
	 * Initialize method
	 * 
	 * @param context		: application context
	 * @param databaseName	: database name
	 */
	private static void initialize(Context context,String databaseName) {  
		if(instance == null) {  
			/**
			 * Try to check if there is an Original copy of DB in asset Directory
			 */
			if (!checkDatabase(context,databaseName)){
				// if not exists, I try to copy from asset dir 
				try {
					copyDataBase(context,databaseName);
				} catch (IOException e) {
					Log.e(TAG,"Database "+databaseName+" does not exists and there is no Original Version in Asset dir");
				}
			}
 
			Log.i(TAG, "Try to create instance of database ("+databaseName+")");
			instance = new DbManager(context, databaseName, null, DATABASE_VERSION);
			sqliteDb = instance.getWritableDatabase();
			Log.i(TAG, "instance of database ("+databaseName+") created !");
		}  
	}
 
	/***
	 * Static method for getting singleton instance
	 * 
	 * @param context		: application context
	 * @param databaseName	: database name
	 * @return				: singleton instance
	 */
	public static final DbManager getInstance(Context context,String databaseName) {  
		initialize(context,databaseName);  
		return instance;  
	}  
	/***
	 * Method to get database instance 
	 * @return	database instance
	 */
	public SQLiteDatabase getDatabase() {  
		return sqliteDb;  
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate : nothing to do");
 
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onCreate : nothing to do");
 
	} 
 
	/***
	 * Method for Copy the database from asset directory to application's data directory
	 * @param databaseName	: database name
	 * @throws IOException	: exception if file does not exists
	 */
	private void copyDataBase (String databaseName) throws IOException{
		copyDataBase(context,databaseName);
	}
 
	/***
	 * Static method for copy the database from asset directory to application's data directory
	 * @param aContext		: application context
	 * @param databaseName	: database name
	 * @throws IOException	: exception if file does not exists
	 */
	private static void copyDataBase(Context aContext,String databaseName) throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = aContext.getAssets().open(databaseName.replace(".db", ".mp3"));
 
 
    	// Path to the just created empty db
    	String outFileName = getDatabasePath(aContext,databaseName);
 
    	Log.i(TAG,"Trying to copy local DB to : "+outFileName);
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    	Log.i(TAG,"DB ("+databaseName+") copied!");
	}
 
	/***
	 * Method to check if database exists in application's data directory
	 * @param databaseName	: database name
	 * @return				: boolean (true if exists)
	 */
	public boolean checkDatabase(String databaseName){
		return checkDatabase(context,databaseName);
	}
 
	/***
	 * Static Method to check if database exists in application's data directory
	 * @param aContext		: application context
	 * @param databaseName	: database name
	 * @return				: boolean (true if exists)
	 */
	public static boolean checkDatabase(Context aContext,String databaseName){
		SQLiteDatabase checkDB = null;
 
		try{
			String myPath = getDatabasePath(aContext,databaseName);
 
	    	Log.i(TAG,"Trying to conntect to : "+myPath);
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			Log.i(TAG,"Database "+databaseName+" found!");
			checkDB.close();
		}catch(SQLiteException e){
			Log.i(TAG,"Database "+databaseName+" does not exists!");
 
		}
 
		return checkDB != null ? true : false;
	}
 
	/***
	 * Method that returns database path in the application's data directory
	 * @param databaseName	: database name
	 * @return				: complete path
	 */
	private String getDatabasePath(String databaseName){
		return getDatabasePath(context,databaseName);
	}
 
	/***
	 * Static Method that returns database path in the application's data directory
	 * @param aContext		: application context
	 * @param databaseName	: database name
	 * @return				: complete path
	 */
	private static String getDatabasePath(Context aContext,String databaseName){
		return DB_PATH_PREFIX + aContext.getPackageName() +DB_PATH_SUFFIX+ databaseName;
	}
	
	
	public Cursor get(String table, String[] columns,String selection, String[] selectionArgs,String groupBy,String having,String orderBy){
       Cursor cursor = sqliteDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
       if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
	}
	
	public Cursor get(String table, String[] columns,String selection){
	       Cursor cursor = sqliteDb.query(table, columns, selection, null, null, null, null);
	       if (cursor != null) {
	            cursor.moveToFirst();
	        }
	        return cursor;
	}
	
	public Cursor get(String table, String[] columns){
	       Cursor cursor = sqliteDb.query(table, columns, null, null, null, null, null);
	       if (cursor != null) {
	            cursor.moveToFirst();
	        }
	        return cursor;
	}
	
	public String getStringById(String table, Integer codigo){
			try {
				Cursor cursor = get(table, new String[] { "_id", AuxiliaryTableColumns.CODIGO, AuxiliaryTableColumns.DESCRIPCION}, table + "." + AuxiliaryTableColumns.CODIGO + "=" + codigo.toString());
				if (cursor != null) {
				    cursor.moveToFirst();
				}
				return cursor.getString(cursor.getColumnIndex(AuxiliaryTableColumns.DESCRIPCION));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return "";
			}	
	}
	
	
	public Cursor getDatosClienteBySecuenciaId(Long idSecuencia){
		try {
			
			Cursor cursor = get("Clientes, Secuencia", new String[] { "Secuencia._id", 
																	  "Clientes.cliente", 
																	  ClientesTable.RAZON_SOCIAL,
																	  ClientesTable.DOMICILIO,
																	  ClientesTable.PROVINCIA,
																	  ClientesTable.LOCALIDAD,
																	  ClientesTable.TELEFONO,
																	  ClientesTable.TIPO_CONTRIBUYENTE,
																	  ClientesTable.CUIT,
																	  ClientesTable.CONDICION_VENTA,
																	  ClientesTable.SALDO,
																	  ClientesTable.LIMITE_CREDITO},"Clientes._id = Secuencia.id_Clientes and Secuencia._id=" + idSecuencia.toString() );
			if (cursor != null) {
			    cursor.moveToFirst();
			}
		    return cursor;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	        return null;
		} 
}
	
	
		
}

/*
public class DbManager extends SQLiteOpenHelper {
	 
    private static final String DB_NAME = "Preventa";
    private static final String DB_PATH = "/data/data/ar.com.preventa/databases/";
    private static final Integer DB_VERSION = 1;
    private static final String TAG = "DbManager";
    private final Context context;
    private SQLiteDatabase db;
    private DbManager dbManager;
 
    public DbManager(Context context) {
        super(context, DB_NAME+ ".db",  null, DB_VERSION);
        this.context = context;
    }
 
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    	createNewDatabase();
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
 
    public DbManager open() {
        dbManager = new DbManager(context);
        db = dbManager.getWritableDatabase();
        return this;
    }
    /*
    public SQLiteDatabase getDb() {
		return this.db;
	}
    */
    /*
    public void createNewDatabase() {
		InputStream assetsDB = null;
		try {
			assetsDB = context.getAssets().open(DB_NAME + ".mp3");
			OutputStream dbOut = new FileOutputStream(DB_PATH + DB_NAME + ".db");

			byte[] buffer = new byte[1024];
			int length;
			while ((length = assetsDB.read(buffer)) > 0) {
				dbOut.write(buffer, 0, length);
			}

			dbOut.flush();
			dbOut.close();
			assetsDB.close();
			Log.i(TAG, "New database created...");
		} catch (IOException e) {
			Log.e(TAG, "Could not create new database...");
			e.printStackTrace();
		}
	}
	
	public Cursor get(String table, String[] columns,String selection, String[] selectionArgs,String groupBy,String having,String orderBy){
       Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
       if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
	}
	
	public Cursor get(String table, String[] columns,String selection){
	       Cursor cursor = db.query(table, columns, selection, null, null, null, null);
	       if (cursor != null) {
	            cursor.moveToFirst();
	        }
	        return cursor;
	}
	
	public Cursor get(String table, String[] columns){
	       Cursor cursor = db.query(table, columns, null, null, null, null, null);
	       if (cursor != null) {
	            cursor.moveToFirst();
	        }
	        return cursor;
	}
	
	public String getStringById(String table, Integer codigo){
			try {
				Cursor cursor = get(table, new String[] { "_id", AuxiliaryTableColumns.CODIGO, AuxiliaryTableColumns.DESCRIPCION}, table + "." + AuxiliaryTableColumns.CODIGO + "=" + codigo.toString());
				if (cursor != null) {
				    cursor.moveToFirst();
				}
				return cursor.getString(cursor.getColumnIndex(AuxiliaryTableColumns.DESCRIPCION));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return "";
			}	
	}
	
	
	public Cursor getDatosClienteBySecuenciaId(Long idSecuencia){
		try {
			
			Cursor cursor = get("Clientes, Secuencia", new String[] { "Secuencia._id", 
																	  "Clientes.cliente", 
																	  ClientesTable.RAZON_SOCIAL,
																	  ClientesTable.DOMICILIO,
																	  ClientesTable.PROVINCIA,
																	  ClientesTable.LOCALIDAD,
																	  ClientesTable.TELEFONO,
																	  ClientesTable.TIPO_CONTRIBUYENTE,
																	  ClientesTable.CUIT,
																	  ClientesTable.CONDICION_VENTA,
																	  ClientesTable.SALDO,
																	  ClientesTable.LIMITE_CREDITO},"Clientes._id = Secuencia.id_Clientes and Secuencia._id=" + idSecuencia.toString() );
			if (cursor != null) {
			    cursor.moveToFirst();
			}
		    return cursor;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	        return null;
		} 
}
	
}

/*
public class DbManager extends SQLiteOpenHelper {

	private static final String DB_NAME = "Preventa";
	private static final String DB_PATH = "/data/data/ar.com.preventa/databases/";
	private static final Integer DB_VERSION = 1;
	private static final String TAG = "DbManager";
	private static Context mContext = null;
	private static SQLiteDatabase db;
	private static DbManager instance;

	private DbManager dbManager;

	private DbManager() {
		super(mContext, DB_NAME + ".db", null, DB_VERSION);
		//this.context = context;
	}
	
	public static DbManager getInstance(Context context){
	       if(instance == null){
	    	   mContext = context;
	    	   instance = new DbManager();
	           try{
	               Log.i(TAG, "Creating or opening the database [ " + PreventaDB.DATABASE_NAME + " ].");
	               db = instance.getWritableDatabase();
	           }catch(SQLiteException se){
	               Log.e(TAG, "Cound not create and/or open the database [ " + PreventaDB.DATABASE_NAME + " ] that will be used for reading and writing.", se);
	           }
	       }
	       return instance;
	   }

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		//createNewDatabase();
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
	}

//	public DbManager open() {
//		dbManager = new DbManager(context);
//		db = dbManager.getWritableDatabase();
//		return this;
//	}
	
	public SQLiteDatabase getDb() {
		return this.db;
	}

	// copia la base de datos desde assets
	public void createNewDatabase() {
		InputStream assetsDB = null;
		try {
			assetsDB = mContext.getAssets().open(DB_NAME + ".mp3");
			OutputStream dbOut = new FileOutputStream(DB_PATH + DB_NAME + ".db");

			byte[] buffer = new byte[1024];
			int length;
			while ((length = assetsDB.read(buffer)) > 0) {
				dbOut.write(buffer, 0, length);
			}

			dbOut.flush();
			dbOut.close();
			assetsDB.close();
			Log.i(TAG, "New database created...");
		} catch (IOException e) {
			Log.e(TAG, "Could not create new database...");
			e.printStackTrace();
		}
	}
}*/