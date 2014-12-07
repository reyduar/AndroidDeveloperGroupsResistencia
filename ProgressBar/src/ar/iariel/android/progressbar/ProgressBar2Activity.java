package ar.iariel.android.progressbar;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.widget.ProgressBar;

public class ProgressBar2Activity extends Activity {
	private static int progreso;
	private ProgressBar barraProgreso;
	private int statusProgreso =0;
	private Handler manejador = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainprogressbar2);
        progreso = 0;
        barraProgreso = (ProgressBar) findViewById(R.id.barraprogreso);
        barraProgreso.setMax(200);
        //-- Hacer algun trabajo en el hilo de fondo --//
        new Thread(new Runnable() {
			public void run() {
				//--Hacer algun trabajo aqui --//
				while (statusProgreso < 200) {
					statusProgreso = hacerAlgunTrabajo();
					//--Actualiza la barra de progreso --//
					manejador.post(new Runnable() {
						public void run() {
							barraProgreso.setProgress(statusProgreso);
						}
					});
				}
				//-- Ocultar la barra de progreso --//
				manejador.post(new Runnable() {
					public void run() {
						//-- 0 - Visible; 4 - Invisible; 8 - Gone --//
						barraProgreso.setVisibility(8);
					}
				});
			}
			//-- Hacer algun trabajo aqui --//
			private int hacerAlgunTrabajo(){
				try {
					//-- Simula hacer algun trabajo
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return ++progreso;
			}
		}).start();
        
    }

  
}
