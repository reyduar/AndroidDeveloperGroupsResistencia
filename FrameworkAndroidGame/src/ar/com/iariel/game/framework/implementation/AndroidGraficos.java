package ar.com.iariel.game.framework.implementation;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import ar.com.iariel.game.framework.Graficos;
import ar.com.iariel.game.framework.Pixmap;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public class AndroidGraficos implements Graficos{
	    //-- Definicion de obejtos que implementamos en la clase--//
		AssetManager assets;//Se utilizara para cargar instancias bitmap 
	    Bitmap frameBuffer;//Un miembro bitmap que representa nuestro frame buffer artificial
	    Canvas canvas;//Utilizaremos para dibujar ese frame buffer artificial
	    Paint paint;//Utilizaremos para dibujar
	    Rect srcRect = new Rect();//Utilizaremos para implementar los metodos draw bitmap
	    Rect dstRect = new Rect();//Utilizaremos para implementar los metodos draw bitmap

	    //-- Definicion del constructor --//
	    public AndroidGraficos(AssetManager assets, Bitmap frameBuffer) {
	        this.assets = assets;
	        this.frameBuffer = frameBuffer;
	        this.canvas = new Canvas(frameBuffer);
	        this.paint = new Paint();
	    }

	    //-- Intenta cargar un bitmap desde un archivo assets usando el PixmapFormat para el formato que se le halla especificado--//
	    @Override
	    public Pixmap newPixmap(String fileName, PixmapFormat format) {
	        Config config = null;
	        //-- Aqui convertimos el formato a una de las constantes de la clase android config--//
	        if (format == PixmapFormat.RGB565)
	            config = Config.RGB_565;
	        else if (format == PixmapFormat.ARGB4444)
	            config = Config.ARGB_4444;
	        else
	            config = Config.ARGB_8888;

	        Options options = new Options();
	        options.inPreferredConfig = config;// Configuramos nuestro formato de colo preferido

	        InputStream in = null;
	        Bitmap bitmap = null;
	        try {
	        	//-- Tratamos de cargar el bitmap desde el assets atravez de BitmapFactory
	            in = assets.open(fileName);
	            bitmap = BitmapFactory.decodeStream(in);
	            if (bitmap == null)
	                throw new RuntimeException("No se ha podido cargar bitmap desde asset '"
	                        + fileName + "'");
	        } catch (IOException e) {
	            throw new RuntimeException("No se ha podido cargar bitmap desde asset '"
	                    + fileName + "'");
	        } finally {
	            if (in != null) {
	                try {
	                    in.close();
	                } catch (IOException e) {
	                }
	            }
	        }
	        //-- Se vuelve a reslizar una condicional para comprobar 
	        if (bitmap.getConfig() == Config.RGB_565)
	            format = PixmapFormat.RGB565;
	        else if (bitmap.getConfig() == Config.ARGB_4444)
	            format = PixmapFormat.ARGB4444;
	        else
	            format = PixmapFormat.ARGB8888;

	        return new AndroidPixmap(bitmap, format);//Construimos una nueva instancia basado en el bitmap y en el formato configurado
	    }

	    //-- Lo que hace es extraer los componentes rojo, verde y azul del parametro del color ARGB de 32bit que se ha especificado --//
	    @Override
	    public void clear(int color) {
	        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
	                (color & 0xff));//limpiara nuestro frame buffer artificial con ese color que se le indique 
	    }

	    //-- Lo que hace es dibujar un pixel en nuestro frame buffer artificial atravez del metodo canvas drawPoint --//
	    @Override
	    public void drawPixel(int x, int y, int color) {
	        paint.setColor(color);//Primero configuramos el color de nuestra variable 
	        canvas.drawPoint(x, y, paint);//Se lo pasamos al metodo con el cual vamos a dibujar, ademas del color le pasamos las coordendas de ese pixel
	    }

	    //-- Va a dibijar la linea que le indiquemos en el frame buffer artificial --//
	    @Override
	    public void drawLine(int x, int y, int x2, int y2, int color) {
	        paint.setColor(color);//Primero configuramos el color de nuestra variable 
	        canvas.drawLine(x, y, x2, y2, paint);//Se lo pasamos al metodo con el cual vamos a dibujar, ademas del color le pasamos las coordendas de ese pixel
	    }

	    //-- Va a dibijar el rectangulo que le indiquemos en el frame buffer artificial --//
	    @Override
	    public void drawRect(int x, int y, int width, int height, int color) {
	        paint.setColor(color);//Primero configuramos el color de nuestra variable 
	        paint.setStyle(Style.FILL);//Le pasamos los atributos para poder dibujar un rectangulo relleno y con color
	        canvas.drawRect(x, y, x + width - 1, y + width - 1, paint);//Coordenada para las esquina del rectangulo, formula que no permite dibujar rectangulos en nuestro canvas
	    }

	    //-- Permite dibujar una porsion de un bitmap --// 
	    @Override
	    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
	            int srcWidth, int srcHeight) {
	        //-- Pirmero configuramos la fuente de los miembros Rect--//
	    	srcRect.left = srcX;
	        srcRect.top = srcY;
	        srcRect.right = srcX + srcWidth - 1;
	        srcRect.bottom = srcY + srcHeight - 1;
	        //-- Configuramos el destino de los miembros Rect--//
	        dstRect.left = x;
	        dstRect.top = y;
	        dstRect.right = x + srcWidth - 1;
	        dstRect.bottom = y + srcHeight - 1;

	        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect,
	                null);//Con llevamos a cabo el dibujo real con el metodo canvas
	    }
	    
	    //-- Permite dibujar el Pixmap en framerbuffer  --// 
	    @Override
	    public void drawPixmap(Pixmap pixmap, int x, int y) {
	        canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);
	    }
	   //-- Devolvera el ancho del framerbuffer --//
	    @Override
	    public int getWidth() {
	        return frameBuffer.getWidth();
	    }
	    
	  //-- Devolvera el alto del framerbuffer --//
	    @Override
	    public int getHeight() {
	        return frameBuffer.getHeight();
	    }
}

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
