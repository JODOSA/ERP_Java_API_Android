package empresa.services;

import empresa.dao.ClienteDAO;
import empresa.models.Cliente;
import empresa.utils.InputHelper;

public class DeleteClient {
	public static void deleteClient(){
		System.out.println("\n--- ELIMINAR CLIENTE ---");

		Long id = InputHelper.leerLong("Introduce el ID del cliente que deseas eliminar");

		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente cliente = clienteDAO.readById(id);

		if(cliente == null){
			System.out.println("No se encontró ningún cliente con el ID: " + id);
			return;
		}

		System.out.println("\nCliente encontrado:");
		System.out.println("ID: " + cliente.getIdCliente());
		System.out.println("Nombre: " + cliente.getNombreCli());
		System.out.println("NIF: " + cliente.getNifCli());
		System.out.println("Dirección: " + cliente.getDireccionCli());
		System.out.println("Población: " + cliente.getPoblacionCli());
		System.out.println("Teléfono: " + cliente.getTelefonoCli());
		System.out.println("Email: " + cliente.getEmailCli());

		String confirmacion = InputHelper.leerString("¿Estás seguro de que quieres eliminar este cliente? (S/N)").toUpperCase();

		if(confirmacion.equals("S")){
			clienteDAO.deleteById(id);
			System.out.println("Cliente eliminado con éxito.");
		}else{
			System.out.println("Operación cancelada. El cliente no fué eliminado.");
		}
	}
}
