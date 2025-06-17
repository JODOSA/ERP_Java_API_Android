package empresa.dao;

import java.util.List;

import empresa.models.Almacen;
import empresa.models.Usuarios;

public class AlmacenDAO extends GenericDAO<Almacen> {

    public AlmacenDAO(){
        super(Almacen.class);
    }

    public Almacen readById(Long id){
        return super.readById(id);
    }

    public List<Almacen> readAll(){
        return super.readAll();
    }

    public void update(Almacen almacen){
        super.update(almacen);
    }

    public void deleteById(Long id){
        super.delete(id);
    }
}

