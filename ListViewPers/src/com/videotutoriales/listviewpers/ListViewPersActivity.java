package com.videotutoriales.listviewpers;
import com.videotutoriales.listviewpers.R;
import android.app.ListActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewPersActivity extends ListActivity {	
	
	 /*String[] provincias = {
	            "La Coruña",
	            "Lugo",
	            "Orense",
	            "Pontevedra",
	            "Oviedo",
	            "Las Palmas",
	            "Sevilla",
	            "Málaga",
	            "Córdoba",
	            "Granada",
	            "Jaén"
	    };*/
	//-- DEfinimos un array para cargar los datos del xml --//
String[] provincias;	 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        //-- setChoiceMode :Para seleccionar numeros de elementos deseado --//
        ListView lstView = getListView();
        //lstView.setChoiceMode(0); //CHOICE_MODE_NONE
        //lstView.setChoiceMode(1); //CHOICE_MODE_SINGLE
        lstView.setChoiceMode(2);   //CHOICE_MODE_MULTIPLE
        lstView.setTextFilterEnabled(true);
        
        //-- getResources: Recupera los datos del xml --//
        provincias = getResources().getStringArray(R.array.array_provincias);
        
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, provincias));
    }
    
    public void onListItemClick(
    ListView parent, View v, int position, long id) 
    {   
    	//---toggle the check displayed next to the item---
    	//-- Coloca un CheckBox en la lista --//
    	parent.setItemChecked(position, parent.isItemChecked(position));   	
    	
        Toast.makeText(this, 
    	    "Has seleccionado " + provincias[position], 
            Toast.LENGTH_SHORT).show();
    }  
};