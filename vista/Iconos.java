package pr3.vista;

import javax.swing.ImageIcon;

public enum Iconos {
	CAMBIAR, EXIT, RANDOM, REINICIAR, UNDO;
	
	public static ImageIcon getIcono(String nombre){
		java.net.URL url = null;
		switch(nombre){
			case "cambiar":{
				url = Iconos.class.getResource("imagenes/cambiar.png");
				break;
			}
			case "salir":{
				url = Iconos.class.getResource("imagenes/exit.png");
				break;
			}
			case "aleatorio":{
				url = Iconos.class.getResource("imagenes/random.png");
				break;
			}
			case "reiniciar":{
				url = Iconos.class.getResource("imagenes/reiniciar.png");
				break;
			}
			case "deshacer":{
				url = Iconos.class.getResource("imagenes/undo.png");
				break;
			}
		}
		ImageIcon ic = new ImageIcon(url);		
		return ic;
	}
	
	
}
