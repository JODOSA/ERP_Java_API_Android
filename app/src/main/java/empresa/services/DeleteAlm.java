package empresa.services;

import empresa.dao.AlmacenDAO;
import empresa.dao.UsuariosDAO;
import empresa.models.Almacen;
import empresa.models.Usuarios;
import empresa.utils.InputHelper;

public class DeleteAlm {

    public static void deleteAlm(){
        System.out.println("\n--- ELIMINAR ALMACEN ---");

        Long id = InputHelper.leerLong("Introduce el ID del almacén que deseas eliminar");

        AlmacenDAO almacenDAO = new AlmacenDAO();
        Almacen almacen = almacenDAO.readById(id);

        if(almacen == null){
            System.out.println("No se encontró ningún almacén con el ID: " + id);
            return;
        }

        System.out.println("\nAlmacén encontrado:");
        System.out.println("ID: " + almacen.getId_Almacen());
        System.out.println("Nombre: " + almacen.getNombreAlm());
        System.out.println("Ubicación: " + almacen.getUbicacionAlm());

        String confirmacion = InputHelper.leerString("¿Estás seguro de que quieres eliminar este almacén? (S/N)").toUpperCase();

        if(confirmacion.equals("S")){
            almacenDAO.deleteById(id);
            System.out.println("Almacén eliminado con éxito.");
        }else{
            System.out.println("Operación cancelada. El almacén no fué eliminado.");
        }
    }
}

