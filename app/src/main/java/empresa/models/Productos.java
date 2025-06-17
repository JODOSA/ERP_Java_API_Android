package empresa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCTOS")

public class Productos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "Id_Prod")
	private Long idProd;

	@Column(name = "Nombre_Prod", nullable = false, length = 100)
	private String nombreProd;

	@Column(name = "Proveedor_Prod", nullable = false, length = 100)
	private String proveedorProd;

	@Column(name = "Precio_Compra", length = 10)
	private Double precioCompra;

	@Column(name = "Precio_Venta", length = 10)
	private Double precioVenta;

	public Productos(){}

	// Constructor para iniciar un nuevo cliente
	public Productos(String nombreProd, String proveedorProd, Double precioCompra, Double precioVenta){
		this.nombreProd = nombreProd;
		this.proveedorProd = proveedorProd;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
	}

	// Getters y Setters
	public Long getId_Prod(){return idProd;}
	public String getNombreProd(){return nombreProd;}
	public String getProveedorProd(){return proveedorProd;}
	public Double getPrecioCompra(){return precioCompra;}
	public Double getPrecioVenta(){return precioVenta;}

	public void setNombreProd(String nombreProd){this.nombreProd = nombreProd;}
	public void setProveedorProd(String proveedorProd){this.proveedorProd = proveedorProd;}
	public void setPrecioCompra(Double precioCompra){this.precioCompra = precioCompra;}
	public void setPrecioVenta(Double precioVenta){this.precioVenta = precioVenta;}

	@Override
	public String toString() {
		return this.getNombreProd();
	}
}
