/* Clase que se utiliza como excepci�n para cuando la columna solicitada no es correcta.
 * */

package pr3.jugadores;

@SuppressWarnings("serial")
public class ColumnaIncorrecta extends Exception{
	public ColumnaIncorrecta(String string){
		super(string);
	}
}
