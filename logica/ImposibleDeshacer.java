/* Clase que se utiliza como excepción cuando es imposible deshacer un movimiento.
 * */

package pr3.logica;

@SuppressWarnings("serial")
public class ImposibleDeshacer extends Exception{
	public ImposibleDeshacer(String string){
		super(string);
	}
}
