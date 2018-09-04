package pr3.movimientos;

import pr3.logica.Ficha;
import pr3.logica.Tablero;

public class MovimientoReversi extends Movimiento{
	private boolean[][] aVoltear;	
	private int fat;
	private int tall;
	/* Este atributo sirve para indicar a ejecutaMovimiento 
	 * si puede llamar a voltear o no*/
	private boolean voltea;
		
	/* ----------------------- CONSTRUCTORA ------------------*/
	public MovimientoReversi(int fila, int col, Ficha turno) {
		super(fila, col, turno);
	}

	/* -------------------- EJECUTA MOVIMIENTO ----------------*/
	public void ejecutaMovimiento(Tablero tab) throws ImposibleFlanquear {
		/* Dejamos el tablero auxiliar de booleanos a false */
		iniciaTabBooleanos(tab);
		
		/* Ahora llamamos sucesivamente a todos los métodos reversi.
		 * Si en alguno de ellos fuera posible flanquear, se llamaria al
		 * método voltea, si no, no*/
	
		/*Si estamos en el límite superior del tablero, sin hacer esquina, no se realizan
		 * comprobaciones hacia arriba*/
		if(this.fila == 0 && this.col > 0 && this.col < this.fat - 1){
			reversiColAbajo(this.fila, tab);
			reversiFilaDer(this.col, tab);
			reversiFilaIz(this.col, tab);
			reversiDdAbajo(this.col, this.fila, tab);
			reversiDiAbajo(this.col, this.fila, tab);
		}
		/*Si estamos en el límite superior del tablero, haciendo esquina con la columna izquierda,
		 * no se realizan comprobaciones hacia arriba ni hacia la izquierda*/
		else if(this.fila == 0 && this.col == 0){
			reversiColAbajo(this.fila, tab);
			reversiFilaDer(this.col, tab);
			reversiDdAbajo(this.col, this.fila, tab);
		}
		/*Si estamos en el límite superior del tablero, haciendo esquina con la columna derecha,
		 * no se realizan comprobaciones hacia arriba ni hacia la derecha*/
		else if(this.fila == 0 && this.col == this.fat - 1){
			reversiColAbajo(this.fila, tab);
			reversiFilaIz(this.col, tab);
			reversiDiAbajo(this.col, this.fila, tab);
		}
		/*Si estamos en el límite inferior del tablero, sin hacer esquina,  no se realizan
		 * comprobaciones hacia abajo*/
		else if(this.fila == this.tall - 1 && this.col > 0 && this.col < this.fat - 1){
			reversiColArriba(this.fila, tab);
			reversiFilaDer(this.col, tab);
			reversiFilaIz(this.col, tab);
			reversiDdArriba(this.col, this.fila, tab);
			reversiDiArriba(this.col, this.fila, tab);
		}
		/*Si estamos en el límite inferior del tablero, haciendo esquina con la columna derecha,
		 * no se realizan comprobaciones hacia abajo ni hacia la derecha*/
		else if(this.fila == this.tall - 1 && this.col == this.fat - 1){
			reversiColArriba(this.fila, tab);
			reversiFilaIz(this.col, tab);
			reversiDiArriba(this.col, this.fila, tab);
		}
		/*Si estamos en el límite inferior del tablero, haciendo esquina con la columna izquierda,
		 * no se realizan comprobaciones hacia abajo ni hacia la izquierda*/
		else if(this.fila == this.tall -1 && this.col == 0){
			reversiColArriba(this.fila, tab);
			reversiFilaDer(this.col, tab);
			reversiDdArriba(this.col, this.fila, tab);
		}
		/*Si estamos en el límite izquierdo del tablero, sin hacer esquina, no se realizan
		 * comprobaciones hacia la izquierda*/
		else if(this.col == 0 && this.fila > 0 && this.fila < this.tall - 1){
			reversiColArriba(this.fila, tab);
			reversiColAbajo(this.fila, tab);
			reversiFilaDer(this.col, tab);
			reversiDdArriba(this.col, this.fila, tab);
			reversiDdAbajo(this.col, this.fila, tab);
		}
		/*Si estamos en el límite derecho del tablero, sin hacer esquina, no se realizan
		 * comprobaciones hacia la derecha*/
		else if(this.col == this.fat - 1 && this.fila > 0 && this.fila < this.tall - 1){
			reversiColArriba(this.fila, tab);
			reversiColAbajo(this.fila, tab);
			reversiFilaIz(this.col, tab);
			reversiDiArriba(this.col, this.fila, tab);
			reversiDiAbajo(this.col, this.fila, tab);
		}
		/*En cualquier otro caso miramos en las ocho direcciones alrededor
		 * de la ficha actual*/
		else{
			reversiColArriba(this.fila, tab);
			reversiColAbajo(this.fila, tab);
			reversiFilaDer(this.col, tab);
			reversiFilaIz(this.col, tab);
			reversiDdArriba(this.col, this.fila, tab);
			reversiDdAbajo(this.col, this.fila, tab);
			reversiDiArriba(this.col, this.fila, tab);
			reversiDiAbajo(this.col, this.fila, tab);
		}
		if(this.voltea){
			tab.colocarFicha(this.col, this.fila, this.turno);
			voltear(tab);
		}else{
			throw new ImposibleFlanquear("es imposible flanquear a las otras fichas");
		}
	}
	
