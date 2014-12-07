package ar.com.iariel.game.framework;
/**
 * @version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public abstract class Pantalla {
	protected final Juego juego;
	
	//-- Constructor pantalla --//
    public Pantalla (Juego juego) {
    	//-- Recibe una instancia Juego --//
        this.juego = juego;//Con este miembro se accede a todas la subclases 
    }

    //-- Definimos los metodos de transicion entre pantallas --//
    public abstract void update(float deltaTime);

    public abstract void present(float deltaTime);

    public abstract void pause();//Este metodo sera llamado cuando el juego sea pausado

    public abstract void resume();//Este metodo sera llamado cuando el juego sea reanudado

    public abstract void dispose();//Eliminara todo lo que se tiene en la anterior pantalla
}

/**
 * @version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
