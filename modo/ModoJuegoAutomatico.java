/* Clase que contiene toda la información necesaria para las partidas automáticas.
 * */

package pr3.modo;

import pr3.vista.ControlVentana;

public class ModoJuegoAutomatico implements ModoJuego{
	
	private ControlVentana cv;
	private Thread hebra;
	
	/*------------------ CONSTRUCTORA ----------------*/
	public ModoJuegoAutomatico(ControlVentana cv){
		this.cv = cv;
	}
	
	/*---------------------- COMENZAR ---------------*/
	public void comenzar() {
		this.hebra = new Thread(){
			public void run(){
				try{
					Thread.sleep(1000);
					cv.poner();
				}catch(InterruptedException ie){}
			}
		};
		hebra.start();
	}

	/*---------------------- TERMINAR ---------------*/
	public void terminar() {
		if(this.hebra != null){
			this.hebra.interrupt();
		}
	}

	/*--------------- DESHACER PULSADO --------------*/
	public void deshacerPulsado() {
		// TODO Auto-generated method stub
		
	}

	/*---------------- TABLERO PULSADO --------------*/
	public void tableroPulsado() {
		// TODO Auto-generated method stub
		
	}

}
