package ar.com.iariel.game.pantallas;

import java.util.List;

import ar.com.iariel.game.framework.Graficos;
import ar.com.iariel.game.framework.Juego;
import ar.com.iariel.game.framework.Pantalla;
import ar.com.iariel.game.framework.Input.TouchEvent;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public class PantallaCredito extends Pantalla{
	public PantallaCredito(Juego juego) {
        super(juego);
        
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                //if(event.x > 256 && event.y > 416 ) {
                    juego.setScreen(new MainMenuScreen(juego));// Aqui le indicamos que vuelva a la pantalla del menu principal
                    if(Configuraciones.sonidoHabilitado){
                    	 Assets.tonight.pause();
                    	 Assets.pulsar.play(1);
                    }
                       
                    return;
              //  }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();      
        g.drawPixmap(Assets.fondocredito, 0, 0);
        g.drawPixmap(Assets.creditos, 60, 50);
        g.drawPixmap(Assets.desarrollo, 60, 140);
        g.drawPixmap(Assets.ariel, 60, 160);
        g.drawPixmap(Assets.disenhio, 60, 240);
        g.drawPixmap(Assets.ivan, 60, 260);
        if(Configuraciones.sonidoHabilitado)
        Assets.tonight.play();
        
        
       // g.drawPixmap(Assets.botones, 256, 416, 0, 128, 64, 64);// Hacemos que se muestre el boton X
        								 
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
