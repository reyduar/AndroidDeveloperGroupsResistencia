package ar.com.android.game.killeverybody;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

	 private Bitmap bmp;
     private SurfaceHolder holder;
     private GameLoopThread gameLoopThread;//Instanciamos nuestra clase con el hilo del loop
     private int x = 0; //Es para la posicion del objeto
     private int xSpeed = 1;//Con esta variable vamos a controlar la velocidad
     
     public GameView(Context context) {
           super(context);
           gameLoopThread = new GameLoopThread(this);
           holder = getHolder();
           holder.addCallback(new SurfaceHolder.Callback() {

                  @Override
                  public void surfaceDestroyed(SurfaceHolder holder) {
                         boolean retry = true;
                         gameLoopThread.setRunning(false);
                         while (retry) {
                                try {
                                      gameLoopThread.join();
                                      retry = false;
                                } catch (InterruptedException e) {
                                }
                         }
                  }

                  @Override
                  public void surfaceCreated(SurfaceHolder holder) {
                         gameLoopThread.setRunning(true);//Le pasamos un valor True a la variable running
                         gameLoopThread.start();//Arrancamos el hilo
                  }

                  @Override
                  public void surfaceChanged(SurfaceHolder holder, int format,
                                int width, int height) {
                  }
           });
           bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ariel);
     }

     
     @Override
     protected void onDraw(Canvas canvas) {
    	// Este codigo permite llegar al icono hasta el borde y rebotar  
    	// getWidth():Borde derecho
    	// bmp.getWidth():largo del bmp
    	// getWidth() - bmp.getWidth() : el bmp toco el borde
    	 if (x == getWidth() - bmp.getWidth()) {
    		 xSpeed = -1;// Cambia de direccion
    	 }
    	 if (x == 0) {
    		 xSpeed = 1;//Si llega a 0 vuelve a hacer lo mismo
    	 }
    	 x = x + xSpeed;//Incrementamos los valores para x
    	   canvas.drawColor(Color.BLACK);
    	   
// Este codigo solo permitia llegar al icono hasta el borde y detenerse    	   
//           if (x < getWidth() - bmp.getWidth()) {
//                  x++;
//           }
           canvas.drawBitmap(bmp, x, 10, null);//Impresion del icono en la posicion
     }
}
