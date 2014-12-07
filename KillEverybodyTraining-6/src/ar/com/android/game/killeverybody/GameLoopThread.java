package ar.com.android.game.killeverybody;
import android.graphics.Canvas;

/**
 * 
 * @author Ariel Duarte
 * 
 */
public class GameLoopThread extends Thread {
	static final long FPS = 10;//Tomamos una velocidad constante de 10 frame por segundo sin desaselerarse
	private GameView view; //Instanciamos una view del tipo Gameview
	private boolean running = false;//Esta variable mientras sea true va a ejecutar el loop
	
	
	public GameLoopThread(GameView view) {
		this.view = view;
	}

	public void setRunning(boolean run) {
		running = run;
	}

	@Override
	public void run() {
		 long ticksPS = 1000 / FPS;// se van a imprimir de 10 frame por segundo osea seria 1seg./10 = 100 que se lo que tendria que durar cada vuelta
         long startTime;//El tiempo que empieza
         long sleepTime;//El tiempo que duerme
		while (running) {
			Canvas c = null;
            startTime = System.currentTimeMillis();//Tomamos el tiempo en milisegundos
			try {
				c = view.getHolder().lockCanvas(); //En loop ponemos el pintado de Canvas
				synchronized (view.getHolder()) { //synchronized es para evitar que otros Threads dibujen mientras nosotros llamamos a onDraw
					view.onDraw(c);//Llamamos al metodo onDraw de la clase GameView
				}
			} finally {//En finally asegura  que unlockCanvasAndPost se ejecute si es posible aunque haya habido un error
				if (c != null) {
					view.getHolder().unlockCanvasAndPost(c);
				}
			}
			//--Lo demoramos para que de oportunidad a la PCU a procesar otras aplicaciones --//
			 sleepTime = ticksPS-(System.currentTimeMillis() - startTime); //Esto no daria cuando tiempo tenemos que dormir 
             try {
                    if (sleepTime > 0)
                           sleep(sleepTime);
                    else
                           sleep(10);
             } catch (Exception e) {}
		}
	}

}
