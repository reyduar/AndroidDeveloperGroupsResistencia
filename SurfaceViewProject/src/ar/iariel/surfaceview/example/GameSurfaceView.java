package ar.iariel.surfaceview.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	
	private Bitmap bmp;
	private int width, height;
	private GameEngine gameEngineThread;
	private SurfaceHolder holder;
	private int x = 0;
	private int controlAddress;
	public GameSurfaceView(Context context) {
		super(context);
		gameEngineThread = new GameEngine(this);
		holder = getHolder();
		holder.addCallback(this);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.car);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		gameEngineThread.setRunning(true);
		gameEngineThread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDraw(Canvas canvas) {
	//Direccion unica choca al llegar
//	
//		 if (x < getWidth() - bmp.getWidth()) {
//             x++;
//      }
		 
	//Direccion de ida y vuelta rebota al llegar 	 
		if (x == getWidth() - bmp.getWidth()) {
			controlAddress = -1;// Cambia de direccion izquierda
		}
		if (x == 0) {
			controlAddress = 1;// Cambia de direccion derecha
		}
		x = x + controlAddress;
		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(bmp, x, 10, null);
	}
	
	

}
