package ar.com.iariel.game.framework;

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */

public interface Musica {

	public void play(); //Empecezar a ejecutar el Stream de sonido

	public void stop(); //Para parar el Stream de sonido

	public void pause(); //Para pausar el Stream de sonido

	public void setLooping(boolean looping); //Para ejecutar el Stream de sonido con un loop cuando termina vuelve a empezar

	public void setVolumen(float volumen); //Para controlar el volumen en un rango de 0 y 1

	public boolean isPlaying();// Pemite consultar el estado actual de la instancia musica is esta play

	public boolean isStoppend();// Pemite consultar el estado actual de la instancia musica is esta stop

	public boolean isLooping();//// Pemite consultar el estado actual de la instancia musica is esta looping

	public void dispose(); //Cierra todos los recursos de audio que se esten utilizando y elimina la instancia
}

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
