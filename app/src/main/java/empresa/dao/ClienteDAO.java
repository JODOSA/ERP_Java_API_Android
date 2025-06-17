package empresa.dao;

import java.util.List;

import empresa.models.Cliente;

// Estendemos GenericDAO para heredar los métodos CRUD sin repetir código
public class ClienteDAO extends GenericDAO<Cliente>{
	// Constructor que llama al constructor de la clase padre (GenericDAO)
	public ClienteDAO(){
		super(Cliente.class);
	}

	public Cliente readById(Long id){
		return super.readById(id);
	}

	public List<Cliente> readAll(){
		return super.readAll();
	}

	public void update(Cliente cliente){
		super.update(cliente);
	}

	public void deleteById(Long id){
		super.delete(id);
	}

	public Cliente findByIdOrName(String input) {
		try {
			int id = Integer.parseInt(input);
			return readById(id); // Este ya debe existir en tu GenericDAO
		} catch (NumberFormatException e) {
			return readAll().stream()
					.filter(cli -> cli.getNombreCli().equalsIgnoreCase(input))
					.findFirst()
					.orElse(null);
		}
	}


}
