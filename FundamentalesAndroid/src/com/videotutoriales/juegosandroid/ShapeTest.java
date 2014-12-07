package com.videotutoriales.juegosandroid;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ShapeTest extends Activity{
	class RenderView extends View{
		Paint paint;
		public RenderView(Context context) {
			super(context);
			paint = new Paint();
		}
		//-- Metodo Canvas --//
		protected void onDraw(Canvas canvas) {
			canvas.drawRGB(255,255,255);
			//-- Configuramos para que sea de color rojo --//
			paint.setColor(Color.BLACK);
			//-- Dibujamos la linea --//
			canvas.drawLine(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
			//-- STROKE es para el contorno --//
			paint.setStyle(Style.STROKE);
			paint.setColor(0xff00ff00);
			//--Dibujamos un circulo --//
			canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2,  40, paint);
			//--Dibujamos un rectangulo --//
			paint.setStyle(Style.FILL);//Aparecera el relleno sin contorno
			paint.setColor(0x770000ff);
			canvas.drawRect(100, 100, 200, 200, paint);
			invalidate(); //Le decimos que lo este renderizando la cantidad de veces como se posible
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
