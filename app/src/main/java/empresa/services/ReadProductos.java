package empresa.services;

import empresa.dao.ProductosDAO;
import empresa.models.Productos;
import empresa.utils.InputHelper;

public class ReadProductos {
    public static void readById(){
        System.out.println("\n*** BUSCAR PRODUCTO POR SU ID ***");

        Long id = InputHelper.leerLong("Introduzca el ID del producto");

        ProductosDAO productosDAO = new ProductosDAO();

        Productos productos = productosDAO.readById(id);

        if(productos != null){
            System.out.println("\n--- Producto encontrado ---");
            System.out.println("ID: " + productos.getId_Prod());
            System.out.println("Nombre: " + productos.getNombreProd());
            System.out.println("Proveedor: " + productos.getProveedorProd());
            System.out.println("Precio Compra: " + productos.getPrecioCompra());
            System.out.println("Precio Venta: " + productos.getPrecioVenta());
        }else{
            System.out.println("No se encontró ningún producto con el ID: " + id);
        }

    }
}

