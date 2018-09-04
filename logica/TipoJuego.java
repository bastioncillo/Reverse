/* Clase que se utiliza como objeto para indicar a qué juego se está jugando.
 * */

package pr3.logica;
///////////////////////////////////////////////
public enum TipoJuego {
	C4, CO, GR, RE;
	
	public String toString() {
		String simbolo = " ";
		if (this == C4) {
			simbolo = "Conecta 4";
		}
		else if (this == CO) {
			simbolo = "Complica";
		}
		else if(this == GR){
			simbolo = "Gravity";
		}else{
			simbolo = "Reversi";
		}
		return simbolo;
	}
///////////////////////////////////////////////
}
