/* Clase que controla un jugador automático para Reversi.
 * Elige una columna y una fila al azar e invoca a la factoría correspondiente.
 * */

package pr3.jugadores;

import java.util.Random;

import pr3.control.factorias.FactoriaJuego;
import pr3.logica.Ficha;
import pr3.logica.Tablero;
import pr3.movimientos.Movimiento;

public class JugadorAleatorioReversi extends Jugador{
	
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) {
		int columna = 0, fila = 0;
		Random random = new Random();
		do{
			do{
				columna = random.nextInt(tab.getAncho());
				fila = random.nextInt(tab.getAlto());
			}while(tab.getFicha(columna, fila) != Ficha.VACIA);
		}while(!puedoHacerJugada(color, tab, columna, fila));
		return factoria.creaMovimiento(fila, columna, color);
	}
	
	// ------------------------- PUEDO HACER UNA JUGADA? ---------------------------------
	private boolean puedoHacerJugada(Ficha turno, Tablero tab, int col , int fila){
		boolean puedes = false;
		/*Si estamos en el límite superior del tablero, sin hacer esquina, no se realizan
		 * comprobaciones hacia arriba*/
		if(fila == 0 && col > 0 && col < tab.getAncho() - 1){
			if(!puedes){
				puedes = reversiColAbajo(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiFilaDer(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiFilaDer(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiFilaIz(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDdAbajo(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDiAbajo(col, fila, tab, turno);
			}
		}
		/*Si estamos en el límite superior del tablero, haciendo esquina con la columna izquierda,
		 * no se realizan comprobaciones hacia arriba ni hacia la izquierda*/
		else if(fila == 0 && col == 0){
			if(!puedes){
				puedes = reversiColAbajo(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiFilaDer(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDdAbajo(col, fila, tab, turno);
			}
		}
		/*Si estamos en el límite superior del tablero, haciendo esquina con la columna derecha,
		 * no se realizan comprobaciones hacia arriba ni hacia la derecha*/
		else if(fila == 0 && col == tab.getAncho() - 1){
			if(!puedes){
				puedes = reversiColAbajo(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiFilaIz(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDiAbajo(col, fila, tab, turno);
			}	
		}
		/*Si estamos en el límite inferior del tablero, sin hacer esquina,  no se realizan
		 * comprobaciones hacia abajo*/
		else if(fila == tab.getAlto() - 1 && col > 0 && col < tab.getAncho() - 1){
			if(!puedes){
				puedes = reversiColArriba(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiFilaDer(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiFilaIz(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDdArriba(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDiArriba(col, fila, tab, turno);
			}
		}
		/*Si estamos en el límite inferior del tablero, haciendo esquina con la columna derecha,
		 * no se realizan comprobaciones hacia abajo ni hacia la derecha*/
		else if(fila == tab.getAlto() - 1 && col == tab.getAncho() - 1){
			if(!puedes){
				puedes = reversiColArriba(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiFilaIz(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDiArriba(col, fila, tab, turno);
			}
		}
		/*Si estamos en el límite inferior del tablero, haciendo esquina con la columna izquierda,
		 * no se realizan comprobaciones hacia abajo ni hacia la izquierda*/
		else if(fila == tab.getAlto() - 1 && col == 0){
			if(!puedes){
				puedes = reversiColArriba(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiFilaDer(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDdArriba(col, fila, tab, turno);
			}
		}
		/*Si estamos en el límite izquierdo del tablero, sin hacer esquina, no se realizan
		 * comprobaciones hacia la izquierda*/
		else if(col == 0 && fila > 0 && fila < tab.getAlto() - 1){
			if(!puedes){
				puedes = reversiColArriba(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiColAbajo(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiFilaDer(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDdArriba(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDdAbajo(col, fila, tab, turno);
			}
		}
		/*Si estamos en el límite derecho del tablero, sin hacer esquina, no se realizan
		 * comprobaciones hacia la derecha*/
		else if(col == tab.getAncho() - 1 && fila > 0 && fila < tab.getAlto() - 1){
			if(!puedes){
				puedes = reversiColArriba(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiColAbajo(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiFilaIz(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDiArriba(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDiAbajo(col, fila, tab, turno);
			}
		}
		/*En cualquier otro caso miramos en las ocho direcciones alrededor
		 * de la ficha actual*/
		else{
			if(!puedes){
				puedes = reversiColArriba(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiColAbajo(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiFilaDer(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiFilaIz(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDdArriba(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDdAbajo(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDiArriba(col, fila, tab, turno);
			}
			if(!puedes){
				puedes = reversiDiAbajo(col, fila, tab, turno);
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
			/*Por si nos slimos del tablero al hacer la comprobación*/
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
}
