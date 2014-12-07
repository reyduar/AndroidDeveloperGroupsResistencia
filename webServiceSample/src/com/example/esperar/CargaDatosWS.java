package com.example.esperar;

import java.io.File;
import java.io.FileOutputStream;

import java.io.InterruptedIOException;
import java.io.OutputStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.ksoap2.transport.HttpTransport;
import org.ksoap2.transport.HttpTransportSE;
import org.kobjects.base64.Base64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapSerializationEnvelope;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;

public class CargaDatosWS {
	public String getClima(String ciudad,String pais){
		String res=null;
		//Se crea un objeto de tipo SoapObjecto. Permite hacer el llamado al WS
		SoapObject rpc;
		rpc = new SoapObject("http://www.webserviceX.NET", "GetWeather");
		//De acuerdo a la documentacion del ws, hay 2 parametros que debemos pasar nombre de la ciuda y del pais
		//Para obtener informacion del WS , se puede consultar http://www.webservicex.net/globalweather.asmx?WSDL
		rpc.addProperty("CityName", ciudad);
		rpc.addProperty("CountryName", pais);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		//Se establece que el servicio web esta hacho en .net
		envelope.dotNet = true;
		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
		//Para acceder al WS se crea un objeto de tipo HttpTransportSE , esto es propio de la libreia KSoap
		HttpTransportSE androidHttpTransport= null;
		try {
			String conexion = "http://www.webservicex.net/globalweather.asmx";
			androidHttpTransport = new HttpTransportSE(conexion);
			androidHttpTransport.debug = true;
			//Llamado al servicio web . Son el nombre del SoapAction, que se encuentra en la documentacion del servicio web y el objeto envelope
			androidHttpTransport.call("http://www.webserviceX.NET/GetWeather", envelope);
			//Respuesta del Servicio web
			res = envelope.getResponse().toString();
		}catch (Exception e){
			System.out.println(e.getMessage());
			res=e.getMessage();
		}

		return res;
		
	}

	
}