/* Clase que se encarga de llevar a cabo las tareas que se le indican.
 * No comprueba nada, ya que de esto se encargan otras clases.
 * Contiene métodos que pueden ser utilizados por todos los juegos.
 * */

package pr3.logica;

import java.util.ArrayList;

import pr3.control.factorias.FactoriaJuego;
import pr3.juego.ReglasJuego;
import pr3.jugadores.*;
import pr3.modo.ModoJuegoHumano;
import pr3.movimientos.ColumnaLlena;
import pr3.movimientos.ImposibleFlanquear;
import pr3.movimientos.Movimiento;

public class Partida {
	private Tablero tablero;
	private Ficha turno;
	private Ficha ganador;
	private ReglasJuego reglas;
	private Pila pila;
	private TipoJuego tj;
	private boolean hayTablas;
	
	ArrayList<Observador> Obs;
		
	// ----------------------------- CONSTRUCTORA -------------------------------
	public Partida(ReglasJuego reglas){
		this.Obs = new ArrayList<Observador>(); 
		this.reglas = reglas;
		this.tablero = this.reglas.iniciarTablero();
		this.turno = this.reglas.juegaPrimero();
		this.ganador = null;
		this.pila = new Pila();
	}
	
	// ------------------------------- CAMBIO JUEGO -------------------------------------
	// Cambia el tipo de juego
	public void cambiarJuego(ReglasJuego reglas){
		this.turno.getModo().terminar();
		this.reglas = reglas;
		this.tablero = this.reglas.iniciarTablero();
		this.turno = this.reglas.juegaPrimero();
		this.tj = this.reglas.juegoActual();
		this.ganador = null;
		this.pila = new Pila();
		Ficha.BLANCA.cambiarModo(TipoTurno.HUMANO, new ModoJuegoHumano());
		Ficha.NEGRA.cambiarModo(TipoTurno.HUMANO, new ModoJuegoHumano());
		for(Observador o :this.Obs){
			o.onCambioJuego(this.tablero, this.tj, this.turno);
		}
	}
	
	// ------------------------------------- RESET -----------------------------
	// Reinicia la partida
	public void reset(){
		this.tablero = this.reglas.iniciarTablero();
		this.turno = this.reglas.juegaPrimero();
		this.ganador = null;
		this.pila = new Pila();
		Ficha.BLANCA.cambiarModo(TipoTurno.HUMANO, new ModoJuegoHumano());
		Ficha.NEGRA.cambiarModo(TipoTurno.HUMANO, new ModoJuegoHumano());
		for(Observador o: this.Obs){
			o.onResetPartida(this.tablero, this.turno);
		}
	}
	
	// ---------------------------------- GET TURNO ------------------------------
	// Devuelve el turno del jugador
	public Ficha getTurno(){
		return this.turno;
	}
	
	// -------------------------------- GET GANADOR ------------------------------
	// Devuelve el turno del jugador
	public Ficha getGanador(){
		return this.ganador;
	}
	
	// ------------------------------- GET MOVIMIENTO -------------------------------
	// Devuelve un movimiento
	public Movimiento getMovimiento(FactoriaJuego factoria, Jugador jugador){
		String aux;
		try{
			return jugador.getMovimiento(factoria, this.tablero, this.turno);
		}catch(CaracterInvalido ci){
			aux = ci.getMessage(); 
			muestraTab(aux, true);
			return null;
		} catch (CasillaOcupada co) {
			aux = co.getMessage(); 
			muestraTab(aux, true);
			return null;
		} catch (FilaIncorrecta fi) {
			aux = fi.getMessage(); 
			muestraTab(aux, true);
			return null;
		} catch (ColumnaIncorrecta ci) {
			aux = ci.getMessage(); 
			muestraTab(aux, true);
			return null;
		}
	}
	
