/* Clase que contiene las reglas específicas de Conecta 4.
 * */

package pr3.juego;

import pr3.logica.TipoJuego;
import pr3.logica.Ficha;
import pr3.logica.Tablero;
import pr3.movimientos.Movimiento;

public class ReglasConecta4 extends ReglasJuego {

	public static int ANCHO = 7;
	public static int ALTO = 7;
		
	public ReglasConecta4() {}
	
	// --------------------------------------- INICIAR TABLERO ---------------------------------------
	// Crea un tablero según los datos introducidos.
	public Tablero iniciarTablero() {
		return new Tablero(ANCHO, ALTO);
	}
	
	// ----------------------------------------- JUEGA PRIMERO -------------------------------------------
	// Devuelve el color de la ficha que mueve primero
	public Ficha juegaPrimero(){
		return Ficha.BLANCA;
	}
	
	// ------------------------------------------ JUEGO ACTUAL ---------------------------------------------
	// Devuelve el tipo de juegoal que se está jugando
	public TipoJuego juegoActual(){
		return TipoJuego.C4;
	}
	
	// ---------------------------------------- COMPROBAR GANADOR ------------------------------------------
	// Comprueba si hay un ganador de la partida según la útima ficha introducida.
	public Ficha comprobarGanador(Tablero tab, Movimiento mov) {
		Ficha ganador;
		if(comprobarFila(mov.getCol(), mov.getFila(), tab)){
			ganador = mov.getTurno();
		}
		else if(comprobarColumna(mov.getCol(), mov.getFila(), tab)){
			ganador = mov.getTurno();
		}
		else if(comprobarDiagonalAscendente(mov.getCol(), mov.getFila(), tab)){
			ganador = mov.getTurno();
		}
		else if(comprobarDiagonalDescendente(mov.getCol(), mov.getFila(), tab)){
			ganador = mov.getTurno();
		}
		else{
			ganador = null;
		}
		return ganador;
	}
	
	// -------------------------------------- COMPROBAR FILA --------------------------------------------
	// Comprueba si se han enlazado cuatro fichas del mismo color en una fila del tablero.
	private boolean comprobarFila(int col, int fila, Tablero tab){
		return ReglasComunes4EnLinea.comprobarFila(col, fila, tab);
	}
	
	// ----------------------------------- COMPROBAR COLUMNA ----------------------------------
	// Comprueba si hay cuatro fichas del mismo color en una columna del tablero.
	private boolean comprobarColumna(int col, int fila, Tablero tab){
		return ReglasComunes4EnLinea.comprobarColumna(col, fila, tab);
	}
		
	// --------------------------------------- COMPROBAR DIAGONAL DESCENDENTE -------------------------------
	// Comprueba si hay cuatro fichas del mismo color consecutivas en una diagonal del tablero.
	private boolean comprobarDiagonalDescendente(int col, int fila, Tablero tab){
		boolean hayCuatro = ReglasComunes4EnLinea.comprobarDiagonalDescendente(col, fila, tab);
		return hayCuatro;
	}
	
	// --------------------------------------- COMPROBAR DIAGONAL ASCENDENTE -------------------------------
	// Comprueba si hay cuatro fichas del mismo color consecutivas en una diagonal del tablero.
	private boolean comprobarDiagonalAscendente(int col, int fila, Tablero tab){
		return ReglasComunes4EnLinea.comprobarDiagonalAscendente(col, fila, tab);
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