/* Clase que se utiliza como excepción concreta para cuando la casilla solicitada está ocupada.
 * */

package pr3.jugadores;

@SuppressWarnings("serial")
public class CasillaOcupada extends Exception{
	public CasillaOcupada(String string){
		super(string);
	}
}
