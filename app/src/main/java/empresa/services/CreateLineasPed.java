package empresa.services;

import empresa.dao.*;
import empresa.models.*;
import empresa.utils.InputHelper;

import java.util.Scanner;

public class CreateLineasPed {

    public static void createLineasPed(CabPedidos pedido){
        System.out.println("\n<<< AÑADIENDO LINEAS AL PEDIDO " + pedido.getNumPed() + " >>>");

        int idAlmacen = InputHelper.leerInt("Introduzca el ID del almacén donde se recibirá el producto");
        AlmacenDAO almacenDAO = new AlmacenDAO();
        Almacen almacen = almacenDAO.readById(idAlmacen);

        if(almacen == null){
            System.out.println("Error: El almacén no existe");
            return;
        }

        LineasPedDAO lineasPedDAO = new LineasPedDAO();
        ProductosDAO productosDAO = new ProductosDAO();
        ProductosAlmacenDAO productosAlmacenDAO = new ProductosAlmacenDAO();
        CabPedidosDAO cabPedidosDAO = new CabPedidosDAO();

        double totalSinIVA = 0.0;

        while(true){
            String input = InputHelper.leerString("Introduzca el ID del producto o escriba 'exit' para salir");
            if(input.equalsIgnoreCase("exit")) break;

            int idProducto;
            try{
                idProducto = Integer.parseInt(input);
            }catch(NumberFormatException e){
                System.out.println("Error: Introduzca un número válido");
                continue;
            }

            Productos producto = productosDAO.readById(idProducto);
            if(producto == null){
                System.out.println("Error: El producto con ID " + idProducto + " no existe");
                continue;
            }

            System.out.println(producto.getNombreProd());

            int cantidad = InputHelper.leerInt("Cantidad: ");
            if(cantidad == 0){
                System.out.println("Producto descartado");
                continue;
            }

            double precioCompra = producto.getPrecioCompra();
            double subtotal = cantidad * precioCompra;
            totalSinIVA += subtotal;

            LineasPed nuevaLinea = new LineasPed(null, cantidad, precioCompra, subtotal, pedido, producto, almacen);
            lineasPedDAO.create(nuevaLinea);

            ProductosAlmacenId prodAlmId = new ProductosAlmacenId(
                    producto.getId_Prod().intValue(),
                    almacen.getId_Almacen().intValue()
            );

            ProductosAlmacen productosAlmacen = productosAlmacenDAO.readById(prodAlmId);

            if(productosAlmacen != null){
                productosAlmacen.setStock(productosAlmacen.getStock() + cantidad);
                productosAlmacenDAO.update(productosAlmacen);
            }
        }

        double iva = pedido.getIvaPed();
        double totalPed = totalSinIVA + (totalSinIVA * iva / 100);
        pedido.setTotalPed(totalPed);
        cabPedidosDAO.update(pedido);

        System.out.println("\nTotal Pedido (sin IVA): " + totalSinIVA);
        System.out.println("Total Pedido (con IVA " + iva + "%): " + totalPed);
        System.out.println("Pedido completado y guardado con éxito");

    }
}
