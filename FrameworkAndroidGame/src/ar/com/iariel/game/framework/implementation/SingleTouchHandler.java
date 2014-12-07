package ar.com.iariel.game.framework.implementation;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;
import ar.com.iariel.game.framework.Pool;
import ar.com.iariel.game.framework.Input.TouchEvent;
import ar.com.iariel.game.framework.Pool.PoolObjectFactory;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public class SingleTouchHandler implements TouchHandler{
	//--DEfinimos las variable y objetos --//
	boolean isTouched; //Permite almacena el estado actual del touch
	int touchX; //Permite almacena la cordenada X
	int touchY; //Permite almacena la cordenada Y
	Pool<TouchEvent> touchEventPool; //Pool que reciclar las instancias de las referencias
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>(); //Listas que contienen los eventos del touch
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>(); //Listas Buffer para almacenar eventos touch anteriores
	float scaleX;//No van ayudar para el tramiento de las resuluciones de la pantalla
	float scaleY;

	//-- Definimos constructor --//
	public SingleTouchHandler(View view, float scaleX, float scaleY) {
        PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }            
        };
        touchEventPool = new Pool<TouchEvent>(factory, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
	
	 @Override
	    public boolean onTouch(View v, MotionEvent event) {
	        synchronized(this) {
	            TouchEvent touchEvent = touchEventPool.newObject();
	            switch (event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	                touchEvent.type = TouchEvent.TOUCH_DOWN;
	                isTouched = true;
	                break;
	            case MotionEvent.ACTION_MOVE:
	                touchEvent.type = TouchEvent.TOUCH_DRAGEED;
	                isTouched = true;
	                break;
	            case MotionEvent.ACTION_CANCEL:                
	            case MotionEvent.ACTION_UP:
	                touchEvent.type = TouchEvent.TOUCH_UP;
	                isTouched = false;
	                break;
	            }
	            
	            touchEvent.x = touchX = (int)(event.getX() * scaleX);
	            touchEvent.y = touchY = (int)(event.getY() * scaleY);
	            touchEventsBuffer.add(touchEvent);                        
	            
	            return true;
	        }
	    }


	 @Override
	    public boolean isTouchDown(int pointer) {
	        synchronized(this) {
	            if(pointer == 0)
	                return isTouched;
	            else
	                return false;
	        }
	    }

	 @Override
	    public int getTouchX(int pointer) {
	        synchronized(this) {
	            return touchX;
	        }
	    }

	 @Override
	    public int getTouchY(int pointer) {
	        synchronized(this) {
	            return touchY;
	        }
	    }

	 @Override
	    public List<TouchEvent> getTouchEvents() {
	        synchronized(this) {     
	            int len = touchEvents.size();
	            for( int i = 0; i < len; i++ )
	                touchEventPool.free(touchEvents.get(i));
	            touchEvents.clear();
	            touchEvents.addAll(touchEventsBuffer);
	            touchEventsBuffer.clear();
	            return touchEvents;
	        }
	    }
}
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
