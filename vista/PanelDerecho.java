package pr3.vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pr3.logica.Ficha;
import pr3.logica.Observador;
import pr3.logica.TableroInmutable;
import pr3.logica.TipoJuego;
import pr3.logica.TipoTurno;

@SuppressWarnings("serial")
public class PanelDerecho extends JPanel implements Observador{
	//Botones
	private JButton bDeshacer;
	private JButton bReiniciar;
	private JButton bCambiar;
	private JButton bSalir;
	//Listas desplegables
	@SuppressWarnings("rawtypes")
	private JComboBox tJuego;
	@SuppressWarnings("rawtypes")
	private JComboBox dimsAncho;
	@SuppressWarnings("rawtypes")
	private JComboBox dimsAlto;
	@SuppressWarnings("rawtypes")
	private JComboBox jugadorBlancas;
	@SuppressWarnings("rawtypes")
	private JComboBox jugadorNegras;
	/*Datos necesarios si quieres 
	 * cambiar a Gravity*/
	private Integer ancho;
	private Integer alto;
	//Paneles
	private JPanel panelPartida;
	private JPanel panelCambiaJuego;
	private JPanel panelDim;
	private JPanel panelTipoJugador;
	private JLabel labelFila;
	private JLabel labelCol;
	private JLabel labelBlancas;
	private JLabel labelNegras;
	//Controlador
	private ControlVentana cv;
	
	/*--------------- CONSTRUCTORA ----------------*/
	public PanelDerecho(ControlVentana cv){
		this.cv = cv;
		this.cv.addObservador(this);
		inciarPanel();
		this.bDeshacer.setEnabled(false);
	}

	private void inciarPanel() {
		//Como distribuir los componentes dentro del panel.
		this.setLayout(new BorderLayout());
		
		construyePanelCambiaJuego();
		construyePanelPartida();
		construyePanelCambiaModo();
		//Panel auxiliar: au
		JPanel au = new JPanel();
		//Esta distribución (BoxLayout) permite colocar paneles en vertical
		au.setLayout(new BoxLayout(au, BoxLayout.Y_AXIS));
		//Primero colocamos el panel Partida
		au.add(this.panelPartida);
		//Después se coloca el panel para cambiar de jugador
		au.add(this.panelTipoJugador);
		//El panel CambiaJuego se coloca automáticamente debajo
		au.add(this.panelCambiaJuego);
		
		//Se crea el botón salir
		this.bSalir = new JButton("Salir");
		this.bSalir.setName("salir");
		this.bSalir.setIcon(Iconos.getIcono("salir"));
		this.bSalir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		/*Al meter el botón Salir dentro de un panel
		 * conseguimos que tome un tamaño más ajustado, 
		 * en vez de ocupar toda el área del panel*/
		JPanel aux = new JPanel();
		aux.add(this.bSalir);
		
		/*Mediante el BorderLayout inicial colocamos el panel au
		 * (con panelPartida y panelCambioJuego)al norte, y el
		 * panel aux(con el botón Salir) al sur*/
		this.add(au, BorderLayout.NORTH);
		this.add(aux, BorderLayout.SOUTH);
		
	}

