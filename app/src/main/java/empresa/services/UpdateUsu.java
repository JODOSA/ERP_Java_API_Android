package empresa.services;

import empresa.dao.UsuariosDAO;
import empresa.models.Usuarios;
import empresa.utils.InputHelper;
import empresa.utils.PasswordHelper;

public class UpdateUsu {
	public static void updateUsu(){
		System.out.println("\n--- ACTUALIZAR USUARIO ---");

		Long id = InputHelper.leerLong("Introduce el ID del usuario a actualizar");

		UsuariosDAO usuariosDAO = new UsuariosDAO();
		Usuarios usuario = usuariosDAO.readById(id);

		if(usuario == null){
			System.out.println("No se encontró ningún usuario con el ID: " + id);
			return;
		}

		System.out.println("\nDatos actuales del usuario:");
		System.out.println("Nombre: " + usuario.getNombreUs());
		System.out.println("Teléfono: " + usuario.getTelefonoUs());
		System.out.println("Email: " + usuario.getEmailUs());
		System.out.println("Password (cod): " + usuario.getPassword());

		System.out.println("Introduzca el dato que desee cambiar o enter para no cambiar");

		String nombre = InputHelper.leerString("Nuevo nombre");
		if(!nombre.isEmpty()) usuario.setNombreUs(nombre);

		String telefono = InputHelper.leerString("Nuevo teléfono");
		if(!telefono.isEmpty()) usuario.setTelefonoUs(telefono);

		String email = InputHelper.leerString("Nuevo email");
		if(!email.isEmpty()) usuario.setEmailUs(email);

		String password = InputHelper.leerString("Nuevo password");
		if(!password.isEmpty()) usuario.setPassword(PasswordHelper.hashPassword(password));

		usuariosDAO.update(usuario);
		System.out.println("Usuario actualizado con éxito.");
	}
}
