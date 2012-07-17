package com.videotutoriales.juegosandroid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;


public class ExternalStorageTest extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		setContentView(textView);
		String estado = Environment.getExternalStorageState();
		if (!estado.equals(Environment.MEDIA_MOUNTED)) {
			textView.setText("No hay almacenamiento externo montado");
		}else{
			File externalDir = Environment.getExternalStorageDirectory();
			File textFile = new File(externalDir.getAbsolutePath() + File.separator + "Archivo.txt");
			try {
				writeTextFile(textFile, "Esto es una prueba de funcionamiento del almacenamiento externo!");
				String texto = readTextFile(textFile);
				textView.setText(texto);
				if(!textFile.delete()){
					textView.setText("No se ha podido eliminar el archivo temporal");
				}
			} catch (IOException e) {
				textView.setText("Se ha producido un erro: "+e.getMessage());
			}
		}
	}

	private String readTextFile(File file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder texto = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			texto.append(line);
			texto.append("\n");
			
		}
		reader.close();
		return texto.toString();
	}

	private void writeTextFile(File file, String texto) throws IOException {
		BufferedWriter write = new BufferedWriter(new FileWriter(file));
		write.write(texto);
		write.close();
	}

}
