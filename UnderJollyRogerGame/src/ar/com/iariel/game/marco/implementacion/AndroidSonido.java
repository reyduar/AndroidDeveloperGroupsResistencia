package ar.com.iariel.game.marco.implementacion;

import android.media.SoundPool;
import ar.com.iariel.game.marco.Sonido;


/**
 * @author Ariel Duarte Date: 22/07/2012
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
 * @author Ariel Duarte Date: 22/07/2012
 */
