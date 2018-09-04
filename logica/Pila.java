/* Clase que se encarga del almacenamiento de tableros para permitir deshacer las �ltimas jugadas.
 * */

package pr3.logica;

import pr3.movimientos.Movimiento;

public class Pila {
	// Atributos privados y constantes
	public static final int MAX_PILA = 10;
	private int tamanio = MAX_PILA;
	private Movimiento[] undoStack;
	private int numUndo;
	    
	//------------------------------- CONSTRUCTORA ------------------------------
	// Crea un objeto pila (array de enteros).
	public Pila(){
		this.undoStack = new Movimiento[MAX_PILA];
		this.numUndo = 0;
	}
	
	//--------------------------- COMPROBAR VAC�A -------------------------------
	// Comprueba si la pila est� completamente vac�a.
	public boolean estaVacia(){
		return(this.numUndo == 0);
	}
	
	//----------------------------- COLOCAR EN PILA -----------------------------
	// Tras realizar un movimiento, se coloca en la pila por si quiere deshacerse.
	// Si la pila est� vac�a, introduce el nuevo movimiento.
	public void poner(Movimiento mov){
		if(!estaLlena()){
			this.undoStack[numUndo] = mov;
			this.numUndo++;
		}
		// Si la pila est� llena, se amplia su tama�o y se introduce el nuevo dato.
		else{
			amplia();
			this.undoStack[numUndo] = mov;
			this.numUndo++;
		}
	}
	
	//------------------------------ DESHACER MOVIMIENTO ----------------------------
	public Movimiento sacar(){
		Movimiento ret;
		// Si la pila est� vac�a, no puede deshacer nada y devuelve -1.
		if(estaVacia()){
			ret = null;
		}
		// Si hay algo almacenado en la pila, deshace el �ltimo movimiento y libera un espacio.
		else{
			ret = this.undoStack[numUndo - 1];
			this.numUndo--;
		}
		return ret;
	}
	
	// ---------------------------- COMPROBAR LLENA -----------------------------------
	// Comprueba si la pila est� completamente llena.
	public boolean estaLlena(){
		boolean llena = false;
		if(this.numUndo == this.tamanio){
			llena = true;
		}
		return llena;
	}
	
	// --------------------------------- AMPLIA -----------------------------------
	private void amplia(){
		this.tamanio *= 2;
		Movimiento[] vieja = this.undoStack;
		this.undoStack = new Movimiento[this.tamanio];
		for(int i = 0; i < this.numUndo; i++){
			this.undoStack[i] = vieja[i];
		}
	}
}
