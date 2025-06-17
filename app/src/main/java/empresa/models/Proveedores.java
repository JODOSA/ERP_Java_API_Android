package empresa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PROVEEDORES")

public class Proveedores {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "Id_Proveedor")
	private Long idProveedor;

	@Column(name = "Nombre_Prov", nullable = false, length = 100)
	private String nombreProv;

	@Column(name = "NIF_Prov", nullable = false, length = 50)
	private String nifProv;

	@Column(name = "Direccion_Prov", length = 255)
	private String direccionProv;

	@Column(name = "Poblacion_Prov", length = 100)
	private String poblacionProv;

	@Column(name = "Telefono_Prov", length = 20)
	private String telefonoProv;

	@Column(name = "Email_Prov", length = 100)
	private String emailProv;

	public Proveedores(){}

	public Proveedores(String nombreProv, String nifProv, String direccionProv,
	String poblacionProv, String telefonoProv, String emailProv){
		this.nombreProv = nombreProv;
		this.nifProv = nifProv;
		this.direccionProv = direccionProv;
		this.poblacionProv = poblacionProv;
		this.telefonoProv = telefonoProv;
		this.emailProv = emailProv;
	}

	public Long getIdProveedor(){return idProveedor;}
	public String getNombreProv(){return nombreProv;}
	public String getNifProv(){return nifProv;}
	public String getDireccionProv(){return direccionProv;}
	public String getPoblacionProv(){return poblacionProv;}
	public String getTelefonoProv(){return telefonoProv;}
	public String getEmailProv(){return emailProv;}

	public void setNombreProv(String nombreProv){this.nombreProv = nombreProv;}
	public void setNifProv(String nifProv){this.nifProv = nifProv;}
	public void setDireccionProv(String direccionProv){this.direccionProv = direccionProv;}
	public void setPoblacionProv(String poblacionProv){this.poblacionProv = poblacionProv;}
	public void setTelefonoProv(String telefonoProv){this.telefonoProv = telefonoProv;}
	public void setEmailProv(String emailProv){this.emailProv = emailProv;}

	@Override
	public String toString(){
		return nombreProv;
	}

}
