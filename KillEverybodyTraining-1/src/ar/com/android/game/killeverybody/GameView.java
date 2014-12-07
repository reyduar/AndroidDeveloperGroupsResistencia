package ar.com.android.game.killeverybody;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView {

	private Bitmap bmp;
	private SurfaceHolder holder;
	 
	public GameView(Context context) {
		super(context);
		//-- Se implementa toda esta estructura para que se pueda trabajar SurfaceView --//
		//-- La SurfaceView no es una View cualquiera es de mas bajo nivel --//
		holder = getHolder();
		//-- Creammos un listener para los eventos callback del holder --//
		//-- Implementa todos los metodos de la clase Callback() --//
		holder.addCallback(new SurfaceHolder.Callback() {
			 
			//-- Metodo que no informa cuando la view es destruida --//
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
            
            //-- Metodo que no informa cuando la view es creada --//
            //-- Este se ejecuta cuando la view este lista para hacer impresiones --//
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //-- Le pide al holder que le entregue el canvas --//    
            	Canvas c = holder.lockCanvas();//Con lockCanvas reservamos lo blockeamos el canvas
                   onDraw(c);//Llamamos al metodo y le pasamos el canvas
                   holder.unlockCanvasAndPost(c);//Con unlock desbloqueamos el canvas 
            }
            //-- Metodo que no informa cuando la view tiene un cambio --//
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                          int width, int height) {
            }
     });
		
		
		//-- cargamos el recurso de la imagen --//
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ariel);
	}

	//-- Este metodo se ejecuta automaticamente por peticion de la View --//
	//-- Pide como parametro un Canvas, que es lo hace que podamos escribir lo que queremos en la pantalla --//
	@Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK); //En este caso le decimos que el fondo sea negro
        canvas.drawBitmap(bmp, 10, 20, null);//Le decimos que imprima el bitmap en la poscion 10,10 
    }
}
