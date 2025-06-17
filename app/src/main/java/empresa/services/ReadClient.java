package empresa.services;

import empresa.dao.ClienteDAO;
import empresa.models.Cliente;
import empresa.utils.InputHelper;

public class ReadClient {
	public static void readById(){
		System.out.println("\n*** BUSCAR CLIENTE POR SU ID ***");

		// Pedimos el id al usuario
		Long id = InputHelper.leerLong("Introduzca el ID del cliente");

		// Creamos una instancia de ClienteDAO
		ClienteDAO clienteDAO = new ClienteDAO();

		// Buscamos el cliente en la BD
		Cliente cliente = clienteDAO.readById(id);

		// Mostramos el resultado
		if(cliente != null){
			System.out.println("\n--- Cliente encontrado ---");
			System.out.println("ID: " + cliente.getIdCliente());
			System.out.println("Nombre: " + cliente.getNombreCli());
			System.out.println("NIF: " + cliente.getNifCli());
			System.out.println("Dirección: " + cliente.getDireccionCli());
			System.out.println("Población: " + cliente.getPoblacionCli());
			System.out.println("Teléfono: " + cliente.getTelefonoCli());
			System.out.println("Email: " + cliente.getEmailCli());
		}else{
			System.out.println("No se encontró ningún cliente con el ID: " + id);
		}

	}
}
