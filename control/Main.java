/* Clase que controla el funcionamiento general del programa.
 * Permite introducir comandos y opciones de consola para modificar el funcionamiento del programa (tipo de juego, tamaño, interfaz...)
 * Lanza excepciones si los comandos o alguno de los valores son erróneos.
 * */

package pr3.control;

import java.util.Scanner;

import pr3.logica.Partida;
import pr3.vista.*;
import pr3.control.factorias.*;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		// El main crea los objetos para partida y controlador e inicia la ejecución del programa.
		// Creamos una factoria por defecto para conecta4
		FactoriaJuego fj = new FactoriaJuegoConecta4();
		Options opciones = new Options();
		// Aquí se reciben las opciones de consola.
		opciones.addOption(OptionBuilder.withLongOpt("game").hasArgs()
							.withArgName("game")
							.withDescription("Tipo de juego (c4, co, gr, rv). Por defecto, c4.")
							.create('g'));		
		opciones.addOption(OptionBuilder.withLongOpt("help")
							.withDescription("Muestra esta ayuda")
							.create('h'));
		opciones.addOption(OptionBuilder.withLongOpt("tamX")
							.hasArg()
							.withArgName("columnNumber")
							.withDescription("Número de columnas del tablero (sólo para Gravity). Por defecto, 10.")
							.create('x'));
		opciones.addOption(OptionBuilder.withLongOpt("tamY")
							.hasArg()
							.withArgName("rowNumber")
							.withDescription("Número de filas del tablero (sólo para Gravity). Por defecto, 10.")
							.create('y'));
		opciones.addOption(OptionBuilder.withLongOpt("ui")
							.hasArgs()
							.withArgName("tipo")
							.withDescription("Tipo de interfaz (console, window). Por defecto, console")
							.create('u'));
		try{
			// Aquí se tratan los datos recibidos.
			CommandLineParser parser = new BasicParser();
			CommandLine cmdLine = parser.parse(opciones, args);
			// En este array capturamos los posible errores (erratas por parte del usuario al introducir los argumentos).
			String[] errores = cmdLine.getArgs();
			if(errores.length != 0){
				String invalidArgs = errores[0];
				// En este bucle unimos todos los errores en un solo string...
				for (int i = 1; i < errores.length; i++){
					invalidArgs += " " + errores[i];
					// ...para pasárselos como argumento a la clase ArgumentoIncorrecto
				}throw new ArgumentoIncorrecto("Argumentos no entendidos: " + invalidArgs);
			}else{
				// Si el usuario a pedido ayuda por la linea de comandos, se llama al texto de ayuda.
				if(cmdLine.hasOption("h")){
					HelpFormatter formatter = new HelpFormatter();
					formatter.printHelp(Main.class.getCanonicalName(), opciones, true);
					System.exit(0);
				/* Si el usario a escrito los comandos correspondientes a un juego,
				se inicia la partida según los parámetros recibidos*/
				}else if(cmdLine.hasOption("g")){
					String g = cmdLine.getOptionValue("g");
					// Para jugar al Conecta4
					if (g.equals("c4")){
						fj = new FactoriaJuegoConecta4();
					// Para jugar al Complica
					}else if (g.equals("co")){
						fj = new FactoriaJuegoComplica();
					// Para jugar al Gravity
					}else if (g.equals("gr")){
						// Creación de la factoria si ha introducido valores para el ancho y el alto...
						if (cmdLine.hasOption("x") && cmdLine.hasOption("y")){
							int alto = Integer.parseInt(cmdLine.getOptionValue("x"));
							int ancho = Integer.parseInt(cmdLine.getOptionValue("y"));
							fj = new FactoriaJuegoGravity(alto, ancho);
						}
						// ... y si no lo ha hecho lo pone de 10x10 automáticamente.
						else if (!cmdLine.hasOption("x") && !cmdLine.hasOption("y")){
							fj = new FactoriaJuegoGravity(10, 10);
						}
						// Lanza una excepción si los valores no son correctos.
						else{
							throw new NumberFormatException();
						}
					// Para jugar al Reversi
					}else if(g.equals("re")){
						fj = new FactoriaJuegoReversi();
					// Si no se corresponde con ningún juego disponible
					}else{
						System.out.println("Ninguno de los comandos introducidos "
						+ "coincide con los tipos de juego disponibles."
						+ "\nUse -h|--help para más detalles.\n");
					}
				}
				if(cmdLine.hasOption("u")){
					String u = cmdLine.getOptionValue("u");
					Scanner in = new java.util.Scanner(System.in);
					// Para jugar en el modo consola
					if (u.equals("console")){
						VistaConsola vc = new VistaConsola(new ControlConsola(new Partida(fj.creaReglas()), in, fj), in);
						vc.run();
					}
					// Para jugar en el modo ventana
					else if (u.equals("window")){
						new VistaVentana(new ControlVentana(new Partida(fj.creaReglas()), fj));
					}
					else {
						throw new ArgumentoIncorrecto("Modo de vista " + u + " no reconocido.");
					}
				// Arrancamos en el modo consola por defecto
				}else{
					Scanner in = new java.util.Scanner(System.in);
					VistaConsola vc = new VistaConsola(new ControlConsola(new Partida(fj.creaReglas()), in, fj), in);
					vc.run();
				}
			}
		// Se atrapan las excepciones lanzadas y se muestra el mensaje correspondiente.
		}catch(ArgumentoIncorrecto ai){
			System.out.println("Uso incorrecto: " + ai.getMessage() 
			+ "\nUse -h|--help para más detalles.\n");
			System.exit(1);
		}catch(NumberFormatException nfe){
			System.out.println("Las dimensiones de gravity deben ser numéricas, "
			+ "y se debe elegir entre introducir"
			+ " ambas o utilizar las dimensiones por defecto."
			+ "\nUse -h|--help para más detalles.\n");
			System.exit(1);
		} catch (ParseException pe) {
			System.out.println("Uso incorrecto: " + pe.getMessage()
			+ "\nUse -h|--help para más detalles.\n");
			System.exit(1);
		}
	}
}
