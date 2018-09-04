/* Clase abstracta que contiene m�todos abstractos utilizados durante el desarrollo de una partida gen�rica.
 * creaReglas(): Crea el conjunto de reglas que se van a emplear durante la partida.
 * creaMovimiento(): Crea el movimiento realizado en ese turno (recibe fila, columna y color de la ficha).
 * creaJugadorAleatorio(): Crea un jugador aleatorio.
 * creaJugadorHumano(): Crea un jugador humano (recibe el m�todo Scanner para recibir informaci�n del usuario).
 * */

package pr3.control.factorias;

import java.util.Scanner;
import pr3.juego.ReglasJuego;
import pr3.jugadores.Jugador;
import pr3.logica.Ficha;
import pr3.movimientos.Movimiento;

public abstract class FactoriaJuego {
	public abstract ReglasJuego creaReglas();
	public abstract Movimiento creaMovimiento(int fila, int col, Ficha color);
	public abstract Jugador creaJugadorAleatorio();
	public abstract Jugador creaJugadorHumano(Scanner sc);
}
