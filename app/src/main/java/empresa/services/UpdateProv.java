package empresa.services;


import empresa.dao.ProveedoresDAO;
import empresa.models.Proveedores;
import empresa.utils.InputHelper;

public class UpdateProv {
	public static void updateProv(){
		System.out.println("\n--- ACTUALIZAR PROVEEDOR ---");

		Long id = InputHelper.leerLong("Introduce el ID del proveedor a actualizar");

		ProveedoresDAO proveedoresDAO = new ProveedoresDAO();
		Proveedores proveedor = proveedoresDAO.readById(id);

		if(proveedor == null){
			System.out.println("No se encontró ningún proveedor con el ID: " + id);
			return;
		}

		System.out.println("\nDatos actuales del proveedor:");
		System.out.println("1. Nombre: " + proveedor.getNombreProv());
		System.out.println("2. NIF: " + proveedor.getNifProv());
		System.out.println("3. Dirección: " + proveedor.getDireccionProv());
		System.out.println("4. Población: " + proveedor.getPoblacionProv());
		System.out.println("5. Teléfono: " + proveedor.getTelefonoProv());
		System.out.println("6. Email: " + proveedor.getEmailProv());

		System.out.println("Introduzca el dato que desee cambiar o enter para no cambiar");

		String nombre = InputHelper.leerString("Nuevo nombre");
		if(!nombre.isEmpty()) proveedor.setNombreProv(nombre);

		String nif = InputHelper.leerString("Nuevo NIF");
		if(!nif.isEmpty()) proveedor.setNifProv(nif);

		String direccion = InputHelper.leerString("Nueva dirección");
		if(!direccion.isEmpty()) proveedor.setDireccionProv(direccion);

		String poblacion = InputHelper.leerString("Nueva población");
		if(!poblacion.isEmpty()) proveedor.setPoblacionProv(poblacion);

		String telefono = InputHelper.leerString("Nuevo teléfono");
		if(!telefono.isEmpty()) proveedor.setTelefonoProv(telefono);

		String email = InputHelper.leerString("Nuevo email");
		if(!email.isEmpty()) proveedor.setEmailProv(email);

		proveedoresDAO.update(proveedor);
		System.out.println("Proveedor actualizado con éxito.");
	}
}
