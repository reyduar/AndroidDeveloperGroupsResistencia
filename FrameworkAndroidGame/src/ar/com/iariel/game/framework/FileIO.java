package ar.com.iariel.game.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public interface FileIO {

	public InputStream leerAsset(String nombreArchivo) throws IOException;

	public InputStream leerArchivo(String nombreArchivo) throws IOException;

	public OutputStream escribirArchivo(String nombreArchivo) throws IOException;
}

/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */