package com.videotutoriales.juegosandroid;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

public class ChoosePictureDraw extends Activity implements OnClickListener, OnTouchListener{
	ImageView eligeImageView;
	Button EligeFoto;

	Bitmap bmp;
	Bitmap alteredBitmap;
	Canvas canvas;
	Paint paint;
	Matrix matrix;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagen2);

		eligeImageView = (ImageView) this.findViewById(R.id.EligeImagenView);
		EligeFoto = (Button) this.findViewById(R.id.EligeFotoButton);

		EligeFoto.setOnClickListener(this);
		eligeImageView.setOnTouchListener(this);
	}

	public void onClick(View v) {
		Intent eligeFotoIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(eligeFotoIntent, 0);
	}
  //-- onActivityResult: se usa cuando queremos que nos de un resultado al finalizar--//
	//-- Permite cargar la imagen seleccionada --//
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (resultCode == RESULT_OK) {
			Uri imageFileUri = intent.getData();
			Display currentDisplay = getWindowManager().getDefaultDisplay();
//--Accedemos al ancho y al alto de la imagen --//
			float dw = currentDisplay.getWidth();
			float dh = currentDisplay.getHeight();

			try {
      //-- La clase BitmapFactory permite crear objetos bitmap --//
				BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
				bmpFactoryOptions.inJustDecodeBounds = true;
				bmp = BitmapFactory
						.decodeStream(getContentResolver().openInputStream(
								imageFileUri), null, bmpFactoryOptions);
//-- Se usa el Math.ceil para convertir el flaot devuelto en entero--//
				int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
						/ dh);
				int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
						/ dw);
            //-- Lo que hace el metodo es redimensinar el tamaño de la imagen para que se adapte al de la  vista--//
				if (heightRatio > 1 && widthRatio > 1) {
					if (heightRatio > widthRatio) {
						bmpFactoryOptions.inSampleSize = heightRatio;
					} else {
						// Ancho ratio es mayor, escalar de acuerdo a esto
						bmpFactoryOptions.inSampleSize = widthRatio;
					}
				}

				bmpFactoryOptions.inJustDecodeBounds = false;
				bmp = BitmapFactory
						.decodeStream(getContentResolver().openInputStream(
								imageFileUri), null, bmpFactoryOptions);
				
				
				
				alteredBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp
						.getHeight(), bmp.getConfig());
				canvas = new Canvas(alteredBitmap);
				paint = new Paint();
				paint.setColor(Color.GREEN);
				paint.setStrokeWidth(5);
				matrix = new Matrix();
				canvas.drawBitmap(bmp, matrix, paint);

				eligeImageView.setImageBitmap(alteredBitmap);
				eligeImageView.setOnTouchListener(this);
			} catch (FileNotFoundException e) {
				Log.v("ERROR", e.toString());
			}
		}
	}
				
	float downx = 0;
	float downy = 0;
	float upx = 0;
	float upy = 0;

	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			downx = event.getX();
			downy = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			upx = event.getX();
			upy = event.getY();
			canvas.drawLine(downx, downy, upx, upy, paint);
			eligeImageView.invalidate();
			downx = upx;
			downy = upy;
			break;
		case MotionEvent.ACTION_UP:
			upx = event.getX();
			upy = event.getY();
			canvas.drawLine(downx, downy, upx, upy, paint);
			eligeImageView.invalidate();
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		default:
			break;
		}

		return true;
	}		
}
