package com.videotutoriales.juegosandroid;

import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class RenderViewTest extends Activity{
	class RenderView extends View{
		Random rand = new Random();
		Bitmap ico;
		//-- Constructor
		public RenderView(Context ctx) {
			super(ctx);
			ico = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
		}

		protected void onDraw(Canvas canvas) {
			canvas.drawRGB(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
			canvas.drawBitmap(ico, 50, 100, null);
			invalidate(); //Le decimos que lo este renderizando la cantidad de veces como se posible
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//-- Permite ocultar los titulos de la pantalla --//
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//-- Permite poner pantalla completa sin barra de titulo y notificaciones--//
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//--Hacemos que la clase RenderView se defina en el contexto de la aplicacion--//
		setContentView(new RenderView(this));
	}
}
