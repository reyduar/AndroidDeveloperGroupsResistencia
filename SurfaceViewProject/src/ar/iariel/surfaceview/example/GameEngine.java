package ar.iariel.surfaceview.example;

import android.graphics.Canvas;

public class GameEngine extends Thread {

	private GameSurfaceView surfaceView;
	private boolean running = false;

	public GameEngine(GameSurfaceView view) {
		this.surfaceView = view;
	}

	public void setRunning(boolean run) {
		running = run;
	}

	@Override
	public void run() {
		while (running) {
			Canvas c = null;
			try {
				c = surfaceView.getHolder().lockCanvas();
				synchronized (surfaceView.getHolder()) {
					surfaceView.onDraw(c);
				}
			} finally {
				if (c != null) {
					surfaceView.getHolder().unlockCanvasAndPost(c);
				}
			}
		}
	}
}
