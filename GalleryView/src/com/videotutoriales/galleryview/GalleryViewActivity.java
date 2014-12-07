package com.videotutoriales.galleryview;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class GalleryViewActivity extends Activity {
	// ---imágenes a mostrar---
	//-- Generamos el array para la lista de imagenes --//
	Integer[] IDsimagenes = { R.drawable.pic1, R.drawable.pic2,
			R.drawable.pic3, R.drawable.pic4, R.drawable.pic5, R.drawable.pic7 };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Gallery gallery = (Gallery) findViewById(R.id.galeria1);
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(getBaseContext(),
						"imagen" + (position + 1) + " seleccionada",
						Toast.LENGTH_SHORT).show();
				// ---mostrar la imagen seleccionada---
				ImageView imageView = (ImageView) findViewById(R.id.image1);
				imageView.setImageResource(IDsimagenes[position]);
			}
		});
	}

	//-- Esta clase nos permite enlazar la view gallery con las view que tienen que ir apareciendo en la Image View--//
	public class ImageAdapter extends BaseAdapter {
		private Context context;
		private int itemBackground;

		public ImageAdapter(Context c) {
			context = c;
			// ---configurar el estilo---
			TypedArray a = obtainStyledAttributes(R.styleable.Galeria1);
			itemBackground = a.getResourceId(
					R.styleable.Galeria1_android_galleryItemBackground, 0);
			a.recycle();
		}

		// ---devolver el número de imágenes---
		public int getCount() {
			return IDsimagenes.length;
		}

		// ---devolver un item---
		public Object getItem(int position) {
			return position;
		}

		// ---devolver la ID de un item---
		public long getItemId(int position) {
			return position;
		}

		// ---devolver una view ImageView---
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(IDsimagenes[position]);//Permite saber cual de las imagenes es la que tiene que mostararse dentro de esta view
			imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);//Modo en el que queremos que se redimensione la imagen para aparecer dentro de la view
			imageView.setLayoutParams(new Gallery.LayoutParams(150, 120));//Para determinar el lugar donde queremos que aparesca la imagen 
			imageView.setBackgroundResource(itemBackground);//Para determiar el fondo
			return imageView;
		}
	}
}