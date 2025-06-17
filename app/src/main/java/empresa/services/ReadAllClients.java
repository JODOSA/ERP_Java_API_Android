package empresa.services;

import java.util.List;

import empresa.dao.ClienteDAO;
import empresa.models.Cliente;

public class ReadAllClients {
	public static void readAll(){
		System.out.println("\n<<< LISTA DE CLIENTES >>>");

		ClienteDAO clienteDAO = new ClienteDAO();

		List<Cliente> clientes = clienteDAO.readAll();

		if(clientes.isEmpty()){
			System.out.println("No hay clientes registrados.");
		}else{
			for(Cliente cliente : clientes){
				System.out.println("ID: " + cliente.getIdCliente());
				System.out.println("Nombre: " + cliente.getNombreCli());
				System.out.println("NIF: " + cliente.getNifCli());
				System.out.println("Dirección: " + cliente.getDireccionCli());
				System.out.println("Población: " + cliente.getPoblacionCli());
				System.out.println("Teléfono: " + cliente.getTelefonoCli());
				System.out.println("Email: " + cliente.getEmailCli());
			}
		}
	}
}