	/*---------------- INICIALIZAR TABLERO DE BOOLEANOS -------------*/
	private void iniciaTabBooleanos(Tablero tab){
		this.fat = tab.getAncho(); this.tall = tab.getAlto();
		/* El tablero queda inicializado a false por defecto */
		this.aVoltear = new boolean[this.fat][this.tall];
		/* También ponemos el atributo privado voltea a true */
		this.voltea = false;
	}
	
	/*---------------------------- ANULAR ------------------------*/
	/* Este método vuleve a poner a false todas aquellas posiciones 
	 * que se hayan puesto a true durante algún método reversi */
	private void anularMarcaje(int x, int y, int col, int fila){
		// reversiColArriba
		if(x == 0 && y == 1){
			while(fila < this.fila){
				this.aVoltear[col][fila] = false; fila++;
			}
		// reversiColAbajo
		}else if(x == 0 && y == -1){
			while(fila > this.fila){
				this.aVoltear[col][fila] = false; fila--;
			}
		// reversiFilaDer
		}else if(x == 1 && y == 0){
			while(col > this.col){
				this.aVoltear[col][fila] = false; col--;
			}
		// reversiFilaIz
		}else if(x == -1 && y == 0){
			while(col < this.col){
				this.aVoltear[col][fila] = false; col++;
			}
		// reversiDdArriba
		}else if(x == 1 && y == 1){
			while(fila < this.fila && col > this.col){
				this.aVoltear[col][fila] = false; fila++; col--;
			}
		// reversiDdAbajo
		}else if(x == 1 && y == -1){
			while(fila > this.fila && col > this.col){
				this.aVoltear[col][fila] = false; fila--; col--;
			}
		// reversiDiArriba
		}else if(x == -1 && y == 1){
			while(fila < this.fila && col < this.col){
				this.aVoltear[col][fila] = false; fila++; col++;
			}
		// reversiDiAbajo
		}else if(x == -1 && y == -1){
			while(fila > this.fila && col < this.col){
				this.aVoltear[col][fila] = false; fila--; col++;
			}
		}
	}
	
