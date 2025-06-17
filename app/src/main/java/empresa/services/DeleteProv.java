package empresa.services;

import empresa.dao.ProveedoresDAO;
import empresa.models.Proveedores;
import empresa.utils.InputHelper;

public class DeleteProv {
	public static void deleteProv(){
		System.out.println("\n--- ELIMINAR PROVEEDOR ---");

		Long id = InputHelper.leerLong("Introduce el ID del proveedor que deseas eliminar");

		ProveedoresDAO proveedoresDAO = new ProveedoresDAO();
		Proveedores proveedor = proveedoresDAO.readById(id);

		if(proveedor == null){
			System.out.println("No se encontró ningún proveedor con el ID: " + id);
			return;
		}

		System.out.println("\nProveedor encontrado:");
		System.out.println("ID: " + proveedor.getIdProveedor());
		System.out.println("Nombre: " + proveedor.getNombreProv());
		System.out.println("NIF: " + proveedor.getNifProv());
		System.out.println("Dirección: " + proveedor.getDireccionProv());
		System.out.println("Población: " + proveedor.getPoblacionProv());
		System.out.println("Teléfono: " + proveedor.getTelefonoProv());
		System.out.println("Email: " + proveedor.getEmailProv());

		String confirmacion = InputHelper.leerString("¿Estás seguro de que quieres eliminar este proveedor? (S/N)").toUpperCase();

		if(confirmacion.equals("S")){
			proveedoresDAO.deleteById(id);
			System.out.println("Proveedor eliminado con éxito.");
		}else{
			System.out.println("Operación cancelada. El proveedor no fué eliminado.");
		}
	}
}
