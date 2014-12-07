package ar.com.iariel.game.framework;

import java.util.List;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public interface Input {
	//-- Definimos la clase para los eventos de teclas --//
	public static class KeyEvent {
		//-- Definimos la constantes para los eventos de las teclas --//
		public static final int KEY_DOWN = 0;
		public static final int KEY_UP = 1;

		//-- Definimos la variables para las capturas
		public int type;
		public int keyCode;
		public char keyChar;

		public String toString(){

			StringBuilder builder = new StringBuilder();
			if (type==KEY_DOWN) 
				builder.append("Tecla pulsada, ");
			else
				builder.append("Tecla levantada, ");
			builder.append(keyCode);
			builder.append(",");
			builder.append(keyChar);
			return builder.toString();
		}
	}
	
	//-- Definimos la clase para los eventos de la pantalla --//
	public static class TouchEvent {
		
		//-- Definimos las constantes --//
		public static final int TOUCH_DOWN = 0;
		public static final int TOUCH_UP = 1;
		public static final int TOUCH_DRAGEED = 2;
		
		public int type; //Tipo de evento
		public int x ,y; //Posicion del dedo
		public int pointer; //Puntero Id para el primer dedo que toca la pantalla
		
		public String toString(){
			StringBuilder builder = new StringBuilder();
			if(type == TOUCH_DOWN)
				builder.append("touch down, ");
			else if (type == TOUCH_DRAGEED)
				builder.append("touch dragged, ");
			else
				builder.append("touch up, ");
			builder.append(pointer);
			builder.append(",");
			builder.append(x);
			builder.append(y);
			return builder.toString();
			
		}
		
	}
	
	//--Metodos Poolling--//
	public boolean isKeyPressed(int keyCode); // Devuelve si la tecla presionada esta siendo procesada
	
	public boolean isTouchDown(int pointer); //Devuelve si un determinado puntero esta pulsando la pantalla
	
	public int getTouchX(int pointer); //Devuelve las cordenadas X
	
	public int getTouchY(int pointer);//Devuelve las cordenadas Y
	
	//-- Devuelve los valores respectivos de cada aceleracion de caja eje del acelerometro--//
	 
	public float getAccelX();
	
	public float getAccelY();
	
	public float getAccelZ();
	
	//--Metodos Event handler--//
	
	public List<KeyEvent> getKeyEvents();//Devuelve las instancias key events de la ultima grabacion
	
	public List<TouchEvent> getTouchEvents(); //Devuelve las instancias key events de la ultima grabacion
	
}

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */


