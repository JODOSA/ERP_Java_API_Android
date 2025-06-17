package empresa.services;

import empresa.dao.ProveedoresDAO;
import empresa.models.Proveedores;
import empresa.utils.InputHelper;

public class CreateProveedores {
	public static void createProveedores(){
		System.out.println("\n<<< CREANDO PROVEEDOR >>>");
		String nombre = InputHelper.leerString("Introduzca el nombre del proveedor");
		String nif = InputHelper.leerString("Introduzca el nif del proveedor");
		String direccion = InputHelper.leerString("Introduzca la dirección del proveedor");
		String poblacion = InputHelper.leerString("Introduzca la población del proveedor");
		String telefono = InputHelper.leerString("Introduzca el teléfono del proveedor");
		String email = InputHelper.leerString("Introduzca el email del proveedor");

		Proveedores newProveedores = new Proveedores(nombre, nif, direccion, poblacion, telefono,email);
		ProveedoresDAO proveedoresDAO = new ProveedoresDAO();
		proveedoresDAO.create(newProveedores);

		System.out.println("\n\nProveedor creado con éxito.");
	}
}
