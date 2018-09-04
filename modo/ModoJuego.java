/* Interfaz genérica que contiene los métodos comunes de los modos de juego.
 * */

package pr3.modo;

public interface ModoJuego {
	void comenzar();
	void terminar();
	void deshacerPulsado();
	void tableroPulsado();
}
