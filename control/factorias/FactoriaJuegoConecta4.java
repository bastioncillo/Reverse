/* Clase que extiende FactoriaJuego que contiene los métodos específicos que utiliza Conecta 4.
 * creaReglas(): Crea un nuevo objeto ReglasConecta4.
 * creaMovimiento(): Crea un nuevo objeto MovimientoConecta4 (recibe fila, columna y color de la ficha).
 * creaJugadorAleatorio(): Crea un nuevo objeto JugadorAleatorioConecta4.
 * creaJugadorHumano(): Crea un nuevo objeto JugadorConsolaConecta4 (recibe el método Scanner para recibir información del usuario).
 * */

package pr3.control.factorias;

import java.util.Scanner;
import pr3.juego.*;
import pr3.jugadores.*;
import pr3.logica.Ficha;
import pr3.movimientos.*;

public class FactoriaJuegoConecta4 extends FactoriaJuego{

	public ReglasJuego creaReglas() {
		return new ReglasConecta4();
	}

	public Movimiento creaMovimiento(int fila, int col, Ficha color) {
		return new MovimientoConecta4(fila, col, color);
	}

	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioConecta4();
	}

	public Jugador creaJugadorHumano(Scanner sc) {
		return new JugadorConsolaConecta4(sc);
	}
}
