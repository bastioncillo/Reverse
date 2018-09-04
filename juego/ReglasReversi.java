/* Clase que contiene las reglas específicas de Reversi.
 * Es distinto al resto de juegos, ya que el tablero empieza con fichas ya colocadas.
 * La comprobación de ganador también es distinta, ya que ahora gana quien tenga más fichas de su color en el tablero al llenarse.
 * El juego puede acabar en empate si hay el mismo número de fichas de cada uno.
 * Se deben realizar comprobaciones para ver si es posible seguir moviendo o no en cualquier dirección.
 * Para esto se emplean varios métodos adicionales no presentes en otros juegos.
 * */

package pr3.juego;

import pr3.logica.TipoJuego;
import pr3.logica.Ficha;
import pr3.logica.Tablero;
import pr3.movimientos.Movimiento;

public class ReglasReversi extends ReglasJuego{
	public static int ANCHO = 8;
	public static int ALTO = 8;
		
	public ReglasReversi(){
		super();
	}
	
	// ----------------------------------------- INICIAR TABLERO --------------------------------
	// Inicializa el tablero con la configuración del Reversi.
	public Tablero iniciarTablero() {
		Tablero tab = new Tablero(ANCHO, ALTO);
		tab.colocarFicha(4, 4, Ficha.NEGRA);
		tab.colocarFicha(3, 3, Ficha.NEGRA);
		tab.colocarFicha(3, 4, Ficha.BLANCA);
		tab.colocarFicha(4, 3, Ficha.BLANCA);
		return tab;
	}
	
	// ------------------------------- DEVUELVE EL PRIMER JUGADOR QUE MUEVE --------------------------
	// Devuelve el primer jugador que mueve al empezar una nueva partida
	public Ficha juegaPrimero(){
		return Ficha.NEGRA;
	}
	
	// ------------------------------ JUEGO ACTUAL ------------------------------
	// Devuelve el tipo de juego al que estés jugando
	public TipoJuego juegoActual(){
		return TipoJuego.RE;
	}
		
	// ---------------------------------------- COMPROBAR GANADOR -------------------------------
	// Comprueba si hay algún ganador de la partida según la última ficha introducida.
	public Ficha comprobarGanador(Tablero tab, Movimiento mov) {
		int blancas = 0, negras= 0;
		Ficha ganador = null;
		boolean lleno = true, ningunoPuedeMover = false;
		Ficha turno = mov.getTurno();
		/*Primero tenemos que ver si alguno de los dos jugadores puede volver
		 * a poner ficha*/
		if(!puedoHacerJugada(turno, tab)){
			if(turno == Ficha.BLANCA){
				turno = Ficha.NEGRA;
			}else if(turno == Ficha.NEGRA){
				turno = Ficha.BLANCA;
			}
			if(!puedoHacerJugada(turno, tab)){
				ningunoPuedeMover = true;
			}
		}
		/*Segundo comprobamos si el tablero está completamente lleno
		 * de fichas*/
		for(int i = 0; i < tab.getAncho() && lleno; i++){
			for(int j = 0; j < tab.getAlto() && lleno; j++){
				if(tab.getFicha(i, j) == Ficha.BLANCA){
					blancas++;	
				}else if(tab.getFicha(i, j) == Ficha.NEGRA){
					negras++;
				}else{
					lleno = false;
				}
			}
		}
		/*En el caso de que el tablero esté lleno, o de que ninguno
		 * puede seguir moviendo ficha, comprobamos quién ha gando*/
		if(lleno || ningunoPuedeMover){
			if(blancas > negras){
				ganador = Ficha.BLANCA;
			}else if(blancas < negras){
				ganador = Ficha.NEGRA;
			}else{
				ganador = Ficha.ByN;
			}
		}
		return ganador;
	}
	
	// ------------------ HAY TABLAS ---------------
	// Comprueba si se han producido tablas.
	public boolean hayTablas(Tablero tab, Movimiento mov) {
		return false;
	}
	
