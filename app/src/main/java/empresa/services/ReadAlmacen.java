package empresa.services;

import empresa.dao.AlmacenDAO;
import empresa.dao.ClienteDAO;
import empresa.models.Almacen;
import empresa.models.Cliente;
import empresa.utils.InputHelper;

public class ReadAlmacen {

    public static void readById(){
        System.out.println("\n*** BUSCAR ALMACEN POR SU ID ***");

        // Pedimos el id al usuario
        Long id = InputHelper.leerLong("Introduzca el ID del almacén");

        // Creamos una instancia de AlmacenDAO
        AlmacenDAO almacenDAO = new AlmacenDAO();

        // Buscamos el almacén en la BD
        Almacen almacen = almacenDAO.readById(id);

        // Mostramos el resultado
        if(almacen != null){
            System.out.println("\n--- Almacén encontrado ---");
            System.out.println("ID: " + almacen.getId_Almacen());
            System.out.println("Nombre: " + almacen.getNombreAlm());
            System.out.println("Ubicación: " + almacen.getUbicacionAlm());
        }else{
            System.out.println("No se encontró ningún almacén con el ID: " + id);
        }

    }
}



