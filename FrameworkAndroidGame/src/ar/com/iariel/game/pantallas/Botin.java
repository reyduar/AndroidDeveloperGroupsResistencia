package ar.com.iariel.game.pantallas;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
public class Botin {
	//-- Define tres constantes que codifican el tipo de botin --//
	public static final int TIPO_1 = 0;
    public static final int TIPO_2 = 1;
    public static final int TIPO_3 = 2;
    public int x, y;//Las cordenadas X;Y permitiran situarlo en el oceano
    public int tipo;//El tipo sera una de las tres constantes que se definieron

    //-- Definimos el constructor --//
    public Botin(int x, int y, int tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
    }
}
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
