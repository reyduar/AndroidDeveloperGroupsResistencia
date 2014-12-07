package ar.com.iariel.game.framework.implementation;

import android.graphics.Bitmap;
import ar.com.iariel.game.framework.Pixmap;
import ar.com.iariel.game.framework.Graficos.PixmapFormat;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public class AndroidPixmap implements Pixmap{
	   
		//-- Definicion de obejtos que implementamos en la clase--// 
		Bitmap bitmap;
	    PixmapFormat format;
	    
	  //-- Definicion del constructor --//
	    public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
	        this.bitmap = bitmap;
	        this.format = format;
	    }
	    //-- Metodo para consultar el ancho --//
	    @Override
	    public int getWidth() {
	        return bitmap.getWidth();
	    }
	    
	    //-- Metodo para consultar el alto --//
	    @Override
	    public int getHeight() {
	        return bitmap.getHeight();
	    }

	    @Override
	    public PixmapFormat getFormat() {
	        return format;
	    }

	    @Override
	    public void dispose() {
	        bitmap.recycle();//Que los pixeles puedan obtenerse desde el almacenamiento de la memoria volatil
	    }  
}

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
