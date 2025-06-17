package empresa.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;
import java.util.List;

public abstract class GenericDAO<T> {
	//private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("empresaPU");

	private static EntityManagerFactory emf = null;

	static{
		try{
			emf = Persistence.createEntityManagerFactory("empresaPU");
		}catch(Exception e){
			e.printStackTrace();
			throw new ExceptionInInitializerError("Error al inicializar EntityManagerFactory.");
		}
	}

	private final Class<T> entityClass;

	public GenericDAO(Class<T> entityClass){
		this.entityClass = entityClass;
	}

	protected static EntityManagerFactory getEntityManagerFactory(){
		return emf;
	}

	public void create(T entity){
		EntityManager em = emf.createEntityManager();
		try{
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			//System.out.println("Entidad guardada con éxito." + entity);
		}catch(Exception e){
			if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			em.close();
		}
	}

	public T readById(Object id){
		EntityManager em = emf.createEntityManager();
		try{
			return em.find(entityClass, id);
		}finally{
			em.close();
		}
	}


	public List<T> readAll(){
		EntityManager em = emf.createEntityManager();
		try{
			return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
		}finally{
			em.close();
		}
	}

	public void update(T entity){
		EntityManager em = emf.createEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
			System.out.println("Entidad actualizada con éxito: " + entity);
		}catch(Exception e){
			if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			em.close();
		}
	}

	public T merge(T entity) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			T mergedEntity = em.merge(entity); // Asociamos la entidad con la sesión de Hibernate
			em.getTransaction().commit();
			return mergedEntity; // Devolvemos la entidad gestionada
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}


	public void delete(Object id){
		EntityManager em = emf.createEntityManager();
		try{
			T entity = em.find(entityClass, id);
			if(entity != null){
				em.getTransaction().begin();
				em.remove(entity);
				em.getTransaction().commit();
			}else{
				System.out.println("No se encontró la entidad con ID: " + id);				
			}
		}catch(Exception e){
			if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			em.close();
		}
	}
}
