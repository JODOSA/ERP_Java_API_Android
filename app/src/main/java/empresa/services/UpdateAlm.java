package empresa.services;

import empresa.dao.AlmacenDAO;
import empresa.dao.UsuariosDAO;
import empresa.models.Almacen;
import empresa.models.Usuarios;
import empresa.utils.InputHelper;

public class UpdateAlm {
    public static void updateAlm(){
        System.out.println("\n--- ACTUALIZAR ALMACEN ---");

        Long id = InputHelper.leerLong("Introduce el ID del almacén a actualizar");

        AlmacenDAO almacenDAO = new AlmacenDAO();
        Almacen almacen = almacenDAO.readById(id);

        if(almacen == null){
            System.out.println("No se encontró ningún almacén con el ID: " + id);
            return;
        }

        System.out.println("\nDatos actuales del almacén:");
        System.out.println("1. Nombre: " + almacen.getNombreAlm());
        System.out.println("5. Ubicación: " + almacen.getUbicacionAlm());

        System.out.println("Introduzca el dato que desee cambiar o enter para no cambiar");

        String nombre = InputHelper.leerString("Nuevo nombre");
        if(!nombre.isEmpty()) almacen.setNombreAlm(nombre);

        String ubicacion = InputHelper.leerString("Nueva ubicuación");
        if(!ubicacion.isEmpty()) almacen.setUbicacionAlm(ubicacion);

        almacenDAO.update(almacen);
        System.out.println("Usuario actualizado con éxito.");
    }
}

