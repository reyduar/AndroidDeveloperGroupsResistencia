package ar.com.iariel.game.marco.implementacion;

import java.util.List;

import android.view.View.OnTouchListener;
import ar.com.iariel.game.marco.Input;
import ar.com.iariel.game.marco.Input.TouchEvent;

public interface TouchHandler extends OnTouchListener {
	//-- Los tres metodo son para hacer un polling de un puntero especifico --//
	public boolean isTouchDown(int pointer);

	public int getTouchX(int pointer);

	public int getTouchY(int pointer);

	//--Este metodo nos permite manejar input basados en eventos 
	public List<TouchEvent> getTouchEvents();
}
