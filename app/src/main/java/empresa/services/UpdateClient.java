package empresa.services;

import empresa.dao.ClienteDAO;
import empresa.models.Cliente;
import empresa.utils.InputHelper;

public class UpdateClient {
	public static void updateClient(){
		System.out.println("\n--- ACTUALIZAR CLIENTE ---");

		Long id = InputHelper.leerLong("Introduce el ID del cliente a actualizar");

		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente cliente = clienteDAO.readById(id);

		if(cliente == null){
			System.out.println("No se encontró ningún cliente con el ID: " + id);
			return;
		}

		System.out.println("\nDatos actuales del cliente:");
		System.out.println("1. Nombre: " + cliente.getNombreCli());
		System.out.println("2. NIF: " + cliente.getNifCli());
		System.out.println("3. Dirección: " + cliente.getDireccionCli());
		System.out.println("4. Población: " + cliente.getPoblacionCli());
		System.out.println("5. Teléfono: " + cliente.getTelefonoCli());
		System.out.println("6. Email: " + cliente.getEmailCli());

		System.out.println("Introduzca el dato que desee cambiar o enter para no cambiar");

		String nombre = InputHelper.leerString("Nuevo nombre");
		if(!nombre.isEmpty()) cliente.setNombreCli(nombre);

		String nif = InputHelper.leerString("Nuevo NIF");
		if(!nif.isEmpty()) cliente.setNifCli(nif);

		String direccion = InputHelper.leerString("Nueva dirección");
		if(!direccion.isEmpty()) cliente.setDireccionCli(direccion);

		String poblacion = InputHelper.leerString("Nueva población");
		if(!poblacion.isEmpty()) cliente.setPoblacionCli(poblacion);

		String telefono = InputHelper.leerString("Nuevo teléfono");
		if(!telefono.isEmpty()) cliente.setTelefonoCli(telefono);

		String email = InputHelper.leerString("Nuevo email");
		if(!email.isEmpty()) cliente.setEmailCli(email);

		clienteDAO.update(cliente);
		System.out.println("Cliente actualizado con éxito.");
	}
}
