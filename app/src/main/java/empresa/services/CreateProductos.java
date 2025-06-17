package empresa.services;

import empresa.dao.ProductosDAO;
import empresa.models.Productos;
import empresa.utils.InputHelper;

public class CreateProductos {

	public static void createProd(){
		System.out.println("\n<<< CREANDO PRODUCTO >>>");
		String nombre = InputHelper.leerString("Introduzca el nombre del producto");
		String proveedor = InputHelper.leerString("Introduzca el proveedor del producto");
		Double precioCompra = InputHelper.leerDouble("Introduzca el precio de compra del producto");
		Double precioVenta = InputHelper.leerDouble("Introduzca el precio de venta del producto");

		Productos newProducto = new Productos(nombre, proveedor, precioCompra, precioVenta);
		ProductosDAO productosDAO = new ProductosDAO();
		productosDAO.create(newProducto);

		System.out.println("\n\nProducto creado con éxito.");
	}
}
