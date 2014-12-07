package ar.com.iariel.game.pantallas;

import java.util.List;

import android.graphics.Color;
import ar.com.iariel.game.framework.Graficos;
import ar.com.iariel.game.framework.Juego;
import ar.com.iariel.game.framework.Pantalla;
import ar.com.iariel.game.framework.Pixmap;
import ar.com.iariel.game.framework.Input.TouchEvent;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public class PantallaJuego extends Pantalla{
	//-- Este puede estar en uno de estos estado --//
	//-- Para esto definimos un enum con los 4 estados del juego posibles--//
	enum EstadoJuego {
        Preparado,
        Ejecutandose,
        Pausado,
        FinJuego
    }
    //-- Definimos los miembros --//
    EstadoJuego estado = EstadoJuego.Preparado; //Contiene el estado actual de pantalla
    Mundo mundo;//Contiene la instancia del mundo
    int antiguaPuntuacion = 0;//Contiene la puntuacion con entero, para llevar a cabo operaciones cuando tengamos que aumentar la puntuacion
    String puntuacion = "0";//Contiene la puntuacion como string
    
    //-- Definimos el constructor --//
    public PantallaJuego(Juego juego) {
        super(juego);
        mundo = new Mundo();//Crea una instancia de la clase mundo y la pantalla de juego se encontrara en el estado preparado
    }

    //-- Este metodo obtiene todos los eventos touch y key que tenga lugar--//
    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();//Obtiene los eventos desde el modulo input
        juego.getInput().getKeyEvents();
        //--Delega la actualizacion a uno de los 4 metodos para que se ejecute --// 
        if(estado == EstadoJuego.Preparado)
            updateReady(touchEvents);
        if(estado == EstadoJuego.Ejecutandose)
            updateRunning(touchEvents, deltaTime);
        if(estado == EstadoJuego.Pausado)
            updatePaused(touchEvents);
        if(estado == EstadoJuego.FinJuego)        	
            updateGameOver(touchEvents);
            
    }
    
    //-- En este estado le pedimos al usuario que toque la pantalla para que iniciar el juego --// 
    //-- Se le llama cuando este en el estado preparado --//
    private void updateReady(List<TouchEvent> touchEvents) {
        if(touchEvents.size() > 0)//Comprueba si se ha pulsado en la pantalla         	
            estado = EstadoJuego.Ejecutandose;//Cambia al estado ejecutandose 
    }
    
    //-- En este estado ejecutandose en updateRunning actualizamos el mundo lo renderizamos --//
    //-- Se le llama cuando este en el estado ejecutandose --//
    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {        
        int len = touchEvents.size();
        //--Se empieza por comprobar si se ha pulsado el boton pause --//
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x < 64 && event.y < 64) {//Cordenada del boton pause
                    if(Configuraciones.sonidoHabilitado)//Verifica el sonido 
                        Assets.pulsar.play(1);
                    estado = EstadoJuego.Pausado;//Cambia al estado a pausado 
                    return;
                }
            }
            //-- Aqui encontramos la parte del CONTROLADOR del juego --//
            //-- Le decimos a Jolly Roger que gire de izquierda a derecha cuando el jugador pulse uno de los botones de la pantalla--//
            if(event.type == TouchEvent.TOUCH_DOWN) {// Se ha compruba el por el TOUCH_DOWN para que sea al toque la navegacion
            	//-- Verifica si uno de los 2 botones de parte inferior de la pantalla han sido pulsado --//
                if(event.x < 64 && event.y > 416) {
                    mundo.jollyroger.girarIzquierda();
                }
                if(event.x > 256 && event.y > 416) {
                    mundo.jollyroger.girarDerecha();
                }
            }
        }
        
        mundo.update(deltaTime);//Le decimos al mundo que se actualice con el delta time indicado
        //-- Si el mundo nos señala que el juego ha finalizado cambiamos los estados --//
        if(mundo.finalJuego) {        	
            if(Configuraciones.sonidoHabilitado)//Verificamos si el sonido esta habilitado
                Assets.derrota.play(1); //Ejecutamos el sonido de fin de  juego              
            estado = EstadoJuego.FinJuego;
        }
        //-- Comprobamos si la puntuacion antigua almacenada es diferente a la mundo y asi sabemos que jolly Roger a capturado un nuevo botin--//
        if(antiguaPuntuacion != mundo.puntuacion) {
            antiguaPuntuacion = mundo.puntuacion;
            puntuacion = "" + antiguaPuntuacion;//Asi que tenemos que cambiar la puntuacion
            if(Configuraciones.sonidoHabilitado)//Pegunta si el sonido esta habilitado
                Assets.ataque.play(1);//Hacemos sonar el sonido de ataque
        }
    }
    
    //-- Metodo que le mostramos las opciones uno para salir del juego y otro para reanudarlo --//
    //-- Comprueba si se ha pulsado sobre uno de las dos opciones del menu --//
    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 80 && event.x <= 240) {
                    if(event.y > 100 && event.y <= 148) {
                        if(Configuraciones.sonidoHabilitado)
                            Assets.pulsar.play(1);
                        estado = EstadoJuego.Ejecutandose;
                        return;
                    }                    
                    if(event.y > 148 && event.y < 196) {
                        if(Configuraciones.sonidoHabilitado)
                            Assets.pulsar.play(1);
                        juego.setScreen(new MainMenuScreen(juego));                        
                        return;
                    }
                }
            }
        }
    }
    //-- Le decimos al usuario que el juego ha finalizado --//
    //-- Comprueba si se ha pulsado el boton que aparece en el medio de la pantalla al finalizar el juego --//
    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            //-- Le proveemos el boton para volver al menu principal --//
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 128 && event.x <= 192 &&
                   event.y >= 200 && event.y <= 264) {
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);                        
                    juego.setScreen(new MainMenuScreen(juego));//Regreso al menu principal
                    return;
                }
            }
        }
    }
    

    //-- Es el metodo que va renderizar el contenido en nuestra pantalla --//
    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();
        
        g.drawPixmap(Assets.fondo, 0, 0);//Empieza por dibujar el fondo
        drawWorld(mundo);
        //-- Se prepara para dibujar los graficos para uno de los cuatro estado de la pantalla --//
        if(estado == EstadoJuego.Preparado) 
            drawReadyUI();// Llama al metodo que dibuja el grafico de prepado
        if(estado == EstadoJuego.Ejecutandose)
            drawRunningUI();// Llama al metodo que  dibuja los graficos que se muestra cuando se ejecuta el juego
        if(estado == EstadoJuego.Pausado)
            drawPausedUI();// Llama al metodo que  dibuja los graficos para cuando el juego esta pausado
        if(estado == EstadoJuego.FinJuego)
        	drawGameOverUI();// Llama al metodo que  dibuja los graficos que se muestran cuando el juego finaliza
            
        
        drawText(g, puntuacion, g.getWidth() / 2 - puntuacion.length()*20 / 2, g.getHeight() - 42);                
    }
    //-- Metodo que dibuja el mundo --//
    private void drawWorld(Mundo mundo) {
        Graficos g = juego.getGraphics();
        JollyRoger jollyroger = mundo.jollyroger;
        Tripulacion head = jollyroger.partes.get(0);
        Botin botin = mundo.botin;
        
        
        Pixmap stainPixmap = null;
        if(botin.tipo== Botin.TIPO_1)
            stainPixmap = Assets.botin1;
        if(botin.tipo == Botin.TIPO_2)
            stainPixmap = Assets.botin2;
        if(botin.tipo == Botin.TIPO_3)
            stainPixmap = Assets.botin3;
        int x = botin.x * 32;
        int y = botin.y * 32;      
        g.drawPixmap(stainPixmap, x, y);             
        
        int len = jollyroger.partes.size();
        for(int i = 1; i < len; i++) {
            Tripulacion part = jollyroger.partes.get(i);
            x = part.x * 32;
            y = part.y * 32;
            g.drawPixmap(Assets.tripulacion, x, y);
        }
        
        Pixmap headPixmap = null;
        if(jollyroger.direccion == JollyRoger.ARRIBA) 
            headPixmap = Assets.barcoarriba;
        if(jollyroger.direccion == JollyRoger.IZQUIERDA) 
            headPixmap = Assets.barcoizquierda;
        if(jollyroger.direccion == JollyRoger.ABAJO) 
            headPixmap = Assets.barcoabajo;
        if(jollyroger.direccion == JollyRoger.DERECHA) 
            headPixmap = Assets.barcoderecha;        
        x = head.x * 32 + 16;
        y = head.y * 32 + 16;
        g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
    }
    
    //-- Metodo que dibuja la interface de preparado --//
    private void drawReadyUI() {
        Graficos g = juego.getGraphics();
        
        g.drawPixmap(Assets.preparado, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }
    
    //-- Metodo que dibuja 
    private void drawRunningUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.botones, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.botones, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.botones, 256, 416, 0, 64, 64, 64);
    }
    
    private void drawPausedUI() {
        Graficos g = juego.getGraphics();
        
        g.drawPixmap(Assets.menupausa, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graficos g = juego.getGraphics();
        
        g.drawPixmap(Assets.finjuego, 62, 100);
        g.drawPixmap(Assets.botones, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }
    
    public void drawText(Graficos g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numeros, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }
    
    //-- Se ejecuta cuando la pantalla es pausada o reemplazada por otra pantalla --//
    @Override
    public void pause() {
        if(estado == EstadoJuego.Ejecutandose)
            estado = EstadoJuego.Pausado;
        //-- Tambien guardamos nuestras configuraciones si el juego ha finalizado--//
        if(mundo.finalJuego) {        	
            Configuraciones.addScore(mundo.puntuacion); //Llamamos al metodo para verficar la ountuaciones
            Configuraciones.save(juego.getFileIO());    //Salvamos todas la configuraciones al almacenamiento externo       
        }
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