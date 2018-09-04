/* Clase que contiene las reglas específicas de Gravity.
 * Tiene una constructora especial, ya que Gravity permite al usuario escoger el tamaño del tablero.
 * */

package pr3.juego;

import pr3.logica.TipoJuego;
import pr3.logica.Ficha;
import pr3.logica.Tablero;
import pr3.movimientos.Movimiento;

public class ReglasGravity extends ReglasJuego {
	
	public static int ALTO = 10;
	public static int ANCHO = 10;
	private int alto, ancho;
	
	// La constructora tiene parámetros porque el usuario puede decidir el tamaño del tablero.
	// Si recibe valores incorrectos utiliza los valores por defecto (10).
	public ReglasGravity(int alto, int ancho) {
		if(alto <= 1 && ancho <= 1){
			this.alto = ALTO;
			this.ancho = ANCHO;
		}else if(alto <= 1 && ancho > 1){
			this.alto = ALTO;
			this.ancho = ancho;
		}else if(alto > 1 && ancho <= 1){
			this.alto = alto;
			this.ancho = ANCHO;
		}else{
			this.alto = alto;
			this.ancho = ancho;
		}
	}

	// ----------------------------------------- INICIAR TABLERO --------------------------------
	// Inicializa el tablero con la configuración por defecto de Gravity.
	public Tablero iniciarTablero() {
		return new Tablero(this.ancho, this.alto);
	}
	
	// ----------------------------------------- JUEGA PRIMERO -------------------------------------------
	// Devuelve el color de la ficha que mueve primero
	public Ficha juegaPrimero(){
		return Ficha.BLANCA;
	}
	
	// ------------------------------------------ JUEGO ACTUAL ---------------------------------------------
	// Devuelve el tipo de juegoal que se está jugando
	public TipoJuego juegoActual(){
		return TipoJuego.GR;
	}
	
	// ---------------------------------------- COMPROBAR GANADOR ------------------------------------------
	// Comprueba si hay un ganador de la partida según la útima ficha introducida.
	public Ficha comprobarGanador(Tablero tab, Movimiento mov) {
		Ficha ganador;
		boolean c4N = c4Negras(tab), c4B = c4Blancas(tab);
		if(c4N && !c4B){
			ganador = Ficha.NEGRA;
		}
		else if(!c4N && c4B){
			ganador = Ficha.BLANCA;
		}
		else{
			ganador = null;
		}
		return ganador;
	}
	
	//------------------------------------- COMPROBAR GANADOR NEGRAS ------------------------------------
	//Comprueba si las fichas negras han hecho 4 en raya
	private boolean c4Negras(Tablero tab){
		return ReglasComunes4EnLinea.c4Negras(tab);
	}
		
	//------------------------------------- COMPROBAR GANADOR BLANCAS ------------------------------------
	//Comprueba si las fichas blancas han hecho 4 en raya
	private boolean c4Blancas(Tablero tab){
		return ReglasComunes4EnLinea.c4Blancas(tab);
	}
	
	//-------------------------- COMPROBAR SI HAY TABLAS ----------------------------------
	//Cuenta el número de posiciones ocupadas en el tablero, para ver si hay tablas.
	public boolean hayTablas(Tablero tab, Movimiento mov) {
		return ReglasComunes4EnLinea.hayTablas(tab);
	}
	
	// ----------------------------------- CAMBIAR TURNO ---------------------------------
	// Cambia el color de la ficha a introducir según el turno que corresponda.
	public Ficha siguienteTurno(Ficha turno, Tablero tab) {
		return ReglasComunes4EnLinea.siguienteTurno(turno, tab);
	}

	// ---------------------------- DIBUJAR TABLERO ----------------------------
	// Dibuja el tablero colocando las fichas correspondientes.
	public String dibujarTablero(Tablero tab) {
		return tab.dibujarTablero();
	}
}
