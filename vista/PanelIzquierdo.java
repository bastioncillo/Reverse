package pr3.vista;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import pr3.logica.Ficha;
import pr3.logica.Observador;
import pr3.logica.TableroInmutable;
import pr3.logica.TipoJuego;
import pr3.logica.TipoTurno;

@SuppressWarnings("serial")
public class PanelIzquierdo extends JPanel implements Observador{
	private ControlVentana cv;
	private JButton bMovAleatorio;
	private PanelTablero panelTablero;
	private JTextField jugadorActual;
	
	public PanelIzquierdo(ControlVentana cv){
		this.cv = cv;
		this.cv.addObservador(this);
		iniciarPanelIzquierdo();
	}

	private void iniciarPanelIzquierdo() {
		this.setLayout(new BorderLayout());
		
		this.jugadorActual = new JTextField(20);
		this.jugadorActual.setFont(new Font("Calibri", 2, 19));
		this.jugadorActual.setForeground(Color.GREEN);
		this.jugadorActual.setBackground(Color.WHITE);
		this.jugadorActual.setHorizontalAlignment(SwingConstants.CENTER);
		this.jugadorActual.setEditable(false);
		JPanel axu = new JPanel();
		axu.setLayout(new FlowLayout());
		axu.add(this.jugadorActual);
		this.add(axu, BorderLayout.NORTH);
		
		this.bMovAleatorio = new JButton("Aleatorio");
		this.bMovAleatorio.setName("aleatorio");
		this.bMovAleatorio.setIcon(Iconos.getIcono("aleatorio"));
		this.bMovAleatorio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				cv.poner();
			}
		});
		JPanel aux = new JPanel();
		aux.add(this.bMovAleatorio);
		this.add(aux, BorderLayout.SOUTH);
	}
	

	/*------------------- PARTIDA TERMINADA ------------------*/
	public void onPartidaTerminada(final TableroInmutable tabFin, final Ficha ganador) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				panelTablero.dibujarTablero(tabFin);
				panelTablero.apaga(tabFin);
				bMovAleatorio.setEnabled(false);
				if(ganador == Ficha.BLANCA){
					jugadorActual.setText("Ganan las blancas");
				}else if(ganador == Ficha.NEGRA){
					jugadorActual.setText("Ganan las negras");
				}else if(ganador == Ficha.ByN){
					jugadorActual.setText("Empate");
				}else{
					jugadorActual.setText("Tablas");
				}	
			}
		});
	}
	
	/*------------------- MOVIMIENTO START ------------------*/
	public void onMovimientoStart(final Ficha turno, final TableroInmutable tabIn) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				if(turno == Ficha.BLANCA){
					jugadorActual.setText("Juegan blancas");		
				}else{
					jugadorActual.setText("Juegan negras");		
				}
				if(turno.getTipoTurno() == TipoTurno.HUMANO){
					if(!bMovAleatorio.isEnabled()){
						bMovAleatorio.setEnabled(true);
					}
					panelTablero.enciende(tabIn);
				}else{
					if(bMovAleatorio.isEnabled()){
						bMovAleatorio.setEnabled(false);
					}
					panelTablero.apaga(tabIn);
				}
				turno.getModo().comenzar();
			}
		});
	}

	/*------------------- MOVIMIENTO END ------------------*/
	public void onMovimientoEnd(final TableroInmutable tab){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				panelTablero.dibujarTablero(tab);
			}
		});
	}

	/*---------------------- ON UNDO ----------------------*/
	public void onUndo(TableroInmutable tab, boolean hayMas) {
		this.panelTablero.dibujarTablero(tab);
	}

	/*------------------- RESET PARTIDA ------------------*/
	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {
		this.panelTablero.dibujarTablero(tabIni);
		onMovimientoStart(turno, tabIni);
		if(!this.bMovAleatorio.isEnabled()){
			this.bMovAleatorio.setEnabled(true);
		}
	}

	/*------------------- UNDO NOT POSSIBLE ------------------*/
	public void onUndoNotPossible() {}

	/*------------------- CAMBIO TURNO ------------------*/
	public void onCambioTurno(Ficha turno, TableroInmutable tabIn) {
		onMovimientoStart(turno, tabIn);
	}

	/*------------------- MOVIMIENTO INCORRECTO ------------------*/
	public void onMovimientoIncorrecto(String explicacion) {}

	/*------------------- CAMBIO JUEGO ------------------*/
	public void onCambioJuego(TableroInmutable tab, TipoJuego tipo, Ficha turno) {
		this.remove(this.panelTablero);
		onStart(tab, turno);
		if(!this.bMovAleatorio.isEnabled()){
			this.bMovAleatorio.setEnabled(true);
		}
	}

	/*------------------- MUESTRA AYUDA ------------------*/
	public void onMuestraAyuda() {}

	/*-------------------- ON START --------------------*/
	public void onStart(TableroInmutable tabIn, Ficha turno) {
		this.panelTablero = new PanelTablero(tabIn, this.cv);
		this.add(this.panelTablero, BorderLayout.CENTER);
		this.revalidate();
		if(turno == Ficha.BLANCA){
			this.jugadorActual.setText("Juegan blancas");
		}else{
			this.jugadorActual.setText("Juegan negras");
		}
	}
}
