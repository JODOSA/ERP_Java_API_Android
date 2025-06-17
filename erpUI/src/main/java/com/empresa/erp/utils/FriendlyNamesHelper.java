package com.empresa.erp.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class FriendlyNamesHelper {

    public static Map<String, String> getFriendlyFieldNames(Class<?> clazz) {
        Map<String, String> map = new LinkedHashMap<>();

        if(clazz.getSimpleName().equals("Cliente")) {
            map.put("IdCliente", "ID");
            map.put("NombreCli", "Nombre");
            map.put("NifCli", "NIF");
            map.put("DireccionCli", "Dirección");
            map.put("PoblacionCli", "Población");
            map.put("TelefonoCli", "Telefono");
            map.put("EmailCli", "Email");
        }else if(clazz.getSimpleName().equals("Usuarios")) {
            map.put("IdUsuario", "ID");
            map.put("NombreUs", "Nombre");
            map.put("TelefonoUs", "Teléfono");
            map.put("EmailUs", "Email");
        }else if(clazz.getSimpleName().equals("Proveedores")) {
            map.put("IdProveedor", "ID");
            map.put("NombreProv", "Nombre");
            map.put("NifProv", "NIF");
            map.put("DireccionProv", "Dirección");
            map.put("PoblacionProv", "Población");
            map.put("TelefonoProv", "Teléfono");
            map.put("EmailProv", "Email");
        }else if(clazz.getSimpleName().equals("Productos")) {
            map.put("Id_Prod", "ID");
            map.put("NombreProd", "Nombre");
            map.put("ProveedorProd", "Proveedor");
            map.put("PrecioCompra", "Precio Compra");
            map.put("PrecioVenta", "Precio Venta");
        }else if(clazz.getSimpleName().equals("Almacen")) {
            map.put("Id_Almacen", "ID");
            map.put("NombreAlm", "Nombre");
            map.put("UbicacionAlm", "Ubicación");

        }else if(clazz.getSimpleName().equals("CabPedidos")) {
            map.put("NumPed", "Pedido");
            map.put("FechaPed", "Fecha");
            map.put("EstadoPed", "Estado");
            map.put("IvaPed", "Iva");
            map.put("TotalPed", "Total");
            map.put("Proveedor", "Proveedor");
            map.put("Usuario", "Usuario");
        }else if(clazz.getSimpleName().equals("CabFacturas")) {
            map.put("NumFact", "Factura");
            map.put("FechaFact", "Fecha");
            map.put("EstadoFact", "Estado");
            map.put("IvaFact", "Iva");
            map.put("TotalFact", "Total");
            map.put("Cliente", "Cliente");
            map.put("Usuario", "Usuario");
        } else if (clazz.getSimpleName().equals("ProductosAlmacen")) {
            map.put("IdProducto", "Clave ID");
            map.put("Stock", "Stock");
            map.put("Producto", "Producto");
            map.put("Almacen", "Almacén");
        }

        return map;
    }
}
