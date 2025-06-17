package empresa.services;

import empresa.dao.ProductosAlmacenDAO;
import empresa.models.ProductosAlmacen;

import java.util.List;

public class ReadAllProductsAlmacen {

    public static void readAll() {
        System.out.println("\n<<< LISTA DE PRODUCTOS EN ALMACÉN >>>");

        // Instanciar el DAO
        ProductosAlmacenDAO productosAlmacenDAO = new ProductosAlmacenDAO();

        // Obtener la lista de productos en almacenes
        List<ProductosAlmacen> productosAlmacenList = productosAlmacenDAO.readAll();

        // Mostrar resultados
        if (productosAlmacenList.isEmpty()) {
            System.out.println("No hay productos almacenados.");
        } else {
            for (ProductosAlmacen productoAlmacen : productosAlmacenList) {
                System.out.println("ID Almacén: " + productoAlmacen.getId().getIdAlmacen());
                System.out.println("ID Producto: " + productoAlmacen.getId().getIdProducto());
                System.out.println("Stock: " + productoAlmacen.getStock());
                System.out.println("------------------------------------");
            }
        }
    }
}

