package empresa.services;

import empresa.dao.UsuariosDAO;
import empresa.models.Usuarios;
import empresa.utils.InputHelper;

public class DeleteUsu {

	public static void deleteUsu(){
		System.out.println("\n--- ELIMINAR USUARIO ---");

		Long id = InputHelper.leerLong("Introduce el ID del usuario que deseas eliminar");

		UsuariosDAO usuariosDAO = new UsuariosDAO();
		Usuarios usuario = usuariosDAO.readById(id);

		if(usuario == null){
			System.out.println("No se encontró ningún usuario con el ID: " + id);
			return;
		}

		System.out.println("\nUsuario encontrado:");
		System.out.println("ID: " + usuario.getIdUsuarios());
		System.out.println("Nombre: " + usuario.getNombreUs());
		System.out.println("Teléfono: " + usuario.getTelefonoUs());
		System.out.println("Email: " + usuario.getEmailUs());
		System.out.println("Password (cod): No es posible borrarlo, solo cambiarlo");

		String confirmacion = InputHelper.leerString("¿Estás seguro de que quieres eliminar este usuario? (S/N)").toUpperCase();

		if(confirmacion.equals("S")){
			usuariosDAO.deleteById(id);
			System.out.println("Usuario eliminado con éxito.");
		}else{
			System.out.println("Operación cancelada. El usuario no fué eliminado.");
		}
	}
}
