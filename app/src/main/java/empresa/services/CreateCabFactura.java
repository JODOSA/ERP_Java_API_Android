package empresa.services;

import empresa.dao.CabFacturasDAO;
import empresa.dao.ClienteDAO;
import empresa.dao.UsuariosDAO;
import empresa.models.CabFacturas;
import empresa.models.Cliente;
import empresa.models.Usuarios;
import empresa.utils.InputHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateCabFactura {

    public static void createCabFactura(){
        System.out.println("\n<<< CREANDO NUEVA FACTURA >>>");

        // Pedir datos al usuario
        Long idCliente = InputHelper.leerLong("Introduzca el ID del cliente");
        Long idUsuario = InputHelper.leerLong("Introduzca el ID de usuario");
        String estadoFact = InputHelper.leerString("Introduzca el estado de la factura");
        Double ivaFact = InputHelper.leerDouble("Introduzca el IVA (%)");
        //Double totalFact = InputHelper.leerDouble("Introduzca el total de la factura");

        // Obtener la fecha actual en formato yyyy-MM-dd
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFact = sdf.format(new Date());

        // Instanciar DAOs
        ClienteDAO clienteDAO = new ClienteDAO();
        UsuariosDAO usuariosDAO = new UsuariosDAO();

        // Verificar si el cliente existe
        Cliente cliente = clienteDAO.readById(idCliente);
        if(cliente == null){
            System.out.println("Error: El cliente con ID " + idCliente + " no existe.");
            return;
        }

        // Verificar si el usuario existe
        Usuarios usuario = usuariosDAO.readById(idUsuario);
        if (usuario == null) {
            System.out.println("Error: El usuario con ID " + idUsuario + " no existe.");
            return;
        }

        // El total de la factura se inicializa a 0.0 y después se calculará
        Double totalFact = 0.0;

        // Crear la entidad factura
        CabFacturas nuevaFactura = new CabFacturas(fechaFact, estadoFact, ivaFact, totalFact, cliente, usuario);

        // Guardar en la BD
        CabFacturasDAO cabFacturasDAO = new CabFacturasDAO();
        cabFacturasDAO.create(nuevaFactura);

        System.out.println("\nFactura creada con éxito. Número de factura: " + nuevaFactura.getNumFact());

        // Lanzar CreateLineasFact
        CreateLineasFact.createLineasFact(nuevaFactura);
    }
}
