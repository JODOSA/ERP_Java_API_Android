package empresa.dao;

import java.util.List;

import empresa.models.Usuarios;

public class UsuariosDAO extends GenericDAO<Usuarios> {

	public UsuariosDAO(){
		super(Usuarios.class);
	}

	public Usuarios readById(Long id){
		return super.readById(id);
	}

	public List<Usuarios> readAll(){
		return super.readAll();
	}

	public void update(Usuarios usuarios){
		super.update(usuarios);
	}

	public void deleteById(Long id){
		super.delete(id);
	}
}
