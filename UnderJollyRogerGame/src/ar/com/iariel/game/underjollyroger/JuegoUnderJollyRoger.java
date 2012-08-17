package ar.com.iariel.game.underjollyroger;

import ar.com.iariel.game.marco.Pantalla;
import ar.com.iariel.game.marco.implementacion.AndroidJuego;

public class JuegoUnderJollyRoger extends AndroidJuego{
	@Override
    public Pantalla getStartScreen() {
    	return new LoadingScreen(this);
    }
}
