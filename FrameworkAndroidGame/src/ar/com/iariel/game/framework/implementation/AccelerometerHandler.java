package ar.com.iariel.game.framework.implementation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public class AccelerometerHandler implements SensorEventListener{
	float accelX;
	float accelY;
	float accelZ;
	
	public AccelerometerHandler(Context context){
		//-- Solicitamos al sistema el servicio que gestiona los sensores --//
		SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		//-- Verificamos si existe un acelerometro instalado --//
		if(manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0){
			//-- Pedimos de la lista solo el sensor para el uso del accelerometer --//
			Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		}
	
	}
	
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//No se hace nada
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		//-- Lo que hacemos es recoger los valores del acelometro --//
		accelX = event.values[0];
		accelY = event.values[1];
		accelZ = event.values[2];
		
	}

	//-- Metodos que nos permite devolvernos la aceleracion actual para cada eje --//
	public float getAccelX(){
		return accelX;
	}
	
	public float getAccelY(){
		return accelY;
	}
	
	public float getAccelZ(){
		return accelZ;
	}
}
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */