package ar.com.iariel.game.pantallas;
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
import java.util.Random;
//-- La clase debera hacer lo siguiente :
//-- 1º Hacer un seguimiento del barco y su tripulacion
//-- 2º Colocar los botines en el oceano
//-- 3º Mover a Jolly Roger usando el "DeltaTime" hacer que se mueva a la siguiente celda cada medio segundo
//-- 4º Comprobar si el barco ha capturado un botin o ha capturado un tripilante
//-- 5º Hacer un seguimiento de la puntuacion
//-- 6º Incrementar la velocidad del barco tras capturar 10 botines
//-- 7º Hacer un seguimiento de si el barco ha sido undido
//-- "DeltaTime" es lo consideramos como acciones basadas en tiempo
public class Mundo {
	//-- Definimos las constantes --//
	static final int MUNDO_ANCHO = 10;//Definimos el ancho del mundo
    static final int MUNDO_ALTO = 13;//Definimos el alto del mundo 
    static final int INCREMENTO_PUNTUACION = 10;//Definimos la puntuancion con la que cada vez Jolly Roger se apodera de un tesoro
    static final float TICK_INICIAL = 0.5f;//El intervalo de tiempo incial usado para avanzar
    static final float TICK_DECREMENTO = 0.05f;//El intervalo de aceleramiento cada vez que Jolly Roger vaya consumiendo mas botines

    public JollyRoger jollyroger;//Define una instancia JollyRoger
    public Botin botin;//Define una instancia Botin
    public boolean finalJuego = false;//Variable que define el final del juego
    public int puntuacion = 0;//Para almacenar la puntucion actual

    //-- Array 2D para reclutar un nuevo tripulante --//
    boolean campos[][] = new boolean[MUNDO_ANCHO][MUNDO_ALTO];
    Random random = new Random();//Define una instancia Random para colocar numeros aleatorios
    float tiempoTick = 0;//Variable acumuladora de tiempo al cual le pasaremos el frame delta time
    static float tick = TICK_INICIAL;//Duracion actual de un tick que define la velocidad a la cual tendra que ir avanzando

    //-- Definimos un  constructor --//
    public Mundo() {
        jollyroger = new JollyRoger();//Se hace la instancia 
        colocarBotin();//Colocamos el primer botin ramdom
    }

    //-- Metodo que calcula donde se va  colocar el botin --//
    private void colocarBotin() {
    	//-- Empcezamos por limpiar todas la celdas --//
        for (int x = 0; x < MUNDO_ANCHO; x++) {
            for (int y = 0; y < MUNDO_ALTO; y++) {
                campos[x][y] = false;
            }
        }
        //-- Confiuguramos todas las celdas ocupadas por holly roger y su tripulacion--//
        int len = jollyroger.partes.size();
        for (int i = 0; i < len; i++) {
            Tripulacion parte = jollyroger.partes.get(i);
            campos[parte.x][parte.y] = true;
        }

        //-- Escaneamos el array buscando una celda libre empezando en una poscion aleatoria --//
        int botinX = random.nextInt(MUNDO_ANCHO);
        int botinY = random.nextInt(MUNDO_ALTO);
        while (true) {
            if (campos[botinX][botinY] == false)
                break;
            botinX += 1;
            //-- Una vez que encontramos una posicion libre --//
            if (botinX >= MUNDO_ANCHO) {
            	botinX = 0;
            	botinY += 1;
                if (botinY >= MUNDO_ALTO) {
                	botinY = 0;
                }
            }
        }
        //-- Llevamos a la creacion de un nuevo botin de algun tipo aleatorio --//
        botin = new Botin(botinX, botinY, random.nextInt(3));
    }

    //-- Metodo responsable de actualizar el mundo basandose para ello en el Delta Time --//
    //-- El metodo sera llamado en cada fotograma en la pantalla del juego --//
    public void update(float deltaTime) {
        if (finalJuego)//Verificamos si el juego ha finalizado
        	
            return;

        tiempoTick += deltaTime;//Si no esta finalizado el juego le añadimos un delta time a nuestro acomulador
        //-- Recorrera la cantidad de tick que hallan sido acumulados --//
        while (tiempoTick > tick) {
        	tiempoTick -= tick;//Subtraemos el intervalo tick desde el acumulador
            jollyroger.avance();//Le decimos a Jolly Roger que avance
            //-- Comprobamos si se ha tocado asi mismo --//
            if (jollyroger.comprobarChoque()) { 
            	finalJuego = true;        //Cambiamos el valor de la varible para informar del game over        
               return;//Salimos
            }
            //-- Comprobamos si holly roger a tocado un botin para incrementar la puntuacion y la tripulacion--//
            Tripulacion head = jollyroger.partes.get(0);
            if (head.x == botin.x && head.y == botin.y) {
                puntuacion += INCREMENTO_PUNTUACION;
                jollyroger.abordaje();//Llamamos al metodo Jolly Roger abordaje
                //-- Verificamos por la cantidad de partes con la que esta compuesta holly roger si esta con la cantidad de celdas del mundo--//
                if (jollyroger.partes.size() == MUNDO_ANCHO * MUNDO_ALTO) {
                    finalJuego = true;//En el caso de que sea asi tenemos que finalizar el juego
                    return;
                } else {
                	colocarBotin();//Colocamos un nuevo botin 
                }
                //-- Verificamos si holly roger a abordado 10 botines mas si ese el caso nuestro umbral va a ir redunciendose--//
                if (puntuacion % 100 == 0 && tick - TICK_DECREMENTO > 0) {
                    tick -= TICK_DECREMENTO;//El tick sera mas corto  y hara que Holly Roger se mueva mas rapido
                }
            }
        }
    }
}
/**
 * Version 1.0.0
 * @author Ariel Duarte 
 * Date: 22/07/2012
 */
