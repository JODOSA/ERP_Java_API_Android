package empresa.services;

import empresa.dao.CabPedidosDAO;
import empresa.dao.ProveedoresDAO;
import empresa.dao.UsuariosDAO;
import empresa.models.CabPedidos;
import empresa.models.Proveedores;
import empresa.models.Usuarios;
import empresa.utils.InputHelper;
import jakarta.persistence.criteria.CriteriaBuilder;
import empresa.utils.UserSession;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateCabPedido {

    public static void createCabPedido(){
        System.out.println("\n<<< CREAR CABECERA DE PEDIDO >>>");

        Long idProveedor = InputHelper.leerLong("Introduzca el ID del proveedor");
        //Long idUsuario = InputHelper.leerLong("Introduzca el ID del usuario que realiza el pedido");
        String estadoPed = InputHelper.leerString("Introduce el estado del pedido");
        Double ivaPed = InputHelper.leerDouble("Introduce el IVA aplicable al pedido");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaPed = sdf.format(new Date());

        ProveedoresDAO proveedoresDAO = new ProveedoresDAO();
        UsuariosDAO usuariosDAO = new UsuariosDAO();

        Proveedores proveedor = proveedoresDAO.readById(idProveedor);
        if(proveedor == null){
            System.out.println("Error: El proveedor con ID " + idProveedor + " no existe");
            return;
        }

        /*Usuarios usuario = usuariosDAO.readById(idUsuario);
        if(usuario == null){
            System.out.println("Error: El usuario con ID " + idUsuario + " no existe");
            return;
        }*/

        Double totalPed = 0.0;

        CabPedidos nuevoPedido = new CabPedidos(fechaPed, estadoPed, ivaPed, totalPed, proveedor, UserSession.getUsuario());

        CabPedidosDAO cabPedidosDAO = new CabPedidosDAO();
        cabPedidosDAO.create(nuevoPedido);

        System.out.println("\nPedido creado con éxito. Número de pedido: " + nuevoPedido.getNumPed());

        CreateLineasPed.createLineasPed(nuevoPedido);
    }
}












