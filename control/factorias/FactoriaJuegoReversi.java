/* Clase que extiende FactoriaJuego que contiene los métodos específicos que utiliza Reversi.
 * creaReglas(): Crea un nuevo objeto ReglasReversi.
 * creaMovimiento(): Crea un nuevo objeto MovimientoReversi (recibe fila, columna y color de la ficha).
 * creaJugadorAleatorio(): Crea un nuevo objeto JugadorAleatorioReversi.
 * creaJugadorHumano(): Crea un nuevo objeto JugadorConsolaReversi (recibe el método Scanner para recibir información del usuario).
 * */

package pr3.control.factorias;

import java.util.Scanner;
import pr3.juego.*;
import pr3.jugadores.*;
import pr3.logica.Ficha;
import pr3.movimientos.*;

public class FactoriaJuegoReversi extends FactoriaJuego{

	public ReglasJuego creaReglas() {
		return new ReglasReversi();
	}

	public Movimiento creaMovimiento(int fila, int col, Ficha color) {
		return new MovimientoReversi(fila, col, color);
	}

	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioReversi();
	}

	public Jugador creaJugadorHumano(Scanner sc) {
		return new JugadorConsolaReversi(sc);
	}
}
