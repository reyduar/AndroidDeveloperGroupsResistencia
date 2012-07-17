package com.videotutoriales.juegosandroid;

import android.app.Activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

public class AssetsTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		setContentView(textView);
		AssetManager assetManager = getAssets();
		InputStream inputStream = null;
		try {
			inputStream = assetManager.open("canciones/power.txt");
			String text = cargaArchivoTexto(inputStream);
			textView.setText(text);
		} catch (IOException e) {
			textView.setText("No se puede carga el archivo "+e.getMessage());
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (Exception e2) {
					textView.setText("No se puede cerrar el archivo");
				}
		}
	}

	private String cargaArchivoTexto(InputStream inputStream)throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		byte[] bytes = new byte[4096];
		int len = 0;
		while ((len = inputStream.read(bytes)) > 0)
			byteStream.write(bytes, 0, len);
		return new String(byteStream.toByteArray(), "UTF8");
	}

}
