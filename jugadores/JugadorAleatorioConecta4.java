/* Clase que controla un jugador autom�tico para Conecta 4.
 * Elige una columna al azar e invoca a la factor�a correspondiente.
 * */

package pr3.jugadores;

import java.util.Random;
import pr3.control.factorias.FactoriaJuego;
import pr3.logica.Ficha;
import pr3.logica.Tablero;
import pr3.movimientos.Movimiento;

public class JugadorAleatorioConecta4 extends Jugador{
	// ------------ OBTENER MOVIMIENTO ALEATORIO ---------------- //
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) {
		int columna = 0;
		Random random = new Random();
		//Creaci�n de la columna aleatoria dentro de los m�rgenes del tablero
		do{
			columna = random.nextInt(tab.getAncho());
		}while(tab.getFicha(columna, 0) != Ficha.VACIA);
		return factoria.creaMovimiento(0, columna, color); 
	}
}