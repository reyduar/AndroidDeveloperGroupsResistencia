package ar.com.iariel.game.pantallas;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
import java.util.List;

import ar.com.iariel.game.framework.Graficos;
import ar.com.iariel.game.framework.Juego;
import ar.com.iariel.game.framework.Pantalla;
import ar.com.iariel.game.framework.Input.TouchEvent;
//-- Va a ser la encargada de renderizar el logo, las opciones del menu principal,
//-- y la configuracion del sonido
public class MainMenuScreen extends Pantalla{
	public MainMenuScreen(Juego juego) {
        super(juego);               
    }   

	//-- En este metodo haremos la comprobacion de todos los eventos touch --//
    @Override
    public void update(float deltaTime) {
        Graficos g = juego.getGraphics();
        //-- Accedemos a todos los eventos touch y keys--//
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();       
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            //-- Buscamos en el loop un evento del tipo TOUCH_UP--//
            if(event.type == TouchEvent.TOUCH_UP) {
            	//-- Ahora tenenmos que comprobar donde se ha producido ese toque --//
                if(inBounds(event, 0, g.getHeight() - 64, 64, 64)) {//-- Touch de sonido
                    Configuraciones.sonidoHabilitado = !Configuraciones.sonidoHabilitado;//Habilita o deshabilita el sonido
                    if(Configuraciones.sonidoHabilitado)//Pregunta si el sonido esta habilitado
                        Assets.pulsar.play(1);//Ejecuta el sonido toque
                }
                if(inBounds(event, 64, 220, 192, 42) ) {//-- Touch de menu Jugar
                    juego.setScreen(new PantallaJuego(juego));//Hacemos la transicion de pantalla a la pantalla de correspondiente
                    if(Configuraciones.sonidoHabilitado)//Pregunta si el sonido esta habilitado
                        {Assets.pulsar.play(1);//Ejecuta el sonido toque                    
                        }                   
                    return;//Hacemos el return para cualquier caso de la pantalla que se haya ejecutado
                }
                if(inBounds(event, 64, 220 + 42, 192, 42) ) {//-- Touch de menu Puntuacion
                    juego.setScreen(new PantallaMaximasPuntuaciones(juego));//Hacemos la transicion de pantalla a la pantalla de correspondiente
                    if(Configuraciones.sonidoHabilitado)//Pregunta si el sonido esta habilitado
                        Assets.pulsar.play(1);//Ejecuta el sonido toque
                    return;//Hacemos el return para cualquier caso de la pantalla que se haya ejecutado
                }
                if(inBounds(event, 64, 220 + 84, 192, 42) ) {//-- touch de menu Ayuda
                    juego.setScreen(new PantallaAyuda(juego));//Hacemos la transicion de pantalla a la pantalla de correspondiente
                    if(Configuraciones.sonidoHabilitado)//Pregunta si el sonido esta habilitado
                        Assets.pulsar.play(1);//Ejecuta el sonido toque
                    return;//Hacemos el return para cualquier caso de la pantalla que se haya ejecutado
                }
                
                if(inBounds(event, 60, 350, 192, 30) ) {//-- touch para creditos
                    juego.setScreen(new PantallaCredito(juego));//Hacemos la transicion de pantalla a la pantalla de correspondiente
                    if(Configuraciones.sonidoHabilitado)//Pregunta si el sonido esta habilitado
                        Assets.pulsar.play(1);//Ejecuta el sonido toque
                    return;//Hacemos el return para cualquier caso de la pantalla que se haya ejecutado
                }
            }
        }
    }
    
    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 && 
           event.y > y && event.y < y + height - 1) 
            return true;
        else
            return false;
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();
        
        g.drawPixmap(Assets.fondo, 0, 0);//renderizamos el fondo en los valores 0,0
        g.drawPixmap(Assets.logo, 32, 20);//dibujamos el logo
        g.drawPixmap(Assets.menuprincipal, 64, 220);//dibujamos menu de opciones
        g.drawPixmap(Assets.creditos, 64, 340);//dibujamos menu de opciones
        if(Configuraciones.sonidoHabilitado)//Preguntamos si el sonido esta habilitado o no para saber que imagen poner
            g.drawPixmap(Assets.botones, 0, 416, 0, 0, 64, 64);//Cordenada de la imagen del boton de sonido habilitado
        else
            g.drawPixmap(Assets.botones, 0, 416, 64, 0, 64, 64);//Cordenada de la imagen del boton de sonido deshabilitado
    }

    @Override
    public void pause() {        
        Configuraciones.save(juego.getFileIO());
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
