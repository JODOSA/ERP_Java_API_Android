package empresa.dao;

import empresa.models.Almacen;
import empresa.models.Productos;
import empresa.models.ProductosAlmacen;
import empresa.models.ProductosAlmacenId;
import jakarta.persistence.EntityManager;

public class ProductosAlmacenDAO extends GenericDAO<ProductosAlmacen> {

    // Constructor que llama al constructor de la clase padre (GenericDAO)
    public ProductosAlmacenDAO() {
        super(ProductosAlmacen.class);
    }

    public void deleteById(ProductosAlmacenId id) {
        super.delete(id);
    }

    public int obtenerStock(Productos producto, Almacen almacen) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        try{
            ProductosAlmacenId id = new ProductosAlmacenId(
                    producto.getId_Prod().intValue(),
                    almacen.getId_Almacen().intValue()
            );
            ProductosAlmacen pa = em.find(ProductosAlmacen.class, id);
            return (pa != null) ? pa.getStock() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

