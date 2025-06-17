package empresa.services;

import empresa.dao.*;
import empresa.models.*;
import empresa.utils.InputHelper;

import java.util.List;

public class CreateLineasFact {

    public static void createLineasFact(CabFacturas factura){
        System.out.println("\n<<< AGREGANDO PRODUCTOS A LA FACTURA " + factura.getNumFact() + " >>>");

        // Selección del almacén
        int idAlmacen = InputHelper.leerInt("Introduzca el ID del almacén del que saldrán los productos");
        AlmacenDAO almacenDAO = new AlmacenDAO();
        Almacen almacen = almacenDAO.readById(idAlmacen);

        if(almacen == null){
            System.out.println("Error el almacén no existe");
            return;
        }

        LineasFactDAO lineasFactDAO = new LineasFactDAO();
        ProductosDAO productosDAO = new ProductosDAO();
        ProductosAlmacenDAO productosAlmacenDAO = new ProductosAlmacenDAO();
        CabFacturasDAO cabFacturasDAO = new CabFacturasDAO();

        double totalSinIVA = 0.0;

        while(true){
            // Pedir el producto
            String input = InputHelper.leerString("Introduzca el ID del producto o escriba 'exit' para salir");
            if(input.equalsIgnoreCase("exit")) break;

            int idProducto;
            try {
                idProducto = Integer.parseInt(input);
            }catch(NumberFormatException e){
                System.out.println("Error: Introduzca un número válido");
                continue;
            }

            // Verificar si el producto existe
            Productos producto = productosDAO.readById(idProducto);
            if(producto == null){
                System.out.println("Error: El producto con ID " + idProducto + " no existe");
                continue;
            }

            System.out.println(producto.getNombreProd());

            // Pedir la cantidad
            int cantidad = InputHelper.leerInt("Cantidad: ");

            if(cantidad == 0){
                System.out.println("Producto descartado");
                continue;
            }

            // Verificar stock
            ProductosAlmacenId prodAlmId = new ProductosAlmacenId(
                    producto.getId_Prod().intValue(),
                    almacen.getId_Almacen().intValue()
            );
            ProductosAlmacen productosAlmacen = productosAlmacenDAO.readById(prodAlmId);
            if(productosAlmacen == null || productosAlmacen.getStock() < cantidad){
                System.out.println("Error: Stock insuficiente");
                continue;
            }

            // Calcular subtotal
            double precioUnitario = producto.getPrecioVenta();
            double subtotal = cantidad * precioUnitario;
            totalSinIVA += subtotal;

            // Crear línea de factura y guardarla
            LineasFact nuevaLinea = new LineasFact(factura, producto, almacen, cantidad,precioUnitario);
            lineasFactDAO.create(nuevaLinea);

            // Actualizar stock en PRODUCTOS_ALMACEN
            productosAlmacen.setStock(productosAlmacen.getStock() - cantidad);
            productosAlmacenDAO.update(productosAlmacen);

        }

        // Calcular 'Total_Fact' con IVA y guardar en 'CAB_FACTURAS'
        double iva = factura.getIvaFact();
        double totalFact = totalSinIVA + (totalSinIVA * iva / 100);
        factura.setTotalFact(totalFact);
        // CabFacturasDAO cabFacturasDAO = new CabFacturasDAO();
        cabFacturasDAO.update(factura);

        System.out.println("\nTotal Factura (sin IVA): " + totalSinIVA);
        System.out.println("Total Factura (con IVA) " + iva + "%): " + totalFact);
        System.out.println("Factura completada y guardada con éxito");
    }
}
