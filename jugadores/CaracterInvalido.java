/* Clase que funciona como excepci�n concreta para cuando se usa un car�cter inv�lido.
 * */

package pr3.jugadores;

@SuppressWarnings("serial")
public class CaracterInvalido extends Exception {
	public CaracterInvalido(String string) {
		super(string);
	}
}
