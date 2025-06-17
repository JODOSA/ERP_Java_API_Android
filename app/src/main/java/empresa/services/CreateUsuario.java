package empresa.services;


import empresa.dao.UsuariosDAO;
import empresa.models.Usuarios;
import empresa.utils.InputHelper;
import empresa.utils.PasswordHelper;

public class CreateUsuario {

	public static void createUsuario(){
		System.out.println("\n<<< CREANDO USUARIO >>>");
		String nombre = InputHelper.leerString("Introduzca el nombre del usuario");
		String telefono = InputHelper.leerString("Introduzca el teléfono del usuario");
		String email = InputHelper.leerString("Introduzca el email del usuario");
		String password = InputHelper.leerString("Introduce la contraseña");

		Usuarios newUsuario = new Usuarios(nombre, telefono, email, password);
		newUsuario.setPassword(PasswordHelper.hashPassword(password));
		UsuariosDAO usuariosDAO = new UsuariosDAO();
		usuariosDAO.create(newUsuario);

		System.out.println("\n\nUsuario creado con éxito.");
	}
}
