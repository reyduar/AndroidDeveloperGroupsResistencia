package ar.com.iariel.game.pantallas;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
import java.util.ArrayList;
import java.util.List;
//-- La clase debera hacer lo siguiente :
//-- 1º Almacenar el barco y la tripulacion
//-- 2º Saber como se esta desplazando el barco
//-- 3º Añadir nuevos tripulantes
//-- 4º Moverse por las celdas en la direccion que le indique
public class JollyRoger {
		//-- Definimos las constantes que codifican la direccion del barco y el barco solo va a poder girar de izquierda a derecha--//
	    public static final int ARRIBA = 0;
	    public static final int IZQUIERDA= 1;
	    public static final int ABAJO = 2;
	    public static final int DERECHA = 3;
	    //-- Definimos una lista a la que vamos a llamar tripulacion y contiene todas las parte incluido el barco 
	    //-- que es la primera imagen de la lista 
	    public List<Tripulacion> partes = new ArrayList<Tripulacion>();
	    public int direccion;//Variable que va a contener la direccion en la que se esta moviendo el barco
	    
	    public JollyRoger() {   
	    	//-- Configuramos a JollyRoger para que este compuesto por el barco y dos tripulantes y en la direccion hacia arriba--//
	    	direccion = ARRIBA;
	        partes.add(new Tripulacion(5, 6));
	        partes.add(new Tripulacion(5, 7));
	        partes.add(new Tripulacion(5, 8));
	    }
	    
	    public void girarIzquierda() {
	    	direccion += 1;
	        if(direccion > DERECHA)
	        	direccion = ARRIBA;
	    }
	    
	    public void girarDerecha() {
	    	direccion -= 1;
	        if(direccion < ARRIBA)
	        	direccion = DERECHA;
	    }
	    
	    //-- Permite añadir un nuevo tripulante al final de la lista --//
	    public void abordaje() {
	        Tripulacion end = partes.get(partes.size()-1); //Recuperamos la ultima posicion de array -1 para excluir al barco
	        partes.add(new Tripulacion(end.x, end.y));//Realizamos el abordaje en esa misma posicion
	    }
	    
	  //-- Permite mover a Jolly Roger y su tripulacion --//
	    public void avance() {
	        Tripulacion barco = partes.get(0); //              
	        
	        int len = partes.size() - 1;//Recuperamos la ultima posicion de array -1 para excluir al barco
	        for(int i = len; i > 0; i--) {
	            Tripulacion antes = partes.get(i-1);
	            Tripulacion parte = partes.get(i);
	            parte.x = antes.x;
	            parte.y = antes.y;
	        }
	        
	        //--Realizamos las comprobaciones de las direcciones del barco --//
	        if(direccion == ARRIBA)
	            barco.y -= 1;
	        if(direccion == IZQUIERDA)
	        	barco.x -= 1;
	        if(direccion == ABAJO)
	        	barco.y += 1;
	        if(direccion == DERECHA)
	        	barco.x += 1;
	        
	        //-- Realizamos la comprobaciones para asegurarnos que el barco no se salga de los limites --//
	        if(barco.x < 0)
	        	barco.x = 9;
	        if(barco.x > 9)
	        	barco.x = 0;
	        if(barco.y < 0)
	        	barco.y = 12;
	        if(barco.y > 12)
	        	barco.y = 0;
	    }
	    
	    //-- Verifica si el barco ha chocado a alguno de sus tripulantes --//
	    public boolean comprobarChoque() {
	        int len = partes.size();
	        Tripulacion barco = partes.get(0);
	        for(int i = 1; i < len; i++) {
	            Tripulacion parte = partes.get(i);
	            if(parte.x == barco.x && parte.y == barco.y)
	                return true;
	        }        
	        return false;
	    }
}
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
