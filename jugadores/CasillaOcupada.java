/* Clase que se utiliza como excepci�n concreta para cuando la casilla solicitada est� ocupada.
 * */

package pr3.jugadores;

@SuppressWarnings("serial")
public class CasillaOcupada extends Exception{
	public CasillaOcupada(String string){
		super(string);
	}
}
