package ar.com.iariel.game.marco;

public interface Juego {
	public Input getInput();//Obtener las entradas de eventos de usuario

    public FileIO getFileIO();//Obtener los archivos

    public Graficos getGraphics();//Obtener los graficos

    public Audio getAudio();//Obtener audios

    public void setScreen(Pantalla pantalla);//Permitira configura cual es la pantalla actual del juego

    public Pantalla getCurrentScreen();//Devolvera la pantalla activa actual en cada momento

    //--Este metodo se implementara en la clase abstracta pantalla--//
    public Pantalla getStartScreen();//Permitira determinar cual es la primera pantalla con la que se inicia nuestro juego
}
