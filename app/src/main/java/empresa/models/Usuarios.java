package empresa.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USUARIOS") // Asegura que JPA usa la tabla de la BD y no crea una nueva
public class Usuarios {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	// Creamos una variable para cada uno de los atributos de la tabla
	// Y hacemos coincidir los nombres de los atributos
	@Column(name = "Id_Usuario")
	private Long idUsuario;

	@Column(name = "Nombre_Us", nullable = false, length = 100)
	private String nombreUs;

	@Column(name = "Telefono_Us", length = 20)
	private String telefonoUs;

	@Column(name = "Email_Us", length = 100)
	private String emailUs;

	@Column(name = "passw", nullable = true, length = 255)
	private String password;

	// Constructor vacio obligatorio para JPA
	public Usuarios(){}

	// Constructor para iniciar un nuevo cliente
	public Usuarios(String nombreUs, String telefonoUs, String emailUs, String password){
		this.nombreUs = nombreUs;
		this.telefonoUs = telefonoUs;
		this.emailUs = emailUs;
		this.password = password;
	}

	// Getters y Setters
	public Long getIdUsuarios(){return idUsuario;}
	public Long getIdUsuario(){return idUsuario;}
	public String getNombreUs(){return nombreUs;}
	public String getTelefonoUs(){return telefonoUs;}
	public String getEmailUs(){return emailUs;}
	public String getPassword(){return password;};

	public void setNombreUs(String nombreUs){this.nombreUs = nombreUs;}
	public void setTelefonoUs(String telefonoUs){this.telefonoUs = telefonoUs;}
	public void setEmailUs(String emailUs){this.emailUs = emailUs;}
	public void setPassword(String password){this.password = password;}

	@Override
	public String toString() {
		return nombreUs;
	}

}

