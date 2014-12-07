package ar.com.iariel.game.framework;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Estas son de las clases que escriben genericamente que nos permiten almacenar
 * cualquier tipo de objeto en nuestro pool y nos permite no estar limpiando sin
 * parar todas las instancias que vayamos creando
 *

 * Esta clase nos ayuda a mejorar la aplicacion en los Touch Events y Key Events 
 * y posilibita ahorrarel trabajo al Garbage Collector, la reutilizacion instancias ayuda a
 * liberar la memoria
 */

public class Pool<T> {
	public interface PoolObjectFactory<T> {
		public T createObject();
	}

	private final List<T> freeObjects;//Permitira almacenar los objetos que se vayan colocando en el pool
	private final PoolObjectFactory<T> factory; //Permitira ir generando nuevas instancias del tipo de objeto que este contenido en la interface
	private final int maxSize;//Almacena el numero maximo de objetos que puede contener el pool necesario para que no crezca de modo idenfinido que podria provocar problemas de memoria
	
	//-- Definimos el constructor de la clase --//
	public Pool(PoolObjectFactory<T> factory, int maxSize){
		this.factory = factory;
		this.maxSize = maxSize;
		this.freeObjects = new ArrayList<T>(maxSize);
	}
	//-- Este metodo va a ser el responsable de crear una nueva instancia o 
	//-- nos devolvera una instancia que este colocada en el pool en el caso
	//-- que exista en el freeObjects algun objeto que podamos reutilzar
	public T newObject(){
		T object = null;
		if(freeObjects.size() == 0)//Si existe el objeto que necesitamos en el pool lo crea
			object = factory.createObject();
		else
			object = freeObjects.remove(freeObjects.size() -1);//Si existe nos devuelve ese objeto y los elimina del pool
		
		return object;
	}
	//-- Metodo free nos permite ir guardando dentro del pool los objetos 
	//-- que ya no necesitemos y que posteriormente podemos reutilizarlo
	public void free(T object){
		if(freeObjects.size() < maxSize)
			freeObjects.add(object);
	}
}



/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
