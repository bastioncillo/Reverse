/* Clase encargada del control gen�rico por consola. Vale para cualquier juego.
 * Cuenta con variables privadas para partida, introducci�n de datos, una factor�a de juego, dos jugadores y un movimiento.
 * */

package pr3.control;

import pr3.control.factorias.*;
import pr3.logica.*;
import pr3.movimientos.Movimiento;
import pr3.jugadores.*;
import java.util.Scanner;

public class ControlConsola {
	
	private Partida partida;
	private Scanner in;
	private FactoriaJuego factoria;
	private Jugador jugador1, jugador2;
	private Movimiento mov;
	
	/*----------------------- CONSTRUCTORA -------------------*/
	/* Recibe una partida, un scanner y una factor�a 
	 * Actualiza los valores de partida, in y factoria seg�n lo recibido por par�metro.
	 * Se crean, por defecto, dos jugadores humanos mediante el m�todo creaJugadorHumano de factoria.
	 * */
	public ControlConsola(Partida partida, Scanner sc, FactoriaJuego fj){
		this.partida = partida;
		this.in = sc;
		this.factoria = fj;
		this.jugador1 = this.factoria.creaJugadorHumano(this.in);
		this.jugador2 = this.factoria.creaJugadorHumano(this.in);
	}
	
	/*------------------- RESET -------------------------*/
	/* Recibe una factor�a de juego
	 * Cambia el juego al especificado por la factor�a recibida por par�metro invocando al m�todo cambioJuego de partida.
	 * */
	private void cambioJuego(FactoriaJuego f){
		this.partida.cambiarJuego(f.creaReglas());
	}
	
	/*-------------------- UNDO -------------------------*/
	/* Invoca al m�todo deshacer de partida para deshacer el �ltimo movimiento.
	 * */
	public void undo(){
		this.partida.deshacer();
	}
	
	/*------------------- CAMBIAR JUGADOR ----------------*/
	/* Recibe el color de ficha y el tipo de jugador.
	 * Seg�n el color y tipo de jugador recibido por par�metro, se cambia el jugador elegido invocando el m�todo correspondiente de factoria.
	 * Suponemos que jugador1 es siempre blancas y jugador2 es siempre negras.
	 * Es el �nico m�todo que no utiliza partida para realizar una acci�n.
	 * */
	public void cambiarJugador(Ficha color, String tipoJugador){
		if(color == Ficha.BLANCA){
			if(tipoJugador.equalsIgnoreCase("aleatorio")){
				this.jugador1 = this.factoria.creaJugadorAleatorio();
			}else if(tipoJugador.equalsIgnoreCase("humano")){
				this.jugador1 = this.factoria.creaJugadorHumano(this.in);
			}
		}else if(color == Ficha.NEGRA){
			if(tipoJugador.equalsIgnoreCase("aleatorio")){
				this.jugador2 = this.factoria.creaJugadorAleatorio();
			}else if(tipoJugador.equalsIgnoreCase("humano")){
				this.jugador2 = this.factoria.creaJugadorHumano(this.in);
			}
		}
	}
	
	/*----------------------- PONER ----------------------*/
	/* Invoca al m�todo getMovimiento de partida (que utiliza factoria y el jugador correspondiente) para colocar una ficha en el tablero.
	 * No comprueba si el movimiento es v�lido, sino que se limita a ponerla si recibe confirmaci�n de que lo es.
	 * Siempre que el movimiento no sea nulo, se realiza el movimiento indicado invocando a ejecutaMovimiento en partida.
	 * */
	public void poner(){
		if(this.partida.getTurno() == Ficha.BLANCA){
			this.mov = this.partida.getMovimiento(this.factoria, this.jugador1);
		}else{
			this.mov = this.partida.getMovimiento(this.factoria, this.jugador2);
		}
		if(this.mov != null){
			this.partida.ejecutaMovimiento(this.mov);
		}
	} 
	
	/*--------------------- REINICIAR ---------------*/
	/* Invoca al m�todo reiniciar de partida para que reinicie la partida por completo.
	 * */
	public void reiniciar(){
		this.partida.reset();
	}
	
	/*--------------------- FINALIZAR -----------------*/
	/* Este m�todo no tiene uso.
	 * */
	public void finalizar(){}
	
	/*-------------------- FINALIZADA -----------------*/
	/* Comprueba si la partida ha finalizado. Por defecto est� a false -> no ha acabado.
	 * Si recibe confirmaci�n de getGanador de partida de que s� existe un ganador, se pone a true.
	 * Si recibe confirmaci�n de getHaytablas de partida de que hay tablas, tambi�n se pone a true.
	 * */
	public boolean finalizada(){
		boolean finDelJuego = false;
		if(this.partida.getGanador() != null){
			finDelJuego = true;
		}else if(this.partida.getHayTablas()){
			finDelJuego = true;
		}
		return finDelJuego;
	}
	
	/*--------------------- CREA JUEGO -----------------*/
	/* Recibe una cadena de caracteres, un ancho y un alto.
	 * Inicializa la factor�a al modo de juego recibido por par�metro (seg�n el string) creando el objeto correspondiente.
	 * La comprobaci�n de si es v�lido se realiza en vistaConsola.
	 * */
	public void creaJuego(String jg, int ancho, int alto){
		if(jg.equalsIgnoreCase("c4")){
			this.factoria = new FactoriaJuegoConecta4();
		}else if(jg.equalsIgnoreCase("co")){
			this.factoria = new FactoriaJuegoComplica();
		}else if(jg.equalsIgnoreCase("gr")){
			this.factoria = new FactoriaJuegoGravity(alto, ancho);
		}else{
			this.factoria = new FactoriaJuegoReversi();
		}
		this.jugador1 = this.factoria.creaJugadorHumano(in);
		this.jugador2 = this.factoria.creaJugadorHumano(in);
		cambioJuego(this.factoria);
	}
	
	/*---------------------- MOSTRAR AYUDA --------------------*/
	/* Invoca al m�todo muestraAyuda de partida para mostrar la ayuda.
	 * */
	public void mostrarAyuda(){
		this.partida.muestraAyuda();
	}
	
	/*-------------------- ADD OBSERVER ---------------*/
	/* Invoca al m�todo addObservador de partida para a�adir un observador.
	 * */
	public void addObservador(Observador o){
		this.partida.addObservador(o);
	}
	
	/*------------------REMOVE OBSERVER --------------*/
	/* Este m�todo no se utiliza
	 * */
	public void removeObservador(Observador o){}
}
