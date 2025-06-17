package empresa.services;

import empresa.dao.ProductosAlmacenDAO;
import empresa.models.Productos;
import empresa.models.ProductosAlmacen;
import empresa.models.ProductosAlmacenId;
import empresa.utils.InputHelper;

public class ReadProductAlmacen {

    public static void readById() {
        System.out.println("\n*** BUSCAR PRODUCTO EN ALMACÉN ***");

        // Pedimos los IDs al usuario
        Long idAlmacen = InputHelper.leerLong("Introduzca el ID del almacén");
        Long idProducto = InputHelper.leerLong("Introduzca el ID del producto");

        // Crear la clave primaria compuesta
        ProductosAlmacenId id = new ProductosAlmacenId(idProducto.intValue(), idAlmacen.intValue());

        // Instanciar el DAO y buscar en la base de datos
        ProductosAlmacenDAO productosAlmacenDAO = new ProductosAlmacenDAO();
        ProductosAlmacen productosAlmacen = productosAlmacenDAO.readById(id);

        // Mostrar el resultado
        if (productosAlmacen != null) {
            System.out.println("\n--- Producto encontrado en almacén ---");
            System.out.println("ID Almacén: " + idAlmacen);
            System.out.println("ID Producto: " + idProducto);
            System.out.println("Stock: " + productosAlmacen.getStock());
        } else {
            System.out.println("No se encontró el producto con ID " + idProducto + " en el almacén " + idAlmacen);
        }
    }
}

