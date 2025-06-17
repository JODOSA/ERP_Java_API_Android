package empresa.services;

import empresa.dao.CabPedidosDAO;
import empresa.dao.LineasPedDAO;
import empresa.models.CabPedidos;
import empresa.models.LineasPed;
import empresa.utils.InputHelper;

import java.util.List;

public class ReadPedido {
    public static void readById() {
        System.out.println("\n*** CONSULTAR PEDIDO POR SU ID ***");

        Long id = InputHelper.leerLong("Introduce el ID del pedido");

        CabPedidosDAO cabPedidosDAO = new CabPedidosDAO();
        CabPedidos pedido = cabPedidosDAO.readById(id);

        if (pedido != null) {
            System.out.println("ID: " + pedido.getNumPed());
            System.out.println("Fecha: " + pedido.getFechaPed());
            System.out.println("Estado: " + pedido.getEstadoPed());
            System.out.println("IVA: " + pedido.getIvaPed() + "%");
            System.out.println("Total: " + pedido.getTotalPed() + " €");
            System.out.println("Proveedor: " + pedido.getProveedor().getNombreProv());
            System.out.println("Usuario: " + pedido.getUsuario().getNombreUs());

            // Mostrar líneas asociadas al pedido
            LineasPedDAO lineasPedDAO = new LineasPedDAO();
            List<LineasPed> lineas = lineasPedDAO.readAll();

            boolean hayLineas = false;
            for (LineasPed linea : lineas) {
                if (linea.getCabPedidos().getNumPed().equals(pedido.getNumPed())) {
                    hayLineas = true;
                    System.out.print("Producto: " + linea.getProducto().getNombreProd());
                    System.out.print("  Cantidad: " + linea.getCantidadProdPed());
                    System.out.print("  Precio compra: " + linea.getPrecioProdPed());
                    System.out.print("  Subtotal: " + linea.getSubtotalPed());
                    System.out.print("  Almacén: " + linea.getAlmacen().getNombreAlm());
                    System.out.println();
                }
            }

            if (!hayLineas) {
                System.out.println("No hay líneas asociadas a este pedido.");
            }

        } else {
            System.out.println("No se encontró ningún pedido con el ID: " + id);
        }
    }
}
