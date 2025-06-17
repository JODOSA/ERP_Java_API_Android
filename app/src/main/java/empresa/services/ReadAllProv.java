package empresa.services;

import java.util.List;

import empresa.dao.ProveedoresDAO;
import empresa.models.Proveedores;

public class ReadAllProv {
	
	public static void readAll(){
		System.out.println("\n<<< LISTA DE PROVEEDORES >>>");

		ProveedoresDAO proveedoresDAO = new ProveedoresDAO();

		List<Proveedores> proveedores = proveedoresDAO.readAll();

		if(proveedores.isEmpty()){
			System.out.println("No hay proveedores registrados.");
		}else{
			for(Proveedores proveedor : proveedores){
				System.out.println("ID: " + proveedor.getIdProveedor());
				System.out.println("Nombre: " + proveedor.getNombreProv());
				System.out.println("NIF: " + proveedor.getNifProv());
				System.out.println("Dirección: " + proveedor.getDireccionProv());
				System.out.println("Población: " + proveedor.getPoblacionProv());
				System.out.println("Teléfono: " + proveedor.getTelefonoProv());
				System.out.println("Email: " + proveedor.getEmailProv());
			}
		}
	}
}

