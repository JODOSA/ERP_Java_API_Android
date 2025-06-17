package empresa.services;

import empresa.dao.ClienteDAO;
import empresa.dao.UsuariosDAO;
import empresa.models.Cliente;
import empresa.models.Usuarios;
import empresa.utils.InputHelper;

public class ReadUsuario {

	public static void readById(){
		System.out.println("\n*** BUSCAR USUARIO POR SU ID ***");

		// Pedimos el id al usuario
		Long id = InputHelper.leerLong("Introduzca el ID del usuario");

		// Creamos una instancia de ClienteDAO
		UsuariosDAO usuariosDAO = new UsuariosDAO();

		// Buscamos el cliente en la BD
		Usuarios usuarios = usuariosDAO.readById(id);

		// Mostramos el resultado
		if(usuarios != null){
			System.out.println("\n--- Usuario encontrado ---");
			System.out.println("ID: " + usuarios.getIdUsuarios());
			System.out.println("Nombre: " + usuarios.getNombreUs());
			System.out.println("Teléfono: " + usuarios.getTelefonoUs());
			System.out.println("Email: " + usuarios.getEmailUs());
			System.out.println("Password: " + usuarios.getPassword());
		}else{
			System.out.println("No se encontró ningún usuario con el ID: " + id);
		}

	}
}


