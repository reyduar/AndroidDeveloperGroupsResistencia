package ar.com.iariel.game.framework;

import ar.com.iariel.game.framework.Graficos.PixmapFormat;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public interface Pixmap {
    public int getWidth();//Devolver el ancho del bitmap siempre en pixeles

    public int getHeight();//Devolver el ancho del bitmap siempre en pixeles

    public PixmapFormat getFormat();//No devolvera el formato en el cual es almacenado en la memoria volatil el bitmap que queremos utilzar 

    public void dispose();//Elimina los distintos graficos que estemos utilizando
}
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