	/* ------------------- REVERSI COLUMNA ARRIBA -------------*/
	private void reversiColArriba(int fila, Tablero tab){
		if(cumpleMinimo(this.col, fila-1, tab)){
			/* Disminuimos el valor de la fila en 2
			* para no coger la misma ficha que ya comprobó
			* cumpleMinimo*/
			fila-=2;
			while(fila >= 0 && tab.getFicha(this.col, fila) != Ficha.VACIA 
					&& tab.getFicha(this.col, fila) != this.turno){
				this.aVoltear[this.col][fila] = true;
				fila--;
			}
			/*Por si nos salimos del tablero al hacer las comprobaciones*/
			if(fila < 0){
				fila = 0;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if(fila == 0 && (tab.getFicha(this.col, fila) == Ficha.VACIA 
					|| tab.getFicha(this.col, fila) != this.turno)){
				anularMarcaje(0, 1, this.col, fila);
				if(!this.voltea){
					this.voltea = false;
				}
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(fila > 0 && tab.getFicha(this.col, fila) == Ficha.VACIA){
				anularMarcaje(0, 1, this.col, fila);
				if(!this.voltea){
					this.voltea = false;
				}
			}else{
				this.voltea = true;
			}
		}
	}
	
	/*------------------- REVERSI COLUMNA ABAJO ---------------*/
	private void reversiColAbajo(int fila, Tablero tab){
		if(cumpleMinimo(this.col, fila+1, tab)){
			/* Aumentamos el valor de la fila en 2
			* para no coger la misma ficha que ya comprobó
			* cumpleMinimo*/
			fila+=2;
			while(fila < this.tall && tab.getFicha(this.col, fila) != Ficha.VACIA
					&& tab.getFicha(this.col, fila) != this.turno){
				this.aVoltear[this.col][fila] = true;
				fila++;
			}
			/*Por si nos salimos del tablero al hacer las comprobaciones*/
			if(fila == this.tall){
				fila = this.tall-1;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if(fila == this.tall-1 && (tab.getFicha(this.col, fila) == Ficha.VACIA
					|| tab.getFicha(this.col, fila) != this.turno)){
				anularMarcaje(0, -1, this.col, fila);
				if(!this.voltea){
					this.voltea = false;
				}
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(fila < this.tall-1 && tab.getFicha(this.col, fila) == Ficha.VACIA){
				anularMarcaje(0, -1, this.col, fila);
				if(!this.voltea){
					this.voltea = false;
				}
			}else{
				this.voltea = true;
			}
		}
	}
	
	/*------------------- REVERSI FILA DERECHA -------------*/
	private void reversiFilaDer(int col, Tablero tab){
		if(cumpleMinimo(col+1, this.fila, tab)){
			/* Aumentamos el valor de la columna en 2
			* para no coger la misma ficha que ya comprobó 
			* cumpleMinimo*/
			col+=2;
			while(col < this.fat && tab.getFicha(col, this.fila) != Ficha.VACIA 
					&& tab.getFicha(col, this.fila) != this.turno){
				this.aVoltear[col][this.fila] = true;
				col++;
			}
			/*Por si nos salimos del tablero al hacer las comprobaciones*/
			if(col == this.fat){
				col = this.fat-1;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if(col == this.fat-1 && (tab.getFicha(col, this.fila) == Ficha.VACIA
					|| tab.getFicha(col, this.fila) != this.turno)){
				anularMarcaje(1, 0, col, this.fila);
				if(!this.voltea){
					this.voltea = false;
				}
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(col < this.fat-1 && tab.getFicha(col, this.fila) == Ficha.VACIA){
				anularMarcaje(1, 0, col, this.fila);
				if(!this.voltea){
					this.voltea = false;
				}
			}else{
				this.voltea = true;
			}
		}
	}
	
	/*------------------- REVERSI FILA IZQUIERDA -------------*/
	private void reversiFilaIz(int col, Tablero tab){
		if(cumpleMinimo(col-1, this.fila, tab)){
			/* Disminuimos el valor de la columna en 2 
			* para no coger la misma ficha que ya comprobó
			* cumpleMinimo*/
			col-=2;
			while(col >= 0 && tab.getFicha(col, this.fila) != Ficha.VACIA 
					&& tab.getFicha(col, this.fila) != this.turno){
				this.aVoltear[col][this.fila] = true;
				col--;
			}
			/*Por si nos salimos del tablero al realizar lsa comprobaciones*/
			if(col < 0){
				col = 0;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if(col == 0 && (tab.getFicha(col, this.fila) == Ficha.VACIA
					|| tab.getFicha(col, this.fila) != this.turno)){
				anularMarcaje(-1, 0, col, this.fila);
				if(!this.voltea){
					this.voltea = false;
				}
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(col > 0 && tab.getFicha(col, this.fila) == Ficha.VACIA){
				anularMarcaje(-1, 0, col, this.fila);
				if(!this.voltea){
					this.voltea = false;
				}
			}else{
				this.voltea = true;
			}
		}
	}
	
	/*--------------- REVERSI DIAGONAL DERECHA ARRIBA ---------*/
	private void reversiDdArriba(int col, int fila, Tablero tab){
		if(cumpleMinimo(col+1, fila-1, tab)){
			/* Disminuimos el valor de la fila en 2 y aumentamos el de 
			 * la columna en 2 para no coger la misma ficha que ya comprobó
			 * cumpleMinimo*/
			fila-=2; col+=2;
			while(fila >= 0 && col < this.fat && tab.getFicha(col, fila) != Ficha.VACIA 
					&& tab.getFicha(col, fila) != this.turno){
				this.aVoltear[col][fila] = true;
				fila--; col++;
			}
			/*Por si nos salimos del tablero al realizar las comprobaciones*/
			if(fila < 0){
				/*Si nos salimos antes por arriba*/
				fila++;
				col--;
			}
			if(col == this.fat){
				/*Si nos salimos antes por la derecha*/
				col--;
				fila++;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if((fila == 0 || col == this.fat-1) && (tab.getFicha(col, fila) == Ficha.VACIA
					|| tab.getFicha(col, fila) != this.turno)){
				anularMarcaje(1, 1, col, fila);
				if(!this.voltea){
					this.voltea = false;
				}
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(fila > 0 && col < this.fat-1 && tab.getFicha(col, fila) == Ficha.VACIA){
				anularMarcaje(1, 1, col, fila);
				if(!this.voltea){
					this.voltea = false;
				}
			}else{
				this.voltea = true;
			}
		}
	}
	
	/*--------------- REVERSI DIAGONAL DERECHA ABAJO ---------*/
	private void reversiDdAbajo(int col, int fila, Tablero tab){
		if(cumpleMinimo(col+1, fila+1, tab)){
			/* Aumentamos el valor de la fila y el de la columna en 2
			 * para no coger la misma ficha que ya comprobó cumpleMinimo*/
			fila+=2; col+=2;
			while(fila < this.tall && col < this.fat && tab.getFicha(col, fila) != Ficha.VACIA 
					&& tab.getFicha(col, fila) != this.turno){
				this.aVoltear[col][fila] = true;
				fila++; col++;
			}
			/*Por si nos salimos del tablero al realizar las comprobaciones*/
			if(fila == this.tall){
				/*Si nos salimos antes por abajo*/
				fila--;
				col--;
			}
			if(col == this.fat){
				/*Si nos salimos antes por la derecha*/
				col--;
				fila--;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if((fila == this.tall-1 || col == this.fat-1) && (tab.getFicha(col, fila) == Ficha.VACIA
					|| tab.getFicha(col, fila) != this.turno)){
				anularMarcaje(1, -1, col, fila);
				if(!this.voltea){
					this.voltea = false;
				}
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(fila < this.tall-1 && col < this.fat-1 && tab.getFicha(col, fila) == Ficha.VACIA){
				anularMarcaje(1, -1, col, fila);
				if(!this.voltea){
					this.voltea = false;
				}
			}else{
				this.voltea = true;
			}
		}
	}
	
	/*------------- REVERSI DIAGONAL IZQUIERDA ARRIBA ---------*/
	private void reversiDiArriba(int col, int fila, Tablero tab){
		if(cumpleMinimo(col-1, fila-1, tab)){
			/* Disminuimos el valor de la fila en y el de la columna en 2
			 * para no coger la misma ficha que ya comprobó cumpleMinimo*/
			fila-=2; col-=2;
			while(fila >= 0 && col >= 0 && tab.getFicha(col, fila) != Ficha.VACIA 
					&& tab.getFicha(col, fila) != this.turno){
				this.aVoltear[col][fila] = true;
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
					|| tab.getFicha(col, fila) != this.turno)){
				anularMarcaje(-1, 1, col, fila);
				if(!this.voltea){
					this.voltea = false;
				}
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(fila > 0 && col > 0 && tab.getFicha(col, fila) == Ficha.VACIA){
				anularMarcaje(-1, 1, col, fila);
				if(!this.voltea){
					this.voltea = false;
				}
			}else{
				this.voltea = true;
			}
		}
	}
	
	/*------------- REVERSI DIAGONAL IZQUIERDA ABAJO ---------*/
	private void reversiDiAbajo(int col, int fila, Tablero tab){
		if(cumpleMinimo(col-1, fila+1, tab)){
			/* Disminuimos el valor de la columna en 2 y aumentamos el de 
			 * la fila en 2 para no coger la misma ficha que ya comprobó
			 * cumpleMinimo*/
			fila+=2; col-=2;
			while(fila < this.tall && col >= 0 && tab.getFicha(col, fila) != Ficha.VACIA 
					&& tab.getFicha(col, fila) != this.turno){
				this.aVoltear[col][fila] = true;
				fila++; col--;
			}
			/*Por si nos slimos del tablero al hacer la comprobación*/
			if(fila == this.tall){
				fila--;
				col++;
			}
			if(col < 0){
				col++;
				fila--;
			}
			/* Si ha llegado hasta el tope del tablero y se ha encontrado con una casilla vacia
			 * o con una ficha distinto al turno actual, significa que no se puede flanquear*/
			if((fila == this.tall-1 || col == 0) && (tab.getFicha(col, fila) == Ficha.VACIA)
					|| tab.getFicha(col, fila) != this.turno){
				anularMarcaje(-1, -1, col, fila);
				if(!this.voltea){
					this.voltea = false;
				}
			/*No se ha llegado al límite del tablero pero nos hemos topado con una casilla vacia*/
			}else if(fila < this.tall-1 && col > 0 && tab.getFicha(col, fila) == Ficha.VACIA){
				anularMarcaje(-1, -1, col, fila);
				if(!this.voltea){
					this.voltea = false;
				}
			}else{
				this.voltea = true;
			}
		}
	}
	
	/*------------------------- VOLTEAR --------------------*/
	/* Convertimos todas las fichas del tablero marcadas al mismo 
	 * color que el turno */
	private void voltear(Tablero tab){
		for(int i = 0; i < this.fat; i++){
			for(int j = 0; j < this.tall; j++){
				if(this.aVoltear[i][j]){
					tab.colocarFicha(i, j, this.turno);
				}
			}
		}
	}
	
	/*---------------------- CUMPLE MINIMO -----------------*/
	private boolean cumpleMinimo(int col, int fila, Tablero tab){
		if(tab.getFicha(col, fila) != Ficha.VACIA &&
			tab.getFicha(col, fila) != this.turno){
			this.aVoltear[col][fila] = true;
			return true;
		}else{
			return false;
		}
	}
	
	/* ------------------------- UNDO ------------------------*/
	public void undo(Tablero tab) {
		/* Quitamos la última ficha que se puso en el tablero */
		tab.colocarFicha(this.col, this.fila, Ficha.VACIA);
		/* Recorremos el array de booleanos y para todas las posiciones marcadas
		 * a true, colocamos una ficha del color contrario al turno en el tablero
		 * de fichas*/
		for(int i = 0; i < this.fat; i++){
			for(int j = 0; j < this.tall; j++){
				if(this.aVoltear[i][j] && this.turno == Ficha.BLANCA){
					tab.colocarFicha(i, j, Ficha.NEGRA);
				}else if(this.aVoltear[i][j] && this.turno == Ficha.NEGRA){
					tab.colocarFicha(i, j, Ficha.BLANCA);
				}
			}
		}
	}

}
