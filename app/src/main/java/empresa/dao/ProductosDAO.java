package empresa.dao;

import java.util.List;

import empresa.models.Productos;

public class ProductosDAO extends GenericDAO<Productos> {

	public ProductosDAO(){
		super(Productos.class);
	}

	public Productos readById(Long id){
		return super.readById(id);
	}

	public List<Productos> readAll(){
		return super.readAll();
	}

	public void update(Productos productos){
		super.update(productos);
	}

	public void deleteById(Long id){
		super.delete(id);
	}
}