	private void construyePanelPartida() {
		//Creas un panel nuevo
		this.panelPartida = new JPanel();
		// Distribución: 1 fila, 2 columnas
		this.panelPartida.setLayout(new GridLayout(1, 2));
		//Se crea el borede del panel con su nombre
		this.panelPartida.setBorder(BorderFactory.createTitledBorder("Partida"));
		
		//Se crea el botón Deshacer
		this.bDeshacer = new JButton("Deshacer");
		this.bDeshacer.setName("deshacer");
		this.bDeshacer.setIcon(Iconos.getIcono("deshacer"));
		this.bDeshacer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				cv.undo();
			}	
		});
		
		//Se crea el botón Reiniciar
		this.bReiniciar = new JButton("Reiniciar");
		this.bReiniciar.setName("reiniciar");
		this.bReiniciar.setIcon(Iconos.getIcono("reiniciar"));
		this.bReiniciar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				cv.reiniciar();				
			}
		});
		
		
		//Añades el botón al panel de Partida
		this.panelPartida.add(this.bDeshacer);
		this.panelPartida.add(this.bReiniciar);
	}

	private void construyePanelCambiaJuego() {
		//Creas un panel nuevo
		this.panelCambiaJuego = new JPanel();
		//Distribución: 2 filas, 1 columna
		this.panelCambiaJuego.setLayout(new GridLayout(2, 1));
		//Se crea el borde del panel con su nombre
		this.panelCambiaJuego.setBorder(BorderFactory.createTitledBorder("Cambio de juego"));
		
		TipoJuego [] juegos = {TipoJuego.C4, TipoJuego.CO,
				TipoJuego.GR, TipoJuego.RE};
		//Creas una barra desplegable de opciones
		this.tJuego = new JComboBox<TipoJuego>(juegos);
		//Le añades el oyente, cuando selecciones una opción el te dice que hacer 
		this.tJuego.addActionListener(new ActionListener(){
			@SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent e) {
				//Aquí guardas la opción de la tabla desplegable introducida
				JComboBox aux = (JComboBox) e.getSource();
				//Aquí coges el nombre asociado a dicha opción
				TipoJuego juegoIntroducido = (TipoJuego) aux.getSelectedItem();
				//Si era Gravity...
				if(juegoIntroducido == TipoJuego.GR){
					//...habilitas el panel de dimensiones
					panelDim.setVisible(true);
				}else{
					panelDim.setVisible(false);
				}
			}
		});
		
		//Creas un panel para introducir las dimensiones del Gravity
		this.panelDim = new JPanel();
		//Distribución: crea x posiciones seguidas hasta que se queda sin espacio
		this.panelDim.setLayout(new FlowLayout());
		
		//Creas etiqueta para las filas
		this.labelFila = new JLabel("Filas:");
		//Creación de la lista desplegable
		Integer [] dMedidas = {10, 9, 8, 7, 6, 5, 4, 3, 2};
		this.dimsAlto = new JComboBox<Integer>(dMedidas);
		this.dimsAlto.addActionListener(new ActionListener(){
			@SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent ae) {
				JComboBox aux = (JComboBox) ae.getSource();
				alto = (Integer) aux.getSelectedItem();
			}
		});
		
		//Creas etiqueta para las columnas
		this.labelCol = new JLabel("Columnas:");
		//Creación de la lista desplegable
		this.dimsAncho = new JComboBox<Integer>(dMedidas);
		this.dimsAncho.addActionListener(new ActionListener(){
			@SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent ae) {
				JComboBox aux = (JComboBox) ae.getSource();
				ancho = (Integer) aux.getSelectedItem();
			}
		});
		
		//Añades las dos etiqutas al panel de dimensiones
		this.panelDim.add(this.labelFila);
		this.panelDim.add(this.dimsAlto);
		this.panelDim.add(this.labelCol);
		this.panelDim.add(this.dimsAncho);
		//Este panel está oculto hasta que no se elija Gravity como juego
		this.panelDim.setVisible(false);
		
		//Se crea el botón Cambiar 
		this.bCambiar = new JButton("Cambiar");
		this.bCambiar.setName("cambiar");
		this.bCambiar.setIcon(Iconos.getIcono("cambiar"));
		this.bCambiar.addActionListener(new ActionListener(){			
			public void actionPerformed(ActionEvent arg0) {
				TipoJuego tj = (TipoJuego) tJuego.getSelectedItem();
				if(tj == TipoJuego.GR && alto != null && ancho != null){
					cv.cambiarJuego(tj, ancho, alto);
				}else if(tj == TipoJuego.GR && alto != null && ancho == null){
					cv.cambiarJuego(tj, 0, alto);
				}else if(tj == TipoJuego.GR && alto == null && ancho != null){
					cv.cambiarJuego(tj, ancho, 0);
				}else{
					cv.cambiarJuego(tj, 0, 0);
				}
			}
		});
		
		JPanel aux = new JPanel();
		aux.setLayout(new GridLayout(2, 1));
		aux.add(this.tJuego);
		aux.add(this.panelDim);
		
		JPanel aux1 = new JPanel();
		aux1.add(this.bCambiar);
		
		this.panelCambiaJuego.add(aux);
		this.panelCambiaJuego.add(aux1);
	}
	
	/*--------------- CONSTRUYE PANEL TIPO JUGADOR -----------*/
	private void construyePanelCambiaModo(){
		this.panelTipoJugador = new JPanel();
		this.panelTipoJugador.setLayout(new GridLayout(2, 2));
		this.panelTipoJugador.setBorder(BorderFactory.createTitledBorder("Tipo de jugador"));
		this.labelBlancas = new JLabel("Jugador de Blancas:");
		this.labelNegras = new JLabel("Jugador de Negras:");
		TipoTurno [] jugador = {TipoTurno.HUMANO, TipoTurno.AUTOMATICO};
		this.jugadorBlancas = new JComboBox<TipoTurno>(jugador);
		this.jugadorBlancas.addActionListener(new ActionListener(){
			@SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent ae) {
				JComboBox aux = (JComboBox) ae.getSource();
				TipoTurno tt = (TipoTurno) aux.getSelectedItem();
				cv.cambiarTipoJugador(Ficha.BLANCA, tt);
			}
		});
		this.jugadorNegras = new JComboBox<TipoTurno>(jugador);
		this.jugadorNegras.addActionListener(new ActionListener(){
			@SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent ae) {
				JComboBox aux = (JComboBox)	ae.getSource();
				TipoTurno tt = (TipoTurno) aux.getSelectedItem();
				cv.cambiarTipoJugador(Ficha.NEGRA, tt);
			}
		});
		this.panelTipoJugador.add(this.labelBlancas);
		this.panelTipoJugador.add(this.jugadorBlancas);
		this.panelTipoJugador.add(this.labelNegras);
		this.panelTipoJugador.add(this.jugadorNegras);
	}
	
	/*------------------- PARTIDA TERMINADA ----------------*/
	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				if(bDeshacer.isEnabled()){
					bDeshacer.setEnabled(false);
				}
				if(!bReiniciar.isEnabled()){
					bReiniciar.setEnabled(true);
				}
			}
		});
	}
	
	/*------------------- MOVIMIETNO START ----------------*/
	public void onMovimientoStart(Ficha turno, TableroInmutable t) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				bReiniciar.setEnabled(true);
				bCambiar.setEnabled(true);
			}
		});
	}

	/*------------------- MOVIMINETO END ------------------*/
	public void onMovimientoEnd(TableroInmutable tab) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				if(!bDeshacer.isEnabled()){
					bDeshacer.setEnabled(true);
				}
				if(!bReiniciar.isEnabled()){
					bReiniciar.setEnabled(true);
				}
			}
		});
	}
	
	/*------------------- UNDO ------------------*/
	public void onUndo(TableroInmutable tab, boolean hayMas) {
		if(!hayMas){
			this.bDeshacer.setEnabled(false);
		}else{
			this.bDeshacer.setEnabled(true);
		}
	}

	/*------------------- RESET PARTIDA ------------------*/
	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {
		this.bReiniciar.setEnabled(true);
		this.bDeshacer.setEnabled(false);
		this.bCambiar.setEnabled(true);
		this.jugadorBlancas.setSelectedIndex(0);
		this.jugadorNegras.setSelectedIndex(0);
	}

	/*------------------- UNDO NOT POSSIBLE ------------------*/
	public void onUndoNotPossible() {}

	/*------------------- CAMBIO TURNO ------------------*/
	public void onCambioTurno(Ficha turno, TableroInmutable t) {}

	/*------------------- MOVIMIENTO INCORRECTO ------------------*/
	public void onMovimientoIncorrecto(String explicacion) {}

	/*------------------- CAMBIO JUEGO ------------------*/
	public void onCambioJuego(TableroInmutable tab, TipoJuego tipo, Ficha turno) {
		onResetPartida(tab, turno);
	}

	/*------------------- MUESTRA AYUDA ------------------*/
	public void onMuestraAyuda() {}

	/*------------------- ON START -----------------------*/
	public void onStart(TableroInmutable tabIn, Ficha f) {}
}
