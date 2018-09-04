package pr3.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import pr3.logica.Ficha;
import pr3.logica.TableroInmutable;

@SuppressWarnings("serial")
public class PanelTablero extends JPanel implements ActionListener{
	
	public class MyButton extends JButton{
		private int col;
		private int fila;
		public MyButton(int col, int fila){
			this.col = col;
			this.fila = fila;
		}
		public int getCol(){
			return this.col;
		}
		public int getFila(){
			return this.fila;
		}
	}
	
	private MyButton[][] tablero;
	private ControlVentana cv;
	 
	/*-------------------- CONSTRUCTORA --------------------*/
	public PanelTablero(TableroInmutable tabIn, ControlVentana cv){
		this.cv = cv;
		int ancho = tabIn.getAncho();
		int alto = tabIn.getAlto();
		//Creamos una cuadrícula de ancho X alto dimensiones
		this.setLayout(new GridLayout(alto, ancho));
		//Instanciamos el tablero al tipo MyButton
		this.tablero = new MyButton[ancho][alto];
		for(int j = 0; j < alto; j++){
			for(int i = 0; i < ancho; i++){
				//Ponemos un objecto MyButton a cada casilla 
				this.tablero[i][j] = new MyButton(i, j);
				//Pintamos la casilla del color que corresponda
				this.tablero[i][j].setBackground(tabIn.getFicha(i, j).getColor());
				//Ponemos un borde de color Gris a la casilla
				this.tablero[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
				//Damos unas dimensiones a la casilla
				this.tablero[i][j].setPreferredSize(new Dimension());
				//Le anadimos el ActionListener
				this.tablero[i][j].addActionListener(this);
				//Añadimos la casilla al panelTablero
				this.add(this.tablero[i][j]);
			}
		}
	}
	
	/*--------------- DESHABILITAR CASILLAS ---------*/
	public void apaga(TableroInmutable tabIn){
		int ancho = tabIn.getAncho();
		int alto = tabIn.getAlto();
		for(int i = 0; i < ancho; i++){
			for(int j = 0; j < alto; j++){
				this.tablero[i][j].setEnabled(false);
			}
		}
	}
	
	/*--------------- HABILITAR CASILLAS ------------*/
	public void enciende(TableroInmutable tabIn){
		int ancho = tabIn.getAncho();
		int alto = tabIn.getAlto();
		for(int i = 0; i < ancho; i++){
			for(int j = 0; j < alto; j++){
				this.tablero[i][j].setEnabled(true);
			}
		}
	}
	
	/*--------------- DIBUJAR TABLERO --------------*/
	public void dibujarTablero(TableroInmutable tabIn){
		for(int i = 0; i < tabIn.getAncho(); i++){
			for(int j = 0; j < tabIn.getAlto(); j++){
				if(tabIn.getFicha(i, j) != Ficha.VACIA){
					this.tablero[i][j].setEnabled(false);
				}else{
					this.tablero[i][j].setEnabled(true);
				}
				this.tablero[i][j].setBackground(tabIn.getFicha(i, j).getColor());
				this.tablero[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
				this.tablero[i][j].setPreferredSize(new Dimension());
			}
		}
	}
	
	/*--------------- ACTION PERFORMED -------------*/
	public void actionPerformed(ActionEvent ae) {
		MyButton boton = (MyButton) ae.getSource();
		this.cv.poner(boton.getCol(), boton.getFila());
	}	
}
