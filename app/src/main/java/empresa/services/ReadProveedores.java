package empresa.services;

import empresa.dao.ProveedoresDAO;
import empresa.models.Proveedores;
import empresa.utils.InputHelper;

public class ReadProveedores {
	public static void readById(){
		System.out.println("\n*** BUSCAR PROVEEDOR POR SU ID ***");

		// Pedimos el id al usuario
		Long id = InputHelper.leerLong("Introduzca el ID del proveedor");

		// Creamos una instancia de ProveedoresDAO
		ProveedoresDAO proveedoresDAO = new ProveedoresDAO();

		// Buscamos el cliente en la BD
		Proveedores proveedores = proveedoresDAO.readById(id);

		// Mostramos el resultado
		if(proveedores != null){
			System.out.println("\n--- Proveedor encontrado ---");
			System.out.println("ID: " + proveedores.getIdProveedor());
			System.out.println("Nombre: " + proveedores.getNombreProv());
			System.out.println("NIF: " + proveedores.getNifProv());
			System.out.println("Dirección: " + proveedores.getDireccionProv());
			System.out.println("Población: " + proveedores.getPoblacionProv());
			System.out.println("Teléfono: " + proveedores.getTelefonoProv());
			System.out.println("Email: " + proveedores.getEmailProv());
		}else{
			System.out.println("No se encontró ningún proveedor con el ID: " + id);
		}

	}
}

