package empresa.services;

import empresa.dao.CabFacturasDAO;
import empresa.dao.LineasFactDAO;
import empresa.models.CabFacturas;
import empresa.models.LineasFact;
import empresa.utils.InputHelper;

import java.util.List;

public class ReadFactura {
    public static void readById(){
        System.out.println("\n<<<< CONSULTAR FACTURA POR SU ID >>>");

        Long id = InputHelper.leerLong("Introduce el ID de la factura");

        CabFacturasDAO cabFacturasDAO = new CabFacturasDAO();
        CabFacturas factura = cabFacturasDAO.readById(id);

        if(factura != null){
            System.out.println("ID: " + factura.getNumFact());
            System.out.println("Fecha: " + factura.getFechaFact());
            System.out.println("Estado" + factura.getEstadoFact());
            System.out.println("IVA: " + factura.getIvaFact() + "%");
            System.out.println("Total: " + factura.getTotalFact() + "€");
            System.out.println("Cliente: " + factura.getCliente().getNombreCli());
            System.out.println("Usuario: " + factura.getUsuario().getNombreUs());

            // Ahora mostramos las líneas de la factura
            System.out.println("\n--- LINEAS DE LA FACTURA ---");
            LineasFactDAO lineasFactDAO = new LineasFactDAO();
            List<LineasFact> lineas = lineasFactDAO.readAll();

            boolean hayLineas = false;
            for(LineasFact linea : lineas){
                if(linea.getFactura().getNumFact().equals(factura.getNumFact())){
                    hayLineas = true;
                    System.out.print("Producto: " + linea.getProducto().getNombreProd());
                    System.out.print(" Cantidad: " + linea.getCantidadProdFact());
                    System.out.print(" Precio unitario: " + linea.getPrecioProdFact());
                    System.out.print(" Subtotal: " + linea.getSubtotalFact());
                    System.out.print(" Almacen: " + linea.getAlmacen().getNombreAlm());
                    System.out.println();
                }
            }
            if(!hayLineas){
                System.out.println("No hay líneas a sociadas a esta factura");
            }
        }else{
            System.out.println("No se encontró ninguna factura con el ID: " + id);
        }
    }
}
