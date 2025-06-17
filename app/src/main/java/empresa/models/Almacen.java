package empresa.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ALMACEN") // Asegura que JPA usa la tabla de la BD y no crea una nueva
public class Almacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Creamos una variable para cada uno de los atributos de la tabla
    // Y hacemos coincidir los nombres de los atributos
    @Column(name = "Id_Almacen")
    private Long idAlmacen;

    @Column(name = "Nombre_Alm", nullable = false, length = 100)
    private String NombreAlm;

    @Column(name = "Ubicacion_Alm", length = 255)
    private String ubicacionAlm;

    // Constructor vacio obligatorio para JPA
    public Almacen(){}

    // Constructor para iniciar un nuevo cliente
    public Almacen(String NombreAlm, String ubicacionAlm){
        this.NombreAlm = NombreAlm;
        this.ubicacionAlm = ubicacionAlm;
    }

    // Getters y Setters
    public Long getId_Almacen(){return idAlmacen;}
    public String getNombreAlm(){return NombreAlm;}
    public String getUbicacionAlm(){return ubicacionAlm;}

    public void setNombreAlm(String NombreAlm){this.NombreAlm = NombreAlm;}
    public void setUbicacionAlm(String ubicacionAlm){this.ubicacionAlm = ubicacionAlm;}

    @Override
    public String toString() {
        return this.NombreAlm;
    }

}


