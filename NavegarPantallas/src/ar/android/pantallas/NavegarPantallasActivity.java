package ar.android.pantallas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NavegarPantallasActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        callInicio();
        

    }
    
    public void callRegistro() {
        setContentView(R.layout.registro);
        Button btVolverRegistro = (Button) findViewById(R.id.btnvolverregistro);
        	btVolverRegistro.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				callInicio();
				
			}
		});
    }
//    
//    
    public void callConsulta() {
        setContentView(R.layout.consulta);
        Button btVolverConsulta = (Button) findViewById(R.id.btnvolverconsulta);
        btVolverConsulta.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				callInicio();
				
			}
		});
        
    }
    
    public void callInicio() {
        setContentView(R.layout.main);
        
        Button btRegistro = (Button) findViewById(R.id.btnregistro);
        Button btConsulta = (Button) findViewById(R.id.btnconsulta);
       
         btRegistro.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				callRegistro();
			}
		});
        
        btConsulta.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				callConsulta();
				
			}
		});
    }
}