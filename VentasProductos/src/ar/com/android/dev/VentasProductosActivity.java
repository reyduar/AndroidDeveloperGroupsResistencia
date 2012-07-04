package ar.com.android.dev;
/**
 * @author Ariel Duarte
 * Date: 18/06/2012
 */
import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.app.AlertDialog;

public class VentasProductosActivity extends Activity {
    /** Called when the activity is first created. */
	CheckBox cbJava, cbDelphi, cbRuby, cbPhp, cbAndroid;
	Button btnRealizar;
	EditText etValor;
	double valorTotal=0.00;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        cbJava = (CheckBox) findViewById(R.id.chkjava);
        cbDelphi = (CheckBox) findViewById(R.id.chkdelphi);
        cbRuby = (CheckBox) findViewById(R.id.chkRuby);
        cbPhp = (CheckBox) findViewById(R.id.chkphp);
        cbAndroid = (CheckBox) findViewById(R.id.chkandroid);
        btnRealizar = (Button) findViewById(R.id.btnrealizar);
        etValor = (EditText) findViewById(R.id.etvalortotal);
        etValor.setText(String.valueOf(valorTotal)+ " $");
        btnRealizar.setOnClickListener(new View.OnClickListener() {
		
        	DecimalFormat formateador = new DecimalFormat("#.##");
        	
			public void onClick(View arg0) {
				valorTotal=0;
				if(cbJava.isChecked())
					valorTotal += 150.25;
				if(cbDelphi.isChecked())
					valorTotal += 100.00;
				if(cbRuby.isChecked())
					valorTotal += 99.66;
				if(cbPhp.isChecked())
					valorTotal += 60.00;
				if(cbAndroid.isChecked())
					valorTotal += 62.00;
				
				etValor.setText(String.valueOf((formateador.format(valorTotal))+ " $"));
				if(valorTotal > 300){
					AlertDialog.Builder alerta = new AlertDialog.Builder(VentasProductosActivity.this);
					alerta.setTitle("Fucking Amazing");	
					alerta.setNeutralButton("Ok", null);
					alerta.setMessage("La compra ha superado los 300$. You Rock!");
					alerta.show();
				}
			}
		});
        
    }
}