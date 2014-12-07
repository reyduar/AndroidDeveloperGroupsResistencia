package ar.com.iariel.game.framework;

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public interface Graficos {
	//-- Metodo enum encargado de codificar los diferentes formatos de pixles que soportaremos --//
    public static enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565
    }
    
    public Pixmap newPixmap(String fileName, PixmapFormat format);//Cargara una imagen a la que se le indique en diferentes formato (.jpeg o .png)

    public void clear(int color);//Limpia completamente el framerbuffer con el colo que se le indique, todos los colores de nuestro juego seran especificados 32bits ARGB8888

    public void drawPixel(int x, int y, int color);//Configurara el pixel en una localizacion de coordenadas x;y y con el color que se le indique

    public void drawLine(int x, int y, int x2, int y2, int color);//Configurar linea especificando las coordenadas y el color

    public void drawRect(int x, int y, int width, int height, int color);//Permitira dibujar un rectagulo en el framerbuffer, le pasamos las cooredenadas el ancho y el alto y el color

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight);//Permitira dibujar un mapa de bits

    public void drawPixmap(Pixmap pixmap, int x, int y);

    public int getWidth();//Devolvera el ancho del framerbuffer

    public int getHeight();//Devolvera el alto del framerbuffer
}
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
