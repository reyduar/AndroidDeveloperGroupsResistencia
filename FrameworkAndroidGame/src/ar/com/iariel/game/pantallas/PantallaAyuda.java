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
public class PantallaAyuda extends Pantalla{
	public PantallaAyuda(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            //-- Se verifica el evento TOUCH_UP --//
            if(event.type == TouchEvent.TOUCH_UP) {
            	//-- Se virifica si fue presionado en la cordenada de boton siguiente --//
                if(event.x > 256 && event.y > 416 ) {
                    juego.setScreen(new PantallaAyuda2(juego));//Hacemos la transicion a la siguiente pantalla
                    if(Configuraciones.sonidoHabilitado)//Pregunta si el sonido esta habilitado
                        Assets.pulsar.play(1);//Ejecuta el sonido toque
                    return;//Hacemos el return para cualquier caso de la pantalla que se haya ejecutado
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();      
        g.drawPixmap(Assets.fondo, 0, 0);//renderizamos el fondo en los valores 0,0
        g.drawPixmap(Assets.ayuda1, 64, 100);//dibujamos la imagen 
        g.drawPixmap(Assets.botones, 256, 416, 0, 64, 64, 64);//dibujamos la imagen del boton siguiente
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