	// -------------------------------- MOSTRAR TABLERO Y PEDIR COMANDO ------------------------------
	/* Muestra el el tablero y pide la siguiente orden al usuario. También muestra 
	 * el error que se le haya pasado, si es que se le ha pasado alguno*/
	private void muestraTab(String error, boolean hayError){
		for(Observador o: this.Obs){
			if(hayError){
				o.onMovimientoIncorrecto(error);
			}
			o.onMovimientoStart(this.turno, this.tablero);
			o.onMovimientoEnd(this.tablero);
		}
	}
	
	// ---------------------------------- START ---------------------------------------
	//Método para iniciar el tablero de Ventana
	public void onStart(){
		for(Observador o: this.Obs){
			o.onStart(this.tablero, this.turno);
		}
	}
	
	// -------------------------------------- MUESTRA AYUDA ------------------------------------
	// Muestra un testo con pautas para jugar al juego
	public void muestraAyuda(){
		for(Observador o: this.Obs){
			o.onMuestraAyuda();
		}
		muestraTab("", false);
	}
	
	// ------------------------------------- EJECUTAR MOVIMIENTO ----------------------------------------
	// Coloca una ficha en el tablero y comprueba si se acaba la partida o no.
	public void ejecutaMovimiento(Movimiento mov){
		String aux = "";
		try{
			mov.ejecutaMovimiento(this.tablero);
			if(!tablas(mov)){
				this.hayTablas = false;
				this.ganador = this.reglas.comprobarGanador(this.tablero, mov);
			}
			else this.hayTablas = true;
			if(this.ganador == null && !this.hayTablas){
				this.pila.poner(mov);
				this.turno = cambiaTurno();
				muestraTab(aux, false);
			}else{
				for(Observador o : this.Obs){
					o.onPartidaTerminada(this.tablero, this.ganador);
				}
			}
		}catch(ColumnaLlena cl){
			aux = cl.getMessage();
			muestraTab(aux, true);
		} catch (ImposibleFlanquear inf) {
			aux = inf.getMessage();
			muestraTab(aux, true);
		}
	}
	
	// ------------------------------------------ TABLAS --------------------------------------------
	// Comprueba si se han formado tablas en el tablero
	private boolean tablas(Movimiento mov){
		return this.reglas.hayTablas(this.tablero, mov);
	}
	
	// ------------------------------------ GET TABLAS ------------------------------
	// Indica al Control si se han producido tablas
	public boolean getHayTablas(){
		return this.hayTablas;
	}
	
	// ----------------------------------------- CAMBIA TURNO ---------------------------------------
	// Cambia el turno para que juegue el siguiente jugador
	private Ficha cambiaTurno(){
		return this.reglas.siguienteTurno(this.turno, this.tablero);
	}
	
	// ------------------------------------------ DESHACER -----------------------------------------
	// Deshace el último movimiento del jugador
	public void deshacer(){
		Movimiento anterior = this.pila.sacar();
		if(anterior != null){
			anterior.undo(this.tablero);
			this.turno = cambiaTurno();
			for(Observador o: this.Obs){
				o.onCambioTurno(this.turno, this.tablero);
			}
			if(!this.pila.estaVacia()){
				for(Observador o : this.Obs){
					o.onUndo(this.tablero, true);
				}
			}else{
				for(Observador o : this.Obs){
					o.onUndo(this.tablero, false);
				}
			}
		}else{
			for(Observador o : this.Obs){
				o.onUndoNotPossible();
			}
			muestraTab("", false);
		}
	}
	
	// ----------------------------------- DETENER PARTIDA --------------------------------------
	// Finaliza la hebra
	public void detenerPartida(){
		this.turno.getModo().terminar();
	}
	
	// ----------------------------------- CONTINUAR PARTIDA --------------------------------------
	// Continuar con le ejecución de la hebra
	public void continuarPartida(){
		for(Observador o: this.Obs){
			//MovimientoStart ya realiza por dentro el getModo.comenzar();
			o.onMovimientoStart(this.turno, this.tablero);	
		}
	}
	
	// ----------------------------------------- ADD-OBSERVER --------------------------------------
	// Añade un observador
	public void addObservador(Observador o){
		this.Obs.add(o);
	}	
}	


