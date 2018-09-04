/* Clase que contiene toda la información y métodos necesarios para los objetos Ficha que se utilizan en el tablero.
 * Común para todos los juegos.
 * */

package pr3.logica;

import java.awt.Color;

import pr3.logica.TipoTurno;
import pr3.modo.ModoJuego;
import pr3.modo.ModoJuegoHumano;

public enum Ficha {
	BLANCA(new Color(255, 255, 255), "O", "blancas", new ModoJuegoHumano(), TipoTurno.HUMANO),
	NEGRA(new Color(0, 0, 0), "X", "negras", new ModoJuegoHumano(), TipoTurno.HUMANO),
	VACIA(new Color(0, 200, 100), " ", "", new ModoJuegoHumano(), TipoTurno.HUMANO),
	ByN(null,"","", null, null);
	
	private Ficha(Color color, String simbolo, String nombre, ModoJuego modo, TipoTurno tipo) {
		this.color = color;
		this.simbolo = simbolo;
		this.nombre = nombre;
		this.modo = modo;
		this.tipo = tipo;
	}
	private Color color;
	private String simbolo;
	private String nombre;
	private ModoJuego modo;
	private TipoTurno tipo;
	
	public Color getColor() {
		return color;
	}
	
	public String getSimbolo() {
		return simbolo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void cambiarModo(TipoTurno tipo, ModoJuego m){
		this.modo = m;
		this.tipo = tipo;
	}
	
	
	public TipoTurno getTipoTurno(){
		return tipo;
	}
	
	public ModoJuego getModo(){
		return modo;
	}
}
