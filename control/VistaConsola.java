/* Clase encargada de la vista gen�rica del programa por consola. Sirve para notificar a los observadores de los distintos cambios en el programa.
 * Como tal, no realiza ning�n cambio directamente, sino que simplemente muestra mensajes por pantalla y notifica al controlador.
 * Utiliza una variable para referirse al controlador y otra para recibir informaci�n del usuario.
 * Adem�s, ejecuta el m�todo Run en el modo de juego por consola.
 * */

package pr3.control;

import java.util.Scanner;

import pr3.logica.Ficha;
import pr3.logica.Observador;
import pr3.logica.TableroInmutable;
import pr3.logica.TipoJuego;

public class VistaConsola implements Observador{
	private ControlConsola cc;
	private Scanner in;
	
	/*Constructora*/
	/* Se actualizan los valores de cc e in seg�n los recibidos por par�metro.
	 * Adem�s, se a�ade un observador del controlador.
	 * */
	public VistaConsola(ControlConsola cc, Scanner sc){
		this.cc = cc;
		this.in = sc;
		this.cc.addObservador(this);
	}

	/*M�todo para notificar a los observadores que la partida ha terminado*/
	/* Recibe un tablero final inmutable y un ganador.
	 * Al acabar, se muestra el tablero final y se indica qui�n ha ganado o si ha habido empate o tablas.
	 * */
	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		System.out.println(tabFin.dibujarTablero());
		if(ganador == Ficha.BLANCA){
			System.out.println("Ganan las BLANCAS");
		}else if(ganador == Ficha.NEGRA){
			System.out.println("Ganan las NEGRAS");
		}else if(ganador == Ficha.ByN){
			System.out.println("Empate");
		}else{
			System.out.println("Partida terminada en tablas");
		}
	}

	/*M�todo para notificar a los observadores que un movimiento ha comenzado*/
	/* Recibe el turno actual y un tablero inmutable.
	 * Se limita a indicar de qui�n es el turno.
	 * */
	public void onMovimientoStart(Ficha turno, TableroInmutable t) {
		if(turno == Ficha.BLANCA){
			System.out.println("Juegan BLANCAS:");
		}else{
			System.out.println("Juegan NEGRAS:");
		}
	}

	/*M�todo para notificar a los observadores que un movimiento ha terminado*/
	/* Recibe un tablero inmutable.
	 * Muestra el tablero para indicar que se ha realizado el movimiento.
	 * */
	public void onMovimientoEnd(TableroInmutable tabIn) {
		System.out.println(tabIn.dibujarTablero());
	}

	/*M�todo que notifica a los observadores que se ha deshecho un movimiento, as� como la posibilidad de que se puedan deshacer m�s
	 * Recibe un tablero inmutable y un booleano que indica si puede deshacer m�s o no.
	 * Muestra un mensaje para indicar si es posible deshacer m�s o no.
	 * */
	public void onUndo(TableroInmutable tabIn, boolean hayMas) {
		System.out.println(tabIn.dibujarTablero());
		if(hayMas){
			System.out.println("Puedes deshacer otro movimiento");
		}else{
			System.out.println("No puedes deshacer m�s movimientos");
		}
	}

	/*M�todo que notifica a los observadores que se ha reiniciado la partida
	 * Recibe un tablero inicial inmutable y el turno actual.
	 * Muestra un mensaje para indicar que se ha reiniciado, indica de qui�n es el turno y dibuja el tablero inicial.
	 * */
	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {
		System.out.println("Partida reiniciada");
		if(turno == Ficha.BLANCA){
			System.out.println("Juegan BLANCAS:");
		}else{
			System.out.println("Juegan NEGRAS:");
		}
		System.out.println(tabIni.dibujarTablero());
	}

	/*Notifica a los observadores que no se puede deshacer un movimiento*/
	/* Esta funci�n sobra ya que "onUndo" tambi�n te indica 
	 * si no puedes deshacer m�s movimientos*/
	public void onUndoNotPossible() {
		System.out.println("IMPOSIBLE DESHACER");
	}

	/*Notifica a los observadores que se ha cambiado el turno
	 * Recibe el turno actual y un tablero inmutable.
	 * Indica de qui�n es el turno actual.
	 * */
	public void onCambioTurno(Ficha turno, TableroInmutable t) {
		if(turno == Ficha.BLANCA){
			System.out.println("Juegan BLANCAS:");
		}else{
			System.out.println("Juegan NEGARS:");
		}
	}

	/*Notifica a los observadores que el movimiento es incorrecto y por qu� lo es
	 * Recibe un string con la explicaci�n de por qu� es incorrecto y lo muestra por pantalla.
	 * */
	public void onMovimientoIncorrecto(String explicacion) {
		System.out.println("El movimiento es incorrecto porque " + explicacion);	
	}

	/*Notifica a los observadores que se ha cambiado el juego
	 * Recibe un tablero inmutable, el tipo de juego y el turno actual.
	 * Indica a qui�n le toca jugar, muestra el tablero y se indica a qu� juego se ha cambiado.
	 * */
	public void onCambioJuego(TableroInmutable tabIn, TipoJuego tipo, Ficha turno) {
		if(turno == Ficha.BLANCA){
			System.out.println("Juegan BLANCAS:");
		}else{
			System.out.println("Juegan NEGRAS:");
		}
		System.out.println(tabIn.dibujarTablero());
		System.out.println("Se ha cambiado el juego a " + tipo);
	}
	
	/* Este m�todo no es necesario.
	 * */
	public void onStart(TableroInmutable tabIn, Ficha f) {}
	
	/*Muestra el texto con las instrucciones de juego por pantalla*/
	public void onMuestraAyuda(){
		System.out.println("Los comandos disponibles son:");
		System.out.println("poner: utilizalo para poner la siguiente ficha");
		System.out.println("deshacer: deshace el �ltimo movimiento de la partida");			
		System.out.println("reiniciar: reinicia la partida");			
		System.out.println("jugar [c4|co|gr|re] [tamX|tamY]: cambia el tipo de juego");
		System.out.println("jugador [blancas|negras] [humano|aleatorio]: cambia el tipo de jugador");
		System.out.println("salir: termina la aplicaci�n");
		System.out.println("ayuda: muestra esta ayuda");
		System.out.println("\n");
	}
	
	/*------------------------------------ RUN ------------------------------------*/
	/*M�todo Run que ejecuta el programa*/
	public void run(){
		// Variables locales																
		boolean finDelJuego = false;
		String aux;
		
		// Se empieza reiniciando el controlador y solicitando al usuario que indique qu� quiere hacer.
		this.cc.reiniciar();
		preguntarUsuario();
		String orden = this.in.nextLine();
		String[] arrayS = orden.split(" +");
		
		while(!finDelJuego){
			// Si el usuario elige "jugar" (y ha introducido m�s informaci�n)...
			if (arrayS[0].equalsIgnoreCase("jugar") && arrayS.length > 1) {
				aux = arrayS[1];
				/* Comprobamos que la primera orden este dentro de los nombre de
				 *  los juegos disponibles...*/
				if(aux.equalsIgnoreCase("c4") || aux.equalsIgnoreCase("co")
						|| aux.equalsIgnoreCase("gr") || aux.equalsIgnoreCase("re")){
					/* Si lo est�, comprobamos la longitud del array...*/
					int largo = arrayS.length, ancho = 0, alto = 0;
					if(largo == 4){
						/* Si mide 4, parseamos el string de las medidas del
						 * ancho y el alto */
						try{
							ancho = Integer.parseInt(arrayS[2]);
							alto = Integer.parseInt(arrayS[3]);
							// Y llamamos a crear juego
							this.cc.creaJuego(aux, ancho, alto);
							preguntarUsuario();
						}catch(NumberFormatException nfe){
							System.out.println("Debes introucir un valor num�rico");
							preguntarUsuario();
						}
					}else if(largo == 2){
						/* Si mide 2, llamamos a creaJuego directamente, ya que 
						 * no necesitamos las medidas del ancho y del alto*/
						this.cc.creaJuego(aux, ancho, alto);
						preguntarUsuario();
					}else{
						/* Si las medidas del array son cualquier otra 
						 * la instrucci�n no es v�lida*/
						System.out.println("Datos insuficientes para crear el juego");
						preguntarUsuario();
					}
				}else{
					/* Si el nombre de juego introducido no es v�lido se lo decimos al jugador*/
					System.out.println("La orden introducida no es v�lida");
					preguntarUsuario();
				}
			}
			// Si el usuario elige "poner"...
			else if (arrayS[0].equalsIgnoreCase("poner")) {
				this.cc.poner();
				// Si no ha habido ninguna excepci�n al ejecutar el movimiento, se comprueba si alguno de los jugadores ha ganado.
				if(this.cc.finalizada()){
					finDelJuego = true;
				}else{
					preguntarUsuario();
				}
			}
			// Si el usuario elige "jugador <B o N> <H o A>"...
			else if (arrayS[0].equalsIgnoreCase("jugador")) {
				boolean ok = true;
				Ficha colorAux = null;
				// Primero comprobamos que el color de la ficha es correcto
				if(arrayS[1].equalsIgnoreCase("negras")){
					colorAux = Ficha.NEGRA;
				}else if(arrayS[1].equalsIgnoreCase("blancas")){
					colorAux = Ficha.BLANCA;
				}else{
					ok = false;
				}
				// Si el color era correcto, comprobamos si lo es tambi�n el jugador
				if(ok){
					if(arrayS[2].equalsIgnoreCase("aleatorio") || 
							arrayS[2].equalsIgnoreCase("humano")){
						this.cc.cambiarJugador(colorAux, arrayS[2]);
						System.out.println("El jugador con fichas "+arrayS[1]+" es ahora "+arrayS[2]);
						preguntarUsuario();
					}else{
						System.out.println("Tipo de jugador incorrecto");
						preguntarUsuario();
					}
				}else{
					System.out.println("Tipo de ficha incorrecta");
					preguntarUsuario();
				}
			}
			// Si el usuario elige "deshacer"...
			else if (arrayS[0].equalsIgnoreCase("deshacer")) {
				// ...se deshace el �ltimo movimiento y se cambia al turno del jugador anterior.
				this.cc.undo();
				preguntarUsuario();
			}
			// Si el usuario elige "reiniciar"...
			else if (arrayS[0].equalsIgnoreCase("reiniciar")) {
				// ...se resetea por completo el tablero.
				this.cc.reiniciar();
				preguntarUsuario();
			}
			//Si el usuario elige "ayuda"...
			else if (arrayS[0].equalsIgnoreCase("ayuda")) {
				//...se le muestran los comandos disponibles
				this.cc.mostrarAyuda();
				preguntarUsuario();
			}
			//Si el usuario elige salir, finDelJuego se pone a true para salir al final del bucle
			else if(arrayS[0].equalsIgnoreCase("salir")){
				this.cc.finalizar();
				finDelJuego = true;
			}
			// Si se introduce cualquier otra instrucci�n, se lanza un mensaje informando del error.
			else {
				System.out.println("La orden introducida no existe");
				preguntarUsuario();
			}
			// Comprobamos si se ha acabado la partida...
			if(!finDelJuego){
				/* Si no se ha terminado, volvemos a pedirle al usuario una instrucci�n */
				orden = this.in.nextLine();
				arrayS = orden.split(" +");
			}
		}
	}
	
	// Muestra todas las opciones al usuario y le pide que introduzca la siguiente instrucci�n.
	private void preguntarUsuario(){
		System.out.println("Opciones: \nsalir // jugar c4|co|gr tamX|tamY // poner // "
			+ "jugador negras|blancas humano|aleatorio // deshacer // reiniciar // ayuda");
		System.out.println("Que quieres hacer?: ");
	}
}
