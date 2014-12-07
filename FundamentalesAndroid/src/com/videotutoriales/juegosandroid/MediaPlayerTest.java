package com.videotutoriales.juegosandroid;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;

public class MediaPlayerTest extends Activity implements OnCompletionListener{

	MediaPlayer mediaPlayer;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}


	@Override
	public void onCompletion(MediaPlayer mp) {
		mediaPlayer.start();
	}


	@Override
	protected void onStart() {
		super.onStart();
		//-- Le pasamos el URI para determinar el recurso a reproducir --//
		mediaPlayer = MediaPlayer.create(this, R.raw.musica);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.start();
	}


	@Override
	protected void onStop() {
		super.onStop();
		mediaPlayer.stop();
		mediaPlayer.release();
	}
	
	
	
}
