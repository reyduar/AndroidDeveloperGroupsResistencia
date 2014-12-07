package com.videotutoriales.juegosandroid;
import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AudioPlayerTest extends Activity implements OnClickListener{
Button playButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		playButton = (Button) findViewById(R.id.bntreproducir);
		//-- Directamente ejecutamos el listener--//
		playButton.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		//-- Creamos el intent para usamos el generico --//
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
		//-- Le indicamos cual es el archivo que tiene que ejecutar
		File sdcard = Environment.getExternalStorageDirectory();
		File audioFile = new File(sdcard.getPath()+ "/music/prelude.mp3");
		intent.setDataAndType(Uri.fromFile(audioFile), "audio/mp3");
		startActivity(intent);
	}

}
