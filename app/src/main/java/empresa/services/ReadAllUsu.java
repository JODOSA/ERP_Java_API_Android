package empresa.services;

import java.util.List;

import empresa.dao.UsuariosDAO;
import empresa.models.Usuarios;

public class ReadAllUsu{

	public static void readAll(){
		System.out.println("\n<<< LISTA DE USUARIOS >>>");

		UsuariosDAO usuariosDAO = new UsuariosDAO();

		List<Usuarios> usuarios = usuariosDAO.readAll();

		if(usuarios.isEmpty()){
			System.out.println("No hay usuarios registrados.");
		}else{
			for(Usuarios usuario : usuarios){
				System.out.println("ID: " + usuario.getIdUsuarios());
				System.out.println("Nombre: " + usuario.getNombreUs());
				System.out.println("Teléfono: " + usuario.getTelefonoUs());
				System.out.println("Email: " + usuario.getEmailUs());
				System.out.println("Password: " + usuario.getPassword());
			}
		}
	}
}
