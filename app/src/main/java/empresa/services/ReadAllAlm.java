package empresa.services;

import java.util.List;

import empresa.dao.AlmacenDAO;
import empresa.dao.UsuariosDAO;
import empresa.models.Almacen;
import empresa.models.Usuarios;

public class ReadAllAlm{

    public static void readAll(){
        System.out.println("\n<<< LISTA DE ALMACENES >>>");

        AlmacenDAO almacenDAO = new AlmacenDAO();

        List<Almacen> almacenes = almacenDAO.readAll();

        if(almacenes.isEmpty()){
            System.out.println("No hay almacenes registrados.");
        }else{
            for(Almacen almacen : almacenes){
                System.out.println("ID: " + almacen.getId_Almacen());
                System.out.println("Nombre: " + almacen.getNombreAlm());
                System.out.println("Ubicación: " + almacen.getUbicacionAlm());
            }
        }
    }
}

