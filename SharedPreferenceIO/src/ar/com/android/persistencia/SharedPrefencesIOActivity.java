package ar.com.android.persistencia;
/**
 * @author reynaldo.duarte
 * date: 11/07/2012
 */
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class SharedPrefencesIOActivity extends Activity {
private EditText editTextIO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        editTextIO = (EditText) findViewById(R.id.edittext01);
        Button btnGuardar = (Button) findViewById(R.id.btnguardar);
        Button btnMostrar = (Button) findViewById(R.id.btnmostrar);
        
        btnGuardar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String nombre = editTextIO.getText().toString();
				//Con las cuatro siguientes líneas estamos asignando 
				//al “shared preferences” llamado “settings” la propiedad 
				//de usar datos compartidos (getSharedPrefetences).
				SharedPreferences setting = getSharedPreferences("perfil", MODE_PRIVATE);
				//Estamos indicándole que vamos a editar su valor (SharedPreferences.Editor)
				SharedPreferences.Editor editor = setting.edit();
				//Y le estamos pasando el valor que queremos asignarle en forma de String (editor.putString)
				editor.putString("nombre", nombre);
				editor.commit();
				//Ya tenemos nuestro valor “shared preferences” grabado.
				editTextIO.setText("");
				//Permite ocultar el teclado virtual
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editTextIO.getWindowToken(), 0);
			}
		});
        
        btnMostrar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences setting = getSharedPreferences("perfil", MODE_PRIVATE);
				String nombre = setting.getString("nombre", "");
				Toast.makeText(SharedPrefencesIOActivity.this, nombre, Toast.LENGTH_LONG).show();
			}
		});
    }

   
    
}
/**
 * @author reynaldo.duarte
 * date: 11/07/2012
 */