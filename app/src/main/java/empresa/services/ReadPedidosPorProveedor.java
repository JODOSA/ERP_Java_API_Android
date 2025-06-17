package empresa.services;

import empresa.dao.CabPedidosDAO;
import empresa.models.CabPedidos;
import empresa.utils.InputHelper;

import java.util.List;

public class ReadPedidosPorProveedor {

    public static void readByProveedorId() {
        System.out.println("\n*** CONSULTAR PEDIDOS DE UN PROVEEDOR ***");

        Long idProveedor = InputHelper.leerLong("Introduce el ID del proveedor");

        CabPedidosDAO cabPedidosDAO = new CabPedidosDAO();
        List<CabPedidos> pedidos = cabPedidosDAO.readAll();

        boolean hayPedidos = false;
        for (CabPedidos pedido : pedidos) {
            if (pedido.getProveedor().getIdProveedor().equals(idProveedor)) {
                hayPedidos = true;
                System.out.print("\nPedido Nº: " + pedido.getNumPed());
                System.out.print(" Fecha: " + pedido.getFechaPed());
                System.out.print(" Estado: " + pedido.getEstadoPed());
                System.out.print(" IVA: " + pedido.getIvaPed() + "%");
                System.out.println(" Total: " + pedido.getTotalPed() + " €");
            }
        }

        if (!hayPedidos) {
            System.out.println("No se encontraron pedidos para el proveedor con ID: " + idProveedor);
        }
    }
}
