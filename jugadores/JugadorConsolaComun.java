/* Clase que controla un jugador humano para las características comunes de los distintos juegos.
 * Invoca a la factoría correspondiente utilizando la información proporcionada por el usuario.
 * Como esta puede ser incorrecta, también lanza excepciones.
 * Se distinguen dos getMovimiento: el primero para C4 y CO, el segundo para GR y RE.
 * */

package pr3.jugadores;

import java.util.InputMismatchException;
import java.util.Scanner;

import pr3.control.factorias.FactoriaJuego;
import pr3.logica.Ficha;
import pr3.logica.Tablero;
import pr3.movimientos.Movimiento;

public class JugadorConsolaComun {
	public static Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color, Scanner in) throws CaracterInvalido, ColumnaIncorrecta {
		int columna = 0;
		try {
			System.out.println("Introduce la columna: ");
			columna = in.nextInt() - 1;
			in.nextLine(); //Para evitar el salto de linea
			if(columna < 0 || columna >= tab.getAncho()){
				throw new ColumnaIncorrecta("Columna incorrecta");
			}
		} catch (InputMismatchException e) {
			in.nextLine();
		    throw new CaracterInvalido("Caracter invalido"); 
		}
		return factoria.creaMovimiento(0, columna, color);
	}

	public static Movimiento getMovimientoGR_RE(FactoriaJuego factoria,	Tablero tab, Ficha color, Scanner in) 
			throws FilaIncorrecta, CasillaOcupada, CaracterInvalido, ColumnaIncorrecta {
		int fila = 0;
		int columna = 0;
		try{
			System.out.print("Introduce la fila: ");
			fila = in.nextInt() - 1;
			in.nextLine();
			if(fila < 0 || fila >= tab.getAlto()){
				throw new FilaIncorrecta("Fila incorrecta");
			}
			System.out.print("Introduce la columna: ");
			columna = in.nextInt() - 1;
			in.nextLine(); //Para evitar el salto de linea
			if(columna < 0 || columna >= tab.getAlto()){
				throw new ColumnaIncorrecta("Columna incorrecta");
			}
			if(tab.getFicha(columna, fila) != Ficha.VACIA){
				throw new CasillaOcupada("Casilla ocupada");
			}
		}catch(InputMismatchException e){
			in.nextLine();
			throw new CaracterInvalido("Caracter inválido");
		}
		return factoria.creaMovimiento(fila, columna, color);
	}
}
