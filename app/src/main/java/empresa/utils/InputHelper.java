package empresa.utils;

import java.util.Scanner;

public class InputHelper {
	private static final Scanner scanner = new Scanner(System.in);

	// Leemos la entrada del usuario
	public static String leerString(String mensaje){
		System.out.println(mensaje + ": ");
		return scanner.nextLine().trim();
	}

	// Leemos el número Long
	public static Long leerLong(String mensaje){
		while (true) {
			try{
				System.out.print(mensaje + ": ");
				return Long.parseLong(scanner.nextLine().trim());
			}catch(NumberFormatException e){
				System.out.println("Entrada inválida. Introduce un número.");
			}
		}
	}

	public static int leerInt(String mensaje){
		while(true){
			try{
				System.out.print(mensaje + ": ");
				return Integer.parseInt(scanner.nextLine().trim());
			}catch(NumberFormatException e){
				System.out.println("Entrada inválida. Introduce un número entero.");
			}
		}
	}

	public static Double leerDouble(String mensaje){
		while(true){
			try{
				System.out.print(mensaje + ": ");
				return Double.parseDouble(scanner.nextLine().trim());
			}catch(NumberFormatException e){
				System.out.println("Entrada inválida. Introduce un número entero.");
			}
		}
	}


	// Cerramos el scanner al terminar
	public static void cerrarScanner(){
		scanner.close();
	}
}
