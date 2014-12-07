package ar.com.android.game.killeverybody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameView extends SurfaceView {
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private List<TempSprite> temps = new ArrayList<TempSprite>();//Lista de sprites temporales
	private long lastClick; //Para demoras en el click
	private Bitmap bmpBlood, bmpMurder;//Mancha de sangre
	private SoundManager soundManager;
	public HashMap hashMap = new HashMap();
	public boolean fin = false;
	private Bundle savedInstanceState;
	public GameView(Context context) {
		super(context);
		this.soundManager = new SoundManager(context);
		gameLoopThread = new GameLoopThread(this);
		getHolder().addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while (retry) {
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {}
				}
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				createSprites();
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}
		});

		//-- creamos un nuevo BMP para la imagen temporal de la sangre -- //
		bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
		bmpMurder = BitmapFactory.decodeResource(getResources(), R.drawable.murder);
	}

	//-- En este metodo cargamos los sprites --// 
	private void createSprites() {
		sprites.add(createSprite(R.drawable.bad1));
		sprites.add(createSprite(R.drawable.bad2));
		sprites.add(createSprite(R.drawable.bad3));
		sprites.add(createSprite(R.drawable.bad4));
		sprites.add(createSprite(R.drawable.bad5));
		sprites.add(createSprite(R.drawable.bad6));
		sprites.add(createSprite(R.drawable.bad7));
		sprites.add(createSprite(R.drawable.bad8));
		sprites.add(createSprite(R.drawable.bad9));
		sprites.add(createSprite(R.drawable.good1));
		sprites.add(createSprite(R.drawable.good2));
		sprites.add(createSprite(R.drawable.good3));
		sprites.add(createSprite(R.drawable.good4));
		sprites.add(createSprite(R.drawable.good5));
		sprites.add(createSprite(R.drawable.good6));
		sprites.add(createSprite(R.drawable.good7));
		sprites.add(createSprite(R.drawable.good8));
		sprites.add(createSprite(R.drawable.good9));
		
		hashMap.put("bad1", sprites.get(0));
		hashMap.put("bad2", sprites.get(1));
		hashMap.put("bad3", sprites.get(2));
		hashMap.put("bad4", sprites.get(3));
		hashMap.put("bad5", sprites.get(4));
		hashMap.put("bad6", sprites.get(5));
		hashMap.put("bad7", sprites.get(6));
		hashMap.put("bad8", sprites.get(7));
		hashMap.put("bad9", sprites.get(8));
		hashMap.put("good1", sprites.get(9));
		hashMap.put("good2", sprites.get(10));
		hashMap.put("good3", sprites.get(11));
		hashMap.put("good4", sprites.get(12));
		hashMap.put("good5", sprites.get(13));
		hashMap.put("good6", sprites.get(14));
		hashMap.put("good7", sprites.get(15));
		hashMap.put("good8", sprites.get(16));
		hashMap.put("good9", sprites.get(17));
		
	}

	//-- Se crea una funcion para que permite crear el Sprite cargandolo en los recursos con BitmapFactory --//
	private Sprite createSprite(int resouce) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
		return new Sprite(this,bmp);//retorna el sprite con la referencia this que es el GameView
	}

	//-- Imprirmir los Sprites
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		//-- Con un iterator cargamos en el canvas los sprite --//
		for (int i = temps.size() - 1; i >= 0; i--) {
			temps.get(i).onDraw(canvas);//Imprimime la mancha de sangre
		}
		for (Sprite sprite : sprites) {
			sprite.onDraw(canvas);//Va imprimiendo los personajes
		}
		if(fin == true){
			canvas.drawBitmap(bmpMurder, 50, 100, null);//Le decimos que imprima el bitmap en la poscion 10,10
			canvas.drawBitmap(bmpBlood, 20, 120, null);//Le decimos que imprima el bitmap en la poscion 10,10
			canvas.drawBitmap(bmpBlood, 160, 150, null);//Le decimos que imprima el bitmap en la poscion 10,10
		}

	}

	//-- Evento touch para la pantalla --//
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (getHolder()) {

			if (System.currentTimeMillis() - lastClick > 500) {
				lastClick = System.currentTimeMillis();
				float x = event.getX();
				float y = event.getY();

				for (int i = sprites.size() - 1; i >= 0; i--) {
					
					Sprite sprite = sprites.get(i);
					if (sprite.isCollision(x, y)) {
//						System.out.println("Valor del sprite: "+sprites.get(i));
//						System.out.println("Valor del map: "+hashMap.get("bad1"));
						
						if(sprites.get(i).equals(hashMap.get("bad1"))) this.soundManager.play(2);
						if(sprites.get(i).equals(hashMap.get("bad2"))) this.soundManager.play(2);
						if(sprites.get(i).equals(hashMap.get("bad3"))) this.soundManager.play(2);
						if(sprites.get(i).equals(hashMap.get("bad4"))) this.soundManager.play(2);
						if(sprites.get(i).equals(hashMap.get("bad5"))) this.soundManager.play(2);
						if(sprites.get(i).equals(hashMap.get("bad6"))) this.soundManager.play(2);
						if(sprites.get(i).equals(hashMap.get("bad7"))) this.soundManager.play(2);
						if(sprites.get(i).equals(hashMap.get("bad8"))) this.soundManager.play(2);
						if(sprites.get(i).equals(hashMap.get("bad9"))) this.soundManager.play(2);
						
						if(sprites.get(i).equals(hashMap.get("good1"))) this.soundManager.play(3);
						if(sprites.get(i).equals(hashMap.get("good2"))) this.soundManager.play(3);
						if(sprites.get(i).equals(hashMap.get("good3"))) this.soundManager.play(3);
						if(sprites.get(i).equals(hashMap.get("good4"))) this.soundManager.play(3);
						if(sprites.get(i).equals(hashMap.get("good5"))) this.soundManager.play(3);
						if(sprites.get(i).equals(hashMap.get("good6"))) this.soundManager.play(3);
						if(sprites.get(i).equals(hashMap.get("good7"))) this.soundManager.play(3);
						if(sprites.get(i).equals(hashMap.get("good8"))) this.soundManager.play(3);
						if(sprites.get(i).equals(hashMap.get("good9"))) this.soundManager.play(3);
														
						sprites.remove(sprite);// Removemos el sprite
						temps.add(new TempSprite(temps, this, x, y, bmpBlood));
						
						
						if (sprites.isEmpty())
							fin = true;
							
						
						break;
					}
					
					if (!sprite.isCollision(x, y)) 
						this.soundManager.play(1);
				}
			}
			return true;
		}
	}
}