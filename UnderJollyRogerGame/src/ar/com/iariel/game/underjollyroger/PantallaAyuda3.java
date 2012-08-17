package ar.com.iariel.game.underjollyroger;

import java.util.List;

import ar.com.iariel.game.marco.Graficos;
import ar.com.iariel.game.marco.Input.TouchEvent;
import ar.com.iariel.game.marco.Juego;
import ar.com.iariel.game.marco.Pantalla;

public class PantallaAyuda3 extends Pantalla{
	public PantallaAyuda3(Juego juego) {
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
                if(event.x > 256 && event.y > 416 ) {
                    juego.setScreen(new MainMenuScreen(juego));// Aqui le indicamos que vuelva a la pantalla del menu principal
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();      
        g.drawPixmap(Assets.fondo, 0, 0);
        g.drawPixmap(Assets.ayuda3, 64, 100);
        g.drawPixmap(Assets.botones, 256, 416, 0, 128, 64, 64);// Hacemos que se muestre el boton X
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
