/* Clase que se utiliza como excepci�n para cuando la fila solicitada es incorrecta.
 * */

package pr3.jugadores;

@SuppressWarnings("serial")
public class FilaIncorrecta extends Exception{
	public FilaIncorrecta(String string) {
		super(string);
	}
}
