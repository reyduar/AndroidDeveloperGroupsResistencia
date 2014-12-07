package ar.com.iariel.game.pantallas;

import ar.com.iariel.game.framework.Pantalla;
import ar.com.iariel.game.framework.implementation.AndroidJuego;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public class JuegoUnderJollyRoger extends AndroidJuego{
	@Override
    public Pantalla getStartScreen() {
    	return new LoadingScreen(this);
    }
}
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
