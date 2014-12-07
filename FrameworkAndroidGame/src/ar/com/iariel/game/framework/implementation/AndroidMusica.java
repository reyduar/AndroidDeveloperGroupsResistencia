package ar.com.iariel.game.framework.implementation;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import ar.com.iariel.game.framework.Musica;


/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */

public class AndroidMusica implements Musica, OnCompletionListener {

	
	//-- Declaracion de variables --//
	MediaPlayer mediaPlayer;
	boolean isPrepared = false; //No permite saber el estado del reproductor media player
	
	//-- Definimos el constructor --//
	public AndroidMusica(AssetFileDescriptor assetFileDescriptor) {
		mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
					assetFileDescriptor.getStartOffset(),
					assetFileDescriptor.getLength());
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
		} catch (Exception e) {
			throw new RuntimeException("No se puedo cargar la musica");
		}
	}
	
	@Override
	public void onCompletion(MediaPlayer player) {
		synchronized (this) {
			isPrepared = false;
		}
	}


	@Override
	public void play() {
		if(mediaPlayer.isPlaying())
			return;
		try {
			synchronized (this){
				if(!isPrepared)
					mediaPlayer.prepare();
				mediaPlayer.start();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		mediaPlayer.stop();
		synchronized (this) {
			isPrepared = false;
		}
	}

	@Override
	public void pause() {
		if(mediaPlayer.isPlaying())
			mediaPlayer.pause();

	}

	@Override
	public void setLooping(boolean isLooping) {
		mediaPlayer.setLooping(isLooping);
	}

	@Override
	public void setVolumen(float volumen) {
		mediaPlayer.setVolume(volumen, volumen);
	}

	@Override
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	@Override
	public boolean isStoppend() {
		return !isPrepared;
	}

	@Override
	public boolean isLooping() {
		return mediaPlayer.isLooping();
	}

	@Override
	public void dispose() {
		if(mediaPlayer.isPlaying())
			mediaPlayer.stop();
		mediaPlayer.release();

	}

}

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */