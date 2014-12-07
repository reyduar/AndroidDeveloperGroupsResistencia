package ar.com.iariel.game.framework.implementation;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;
import ar.com.iariel.game.framework.Input;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public class AndroidInput implements Input{
	//-- DEfinicion de obejtos que implementamos en la clase--// 
	AccelerometerHandler accelHandler;
	 KeyboardHandler keyHandler;
	 TouchHandler touchHandler;
	 //-- Definicion del constructor --//
	 public AndroidInput(Context context, View view, float scaleX, float scaleY) {
	        accelHandler = new AccelerometerHandler(context);
	        keyHandler = new KeyboardHandler(view);               
	        //-- Para saber que tocuh handler usar comprobamos la version
	        if(Integer.parseInt(VERSION.SDK) < 5) //como nos devuelve un string lo pasamos a entero para llevar a cabo la comprobacion
	            touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
	        else
	            touchHandler = new MultiTouchHandler(view, scaleX, scaleY);//Apartir de la version 2.0 se uso el multitouch corresponde a la 5  
	    }
	 

	 @Override
	    public boolean isKeyPressed(int keyCode) {
	        return keyHandler.isKeyPressed(keyCode);
	    }

	 @Override
	    public boolean isTouchDown(int pointer) {
	        return touchHandler.isTouchDown(pointer);
	    }

	 @Override
	    public int getTouchX(int pointer) {
	        return touchHandler.getTouchX(pointer);
	    }

	 @Override
	    public int getTouchY(int pointer) {
	        return touchHandler.getTouchY(pointer);
	    }

	 @Override
	    public float getAccelX() {
	        return accelHandler.getAccelX();
	    }

	 @Override
	    public float getAccelY() {
	        return accelHandler.getAccelY();
	    }

	 @Override
	    public float getAccelZ() {
	        return accelHandler.getAccelZ();
	    }

	 @Override
	    public List<TouchEvent> getTouchEvents() {
	        return touchHandler.getTouchEvents();
	    }
	 
	 @Override
	    public List<KeyEvent> getKeyEvents() {
	        return keyHandler.getKeyEvents();
	    }

}

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
