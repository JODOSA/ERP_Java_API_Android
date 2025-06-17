package empresa.services;

import empresa.dao.CabFacturasDAO;
import empresa.models.CabFacturas;
import empresa.utils.InputHelper;

import java.util.List;

public class ReadFacturasPorCliente {

    public static void readByClienteId(){
        System.out.println("\n<<< CONSULTAR FACTURAS DE UN CLIENTE >>>");

        Long idCliente = InputHelper.leerLong("Introduce el ID del cliente");

        CabFacturasDAO cabFacturasDAO = new CabFacturasDAO();
        List<CabFacturas> facturas = cabFacturasDAO.readAll();

        boolean hayFacturas = false;
        for(CabFacturas factura : facturas){
            if(factura.getCliente().getIdCliente().equals(idCliente)){
                hayFacturas = true;
                System.out.print("Factura nº: " + factura.getNumFact());
                System.out.print(" Fecha: " + factura.getFechaFact());
                System.out.print(" Estado: " + factura.getEstadoFact());
                System.out.print(" IVA: " + factura.getIvaFact());
                System.out.println(" Total: " + factura.getTotalFact());
            }
        }
        if(!hayFacturas){
            System.out.println("No se encontraron facturas para el cliente con el ID: " + idCliente);
        }
    }
}
