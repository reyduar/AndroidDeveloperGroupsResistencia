package ar.com.iariel.game.framework.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import android.content.res.AssetManager;
import android.os.Environment;

import ar.com.iariel.game.framework.FileIO;

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */

public class AndroidFileIO implements FileIO {
	
	//-- Creacion de variables --// 
	AssetManager assets;
	String rutaAlmacenamientoExterno;
	
	//-- Creacion de constructor --// 
	public AndroidFileIO(AssetManager assets) {
		this.assets = assets;
		this.rutaAlmacenamientoExterno = Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}

	@Override
	public InputStream leerAsset(String nombreArchivo) throws IOException {
		return assets.open(nombreArchivo);
	}

	@Override
	public InputStream leerArchivo(String nombreArchivo) throws IOException {
		return new FileInputStream(rutaAlmacenamientoExterno + nombreArchivo);
	}

	@Override
	public OutputStream escribirArchivo(String nombreArchivo) throws IOException {
		return new FileOutputStream(rutaAlmacenamientoExterno + nombreArchivo);
	}
}

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
