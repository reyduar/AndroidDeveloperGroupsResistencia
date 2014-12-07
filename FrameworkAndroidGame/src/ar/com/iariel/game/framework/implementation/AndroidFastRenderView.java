package ar.com.iariel.game.framework.implementation;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
//-- SurfaceView: es para ejecutar una renderizacion continua en un hilo separado --//
//-- Basicamente esta clase va a configurar un hilo en el cual se va a estar renderizando 
//-- constantemente la SurfaceView atravez de un canvas --//
//-- 1º Mantener una referencia a una instancia del juego de la cual se puede obtener la Pantalla Activa--//
//-- 2º Llamar a Pantalla.update() y Pantalla.present() desde el hilo FastRenderView y controla el Delta Time--//
//-- Delta Time: su objetivo es eliminar el problema del lag(lentitud del juego) monitoriza la velocidad de los objetos en un dispositivo y adapta el juego esa velocidad--//
//-- 3º Dibujar el Framebuffer en la SurfaceView --//
public class AndroidFastRenderView extends SurfaceView implements Runnable {
	AndroidJuego juego;
    Bitmap framebuffer;
    Thread renderThread = null;//Responsable de renderizar la logica de hilos que queremos implementar en nuestro juego
    SurfaceHolder holder;
    volatile boolean running = false;//Es un indicador booleano se usa para ir señalando que hilo de la renderizacion debe ser detenido
    
    public AndroidFastRenderView(AndroidJuego juego, Bitmap framebuffer) {
        super(juego);
        this.juego = juego;
        this.framebuffer = framebuffer;
        this.holder = getHolder();
    }
    
    //-- Se asegura que nuestro hilo se ejecuta bien con el ciclo de vida de la activity --//
    public void resume() { 
        running = true;
        renderThread = new Thread(this);
        renderThread.start();         
    }      
    
    public void run() {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();//Rastreeador del tiempo delta entre cada frame, nanoTime devuelve el tiempo actual en nanosegundos  
        while(running) {  
            if(!holder.getSurface().isValid())
                continue;           
            //-- Se calcula el Delta Time --//
            float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            
            //-- Son los que van renderizando la logica del juego --//
            juego.getCurrentScreen().update(deltaTime);
            juego.getCurrentScreen().present(deltaTime);
            
            //-- Dibuja el framebuffer artificial --//
            Canvas canvas = holder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(framebuffer, null, dstRect, null);                           
            holder.unlockCanvasAndPost(canvas);
        }
    }
    //-- Encargado de terminar el hilo del loop del renderizado--// 
    public void pause() {                        
        running = false;                        
        while(true) {
            try {
                renderThread.join();
                break;
            } catch (InterruptedException e) {
                // retry
            }
        }
    }   
}
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */