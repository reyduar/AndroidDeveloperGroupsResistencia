package ar.com.iariel.game.framework.implementation;

import android.media.SoundPool;
import ar.com.iariel.game.framework.Sonido;


/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */


public class AndroidSonido implements Sonido {
	
	//-- Declaracion de variables --//
	int soundId;
	SoundPool soundPool;
	
	//-- Definimos el constructor --//
	public AndroidSonido(SoundPool soundPool, int soundId) {
		super();
		this.soundId = soundId;
		this.soundPool = soundPool;
	}
	
	
	@Override
	public void play(float volumen) {
		soundPool.play(soundId, volumen, volumen, 0, 0, 1);
	}


	@Override
	public void dispose() {
		soundPool.unload(soundId);

	}

}

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
