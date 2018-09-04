/* Clase que controla tableros que no pueden ser modificados. Se utiliza en comprobaciones en la vista.
 * */

package pr3.logica;

public interface TableroInmutable {
	int getAlto();
	int getAncho();
	Ficha getFicha(int col, int fila);
	String dibujarTablero();
}
