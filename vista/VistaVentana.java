package pr3.vista;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class VistaVentana extends JFrame{
	private ControlVentana cv;
	private PanelDerecho panelDerecho;
	private PanelIzquierdo panelIzquierdo;
	
	public VistaVentana(ControlVentana controlVentana){
		super("PRÁCTICA 4");
		this.cv = controlVentana;
		
		this.setLayout(new GridLayout(1, 2));
		this.panelIzquierdo = new PanelIzquierdo(this.cv);
		this.panelDerecho = new PanelDerecho(this.cv);
		this.cv.Start();
		this.add(this.panelIzquierdo);
		this.add(this.panelDerecho);
		
		//Donde aparece la ventana
		this.setLocation(50, 100);
		//Tamaño de la ventana en pixeles, ancho y alto
		this.setSize(650, 500);
		//Tamaño mínimo que tiene la ventana
		this.setMinimumSize(new Dimension(650, 500));
		//Permite ver la ventana, si no no aparece
		this.setVisible(true);
		//Es para que cuando cierres al ventana se cierre también la aplicación
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
