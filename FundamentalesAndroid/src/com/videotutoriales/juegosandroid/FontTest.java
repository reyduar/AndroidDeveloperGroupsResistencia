package com.videotutoriales.juegosandroid;

import com.videotutoriales.juegosandroid.ShapeTest.RenderView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class FontTest extends Activity{
	class RenderView extends View{
		Paint paint;
		Typeface font;
		Rect bounds = new Rect();
		public RenderView(Context context) {
			super(context);
			paint = new Paint();
			font = Typeface.createFromAsset(context.getAssets(), "manual.ttf");
		}
		
		protected void onDraw(Canvas canvas) {
			paint.setColor(Color.YELLOW);
			paint.setTypeface(font);
			paint.setTextSize(28);
			paint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText("Esto seria una prueba de fuente", canvas.getWidth()/2, 100, paint);
			String text = "Esto es otra prueba copada de fuente!";
			paint.setColor(Color.WHITE);
			paint.setTextSize(18);
			paint.setTextAlign(Paint.Align.LEFT);
			paint.getTextBounds(text, 0, text.length(), bounds);
			canvas.drawText(text, canvas.getWidth()-bounds.width(), 140, paint);
			invalidate();
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//-- Permite ocultar los titulos de la pantalla --//
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//-- Permite poner pantalla completa sin barra de titulo y notificaciones--//
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//--Hacemos que la clase RenderView se defina en el contexto de la aplicacion--//
		setContentView(new RenderView(this));
	}
	
	
}
