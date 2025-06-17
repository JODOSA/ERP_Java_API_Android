package empresa.services;

import empresa.dao.ProductosAlmacenDAO;
import empresa.dao.ProductosDAO;
import empresa.dao.AlmacenDAO;
import empresa.models.ProductosAlmacen;
import empresa.models.ProductosAlmacenId;
import empresa.models.Productos;
import empresa.models.Almacen;
import empresa.utils.InputHelper;

public class CreateProductAlmacen {

    public static void createProductAlmacen() {
        System.out.println("\n<<< CREANDO PRODUCTO EN ALMACÉN >>>");

        // Pedir datos al usuario
        Long idAlmacen = InputHelper.leerLong("Introduzca el ID del almacén");
        Long idProducto = InputHelper.leerLong("Introduzca el ID del producto");
        int stock = InputHelper.leerInt("Introduzca la cantidad de stock");

        // Instanciar DAOs
        ProductosDAO productosDAO = new ProductosDAO();
        AlmacenDAO almacenDAO = new AlmacenDAO();

        // Verificar si el producto existe
        Productos producto = productosDAO.readById(idProducto);
        if (producto == null) {
            System.out.println("Error: El producto con ID " + idProducto + " no existe.");
            return;
        }

        // Verificar si el almacén existe
        Almacen almacen = almacenDAO.readById(idAlmacen);
        if (almacen == null) {
            System.out.println("Error: El almacén con ID " + idAlmacen + " no existe.");
            return;
        }

        // Crear la clave primaria compuesta
        ProductosAlmacenId id = new ProductosAlmacenId(idProducto.intValue(), idAlmacen.intValue());

        // Crear la entidad
        ProductosAlmacen nuevoRegistro = new ProductosAlmacen(id, stock);

        // Guardar en la base de datos
        ProductosAlmacenDAO productosAlmacenDAO = new ProductosAlmacenDAO();
        productosAlmacenDAO.create(nuevoRegistro);

        System.out.println("\nProducto agregado al almacén con éxito.");
    }
}
