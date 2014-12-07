package com.videotutoriales.juegosandroid;

import java.io.IOException;
import android.content.res.AssetManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.app.Activity;

public class SoundPoolTest extends Activity implements OnTouchListener{
	SoundPool soundPool;
	int miSonidoId = -1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		textView.setOnTouchListener(this);
		setContentView(textView);
		//-- Controlamos el control de volumen --//
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		//-- Configuracion del soundPool con 20 efectos de sonido --//
		soundPool = new SoundPool(20,AudioManager.STREAM_MUSIC,0);
		//-- Accedemos a un AssetFileDescritor con el metodo openFd--//
		try {
			AssetManager assetManager = getAssets();
			AssetFileDescriptor descriptor = assetManager.openFd("sound37.ogg");
			//-- Pasamos al descriptor el identificador del sonido a reproducir --//
			miSonidoId = soundPool.load(descriptor, 1);
			
			
		} catch (IOException e) {
			textView.setText("No se ha podido cargar el efecto del sonido desde asset: "+e.getMessage());
		}

	}
	
	//-- El  sonido se va a reproducir mientras se pulse en la pantalla --//
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction()==MotionEvent.ACTION_UP) {
			if (miSonidoId != -1) {
				//-- Usamos soundPool.play para reproducir el sonido --//
				soundPool.play(miSonidoId, 1, 1, 0, 0, 1);
			}
		}
		return true;
	}

}
