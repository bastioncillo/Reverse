/* Clase que extiende FactoriaJuego que contiene los métodos específicos que utiliza Gravity.
 * Contiene unas variables privadas para el alto y el ancho que se actualizan en la constructora según establezca el usuario.
 * creaReglas(): Crea un nuevo objeto ReglasGravity con el alto y el ancho especificados por el usuario.
 * creaMovimiento(): Crea un nuevo objeto MovimientoGravity (recibe fila, columna y color de la ficha).
 * creaJugadorAleatorio(): Crea un nuevo objeto JugadorAleatorioGravity.
 * creaJugadorHumano(): Crea un nuevo objeto JugadorAleatorioGravity (recibe el método Scanner para recibir información del usuario).
 * */

package pr3.control.factorias;

import java.util.Scanner;
import pr3.juego.*;
import pr3.jugadores.*;
import pr3.logica.Ficha;
import pr3.movimientos.*;

public class FactoriaJuegoGravity extends FactoriaJuego{
	private int alto, ancho;
	
	public FactoriaJuegoGravity(int alto, int ancho){
		this.alto = alto;
		this.ancho = ancho;
	}
		
	public ReglasJuego creaReglas() {
		return new ReglasGravity(this.alto, this.ancho);
	}

	public Movimiento creaMovimiento(int fila, int col, Ficha color) {
		return new MovimientoGravity(fila, col, color);
	}

	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioGravity();
	}

	public Jugador creaJugadorHumano(Scanner sc) {
		return new JugadorConsolaGravity(sc);
	}
}
