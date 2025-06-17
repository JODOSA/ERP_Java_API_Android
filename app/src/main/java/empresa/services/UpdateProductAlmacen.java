package empresa.services;

import empresa.dao.ProductosAlmacenDAO;
import empresa.models.ProductosAlmacen;
import empresa.models.ProductosAlmacenId;
import empresa.utils.InputHelper;

public class UpdateProductAlmacen {

    public static void updateStock() {
        System.out.println("\n--- ACTUALIZAR STOCK DE UN PRODUCTO EN ALMACÉN ---");

        // Pedimos los IDs al usuario
        Long idAlmacen = InputHelper.leerLong("Introduzca el ID del almacén");
        Long idProducto = InputHelper.leerLong("Introduzca el ID del producto");

        // Crear la clave primaria compuesta
        ProductosAlmacenId id = new ProductosAlmacenId(idProducto.intValue(), idAlmacen.intValue());

        // Instanciar el DAO y buscar en la base de datos
        ProductosAlmacenDAO productosAlmacenDAO = new ProductosAlmacenDAO();
        ProductosAlmacen productosAlmacen = productosAlmacenDAO.readById(id);

        // Si no se encuentra el producto en el almacén, mostrar mensaje y salir
        if (productosAlmacen == null) {
            System.out.println("No se encontró el producto con ID " + idProducto + " en el almacén " + idAlmacen);
            return;
        }

        // Mostrar stock actual
        System.out.println("\nStock actual: " + productosAlmacen.getStock());

        // Pedir el nuevo stock
        int nuevoStock = InputHelper.leerInt("Introduzca el nuevo stock");

        // Verificar que el stock no sea negativo
        if (nuevoStock < 0) {
            System.out.println("Error: El stock no puede ser negativo.");
            return;
        }

        // Actualizar el stock
        productosAlmacen.setStock(nuevoStock);
        productosAlmacenDAO.update(productosAlmacen);

        System.out.println("Stock actualizado con éxito.");
    }
}

