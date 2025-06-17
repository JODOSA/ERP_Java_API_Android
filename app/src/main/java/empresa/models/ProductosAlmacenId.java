package empresa.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductosAlmacenId implements Serializable {

    @Column(name = "Id_Producto")
    private int idProducto;

    @Column(name = "Id_Almacen")
    private int idAlmacen;

    // Constructor vacío
    public ProductosAlmacenId() {}

    // Constructor con parámetros
    public ProductosAlmacenId(int idProducto, int idAlmacen) {
        this.idProducto = idProducto;
        this.idAlmacen = idAlmacen;
    }

    // Getters y Setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductosAlmacenId that = (ProductosAlmacenId) o;
        return idProducto == that.idProducto && idAlmacen == that.idAlmacen;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, idAlmacen);
    }
}

