package empresa.services;

import java.util.List;

import empresa.dao.ClienteDAO;
import empresa.dao.ProductosDAO;
import empresa.models.Cliente;
import empresa.models.Productos;

public class ReadAllProd {
    public static void readAll(){
        System.out.println("\n<<< LISTA DE PRODUCTOS >>>");

        ProductosDAO productosDAO = new ProductosDAO();

        List<Productos> productos = productosDAO.readAll();

        if(productos.isEmpty()){
            System.out.println("No hay productos registrados.");
        }else{
            for(Productos producto : productos){
                System.out.println("ID: " + producto.getId_Prod());
                System.out.println("Nombre: " + producto.getNombreProd());
                System.out.println("Proveedor: " + producto.getProveedorProd());
                System.out.println("Precio de Compra: " + producto.getPrecioCompra());
                System.out.println("Precio de Venta: " + producto.getPrecioVenta());
            }
        }
    }
}

