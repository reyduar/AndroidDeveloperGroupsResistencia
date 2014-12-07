package ar.com.iariel.game.framework.implementation;

import java.util.List;

import android.view.View.OnTouchListener;
import ar.com.iariel.game.framework.Input;
import ar.com.iariel.game.framework.Input.TouchEvent;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public interface TouchHandler extends OnTouchListener {
	//-- Los tres metodo son para hacer un polling de un puntero especifico --//
	public boolean isTouchDown(int pointer);

	public int getTouchX(int pointer);

	public int getTouchY(int pointer);

	//--Este metodo nos permite manejar input basados en eventos 
	public List<TouchEvent> getTouchEvents();
}
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
