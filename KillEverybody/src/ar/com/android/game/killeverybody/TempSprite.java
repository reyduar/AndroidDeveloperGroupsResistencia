package ar.com.android.game.killeverybody;
/**
 * @author reynaldo.duarte
 */
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class TempSprite {
	private float x;//Posicion del Sprite temporal
    private float y;//Posicion del Sprite temporal
    private Bitmap bmp;//Imagen temporal
    private int life = 15;//Tiempo de vida del sprite en 15 ticks, un ticks es una llamada al metodo Update
    private List<TempSprite> temps;//La lista que va contener todos los sprites temporales que va ser usada en el oDraw en el View

    public TempSprite(List<TempSprite> temps, GameView gameView, float x, float y, Bitmap bmp) {
         //---Calculo para centralizar la mancha de sangre --//
    	  this.x = Math.min(Math.max(x - bmp.getWidth() / 2, 0),
                        gameView.getWidth() - bmp.getWidth());
          this.y = Math.min(Math.max(y - bmp.getHeight() / 2, 0),
                        gameView.getHeight() - bmp.getHeight());
          this.bmp = bmp;
          this.temps = temps;
    }

    public void onDraw(Canvas canvas) {
          update();
          canvas.drawBitmap(bmp, x, y, null);
    }
    //--
    private void update() {
          if (--life < 1) {
                 temps.remove(this);
          }
    }
}
