/* Clase abstracta que recoge todos los m�todos abstractos de las reglas del juego.
 * iniciarTablero(): Inicia un tablero seg�n la especificaci�n del juego.
 * comprobarGanador(): Comprueba si uno de los dos jugadores ha ganado.
 * hayTablas(): Comprueba si se han producido tablas.
 * siguienteTurno(): Pasa el turno al siguiente jugador.
 * dibujarTablero(): Muestra el tablero actual por pantalla.
 * juegaPrimero(): Indica qui�n juega primero.
 * juegoActual(): Indica a qu� juego se est� jugando.
 * */

package pr3.juego;

import pr3.logica.Ficha;
import pr3.logica.Tablero;
import pr3.movimientos.Movimiento;
import pr3.logica.TipoJuego;

public abstract class ReglasJuego {
	public abstract Tablero iniciarTablero();
	public abstract Ficha comprobarGanador(Tablero tab, Movimiento mov);
	public abstract boolean hayTablas(Tablero tab, Movimiento mov);
	public abstract Ficha siguienteTurno(Ficha turno, Tablero tab);
	public abstract String dibujarTablero(Tablero tab);
	public abstract Ficha juegaPrimero();
	public abstract TipoJuego juegoActual();
}
