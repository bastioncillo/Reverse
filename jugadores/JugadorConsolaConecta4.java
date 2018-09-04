/* Clase que controla un jugador humano para Conecta 4.
 * Invoca a la factor�a correspondiente utilizando la informaci�n proporcionada por el usuario.
 * Como esta puede ser incorrecta, tambi�n lanza excepciones.
 * */

package pr3.jugadores;

import java.util.Scanner;
import pr3.control.factorias.FactoriaJuego;
import pr3.logica.Ficha;
import pr3.logica.Tablero;
import pr3.movimientos.Movimiento;

public class JugadorConsolaConecta4 extends Jugador{
	private Scanner in;
	public JugadorConsolaConecta4(Scanner sc) {
		this.in = sc;
	}

	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) throws CaracterInvalido, ColumnaIncorrecta {
		return JugadorConsolaComun.getMovimiento(factoria, tab, color, this.in);
	}
}