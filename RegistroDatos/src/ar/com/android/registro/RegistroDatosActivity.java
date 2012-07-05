package ar.com.android.registro;

/**
 * @author Ariel Duarte
 * date: 20/06/2012
 */
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistroDatosActivity extends Activity {
	/**
	 * @author Ariel Duarte Definicion de variables
	 */
	Registro oRegistro, oAuxRegistro, oUltimoRegistro, oPrimerRegistro;
	EditText etNombre, etApellido, etTelefono, etEmail;
	int contadorRegistros = 0, posicion = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		oUltimoRegistro = null;
		oPrimerRegistro = null;
		callInicio();

	}

	/**
	 * @author Ariel Duarte Metodo para invocar al layout de registros de datos
	 */
	public void callRegistro() {
		setContentView(R.layout.registro);
		oRegistro = new Registro();
		etNombre = (EditText) findViewById(R.id.ednombre);
		etApellido = (EditText) findViewById(R.id.edapellido);
		etTelefono = (EditText) findViewById(R.id.edtelefono);
		etEmail = (EditText) findViewById(R.id.edemail);
		etNombre.requestFocus();
		Button btFinalizar = (Button) findViewById(R.id.btnfinalizar);
		Button btVolverRegistro = (Button) findViewById(R.id.btnvolverregistro);

		btFinalizar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Metodo para guardar registros
				if (etNombre.getText().toString().equals("")) {
					alertMenssage("Campo obligatorio!", "Ingrese el nombre porfavor.");
					etNombre.requestFocus();
				} else if(etApellido.getText().toString().equals("")){
					alertMenssage("Campo obligatorio!", "Ingrese el apellido porfavor.");
					etApellido.requestFocus();
				} else if(etTelefono.getText().toString().equals("")){
					alertMenssage("Campo obligatorio!", "Ingrese el teléfono porfavor.");
					etTelefono.requestFocus();	 
				} else {
					try {
						// Guardo
						oRegistro.nombre = etNombre.getText().toString();
						oRegistro.apellido = etApellido.getText().toString();
						oRegistro.telefono = etTelefono.getText().toString();
						oRegistro.email = etEmail.getText().toString();

						// Pimer Registro
						if (oPrimerRegistro == null)
							oPrimerRegistro = oRegistro;

						oRegistro.anteriorRegistro = oUltimoRegistro;

						// Ultimo Registro
						if (oUltimoRegistro == null)
							oUltimoRegistro = oRegistro;
						else {
							oUltimoRegistro.proximoRegistro = oRegistro;
							oUltimoRegistro = oRegistro;
						}
						contadorRegistros++;
						alertMenssage("Exito!",
								"Los datos se guardaron exitosamente..");
						callInicio();
					} catch (Exception e) {
						alertMenssage(
								"Error!",
								"Ocurrio un error al guardar los datos"
										+ e.getMessage()
										+ ". \nPorfavor intente de nuevo.");
					}
				}

			}
		});

		btVolverRegistro.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				callInicio();
			}
		});
	}

	/**
	 * @author Ariel Duarte Metodo para personalizar los mensajes de alertas
	 */
	public void alertMenssage(String titulo, String texto) {
		AlertDialog.Builder msg = new AlertDialog.Builder(
				RegistroDatosActivity.this);
		msg.setTitle(titulo);
		msg.setMessage(texto);
		msg.setNeutralButton("Aceptar", null);
		msg.show();
	}

	/**
	 * @author Ariel Duarte Metodo para invocar al layout de consulta de
	 *         registros
	 */
	public void callConsulta() {
		if (contadorRegistros == 0) {
			alertMenssage("Atención!", "No existen registros para consultar.");
			callInicio();
			return;
		}
		posicion = 1;
		setContentView(R.layout.consulta);
		Button btVolverConsulta = (Button) findViewById(R.id.btnvolverconsulta);
		Button btProximo = (Button) findViewById(R.id.btnproximo);
		Button btAnterior = (Button) findViewById(R.id.btnanterior);
		/**
		 * Consulta los datos registrados
		 */
		TextView tvNombre = (TextView) findViewById(R.id.tvnombre);
		TextView tvApellido = (TextView) findViewById(R.id.tvapellido);
		TextView tvTelefono = (TextView) findViewById(R.id.tvtelefono);
		TextView tvEmail = (TextView) findViewById(R.id.tvemail);

		oAuxRegistro = oPrimerRegistro;

		tvNombre.setText(oAuxRegistro.nombre);
		tvApellido.setText(oAuxRegistro.apellido);
		tvTelefono.setText(oAuxRegistro.telefono);
		tvEmail.setText(oAuxRegistro.email);

		btProximo.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (posicion == contadorRegistros)
					return;
				posicion++;
				oAuxRegistro = oAuxRegistro.proximoRegistro;
				TextView tvNombre = (TextView) findViewById(R.id.tvnombre);
				TextView tvApellido = (TextView) findViewById(R.id.tvapellido);
				TextView tvTelefono = (TextView) findViewById(R.id.tvtelefono);
				TextView tvEmail = (TextView) findViewById(R.id.tvemail);

				tvNombre.setText(oAuxRegistro.nombre);
				tvApellido.setText(oAuxRegistro.apellido);
				tvTelefono.setText(oAuxRegistro.telefono);
				tvEmail.setText(oAuxRegistro.email);

			}
		});

		btAnterior.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (posicion == 1)
					return;
				posicion--;
				oAuxRegistro = oAuxRegistro.anteriorRegistro;
				TextView tvNombre = (TextView) findViewById(R.id.tvnombre);
				TextView tvApellido = (TextView) findViewById(R.id.tvapellido);
				TextView tvTelefono = (TextView) findViewById(R.id.tvtelefono);
				TextView tvEmail = (TextView) findViewById(R.id.tvemail);

				tvNombre.setText(oAuxRegistro.nombre);
				tvApellido.setText(oAuxRegistro.apellido);
				tvTelefono.setText(oAuxRegistro.telefono);
				tvEmail.setText(oAuxRegistro.email);
			}
		});

		btVolverConsulta.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				callInicio();

			}
		});

	}

	/**
	 * @author Ariel Duarte Metodo para invocar al layout del menu principal
	 */
	public void callInicio() {
		setContentView(R.layout.main);

		Button btRegistro = (Button) findViewById(R.id.btnregistro);
		Button btConsulta = (Button) findViewById(R.id.btnconsulta);

		btRegistro.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				callRegistro();
			}
		});

		btConsulta.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				callConsulta();

			}
		});
	}

}