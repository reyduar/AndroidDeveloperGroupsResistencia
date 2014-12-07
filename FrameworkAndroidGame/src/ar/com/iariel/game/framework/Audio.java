package ar.com.iariel.game.framework;

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */

public interface Audio {
	public Musica nuevaMusica(String nombreArchivo);//Archivo de audio que se ejecuta en Streaming

	public Sonido nuevoSonido(String nombreArchivo);//Corto efecto de sonido que mantenemos en memoria
}
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */