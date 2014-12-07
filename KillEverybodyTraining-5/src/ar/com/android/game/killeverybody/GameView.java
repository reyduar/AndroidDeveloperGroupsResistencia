package ar.com.android.game.killeverybody;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private List<Sprite> sprites = new ArrayList<Sprite>();

	public GameView(Context context) {
		super(context);
		gameLoopThread = new GameLoopThread(this);
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {

		//-- Metodo que se ejecuta cuando se cierra la aplicacion --//
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

		//-- Primer metodo que se ejecuta --//
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				createSprites();//Este es el momento en que la view ya esta creada podemos utilizar nuestros spirtes
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
			}

			// -- No se implementa --//
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}

		});

	}

	//-- En este metodo cargamos los sprites --// 
	private void createSprites() {
		sprites.add(createSprite(R.drawable.bad1));
		sprites.add(createSprite(R.drawable.bad2));
		sprites.add(createSprite(R.drawable.bad3));
		sprites.add(createSprite(R.drawable.bad4));
		sprites.add(createSprite(R.drawable.bad5));
		sprites.add(createSprite(R.drawable.bad6));
		sprites.add(createSprite(R.drawable.good1));
		sprites.add(createSprite(R.drawable.good2));
		sprites.add(createSprite(R.drawable.good3));
		sprites.add(createSprite(R.drawable.good4));
		sprites.add(createSprite(R.drawable.good5));
		sprites.add(createSprite(R.drawable.good6));
	}

   //-- Se crea una funcion para que permite crear el Sprite cargandolo en los recursos con BitmapFactory --//
	private Sprite createSprite(int resouce) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
		return new Sprite(this,bmp);//retorna el sprite con la referencia this que es el GameView
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		//-- Con un iterator cargamos en el canvas los sprite --//
		for (Sprite sprite : sprites) {
			sprite.onDraw(canvas); //Imprimimos nuestros sprites
		}

	}
}
