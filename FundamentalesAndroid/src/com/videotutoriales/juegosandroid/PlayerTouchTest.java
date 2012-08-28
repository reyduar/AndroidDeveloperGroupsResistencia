package com.videotutoriales.juegosandroid;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class PlayerTouchTest extends Activity implements OnCompletionListener,
OnTouchListener, OnClickListener{

	MediaPlayer mediaPlayer;
	View theView;
	Button stopButton, startButton;
	
	int position = 0;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playertouch);
		//-- Definimos los listener de los controles --//
		stopButton = (Button) findViewById(R.id.btnparar);
		startButton = (Button) findViewById(R.id.btniniciar);
		startButton.setOnClickListener(this);
		stopButton.setOnClickListener(this);
		
		theView = this.findViewById(R.id.theview);
		theView.setOnTouchListener(this);
		
		mediaPlayer = MediaPlayer.create(this, R.raw.musica);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.start();
		
	}

	@Override
	public void onClick(View v) {
		if (v == stopButton) {
			mediaPlayer.pause();
		}else if(v == startButton){
			mediaPlayer.start();
		}
	}


	@Override
	public void onCompletion(MediaPlayer mp) {
		mediaPlayer.start();
		mediaPlayer.seekTo(position);
	}

	@Override
	public boolean onTouch(View v, MotionEvent me) {
		if(me.getAction() == MotionEvent.ACTION_MOVE){
			if (mediaPlayer.isPlaying()) {
				position = (int) (me.getX() * mediaPlayer.getDuration() / theView.getWidth());
				Log.v("SEEK ", ""+ position);
				mediaPlayer.seekTo(position);
			}
		}
		return true;
	}
}
