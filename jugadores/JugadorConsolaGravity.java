/* Clase que controla un jugador humano para Gravity.
 * Invoca a la factor�a correspondiente utilizando la informaci�n proporcionada por el usuario.
 * Como esta puede ser incorrecta, tambi�n lanza excepciones.
 * */

package pr3.jugadores;

import pr3.control.factorias.FactoriaJuego;
import pr3.logica.Ficha;
import pr3.logica.Tablero;
import pr3.movimientos.Movimiento;

import java.util.Scanner;

public class JugadorConsolaGravity extends Jugador{
	private Scanner in;
	public JugadorConsolaGravity(Scanner sc){
		this.in = sc;
	}
	
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) throws 
	CaracterInvalido, CasillaOcupada, FilaIncorrecta, ColumnaIncorrecta {
		return JugadorConsolaComun.getMovimientoGR_RE(factoria, tab, color, this.in);
	}
}