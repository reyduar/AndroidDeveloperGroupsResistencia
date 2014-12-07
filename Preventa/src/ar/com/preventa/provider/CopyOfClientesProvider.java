package ar.com.preventa.provider;

import java.util.HashMap;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.content.Context;



public class CopyOfClientesProvider extends ContentProvider {
	private static final String LOG_TAG = "ClientesProvider";
    
	public static final Uri CONTENT_URI = Uri
					.parse("content://ar.com.preventa.provider.clientes");
	
    private static final String DATABASE_NAME = "Monitor";
    private static final int DATABASE_VERSION = 1;

    private static final int SEARCH = 1;
    private static final int CLIENTES = 2;
    private static final int CLIENTES_ID = 3;

    public static final String AUTHORITY = "ar.com.preventa.provider.clientes";

    private static final UriMatcher URI_MATCHER;
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH);
        URI_MATCHER.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH);
        URI_MATCHER.addURI(AUTHORITY, "clientes", CLIENTES);
        URI_MATCHER.addURI(AUTHORITY, "clientes/#", CLIENTES_ID);
    }

    private static final HashMap<String, String> SUGGESTION_PROJECTION_MAP;
    static {
        SUGGESTION_PROJECTION_MAP = new HashMap<String, String>();
        SUGGESTION_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_1,
                "razonsocial" + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
        SUGGESTION_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_2,
                "direccion" + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_2);
        SUGGESTION_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID,
                "_id" + " AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
        SUGGESTION_PROJECTION_MAP.put("_id", "_id");
    }

    private SQLiteOpenHelper mOpenHelper;

    //private Pattern[] mKeyPrefixes;
    //private Pattern[] mKeySuffixes;

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (URI_MATCHER.match(uri)) {
            case SEARCH:
                qb.setTables("clientes");
                String query = uri.getLastPathSegment();
                if (!TextUtils.isEmpty(query)) {
                    qb.appendWhere("razonsocial" + " LIKE ");
                    qb.appendWhereEscapeString('%' + query + '%');
                    //qb.appendWhere(" OR ");
                    //qb.appendWhere("direccion" + " LIKE ");
                    //qb.appendWhereEscapeString('%' + query + '%');
                }
                qb.setProjectionMap(SUGGESTION_PROJECTION_MAP);
                break;
            case CLIENTES:
                qb.setTables("clientes");
                break;
            case CLIENTES_ID:
                qb.setTables("clientes");
                qb.appendWhere("_id=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If no sort order is specified use the default
        String orderBy;
        //if (TextUtils.isEmpty(sortOrder)) {
        //    orderBy = BooksStore.Book.DEFAULT_SORT_ORDER;
        //} else {
            orderBy = sortOrder;
        //}

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, null);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case CLIENTES:
                return "vnd.android.cursor.dir/vnd.ar.com.preventa.provider.clientes";
            case CLIENTES_ID:
                return "vnd.android.cursor.item/vnd.ar.com.preventa.provider.clientes";
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
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

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
//            db.execSQL("CREATE TABLE books ("
//                    + BooksStore.Book._ID + " INTEGER PRIMARY KEY, "
//                    + BooksStore.Book.INTERNAL_ID + " TEXT, "
//                    + BooksStore.Book.EAN + " TEXT, "
//                    + BooksStore.Book.ISBN + " TEXT, "
//                    + BooksStore.Book.TITLE + " TEXT, "
//                    + BooksStore.Book.SORT_TITLE + " TEXT, "
//                    + BooksStore.Book.AUTHORS + " TEXT, "
//                    + BooksStore.Book.PUBLISHER + " TEXT, "
//                    + BooksStore.Book.REVIEWS + " TEXT, "
//                    + BooksStore.Book.PAGES + " INTEGER, "
//                    + BooksStore.Book.PUBLICATION + " TEXT, "
//                    + BooksStore.Book.LAST_MODIFIED + " INTEGER, "
//                    + BooksStore.Book.DETAILS_URL + " TEXT, "
//                    + BooksStore.Book.TINY_URL + " TEXT);");
//            db.execSQL("CREATE INDEX bookIndexTitle ON books(" + BooksStore.Book.SORT_TITLE + ");");
//            db.execSQL("CREATE INDEX bookIndexAuthors ON books(" + BooksStore.Book.AUTHORS + ");");
        }
        
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
//                    newVersion + ", which will destroy all old data");
//
//            db.execSQL("DROP TABLE IF EXISTS books");
//            onCreate(db);
        }
    }
}
