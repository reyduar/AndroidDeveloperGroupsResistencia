package ar.android.dev.act;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;

public class AndroidCalcActivity extends Activity {
    /** Called when the activity is first created. */
	EditText etNum1, etNum2, etResultado;
	Button btnSumar, btnRestar, btnMultiplicar, btnDividir, btnLimpiar;
	double num1, num2, resultado;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        etNum1 = (EditText) findViewById(R.calculo.numero1);
        etNum2 = (EditText) findViewById(R.calculo.numero2);
        etResultado = (EditText) findViewById(R.calculo.resultado);
        btnSumar = (Button) findViewById(R.sumar.btnsumar);
        btnRestar = (Button) findViewById(R.restar.btnrestar);
        btnMultiplicar = (Button) findViewById(R.multiplicar.btnmultiplicar);
        btnDividir = (Button) findViewById(R.dividir.btndividir);
        btnLimpiar = (Button) findViewById(R.limpiar.btnlimpiar);
        
        btnSumar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				num1 = Double.parseDouble(etNum1.getText().toString());
				num2 = Double.parseDouble(etNum2.getText().toString());
				resultado = num1+num2;
				etResultado.setText(String.valueOf(resultado));
			}
		});
        
        btnRestar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				num1 = Double.parseDouble(etNum1.getText().toString());
				num2 = Double.parseDouble(etNum2.getText().toString());
				resultado = num1-num2;
				etResultado.setText(String.valueOf(resultado));
				
			}
		});
        
        btnMultiplicar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				num1 = Double.parseDouble(etNum1.getText().toString());
				num2 = Double.parseDouble(etNum2.getText().toString());
				resultado = num1*num2;
				etResultado.setText(String.valueOf(resultado));
				
			}
		});
        
        btnDividir.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				num1 = Double.parseDouble(etNum1.getText().toString());
				num2 = Double.parseDouble(etNum2.getText().toString());
				resultado = num1/num2;
				etResultado.setText(String.valueOf(resultado));
				
			}
		});
        
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				etNum1.setText("");
				etNum2.setText("");
				etResultado.setText("0");
			}
		});
    }
}