package ar.com.adroidbasico.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.NavUtils;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        //-- Para llenar con datos el dropdown --//
        Spinner paisesSpinner = (Spinner) findViewById(R.id.sp_pais);
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.paises, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paisesSpinner.setAdapter(adaptador) ;
        
        //-- Evento de botones --//
        Button btnImprimir = (Button) findViewById(R.id.boton_imprimir);
        btnImprimir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView txtResut = (TextView) findViewById(R.id.txt_result);
				EditText edIngreso = (EditText) findViewById(R.id.et_ingreso);
				txtResut.setText(edIngreso.getText());
				quitarTeclado(edIngreso);
			}

			private void quitarTeclado(View v) {
				InputMethodManager teclado = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //Representa el servicio que maneja el teclado virtual
				teclado.hideSoftInputFromWindow(v.getWindowToken(), 0); //getWindowToken() recibe la ventana a la que view esta asociado
			}
		});
        
        Button btnAbout = (Button) findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(new OnClickListener() {
			
			//-- Abrir una nueva actividad --//
        	@Override
			public void onClick(View v) {
				Intent about = new Intent(MainActivity.this, AboutActivity.class);
				startActivity(about);
				
			}
		});//--Fin de onClick btnAbout
    
        Button btnList = (Button) findViewById(R.id.btn_list);
        btnList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent list = new Intent(MainActivity.this, ListActivity.class);
				startActivity(list);
				
			}
		});//--Fin de onClick btnList
    
    }//-- Fin de onCreate

}//-- Fin de clase