	// ------------------------- PUEDO HACER UNA JUGADA? ---------------------------------
	private boolean puedoHacerJugada(Ficha turno, Tablero tab){
		boolean puedes = false;
		for(int i = 0; i < tab.getAncho() && !puedes; i++){
			for(int j = 0; j < tab.getAlto() && !puedes; j++){
				/*Primero hay que comprobar si la casilla por la que pasamos
				 * puede ser utilizada*/
				if(tab.getFicha(i, j) == Ficha.VACIA){
					/*Si estamos en el límite superior del tablero, sin hacer esquina, no se realizan
					 * comprobaciones hacia arriba*/
					if(j == 0 && i > 0 && i < tab.getAncho() - 1){
						if(!puedes){
							puedes = reversiColAbajo(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiFilaDer(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiFilaDer(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiFilaIz(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDdAbajo(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDiAbajo(i, j, tab, turno);
						}
					}
					/*Si estamos en el límite superior del tablero, haciendo esquina con la columna izquierda,
					 * no se realizan comprobaciones hacia arriba ni hacia la izquierda*/
					else if(j == 0 && i == 0){
						if(!puedes){
							puedes = reversiColAbajo(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiFilaDer(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDdAbajo(i, j, tab, turno);
						}
					}
					/*Si estamos en el límite superior del tablero, haciendo esquina con la columna derecha,
					 * no se realizan comprobaciones hacia arriba ni hacia la derecha*/
					else if(j == 0 && i == tab.getAncho() - 1){
						if(!puedes){
							puedes = reversiColAbajo(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiFilaIz(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDiAbajo(i, j, tab, turno);
						}	
					}
					/*Si estamos en el límite inferior del tablero, sin hacer esquina,  no se realizan
					 * comprobaciones hacia abajo*/
					else if(j == tab.getAlto() - 1 && i > 0 && i < tab.getAncho() - 1){
						if(!puedes){
							puedes = reversiColArriba(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiFilaDer(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiFilaIz(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDdArriba(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDiArriba(i, j, tab, turno);
						}
					}
					/*Si estamos en el límite inferior del tablero, haciendo esquina con la columna derecha,
					 * no se realizan comprobaciones hacia abajo ni hacia la derecha*/
					else if(j == tab.getAlto() - 1 && i == tab.getAncho() - 1){
						if(!puedes){
							puedes = reversiColArriba(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiFilaIz(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDiArriba(i, j, tab, turno);
						}
					}
					/*Si estamos en el límite inferior del tablero, haciendo esquina con la columna izquierda,
					 * no se realizan comprobaciones hacia abajo ni hacia la izquierda*/
					else if(j == tab.getAlto() - 1 && i == 0){
						if(!puedes){
							puedes = reversiColArriba(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiFilaDer(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDdArriba(i, j, tab, turno);
						}
					}
					/*Si estamos en el límite izquierdo del tablero, sin hacer esquina, no se realizan
					 * comprobaciones hacia la izquierda*/
					else if(i == 0 && j > 0 && j < tab.getAlto() - 1){
						if(!puedes){
							puedes = reversiColArriba(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiColAbajo(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiFilaDer(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDdArriba(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDdAbajo(i, j, tab, turno);
						}
					}
					/*Si estamos en el límite derecho del tablero, sin hacer esquina, no se realizan
					 * comprobaciones hacia la derecha*/
					else if(i == tab.getAncho() - 1 && j > 0 && j < tab.getAlto() - 1){
						if(!puedes){
							puedes = reversiColArriba(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiColAbajo(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiFilaIz(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDiArriba(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDiAbajo(i, j, tab, turno);
						}
					}
					/*En cualquier otro caso miramos en las ocho direcciones alrededor
					 * de la ficha actual*/
					else{
						if(!puedes){
							puedes = reversiColArriba(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiColAbajo(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiFilaDer(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiFilaIz(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDdArriba(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDdAbajo(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDiArriba(i, j, tab, turno);
						}
						if(!puedes){
							puedes = reversiDiAbajo(i, j, tab, turno);
						}
					}
				}
			}
		}
		return puedes;
	}
	
	/* ------------------- REVERSI COLUMNA ARRIBA -------------*/
	private boolean reversiColArriba(int col, int fila, Tablero tab, Ficha turno){
		if(cumpleMinimo(col, fila-1, tab, turno)){
			/* Disminuimos el valor de la fila en 2
			* para no coger la misma ficha que ya comprobó
			* cumpleMinimo*/
			fila-=2;
			while(fila >= 0 && tab.getFicha(col, fila) != Ficha.VACIA 
					&& tab.getFicha(col, fila) != turno){
				fila--;
			}
			/*Por si nos salimos del tablero al hacer las comprobaciones*/
			if(fila < 0){
				fila = 0;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if(fila == 0 && (tab.getFicha(col, fila) == Ficha.VACIA 
					|| tab.getFicha(col, fila) != turno)){
				return false;
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(fila > 0 && tab.getFicha(col, fila) == Ficha.VACIA){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}		
	
	/*------------------- REVERSI FILA DERECHA -------------*/
	private boolean reversiFilaDer(int col, int fila, Tablero tab, Ficha turno){
		if(cumpleMinimo(col+1, fila, tab, turno)){
			/* Aumentamos el valor de la columna en 2
			* para no coger la misma ficha que ya comprobó 
			* cumpleMinimo*/
			col+=2;
			while(col < tab.getAncho() && tab.getFicha(col, fila) != Ficha.VACIA 
					&& tab.getFicha(col, fila) != turno){
				col++;
			}
			/*Por si nos salimos del tablero al hacer las comprobaciones*/
			if(col == tab.getAncho()){
				col = tab.getAncho()-1;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if(col == tab.getAncho()-1 && (tab.getFicha(col, fila) == Ficha.VACIA
					|| tab.getFicha(col, fila) != turno)){
				return false;
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(col < tab.getAncho()-1 && tab.getFicha(col, fila) == Ficha.VACIA){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/*------------------- REVERSI COLUMNA ABAJO ---------------*/
	private boolean reversiColAbajo(int col, int fila, Tablero tab, Ficha turno){
		if(cumpleMinimo(col, fila+1, tab, turno)){
			/* Aumentamos el valor de la fila en 2
			* para no coger la misma ficha que ya comprobó
			* cumpleMinimo*/
			fila+=2;
			while(fila < tab.getAlto() && tab.getFicha(col, fila) != Ficha.VACIA
					&& tab.getFicha(col, fila) != turno){
				fila++;
			}
			/*Por si nos salimos del tablero al hacer las comprobaciones*/
			if(fila == tab.getAlto()){
				fila = tab.getAlto()-1;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if(fila == tab.getAlto()-1 && (tab.getFicha(col, fila) == Ficha.VACIA
					|| tab.getFicha(col, fila) != turno)){
				return false;
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(fila < tab.getAlto()-1 && tab.getFicha(col, fila) == Ficha.VACIA){
				 return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/*------------------- REVERSI FILA IZQUIERDA -------------*/
	private boolean reversiFilaIz(int col, int fila, Tablero tab, Ficha turno){
		if(cumpleMinimo(col-1, fila, tab, turno)){
			/* Disminuimos el valor de la columna en 2 
			* para no coger la misma ficha que ya comprobó
			* cumpleMinimo*/
			col-=2;
			while(col >= 0 && tab.getFicha(col, fila) != Ficha.VACIA 
					&& tab.getFicha(col, fila) != turno){
				col--;
			}
			/*Por si nos salimos del tablero al realizar lsa comprobaciones*/
			if(col < 0){
				col = 0;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if(col == 0 && (tab.getFicha(col, fila) == Ficha.VACIA
					|| tab.getFicha(col, fila) != turno)){
				return false;
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(col > 0 && tab.getFicha(col, fila) == Ficha.VACIA){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/*--------------- REVERSI DIAGONAL DERECHA ARRIBA ---------*/
	private boolean reversiDdArriba(int col, int fila, Tablero tab, Ficha turno){
		if(cumpleMinimo(col+1, fila-1, tab, turno)){
			/* Disminuimos el valor de la fila en 2 y aumentamos el de 
			 * la columna en 2 para no coger la misma ficha que ya comprobó
			 * cumpleMinimo*/
			fila-=2; col+=2;
			while(fila >= 0 && col < tab.getAncho() && tab.getFicha(col, fila) != Ficha.VACIA 
					&& tab.getFicha(col, fila) != turno){
				fila--; col++;
			}
			/*Por si nos salimos del tablero al realizar las comprobaciones*/
			if(fila < 0){
				/*Si nos salimos antes por arriba*/
				fila++;
				col--;
			}
			if(col == tab.getAncho()){
				/*Si nos salimos antes por la derecha*/
				col--;
				fila++;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if((fila == 0 || col == tab.getAncho()-1) && (tab.getFicha(col, fila) == Ficha.VACIA
					|| tab.getFicha(col, fila) != turno)){
				return false;
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(fila > 0 && col < tab.getAncho()-1 && tab.getFicha(col, fila) == Ficha.VACIA){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/*--------------- REVERSI DIAGONAL DERECHA ABAJO ---------*/
	private boolean reversiDdAbajo(int col, int fila, Tablero tab, Ficha turno){
		if(cumpleMinimo(col+1, fila+1, tab, turno)){
			/* Aumentamos el valor de la fila y el de la columna en 2
			 * para no coger la misma ficha que ya comprobó cumpleMinimo*/
			fila+=2; col+=2;
			while(fila < tab.getAlto() && col < tab.getAncho() && tab.getFicha(col, fila) != Ficha.VACIA 
					&& tab.getFicha(col, fila) != turno){
				fila++; col++;
			}
			/*Por si nos salimos del tablero al realizar las comprobaciones*/
			if(fila == tab.getAlto()){
				/*Si nos salimos antes por abajo*/
				fila--;
				col--;
			}
			if(col == tab.getAncho()){
				/*Si nos salimos antes por la derecha*/
				col--;
				fila--;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if((fila == tab.getAlto()-1 || col == tab.getAncho()-1) && (tab.getFicha(col, fila) == Ficha.VACIA
					|| tab.getFicha(col, fila) != turno)){
				return false;
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(fila < tab.getAlto()-1 && col < tab.getAncho()-1 && tab.getFicha(col, fila) == Ficha.VACIA){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/*------------- REVERSI DIAGONAL IZQUIERDA ARRIBA ---------*/
	private boolean reversiDiArriba(int col, int fila, Tablero tab, Ficha turno){
		if(cumpleMinimo(col-1, fila-1, tab, turno)){
			/* Disminuimos el valor de la fila en y el de la columna en 2
			 * para no coger la misma ficha que ya comprobó cumpleMinimo*/
			fila-=2; col-=2;
			while(fila >= 0 && col >= 0 && tab.getFicha(col, fila) != Ficha.VACIA 
					&& tab.getFicha(col, fila) != turno){
				fila--; col--;
			}
			/*Por si nos salimos del tablero al realizar las comprobaciones*/
			if(fila < 0){
				/*Si nos salimos antes por arriba*/
				fila++;
				col++;
			}
			if(col < 0){
				/*Si nos salimos antes por la izquierda*/
				col++;
				fila++;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if((fila == 0 || col == 0) && (tab.getFicha(col, fila) == Ficha.VACIA
					|| tab.getFicha(col, fila) != turno)){
				return false;
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(fila > 0 && col > 0 && tab.getFicha(col, fila) == Ficha.VACIA){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/*------------- REVERSI DIAGONAL IZQUIERDA ABAJO ---------*/
	private boolean reversiDiAbajo(int col, int fila, Tablero tab, Ficha turno){
		if(cumpleMinimo(col-1, fila+1, tab, turno)){
			/* Disminuimos el valor de la columna en 2 y aumentamos el de 
			 * la fila en 2 para no coger la misma ficha que ya comprobó
			 * cumpleMinimo*/
			fila+=2; col-=2;
			while(fila < tab.getAlto() && col >= 0 && tab.getFicha(col, fila) != Ficha.VACIA 
					&& tab.getFicha(col, fila) != turno){
				fila++; col--;
			}
			/*Por si nos salimos del tablero al hacer la comprobación*/
			if(fila == tab.getAlto()){
				fila--;
				col++;
			}
			if(col < 0){
				col++;
				fila--;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if((fila == tab.getAlto()-1 || col == 0) && (tab.getFicha(col, fila) == Ficha.VACIA)
					|| tab.getFicha(col, fila) != turno){
				return false;
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(fila < tab.getAlto()-1 && col > 0 && tab.getFicha(col, fila) == Ficha.VACIA){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/*---------------------- CUMPLE MINIMO -----------------*/
	private boolean cumpleMinimo(int col, int fila, Tablero tab, Ficha turno){
		if(tab.getFicha(col, fila) != Ficha.VACIA &&
			tab.getFicha(col, fila) != turno){
			return true;
		}else{
			return false;
		}
	}
	
	// ----------------------------------- CAMBIAR TURNO ---------------------------------
	// Cambia el color de la ficha a introducir según el turno que corresponda.
	public Ficha siguienteTurno(Ficha turno, Tablero tab) {
		/*Inicialmente el turno siguiente es el mismo que el actual*/
		Ficha sigTurno = turno;
		if(turno == Ficha.BLANCA){
			/*Si el jugador siguiente al actual puede mover se cambia el turno*/
			if(puedoHacerJugada(Ficha.NEGRA, tab)){
				sigTurno = Ficha.NEGRA;
			}
		}else if(turno == Ficha.NEGRA){
			/*Si el jugador siguiente al actual puede mover se cambia el turno*/
			if(puedoHacerJugada(Ficha.BLANCA, tab)){
				sigTurno = Ficha.BLANCA;
			}
		}
		return sigTurno;
	}

	// ---------------------------- DIBUJAR TABLERO ----------------------------
	// Dibuja el tablero colocando las fichas correspondientes.
	public String dibujarTablero(Tablero tab) {
		return tab.dibujarTablero();
	}
}
