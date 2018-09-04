/* Clase que extiende Exception y sirve para indicar que un argumento no es correcto. */

package pr3.control;

@SuppressWarnings("serial")
public class ArgumentoIncorrecto extends Exception{
	public ArgumentoIncorrecto(String string){
		super(string);
	}
}
