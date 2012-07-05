package ar.com.android.listas;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class ListaAndroidActivity extends ListActivity {
	//Mantenemos un Array de elementos en la que podremos guardar
	//más información de la que mostraremos en el listado
	ArrayList<HashMap<String,String>> Eventos;
	 
	//Con los siguientes Arrays establecemos la correspondencia
	//entre los elementos del Array de HashMaps de eventos (from)
	//con los elementos del diseño en XML de cada una de las filas (to)
	String[] from=new String[] {"Time","Name","Desc"};
	int[] to=new int[]{R.id.hora,R.id.nombre,R.id.desc1};
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	 
	    super.onCreate(savedInstanceState);
	    //Establecemos el diseño principal de la Actividad
	    setContentView(R.layout.lista);
	 
	    // Este método de obtención de elementos puede cambiarse por cualquier otro
	    //como leerlos de una BBDD o de un servidor web con JSON
	    ArrayList<String[]> lista = new ArrayList<String[]>();
	 
	    String[] evento1 = {"11:30","Ofrenda de Flores","Participa la Banda Sinfónica Municipal de Albacete", "1"};
	    lista.add(evento1);
	 
	    String[] evento2 = {"12:00","Los Redondeles","La ronda de los Redondeles 2011", "2"};
	    lista.add(evento2);
	 
	    String[] evento3 = {"12:00","Futbol","Albacete Balompie Vs. Tenerife C.D.", "3"};
	    lista.add(evento3);
	 
	    // Transformamos los elementos String[] en HashMap para
	    //posteriormente incluirlos en el Array Global que se utilizará
	    //para rellenar la lista
	    Eventos = new ArrayList<HashMap<String, String>>();
	    for(String[] evento:lista){
	        HashMap<String,String> datosEvento=new HashMap<String, String>();
	 
	        // Aquí es dónde utilizamos las referencias creadas inicialmente
	        //en el elemento "from"
	        datosEvento.put("Time", evento[0]);
	        datosEvento.put("Name", evento[1]);
	        datosEvento.put("Desc", evento[2]);
	        datosEvento.put("id", evento[3]);
	 
	        Eventos.add(datosEvento);
	    }
	    // Una vez tenemos toda la información necesaria para rellenar la lista
	    //creamos un elemento que nos facilitará la tarea:
	    //SimpleAdapter(Actividad, Array de HashMap con elementos, Fichero XML del
	    //diseño de cada fila, Cadenas del HashMap, Ids del Fichero XML del diseño de cada fila)
	    SimpleAdapter ListadoAdapter=new SimpleAdapter(this, Eventos, R.layout.row, from, to);
	    setListAdapter(ListadoAdapter);
	}
}