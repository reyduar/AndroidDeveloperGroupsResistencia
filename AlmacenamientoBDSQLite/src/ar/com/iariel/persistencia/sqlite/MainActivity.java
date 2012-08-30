package ar.com.iariel.persistencia.sqlite;

import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.database.Cursor;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        AdaptadorBD db = new AdaptadorBD(this);

        //-- a�adir contacto
//        db.abrir();
//        long id = db.insertarContacto("Ariel Duarte", "arielpy@gmail.com");
//        id = db.insertarContacto("Marcelo", "Marcelo@666.org");
//        db.cerrar();
        

        
        
        
//        //-- Actualizar un contacto
//        db.abrir();
//        if(db.actualizarContacto(2, "Noelia Ca�ete", "noe52@gmail.com"))
//        	Toast.makeText(this, "Actualizaci�n correcta!", Toast.LENGTH_LONG).show();
//        	else
//        		Toast.makeText(this, "Actualizaci�n ha fallado!", Toast.LENGTH_LONG).show();
//        
//        db.cerrar();
        
        //-- Eliminar un contacto
        db.abrir();
        if(db.borrarContacto(1))
        	Toast.makeText(this, "Eliminaci�n correcta!", Toast.LENGTH_LONG).show();
        	else
        		Toast.makeText(this, "La eliminaci�n ha fallado!", Toast.LENGTH_LONG).show();
        
        db.cerrar();
        
//      //-- Consultar un contacto
//      db.abrir();
//      Cursor c = db.obtenerContacto(2);
//      if(c.moveToFirst())
//      	displayContact(c);
//      	else
//      		Toast.makeText(this, "No se encontro el contacto!", Toast.LENGTH_SHORT).show();
//      
//      db.cerrar();
        
        //-- Consultar todos los contacto
      db.abrir();
      Cursor c = db.obtenerTodosContactos();
      if(c.moveToFirst()){
      	do {
				displayContact(c);
			} while (c.moveToNext());
      }
      db.cerrar();
      
    }

    
    public void displayContact(Cursor c){
    	Toast.makeText(this, 
    			"Id: "+ c.getString(0)+ "\n" +
    			"Nombre: "+c.getString(1)+ "\n" +
    			"email :"+c.getString(2), Toast.LENGTH_SHORT).show();
    }
}
