package ar.com.iariel.game.framework.implementation;

import java.util.ArrayList;
import java.util.List;


import android.view.View;
import android.view.View.OnKeyListener;
import ar.com.iariel.game.framework.Pool;
import ar.com.iariel.game.framework.Input.KeyEvent;
import ar.com.iariel.game.framework.Pool.PoolObjectFactory;

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public class KeyboardHandler implements OnKeyListener {
	// -- Definicion de variables y obejtos --//
	boolean[] pressedKeys = new boolean[128];// Permitira analizar el estado actual de pulsado o no de cada una de las teclas
	Pool<KeyEvent> keyEventPool;// Va a contener instancias de nuestra Key Event para reciclarlo
	List<KeyEvent> keyEventsBuffer = new ArrayList<KeyEvent>();// Permite almacenar los KeyEvent que aun no hayan sido consumido por											// nuestro juego
	List<KeyEvent> keyEvents = new ArrayList<KeyEvent>();// Permite devolver los KeyEvent

	// -- Definicion el constructor el cual tiene un parametro View que nos va a
	// -- indicar la vista de donde queremos recibir un evento de Tecla --//
	public KeyboardHandler(View view) {
		PoolObjectFactory<KeyEvent> factory = new PoolObjectFactory<KeyEvent>() {

			@Override
			public KeyEvent createObject() {
				return new KeyEvent();
			}
		};
		keyEventPool = new Pool<KeyEvent>(factory, 100);//Creamos una instancia pool
		view.setOnKeyListener(this);
		view.setFocusableInTouchMode(true);
		view.requestFocus();//Le damos el foco a la view
	}

	@Override
	public boolean onKey(View v, int keyCode, android.view.KeyEvent event) {
		//-- Ignoramos cualquier evento de android que codifique eventos de accion multiple --// 
		if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE)
			return false;
		//-- Se sincroniza los eventos del teclado por que no queremos que funcione en paralelo --//
		synchronized (this) {
			KeyEvent keyEvent = keyEventPool.newObject();//Recojemos una instancia desde el pool puede que sea una instancia resiclada o una nueva
			keyEvent.keyCode = keyCode;
			keyEvent.keyChar = (char) event.getUnicodeChar();
			if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
				keyEvent.type = KeyEvent.KEY_DOWN;
				if (keyCode > 0 && keyCode < 127)
					pressedKeys[keyCode] = true;
			}
			if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
				keyEvent.type = KeyEvent.KEY_UP;
				if (keyCode > 0 && keyCode < 127)
					pressedKeys[keyCode] = false;
			}
			keyEventsBuffer.add(keyEvent);//Le terminamos añadiendo el evento de tecla a la lista de de Key Events Buffer  
		}
		return false;
	}
	//-- Implementamos el metodo isKeyPressed  le pasamos un entero especificando el keyCode 
	//-- y nos devuelve si esta tecla esta siendo pulsada o no
	//-- de este modo comprobamos que teclas estan siendo pulsadas en cada momento
	public boolean isKeyPressed(int keyCode) 
	{
		if (keyCode < 0 || keyCode > 127)
			return false;
		return pressedKeys[keyCode];
	}
	
	//-- Implementamos el metodo getKeyEvents
	//-- Devuelve las instancias Key Events de la ultima grabacion
	public List<KeyEvent> getKeyEvents() 
	{
		//-- Creamos una estructura sincrinizada porque este metodo sera ejecuda desde un hilo diferente --//
		synchronized (this) 
		{
			int len = keyEvents.size();
			//--  Se recorre el el array keyEvents
			for (int i = 0; i < len; i++)
				keyEventPool.free(keyEvents.get(i));
			
			keyEvents.clear();
			keyEvents.addAll(keyEventsBuffer);// Guardamos los eventos en el array keyEvents para que no se pierda
			keyEventsBuffer.clear();// Limpiamos el array keyEventsBuffer
			return keyEvents;
		}
	}
}
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */