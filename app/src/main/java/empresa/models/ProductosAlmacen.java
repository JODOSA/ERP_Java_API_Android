package empresa.models;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCTOS_ALMACEN") // Mapea la tabla en la base de datos
public class ProductosAlmacen {

    @EmbeddedId
    private ProductosAlmacenId id; // Clave primaria compuesta

    @Column(name = "Stock", nullable = false)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "Id_Producto", insertable = false, updatable = false)
    private Productos producto;

    @ManyToOne
    @JoinColumn(name = "Id_Almacen", insertable = false, updatable = false)
    private Almacen almacen;


    // Constructor vacío obligatorio para JPA
    public ProductosAlmacen() {}

    // Constructor con parámetros
    public ProductosAlmacen(ProductosAlmacenId id, int stock) {
        this.id = id;
        this.stock = stock;
    }

    public int getIdProducto() {
        return id != null ? id.getIdProducto() : 0;
    }

    // Getters y Setters

    public Productos getProducto() {
        return producto;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public ProductosAlmacenId getId() {
        return id;
    }

    public void setId(ProductosAlmacenId id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ProductosAlmacen{" +
                "idProducto=" + id.getIdProducto() +
                ", idAlmacen=" + id.getIdAlmacen() +
                ", stock=" + stock +
                '}';
    }
}

