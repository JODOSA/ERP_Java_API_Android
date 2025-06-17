package empresa.services;

import empresa.dao.ClienteDAO;
import empresa.models.Cliente;
import empresa.utils.InputHelper;

public class CreateClient {

	public static void createClient(){
		System.out.println("\n<<< CREANDO CLIENTE >>>");
		String nombre = InputHelper.leerString("Introduzca el nombre del cliente");
		String nif = InputHelper.leerString("Introduzca el nif del cliente");
		String direccion = InputHelper.leerString("Introduzca la dirección del cliente");
		String poblacion = InputHelper.leerString("Introduzca la población del cliente");
		String telefono = InputHelper.leerString("Introduzca el teléfono del cliente");
		String email = InputHelper.leerString("Introduzca el email del cliente");

		Cliente newClient = new Cliente(nombre, nif, direccion, poblacion, telefono, email);
		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.create(newClient);

		System.out.println("\n\nCliente creado con éxito.");
	}
}
