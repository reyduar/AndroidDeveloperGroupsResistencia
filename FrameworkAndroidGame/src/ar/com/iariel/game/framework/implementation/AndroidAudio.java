package ar.com.iariel.game.framework.implementation;



import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import ar.com.iariel.game.framework.Audio;
import ar.com.iariel.game.framework.Musica;
import ar.com.iariel.game.framework.Sonido;

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */

public class AndroidAudio implements Audio {

	//-- Definicion de variables--//
	AssetManager assets; //No permite cargar efectos del sonido dentro del soudpool
	SoundPool soundPool;
	
	//-- Definicion del constructor --//
	
	public AndroidAudio(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		//-- Indica cuantos sonidos como MÁXIMO queremos reproducir al mismo tiempo --//
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC,0);
	}
	
	@Override
	public Musica nuevaMusica(String filename) {
		try {
			//-- Para cargar el efecto de sonido a memoria se usa pasandole un AssetFileDescriptor --//
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			return new AndroidMusica(assetDescriptor);
		} catch (IOException e) {
			throw new RuntimeException("No se ha podido Cargar el archivo '" +filename+ "'");
		}
	}


	@Override
	public Sonido nuevoSonido(String filename) {
		
		try {
			//-- Para cargar el efecto de sonido a memoria se usa pasandole un AssetFileDescriptor --//
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			int soundId = soundPool.load(assetDescriptor, 0);
			return new AndroidSonido(soundPool, soundId);
		} catch (Exception e) {
			throw new RuntimeException("No se ha podido Cargar el archivo '" +filename+ "'");
		}
	}

}


/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */