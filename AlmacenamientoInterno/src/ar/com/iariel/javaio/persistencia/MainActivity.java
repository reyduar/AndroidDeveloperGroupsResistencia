package ar.com.iariel.javaio.persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;


public class MainActivity extends Activity {
	private EditText editTextIO;
	private static final int LEER_TAM_BLOQUE = 100;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        editTextIO = (EditText) findViewById(R.id.edittext01);
        Button btnGuardar = (Button) findViewById(R.id.btnguardar);
        Button btnMostrar = (Button) findViewById(R.id.btnmostrar);
        
        btnGuardar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String str = editTextIO.getText().toString();
				try {
					FileOutputStream fOut = openFileOutput("archivotexto.txt", MODE_WORLD_READABLE);
					OutputStreamWriter osw = new OutputStreamWriter(fOut);
					// --Escribir el string al archivo --//
					osw.write(str);
					osw.flush();
					osw.close();
					//-- Mostar el mensaje de archvio salvado--//
					Toast.makeText(getBaseContext(), "Archivo guardado correctamente!!", Toast.LENGTH_SHORT).show();
					//-- Limpiar el editTExt --//
					editTextIO.setText("");
					//Permite ocultar el teclado virtual
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(editTextIO.getWindowToken(), 0);
				} catch (IOException ieo) {
					ieo.printStackTrace();
				}
			}
		});
        
        btnMostrar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					FileInputStream fIn = openFileInput("archivotexto.txt");
					InputStreamReader isr = new InputStreamReader(fIn);
					char[] inputBuffer = new char[LEER_TAM_BLOQUE];
					String s = "";
					int charRead;
					while ((charRead = isr.read(inputBuffer))>0) {
						// --Convetir los chars a un string --//
						String readString = String.copyValueOf(inputBuffer,0,charRead);
						s += readString;
						inputBuffer = new char[LEER_TAM_BLOQUE];
					}
					// --Asignar al editText el texto que ha sido leido-- //
					editTextIO.setText(s);
					Toast.makeText(getBaseContext(), "Archivo cargado correctamente", Toast.LENGTH_SHORT).show();
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
    }

   

    
}
