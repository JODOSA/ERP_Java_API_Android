package empresa.services;

import empresa.dao.AlmacenDAO;
import empresa.dao.ClienteDAO;
import empresa.models.Almacen;
import empresa.models.Cliente;
import empresa.utils.InputHelper;

public class CreateAlmacen {

    public static void createAlmacen(){
        System.out.println("\n<<< CREANDO ALMACEN >>>");
        String nombre = InputHelper.leerString("Introduzca el nombre del almacén");
        String ubicacion = InputHelper.leerString("Introduzca la ubicación del almacén");

        Almacen newAlmacen = new Almacen(nombre, ubicacion);
        AlmacenDAO almacenDAO = new AlmacenDAO();
        almacenDAO.create(newAlmacen);

        System.out.println("\n\nAlmacén creado con éxito.");
    }
}

