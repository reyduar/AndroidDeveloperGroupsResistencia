package com.videotutoriales.juegosandroid;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FundamentalesAndroid extends ListActivity {
	
	String pruebas[] = { "LifeCycleTest", "SingleTouchTest", "MultiTouchTest",
			"KeyTest", "AccelerometerTest", "AssetsTest",
			"ExternalStorageTest", "SoundPoolTest", "AudioPlayerTest", 
			"PlayerTouchTest", "MediaPlayerTest", "AudioRecordTest",
			"FullScreenTest", "RenderViewTest", "ShapeTest", "BitmapTest",
			"FontTest", "SurfaceViewTest" };
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, pruebas));
				}
	@Override
	protected void onListItemClick(ListView list, View view, int position,
	long id) {
		super.onListItemClick(list, view, position, id);
		String nombrePrueba = pruebas[position];
		try {
			Class<?> clazz = Class.forName("com.videotutoriales.juegosandroid." + nombrePrueba);
		    Intent intent = new Intent(this, clazz);
		    startActivity(intent);
			
		} catch (ClassNotFoundException e) {
		e.printStackTrace();
		}
	}
		
		
	}
	
	


