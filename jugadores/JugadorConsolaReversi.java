/* Clase que controla un jugador humano para Reversi.
 * Invoca a la factoría correspondiente utilizando la información proporcionada por el usuario.
 * Como esta puede ser incorrecta, también lanza excepciones.
 * */

package pr3.jugadores;

import java.util.Scanner;
import pr3.control.factorias.FactoriaJuego;
import pr3.logica.Ficha;
import pr3.logica.Tablero;
import pr3.movimientos.Movimiento;

public class JugadorConsolaReversi extends Jugador{
	private Scanner in;
	
	public JugadorConsolaReversi(Scanner sc){
		this.in = sc;
	}
	
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) 
			throws CaracterInvalido, CasillaOcupada, FilaIncorrecta, ColumnaIncorrecta {
		return JugadorConsolaComun.getMovimientoGR_RE(factoria, tab, color, this.in);
	}

}
