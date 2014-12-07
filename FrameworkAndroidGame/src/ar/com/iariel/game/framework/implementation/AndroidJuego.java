package ar.com.iariel.game.framework.implementation;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import ar.com.iariel.game.framework.Audio;
import ar.com.iariel.game.framework.FileIO;
import ar.com.iariel.game.framework.Graficos;
import ar.com.iariel.game.framework.Input;
import ar.com.iariel.game.framework.Juego;
import ar.com.iariel.game.framework.Pantalla;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public abstract class AndroidJuego extends Activity implements Juego {
	//-- Creacion de variables y objetos --// 
	AndroidFastRenderView renderView; //Es el que manejara y dibujaremos el hilo de nuestro loop principal
    Graficos graficos;
    Audio audio;
    Input input;
    FileIO fileIO;
    Pantalla pantalla;//Contiene la pantalla activa en cada momento
    WakeLock wakeLock;//Impedira que la pantalla se atenue 

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //-- Hacemos que la activity se muestre a pantalla completa --//
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //-- Configuramos nuestro framebuffer artificial --//
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;//Para determinar que orientacion de pantalla esta usando la activity con getResources() 
        //-- Configuramos el tamaño del framebuffer --//
        int frameBufferWidth = isLandscape ? 480 : 320;//Modo paisaje
        int frameBufferHeight = isLandscape ? 320 : 480;//Modo retrato
        //-- Instanciamos un bitmap que maneja las instancias android render view y graficos --//
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);//Va a tener un formato de color RGB_565 para no consumir memoria
        
        //-- Calculamos los valores scaleX para las clases sigle y multitouch handle usaran estos valores para transformar las coordenadas 
        float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();

        //-- Intanciamos las clases helper con los valores necesarios del contructor --//
        renderView = new AndroidFastRenderView(this, frameBuffer);
        graficos = new AndroidGraficos(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(getAssets());
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        pantalla = getStartScreen();//Con este metodo vamos a implemetar el comienzo del juego 
        setContentView(renderView);//Configuramos el setContentView con renderView
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
    }
    
    //-- Metodo que se ejecuta al reanudar el ciclo de vida de la activity, siempre se ejecuta --//
    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();//Adquiere el wakeLock
        pantalla.resume();//Avisa al metodo de la pantalla que fue reanundado
        renderView.resume();//Le decimos que reanude el hilo de renderizacion que a su vez reanudara el hilo principal de nuestro juego y le decimos a la pantalla actual que se actualice 
    }
    
    //-- Metodo que se ejecuta cuando sale del juego --//
    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();//Liberamos el wakeLock
        renderView.pause();//Aseguramos que el hilo de renderizacion esta termiando
        pantalla.pause();
        
        //-- Metodo de limpieza una vez que nos aseguramos que la pantalla esta pausada --//
        if (isFinishing())
            pantalla.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graficos getGraphics() {
        return graficos;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    //-- Metodo que llamaremos siempre que queramos actualizar la pantalla --//
    @Override
    public void setScreen(Pantalla pantalla) {
    	//-- Comprobamos para empezar a trabajar que la pantalla no devuelva null--//
        if (pantalla == null)
            throw new IllegalArgumentException("Pantalla no debe ser null");
        //-- Estamos haciendo sitio para la nueva pantalla --//
        this.pantalla.pause();
        this.pantalla.dispose();
        pantalla.resume();//Pedimos a la pantalla que se reanude a ella misma
        pantalla.update(0);//Pedimos que actualice a ella misma con un delta time de 0 
        this.pantalla = pantalla;//Configuramos el miembro pantalla a este miembro pantalla
    }
    
    //-- Metodo que no devuelve la pantalla que este activa actualmente --//
    public Pantalla getCurrentScreen() {
        return pantalla;
    }
}

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
