package ar.com.android.game.killeverybody;
/**
 * @author Ariel Duarte
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

//-- Todo lo relacionado a moviento y velocidad los traemos a esta clase --//
public class Sprite {
	 private static final int BMP_ROWS = 4; //Cantidad de filas de la imagen
     private static final int BMP_COLUMNS = 3;//Cantidad de columnas de la imagen
     private int x = 0;
     private int y = 0;
     private int xSpeed = 5;
     private GameView gameView;
     private Bitmap bmp;
     private int currentFrame = 0; //Es la encargada de la animacion, guardamos en una variable cual se esta mostrando 
     private int width;
     private int height;
     
	public Sprite(GameView gameView, Bitmap bmp) {
		 this.gameView = gameView;
         this.bmp = bmp;
         //-- Aca recortamos el bmp para cada imagen --//
         this.width = bmp.getWidth() / BMP_COLUMNS; //Ancho del sprite seria el ancho del bmp divido la cantidad de columnas
         this.height = bmp.getHeight() / BMP_ROWS;//Alto del sprite seria el alto del bpm divido la cantidad
	}

	//-- Ahora tenemos toda la logica de control de bordes en este metodo --//
	private void update() {
	//-- En cargado de que camine nuestro personaje -//
	 if (x > gameView.getWidth() - width - xSpeed) {
             xSpeed = -5;
      }
      if (x + xSpeed < 0) {
             xSpeed = 5;
      }
      x = x + xSpeed;
      currentFrame = ++currentFrame % BMP_COLUMNS;//Incrementamos este campo y le mod. cantidad columnas 
      //se incrementara y luego volvera a 0 las cuales seran la posciones del grafico 
	}

	//-- En este caso nosotro mismo vamos a imprimir el canvas --//
	public void onDraw(Canvas canvas) {
		 update();
		 //-- Manejo de la impresion 
         int srcX = currentFrame * width;
         int srcY = 1 * height;
         Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);//Del bmp fuente yo voy agarrar un rectangulo para imprimir en otro rectagulo de destino
         Rect dst = new Rect(x, y, x + width, y + height);//Calcula el destino en la pantalla donde vamos a colocarlo
         canvas.drawBitmap(bmp, src, dst, null);//Le mandamos lo que serian rectangulos al canvas el Source y el destino 
	}
}
