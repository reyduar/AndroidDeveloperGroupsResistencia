package com.videotutoriales.imageswitcher;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ViewSwitcher.ViewFactory;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

public class ImageSwitcherActivity extends Activity implements ViewFactory {
	// ---imágenes a mostrar---
	Integer[] IDsImagen = { R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
			R.drawable.pic4, R.drawable.pic5, R.drawable.pic6, R.drawable.pic7 };
	private ImageSwitcher imageSwitcher;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		imageSwitcher = (ImageSwitcher) findViewById(R.id.switcher1);
		imageSwitcher.setFactory(this);
		
		//---Son las que hacen el efecto cuando van apareciendo las imagenes ---// 
		
		
		  imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
		  android.R.anim.fade_in));
		  imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
		  android.R.anim.fade_out));
		 
//		imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
//				android.R.anim.slide_in_left));
//		imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
//				android.R.anim.slide_out_right));
		Gallery gallery = (Gallery) findViewById(R.id.galeria1);
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				imageSwitcher.setImageResource(IDsImagen[position]);
			}
		});
	}

	public View makeView() {
		ImageView imageView = new ImageView(this);
		imageView.setBackgroundColor(0xFF000000);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return imageView;
	}

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
			return IDsImagen.length;
		}

		// ---devolver la ID de un item---
		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		// ---devolver una view ImageView---
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(IDsImagen[position]);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(150, 120));
			imageView.setBackgroundResource(itemBackground);
			return imageView;
		}
	}
}