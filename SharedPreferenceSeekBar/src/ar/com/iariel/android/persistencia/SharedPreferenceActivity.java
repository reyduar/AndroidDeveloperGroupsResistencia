package ar.com.iariel.android.persistencia;
/**
 * @author reynaldo.duarte
 * date: 10/07/2012
 * Ejemplo de persistencia con SharedPreferences
 */
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;


public class SharedPreferenceActivity extends Activity {
	/**
	 * Definimos variables para el desarrollo del entorno
	 */
	private SharedPreferences prefs;
	private String nombrePref = "MiPref";
	private EditText editText;
	private SeekBar seekBar;
	private Button btnGuardar;
	/**
	 * Definimos constantes para el desarrollo del control de la aplicacion
	 */
	private static final String TAM_FUENTE_KEY = "tamFuente";
	private static final String VALOR_TEXTO_KEY = "valortexto";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /**
         * Definicion de objetos  
         */
        editText = (EditText) findViewById(R.id.edittext01);
        seekBar = (SeekBar) findViewById(R.id.seekbar01);
        btnGuardar = (Button) findViewById(R.id.btnguardar);
        
        /**
         * Evento del boton guardar preferencias
         */
        btnGuardar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			// ---Obtener el objeto SharedPreferences object--- //
				prefs = getSharedPreferences(nombrePref, MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
			// ---Salvar los valores en la view EditText a preferencias--- //
				editor.putFloat(TAM_FUENTE_KEY, editText.getTextSize());
				editor.putString(VALOR_TEXTO_KEY, editText.getText().toString());
			// ---Guardar las preferencias--- //
				editor.commit();
			// ---Muestra mensaje emergente con Toast--- //
				Toast.makeText(getBaseContext(), "Valores guardados correctamente!!", 
						Toast.LENGTH_SHORT).show();
			}
		});
     // --- Cargamos el objeto SharedPreferences --- //
		SharedPreferences prefs = getSharedPreferences(nombrePref, MODE_PRIVATE);
	 // --- Configurar el tamaño de fuente de TextView a los valores Salvados previamente ---//
		float fontSize = prefs.getFloat(TAM_FUENTE_KEY, 12);
	 // --- Init la SeekBar y  EditText --- //
		seekBar.setProgress((int) fontSize);
		editText.setText(prefs.getString(VALOR_TEXTO_KEY, ""));
		editText.setTextSize(seekBar.getProgress());
	
	/**
	 * Eventos para el SeekBar
	 */
		
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				editText.setTextSize(progress);
				
			}
		});
    }

   
}
/**
 * @author reynaldo.duarte
 * date: 10/07/2012
 */