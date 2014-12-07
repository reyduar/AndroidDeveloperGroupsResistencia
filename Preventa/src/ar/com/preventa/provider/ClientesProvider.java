package ar.com.preventa.provider;

import java.util.HashMap;


import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import ar.com.preventa.PreventaApp;
import ar.com.preventa.db.DatabaseHelper;
import ar.com.preventa.db.DbManager;
import ar.com.preventa.db.PreventaDB;
import ar.com.preventa.db.PreventaDB.ClientesTable;
import ar.com.preventa.db.PreventaDB.SecuenciaTable;



public class ClientesProvider extends ContentProvider {
	//private static final String LOG_TAG = "ClientesProvider";
    
	//public static final Uri CONTENT_URI = Uri
	//				.parse("content://ar.com.preventa.provider.clientes");
	
    //private static final String DATABASE_NAME = "Monitor";
    //private static final int DATABASE_VERSION = 1;
	//private PreventaApp application;

	
    private static final int SEARCH = 1;
    private static final int CLIENTES = 2;
    private static final int CLIENTES_ID = 3;
    private static final int CLIENTES_BY_SECUENCIA_ID = 4;
    
    
    

    //public static final String AUTHORITY = "ar.com.preventa.provider.clientes";

    private static final UriMatcher URI_MATCHER;
    
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(PreventaDB.AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH);
        URI_MATCHER.addURI(PreventaDB.AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH);
        URI_MATCHER.addURI(PreventaDB.AUTHORITY, "Clientes", CLIENTES);
        URI_MATCHER.addURI(PreventaDB.AUTHORITY, "Clientes/#", CLIENTES_ID); 
        URI_MATCHER.addURI(PreventaDB.AUTHORITY, "Secuencia/#", CLIENTES_BY_SECUENCIA_ID); 
    }
    
    private static final HashMap<String, String> CLIENTES_PROJECTION_MAP;
    static {
    	CLIENTES_PROJECTION_MAP = new HashMap<String, String>();
        CLIENTES_PROJECTION_MAP.put(ClientesTable._ID, "Clientes._id");
        CLIENTES_PROJECTION_MAP.put(SecuenciaTable._ID, "Secuencia._id");
        CLIENTES_PROJECTION_MAP.put(ClientesTable.RAZON_SOCIAL, "Clientes.razonsocial");
        CLIENTES_PROJECTION_MAP.put(ClientesTable.DOMICILIO, "Clientes.domicilio");
        //CLIENTES_PROJECTION_MAP.put(ClientesTable.SUCURSAL, "Clientes.sucursal");
        //CLIENTES_PROJECTION_MAP.put(SecuenciaTable.SUCURSAL, "Secuencia.sucursal");
        //CLIENTES_PROJECTION_MAP.put(ClientesTable.COMERCIO, "Clientes.comercio");
        //CLIENTES_PROJECTION_MAP.put(SecuenciaTable.COMERCIO, "Secuencia.comercio");
        CLIENTES_PROJECTION_MAP.put(ClientesTable.CODIGO, "Clientes.cliente");
        CLIENTES_PROJECTION_MAP.put(SecuenciaTable.CLIENTE, "Secuencia.cliente");
        CLIENTES_PROJECTION_MAP.put(SecuenciaTable.DIA, "Secuencia.dia");
        CLIENTES_PROJECTION_MAP.put(SecuenciaTable.ID_CLIENTE, "Secuencia.id_Clientes");
    }
    
    private static final HashMap<String, String> CLIENTE_BY_SECUENCIA_PROJECTION_MAP;
    static {
    	CLIENTE_BY_SECUENCIA_PROJECTION_MAP = new HashMap<String, String>();
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(ClientesTable._ID, "Clientes._id");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(SecuenciaTable._ID, "Secuencia._id");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(ClientesTable.RAZON_SOCIAL, "Clientes.razonsocial");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(ClientesTable.DOMICILIO, "Clientes.domicilio");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(ClientesTable.CODIGO, "Clientes.cliente");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(SecuenciaTable.CLIENTE, "Secuencia.cliente");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(SecuenciaTable.DIA, "Secuencia.dia");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(SecuenciaTable.ID_CLIENTE, "Secuencia.id_Clientes");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(ClientesTable.PROVINCIA, "Clientes.provincia");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(ClientesTable.LOCALIDAD, "Clientes.localidad");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(ClientesTable.TELEFONO, "Clientes.telefono");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(ClientesTable.TIPO_CONTRIBUYENTE, "Clientes.tipocontribuyente");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(ClientesTable.CUIT, "Clientes.cuit");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(ClientesTable.CONDICION_VENTA, "Clientes.condicionventa");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(ClientesTable.SALDO, "Clientes.saldo");
        CLIENTE_BY_SECUENCIA_PROJECTION_MAP.put(ClientesTable.LIMITE_CREDITO, "Clientes.limitecredito");
    }
    
    private static final HashMap<String, String> SUGGESTION_PROJECTION_MAP;
    static {
        SUGGESTION_PROJECTION_MAP = new HashMap<String, String>();
        
        SUGGESTION_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_1,
        		ClientesTable.RAZON_SOCIAL + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
        
        SUGGESTION_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_2,
        		ClientesTable.DOMICILIO   + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_2);
        
        SUGGESTION_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID,
        		ClientesTable._ID + " AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
        
        SUGGESTION_PROJECTION_MAP.put(ClientesTable._ID, "Clientes._id");
    }

    private DbManager mOpenHelper = null;

    @Override
    public boolean onCreate() {
 
    	//lo primero que se instancia son los contents providers
    	//aun no tengo instancia de PreventaApp
     
//    	if (mOpenHelper == null) {
//    		mOpenHelper = new DbManager(getContext()); 	
//    		mOpenHelper.createNewDatabase();
//    	}
//    	mOpenHelper.open();
    	
    	return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (URI_MATCHER.match(uri)) {
            case SEARCH:
                qb.setTables(ClientesTable.TABLE);
                String query = uri.getLastPathSegment();
                if (!TextUtils.isEmpty(query)) {
                    qb.appendWhere(ClientesTable.RAZON_SOCIAL + " LIKE ");
                    qb.appendWhereEscapeString('%' + query + '%');
                    //qb.appendWhere(" OR ");
                    //qb.appendWhere(Clientes.DOMICILIO + " LIKE ");
                    //qb.appendWhereEscapeString('%' + query + '%');
                }
                sortOrder = ClientesTable.DEFAULT_SORT_ORDER;
                qb.setProjectionMap(SUGGESTION_PROJECTION_MAP);
                break;
            case CLIENTES:
                qb.setTables(ClientesTable.TABLE + "," + SecuenciaTable.TABLE);
                qb.appendWhere("Clientes._id = Secuencia.id_Clientes");
                qb.setProjectionMap(CLIENTES_PROJECTION_MAP);
                break;
            case CLIENTES_ID:
                qb.setTables(ClientesTable.TABLE);
                qb.appendWhere("_id=" + uri.getPathSegments().get(1));
                break;               
            case CLIENTES_BY_SECUENCIA_ID:
            	qb.setTables(ClientesTable.TABLE + "," + SecuenciaTable.TABLE);
                qb.appendWhere("Clientes._id = Secuencia.id_Clientes");
                qb.appendWhere(" AND ");
                qb.appendWhere("Secuencia._id=" + uri.getPathSegments().get(1));
                qb.setProjectionMap(CLIENTE_BY_SECUENCIA_PROJECTION_MAP);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If no sort order is specified use the default
//        String orderBy;
//        if (TextUtils.isEmpty(sortOrder)) {
//            orderBy = SecuenciaTable.DEFAULT_SORT_ORDER;
//        } else {
//            orderBy = sortOrder;
//        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, null);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    public String getType(Uri uri) {
    	String mime = null;
    	switch (URI_MATCHER.match(uri)) {
            case CLIENTES:
            	mime = ClientesTable.CONTENT_TYPE;
            	break;
            case CLIENTES_ID:
                mime = ClientesTable.CONTENT_ITEM_TYPE;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    	return mime;
    }

    public Uri insert(Uri uri, ContentValues initialValues) {
    	throw new UnsupportedOperationException();
    }

    

    public int delete(Uri uri, String selection, String[] selectionArgs) {
    	throw new UnsupportedOperationException();
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    	throw new UnsupportedOperationException();
    }
}
