package ar.com.preventa;

import android.app.Application;
import android.util.Log;
import ar.com.preventa.db.DbManager;
import ar.com.preventa.db.PreventaDB;

public class PreventaApp extends Application {

	public  static final String APP_NAME = "AndroidExamples";
    private static PreventaApp  singleton;
    private DbManager dataHelper;	

	// Returns the application instance
	public static PreventaApp getInstance() {
		return singleton;
	}
    
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(APP_NAME, "APPLICATION onCreate");
		singleton = this;
		this.dataHelper = DbManager.getInstance(this, PreventaDB.DATABASE_NAME); 
	}

	@Override
	public void onTerminate() {
		Log.d(APP_NAME, "APPLICATION onTerminate");
		super.onTerminate();
	}
    
    @Override
    public final void onLowMemory() {
    	super.onLowMemory();
    }
    
    public DbManager getDataHelper() {
		return dataHelper;
	}

	public void setDataHelper(DbManager dataHelper) {
		this.dataHelper = dataHelper;
	}
}
