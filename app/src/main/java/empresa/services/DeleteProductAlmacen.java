package empresa.services;

import empresa.dao.ProductosAlmacenDAO;
import empresa.models.ProductosAlmacen;
import empresa.models.ProductosAlmacenId;
import empresa.utils.InputHelper;

public class DeleteProductAlmacen {

    public static void deleteProductAlmacen() {
        System.out.println("\n--- ELIMINAR PRODUCTO DE ALMACÉN ---");

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

        // Confirmación de eliminación
        String confirmacion = InputHelper.leerString("¿Está seguro de eliminar este registro? (S/N)").toUpperCase();
        if (!confirmacion.equals("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        // Eliminar el registro
        productosAlmacenDAO.deleteById(id);
        System.out.println("Producto eliminado del almacén con éxito.");
    }
}

