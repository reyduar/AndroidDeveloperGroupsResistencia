package ar.com.preventa.data.utils;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GenericDAO_Original extends SQLiteOpenHelper {

    private static final String TAG = "GenericDAO";
    private static SQLiteDatabase db;
    private static String dName;
    private static String tName;
    private static Context context;
    private static String sql;
    public static final String KEY_ID = "_id";
    
    private static String DB_PATH = "/data/data/ar.com.preventa/databases/";
    
    private static String DB_NAME = "Monitor";

    private static GenericDAO_Original instance;
    
    private GenericDAO_Original(Context ctx, String dbName, String sql, String tableName, int ver){
        super(ctx, dbName, null, ver);
        Log.i(TAG, "Creating or opening database [ " + dbName + " ].");
        GenericDAO_Original.sql = sql;
        dName = dbName;
        tName = tableName;
        context = ctx;
    }
    
    public static GenericDAO_Original getInstance(Context ctx, String dbName, String sql, String tableName, int ver){
        if(instance == null){
            instance = new GenericDAO_Original(ctx, dbName, sql, tableName, ver);
            try{
                Log.i(TAG, "Creating or opening the database [ " + dbName + " ].");
                db = instance.getWritableDatabase();
            }catch(SQLiteException se){
                Log.e(TAG, "Cound not create and/or open the database [ " + dbName + " ] that will be used for reading and writing.", se);
            }
        }
        return instance;
    }
    /*
    //copio database existente en assests
    private static void copyDataBase() throws IOException{
    	 
    	//Open your local db as the input stream
    	InputStream myInput = context.getAssets().open(DB_NAME + ".mp3");
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	
    	while ( (length = myInput.read(buffer)) > 0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
    */
    private void copyDataBase() throws IOException{ 
        OutputStream databaseOutputStream = new 
        FileOutputStream(DB_PATH + DB_NAME); 
         InputStream databaseInputStream; 
        byte[] buffer = new byte[1024]; 
        int length; 
        
        databaseInputStream = context.getAssets().open(DB_NAME + ".mp3"); 
        while ( (length = databaseInputStream.read(buffer)) > 0 ) { 
            databaseOutputStream.write(buffer); 
        } 
        databaseInputStream.close(); 
        
        /*
        databaseInputStream = databaseOpenHelperContext.getResources().openRawResource(R.raw.datafileab); 
        while ( (length = databaseInputStream.read(buffer)) > 0 ) { 
            databaseOutputStream.write(buffer); 
        } 
        databaseInputStream.close(); 
        databaseInputStream = databaseOpenHelperContext.getResources().openRawResource(R.raw.datafileac); 
        while ( (length = databaseInputStream.read(buffer)) > 0 ) { 
            databaseOutputStream.write(buffer); 
        } 
        databaseInputStream.close(); 
        */
        databaseOutputStream.flush(); 
        databaseOutputStream.close(); 
    } 
    
    public void close(){
        if(instance != null){
            Log.i(TAG, "Closing the database [ " + dName + " ].");
            db.close();
            instance = null;
        }
    }
    
    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i(TAG, "Trying to create database table if it isn't existed [ " + sql + " ].");
        /*
        try {             	 
			copyDataBase();
		} catch (IOException e) { 
    		throw new Error("Error copying database");
    	}
        */
        /*
        try{
            //db.execSQL(sql);
        }catch(SQLException se){
            Log.e(TAG, "Cound not create the database table according to the SQL statement [ " + sql + " ].", se);
        }
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
       Log.i(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
       try{
           db.execSQL("DROP TABLE IF EXISTS " + tName);
       }catch(SQLException se){
            Log.e(TAG, "Cound not drop the database table [ " + tName + " ].", se);
        }
       onCreate(db);
    }

    public long insert(String table, ContentValues values){
        return db.insert(table, null, values);
    }
    
    public Cursor get(String table, String[] columns){
        return db.query(table, columns, null, null, null, null, null);
    }
    
   // query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
    public Cursor get(String table, String[] columns,String selection, String[] selectionArgs,String groupBy,String having,String orderBy){
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
    
    public Cursor get(String table, String[] columns, long id){
        Cursor cursor =db.query(true, table, columns, KEY_ID + "=" + id, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    
    public int delete(String table) {
        return db.delete(table, "1", null);
    }
    
    public int delete(String table, long id) {
        return db.delete(table, KEY_ID + "=" + id, null);
    }
    
    public int update(String table, long id, ContentValues values) {
        return db.update(table, values, KEY_ID + "=" + id, null);
    }
}
