/* Clase que contiene todos los métodos necesarios para crear un Observador.
 * */

package pr3.logica;

public interface Observador {
	void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador);
	void onMovimientoStart(Ficha turno, TableroInmutable tab);
	void onMovimientoEnd(TableroInmutable tab);
	void onUndo(TableroInmutable tab, boolean hayMas);
	void onResetPartida(TableroInmutable tabIni, Ficha turno);
	void onUndoNotPossible();
	void onCambioTurno(Ficha turno, TableroInmutable tab);
	void onMovimientoIncorrecto(String explicacion);
	void onCambioJuego(TableroInmutable tab,
	TipoJuego tipo, Ficha turno);
	void onMuestraAyuda();
	void onStart(TableroInmutable tabIn, Ficha turno);
}
