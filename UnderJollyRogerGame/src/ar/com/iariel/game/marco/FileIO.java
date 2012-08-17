package ar.com.iariel.game.marco;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Ariel Duarte Date: 22/07/2012
 */
public interface FileIO {

	public InputStream leerAsset(String nombreArchivo) throws IOException;

	public InputStream leerArchivo(String nombreArchivo) throws IOException;

	public OutputStream escribirArchivo(String nombreArchivo) throws IOException;
}

/**
 * @author Ariel Duarte Date: 22/07/2012
 */