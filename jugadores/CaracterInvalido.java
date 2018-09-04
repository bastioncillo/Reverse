/* Clase que funciona como excepción concreta para cuando se usa un carácter inválido.
 * */

package pr3.jugadores;

@SuppressWarnings("serial")
public class CaracterInvalido extends Exception {
	public CaracterInvalido(String string) {
		super(string);
	}
}
