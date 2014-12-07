package ar.com.android.game.killeverybody;
/**
 * @author Ariel Duarte
 */
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

//-- Todo lo relacionado a moviento y velocidad los traemos a esta clase --//
public class Sprite {
	// direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };// Direcciones x,y de las partidas de la animaciones
    private static final int BMP_ROWS = 4;//Cantidad de filas
    private static final int BMP_COLUMNS = 3;//Cantidad de columnas
    private static final int MAX_SPEED = 5;//Contante de la maxima velocidad
    private GameView gameView;
    private Bitmap bmp;
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private int currentFrame = 0;
    private int width;
    private int height;
     
	public Sprite(GameView gameView, Bitmap bmp) {
		this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameView = gameView;
        this.bmp = bmp;

        //-- Manejar las velocidades aleatoriamente de la partida no siempre van al mismo lugar todos --//
        Random rnd = new Random();
        //-- Con esto la velocidad sera 0 o 5 para x, 0 o 5 para y tanto positivo como negativo --//
        x = rnd.nextInt(gameView.getWidth() - width); //Sortea un numero de 0-10 y le resta 5, por lo cual va a dar un numero en 5 o -5
        y = rnd.nextInt(gameView.getHeight() - height);//Sortea un numero de 0-10 y le resta 5, por lo cual va a dar un numero en 5 o -5
        xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
	}

	//-- Ahora tenemos toda la logica de control de bordes en este metodo --//
	private void update() {
	//-- Controles de margenes de x,y -//
		if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0) {
			xSpeed = -xSpeed;
		}
		x = x + xSpeed;
		if (y >= gameView.getHeight() - height - ySpeed || y + ySpeed <= 0) {
			ySpeed = -ySpeed;
		}
		y = y + ySpeed;
		currentFrame = ++currentFrame % BMP_COLUMNS;//Incrementamos este campo y le mod. cantidad columnas 
      //se incrementara y luego volvera a 0 las cuales seran la posciones del grafico 
	}

	//-- En este caso nosotro mismo vamos a imprimir el canvas --//
	public void onDraw(Canvas canvas) {
		 update();
		 //-- Manejo de la impresion 
         int srcX = currentFrame * width;
         int srcY = getAnimationRow() * height;
         Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);//Del bmp fuente yo voy agarrar un rectangulo para imprimir en otro rectagulo de destino
         Rect dst = new Rect(x, y, x + width, y + height);//Calcula el destino en la pantalla donde vamos a colocarlo
         canvas.drawBitmap(bmp, src, dst, null);//Le mandamos lo que serian rectangulos al canvas el Source y el destino 
	}
	
	//-- Cortamos los bmp y hacemos que recorran un radio recuperado con la funcion Math.atan --//
	  private int getAnimationRow() {
          double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
          int direction = (int) Math.round(dirDouble) % BMP_ROWS;
          return DIRECTION_TO_ANIMATION_MAP[direction];

    }
	//--- Este metodo devuelve true si x2 y y2 estan dentro de lo que ocuparia la superficie --//
	  public boolean isCollision(float x2, float y2) {
          return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }
}
