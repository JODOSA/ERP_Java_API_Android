package empresa.dao;

import java.util.List;

import empresa.models.Proveedores;

public class ProveedoresDAO extends GenericDAO<Proveedores> {
	
	public ProveedoresDAO(){
		super(Proveedores.class);
	}
	public Proveedores readById(Long id){
		return super.readById(id);
	}

	public List<Proveedores> readAll(){
		return super.readAll();
	}

	public void update(Proveedores proveedores){
		super.update(proveedores);
	}

	public void deleteById(Long id){
		super.delete(id);
	}
}
