package empresa.services;

import empresa.dao.ClienteDAO;
import empresa.dao.ProductosDAO;
import empresa.models.Cliente;
import empresa.models.Productos;
import empresa.utils.InputHelper;

public class UpdateProd {
    public static void updateProd(){
        System.out.println("\n--- ACTUALIZAR PRODUCTO ---");

        Long id = InputHelper.leerLong("Introduce el ID del producto a actualizar");

        ProductosDAO productosDAO = new ProductosDAO();
        Productos productos = productosDAO.readById(id);

        if(productos == null){
            System.out.println("No se encontró ningún producto con el ID: " + id);
            return;
        }

        System.out.println("\nDatos actuales del producto:");
        System.out.println("1. Nombre: " + productos.getNombreProd());
        System.out.println("2. Proveedor: " + productos.getProveedorProd());
        System.out.println("3. Precio de Compra: " + productos.getPrecioCompra());
        System.out.println("4. Precio de Venta: " + productos.getPrecioVenta());

        System.out.println("Introduzca el dato que desee cambiar o enter para no cambiar");

        String nombre = InputHelper.leerString("Nuevo nombre");
        if(!nombre.isEmpty()) productos.setNombreProd(nombre);

        String proveedor = InputHelper.leerString("Nuevo Proveedor");
        if(!proveedor.isEmpty()) productos.setProveedorProd(proveedor);

        Double precioCompra = InputHelper.leerDouble("Nuevo precio de compra");
        if(precioCompra > 0.0) productos.setPrecioCompra(precioCompra);

        Double precioVenta = InputHelper.leerDouble("Nuevo precio de venta");
        if(precioVenta > 0.0) productos.setPrecioVenta(precioVenta);

        productosDAO.update(productos);
        System.out.println("Producto actualizado con éxito.");
    }
}

