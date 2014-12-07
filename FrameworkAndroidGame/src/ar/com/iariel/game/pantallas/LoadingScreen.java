package ar.com.iariel.game.pantallas;

import ar.com.iariel.game.framework.Graficos;
import ar.com.iariel.game.framework.Juego;
import ar.com.iariel.game.framework.Pantalla;
import ar.com.iariel.game.framework.Graficos.PixmapFormat;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
//-- Se implementara en la clase JuegoUnderJollyRoger --//
public class LoadingScreen extends Pantalla{
	public LoadingScreen(Juego juego) {
        super(juego);
    }

    @Override
    //-- Aqui cargamos todos los assets y configuraciones --//
    public void update(float deltaTime) {
        Graficos g = juego.getGraphics();
        //-- Cargamos los graficos con sus formatos de color correspondiente --//
        Assets.fondo = g.newPixmap("fondo.png", PixmapFormat.RGB565);
        Assets.fondocredito = g.newPixmap("fondocredito.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.menuprincipal = g.newPixmap("menuprincipal.png", PixmapFormat.ARGB4444);
        Assets.botones = g.newPixmap("botones.png", PixmapFormat.ARGB4444);
        Assets.ayuda1 = g.newPixmap("ayuda1.png", PixmapFormat.ARGB4444);
        Assets.ayuda2 = g.newPixmap("ayuda2.png", PixmapFormat.ARGB4444);
        Assets.ayuda3 = g.newPixmap("ayuda3.png", PixmapFormat.ARGB4444);
        Assets.numeros = g.newPixmap("numeros.png", PixmapFormat.ARGB4444);
        Assets.preparado = g.newPixmap("preparado.png", PixmapFormat.ARGB4444);
        Assets.menupausa = g.newPixmap("menupausa.png", PixmapFormat.ARGB4444);
        Assets.finjuego = g.newPixmap("finjuego.png", PixmapFormat.ARGB4444);
        Assets.barcoarriba = g.newPixmap("barcoarriba.png", PixmapFormat.ARGB4444);
        Assets.barcoizquierda = g.newPixmap("barcoizquierda.png", PixmapFormat.ARGB4444);
        Assets.barcoabajo = g.newPixmap("barcoabajo.png", PixmapFormat.ARGB4444);
        Assets.barcoderecha = g.newPixmap("barcoderecha.png", PixmapFormat.ARGB4444);
        Assets.tripulacion = g.newPixmap("tripulacion.png", PixmapFormat.ARGB4444);
        Assets.botin1 = g.newPixmap("botin1.png", PixmapFormat.ARGB4444);
        Assets.botin2 = g.newPixmap("botin2.png", PixmapFormat.ARGB4444);
        Assets.botin3 = g.newPixmap("botin3.png", PixmapFormat.ARGB4444);
        Assets.creditos = g.newPixmap("creditos.png", PixmapFormat.ARGB4444);
        Assets.ariel = g.newPixmap("ariel.png", PixmapFormat.ARGB4444);
        Assets.ivan = g.newPixmap("ivan.png", PixmapFormat.ARGB4444);
        Assets.desarrollo = g.newPixmap("desarrollo.png", PixmapFormat.ARGB4444);
        Assets.disenhio = g.newPixmap("disenhio.png", PixmapFormat.ARGB4444);
        
        //-- Cargamos los efectos de sonido --//
        Assets.pulsar = juego.getAudio().nuevoSonido("pulsar.ogg");
        Assets.ataque = juego.getAudio().nuevoSonido("ataque.ogg");
        Assets.derrota = juego.getAudio().nuevoSonido("derrota.ogg");
        Assets.tonight = juego.getAudio().nuevaMusica("tonight.ogg");
      //  Assets.tonight = (Sonido) juego.getAudio().nuevaMusica("tonight.ogg");
        
        
        Configuraciones.cargar(juego.getFileIO()); //Cargamos las configuraciones desde el almacenamiento externo      
        juego.setScreen(new MainMenuScreen(juego));//Se la encargada de llevar a cabo la ejecucion del juego a patir de este punto
    }

    @Override
    public void present(float deltaTime) {

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
