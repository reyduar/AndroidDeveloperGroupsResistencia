package ar.com.preventa.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ar.com.preventa.R;
import ar.com.preventa.db.Db4oHelper;

public class MenuActivity extends Activity{
	
	private Db4oHelper db4oHelper = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.menu_activity);
		dbHelper();  		
	}
	
	private Db4oHelper dbHelper() {
		if (db4oHelper == null) {
			db4oHelper = new Db4oHelper(this);
			db4oHelper.db();
		}
		return db4oHelper;
	} 
	
	public void ClickHandler(View target) {
        // Do stuff
        switch (target.getId()) {
            case R.id.clientes_main_screen: // doStuff
                Intent actionIntent = new Intent( this, ClientesDb4oActivity.class );
                startActivity( actionIntent );
                break;
            case R.id.productos: // doStuff
                break;
            case R.id.sync:
            	//db4oHelper.storePilotAndCarToDb4o();
            	//dbHelper().storePersons();
            	dbHelper().replicate();
                break;
            default:
            	break;
        }
    }
    
    @Override
    	protected void onPause() {
    	super.onDestroy();
    	dbHelper().close();
    	db4oHelper = null;
    }
}
