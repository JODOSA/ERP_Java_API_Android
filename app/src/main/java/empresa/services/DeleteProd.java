package empresa.services;

import empresa.dao.ClienteDAO;
import empresa.dao.ProductosDAO;
import empresa.models.Cliente;
import empresa.models.Productos;
import empresa.utils.InputHelper;

public class DeleteProd {
    public static void deleteProd(){
        System.out.println("\n--- ELIMINAR PRODUCTO ---");

        Long id = InputHelper.leerLong("Introduce el ID del producto que deseas eliminar");

        ProductosDAO productosDAO = new ProductosDAO();
        Productos productos = productosDAO.readById(id);

        if(productos == null){
            System.out.println("No se encontró ningún producto con el ID: " + id);
            return;
        }

        System.out.println("\nProducto encontrado:");
        System.out.println("ID: " + productos.getId_Prod());
        System.out.println("Nombre: " + productos.getNombreProd());
        System.out.println("Proveedor: " + productos.getProveedorProd());
        System.out.println("Precio de Compara: " + productos.getPrecioCompra());
        System.out.println("Precio de Venta: " + productos.getPrecioVenta());

        String confirmacion = InputHelper.leerString("¿Estás seguro de que quieres eliminar este producto? (S/N)").toUpperCase();

        if(confirmacion.equals("S")){
            productosDAO.deleteById(id);
            System.out.println("Producto eliminado con éxito.");
        }else{
            System.out.println("Operación cancelada. El producto no fué eliminado.");
        }
    }
}

