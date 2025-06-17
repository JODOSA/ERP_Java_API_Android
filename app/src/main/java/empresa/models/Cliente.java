package empresa.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CLIENTES") // Asegura que JPA usa la tabla de la BD y no crea una nueva
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	// Creamos una variable para cada uno de los atributos de la tabla
	// Y hacemos coincidir los nombres de los atributos
	@Column(name = "Id_Cliente")
	private Long idCliente;

	@Column(name = "Nombre_Cli", nullable = false, length = 100)
	private String nombreCli;

	@Column(name = "NIF_Cli", nullable = false, length = 50, unique = true)
	private String nifCli;

	@Column(name = "Direccion_Cli", length = 255)
	private String direccionCli;

	@Column(name = "Poblacion_Cli", length = 100)
	private String poblacionCli;

	@Column(name = "Telefono_Cli", length = 20)
	private String telefonoCli;

	@Column(name = "Email_Cli", length = 100)
	private String emailCli;

	// Constructor vacio obligatorio para JPA
	public Cliente(){}

	// Constructor para iniciar un nuevo cliente
	public Cliente(String nombreCli, String nifCli, String direccionCli, String poblacionCli, String telefonoCli, String emailCli){
		this.nombreCli = nombreCli;
		this.nifCli = nifCli;
		this.direccionCli = direccionCli;
		this.poblacionCli = poblacionCli;
		this.telefonoCli = telefonoCli;
		this.emailCli = emailCli;
	}

	// Getters y Setters
	public Long getIdCliente(){return idCliente;}
	public String getNombreCli(){return nombreCli;}
	public String getNifCli(){return nifCli;}
	public String getDireccionCli(){return direccionCli;}
	public String getPoblacionCli(){return poblacionCli;}
	public String getTelefonoCli(){return telefonoCli;}
	public String getEmailCli(){return emailCli;}

	public void setNombreCli(String nombreCli){this.nombreCli = nombreCli;}
	public void setNifCli(String nifCli){this.nifCli = nifCli;}
	public void setDireccionCli(String direccionCli){this.direccionCli = direccionCli;}
	public void setPoblacionCli(String poblacionCli){this.poblacionCli = poblacionCli;}
	public void setTelefonoCli(String telefonoCli){this.telefonoCli = telefonoCli;}
	public void setEmailCli(String emailCli){this.emailCli = emailCli;}

	@Override
	public String toString(){
		return this.nombreCli;
	}
}
