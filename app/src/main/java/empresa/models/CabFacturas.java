package empresa.models;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "CAB_FACTURAS")
public class CabFacturas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Num_Fact")
    private Integer numFact;

    @Column(name = "Fecha_Fact", nullable = false)
    @Temporal(TemporalType.DATE)
    //private Date fechaFact;
    private String fechaFact;

    @Column(name = "Estado_Fact", nullable = false, length = 50)
    private String estadoFact;

    //@Column(name = "IVA_Fact", nullable = false, precision = 5, scale = 2)
    @Column(name = "IVA_Fact", nullable = false)
    private Double ivaFact;

    //@Column(name = "Total_Fact", nullable = false, precision = 10, scale = 2)
    @Column(name = "Total_Fact", nullable = false)
    private Double totalFact;

    @ManyToOne
    @JoinColumn(name = "Id_Cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "Id_Usuario", nullable = false)
    private Usuarios usuario;

    public CabFacturas(){}

    public CabFacturas( String fechaFact, String estadoFact, Double ivaFact, Double totalFact, Cliente cliente, Usuarios usuario){

        this.fechaFact = fechaFact;
        this.estadoFact = estadoFact;
        this.ivaFact = ivaFact;
        this.totalFact = totalFact;
        this.cliente = cliente;
        this.usuario = usuario;
    }

    // Método para convertir Date a String en formato yyyy-mm-dd
    private String convertirFechaAString(Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }

    public Integer getNumFact(){return numFact;}
    public String getFechaFact(){return fechaFact;}
    public String getEstadoFact(){return estadoFact;}
    public Double getIvaFact(){return ivaFact;}
    public Double getTotalFact(){return totalFact;}
    public Cliente getCliente(){return cliente;}
    public Usuarios getUsuario(){return usuario;}

    //public void setFechaFact(String fechaFact){this.fechaFact = convertirFechaAString(fechaFact);}
    public void setFechaFact(String fechaFact){this.fechaFact = fechaFact;}
    public void setEstadoFact(String estadoFact){this.estadoFact = estadoFact;}
    public void setIvaFact(Double ivaFact){this.ivaFact = ivaFact;}
    public void setTotalFact(Double totalFact){this.totalFact = totalFact;}
    public void setCliente(Cliente cliente){this.cliente = cliente;}
    public void setUsuario(Usuarios usuario){this.usuario = usuario;}

    @Override
    public String toString(){
        return "CabFacturas{" +
                "numFact= " + numFact +
                ", fechaFact= " + fechaFact +
                ", estadoFact= " + estadoFact +
                ", ivaFact= " + ivaFact +
                ", totalFact= " + totalFact +
                ", cliente= " + cliente.getIdCliente() +
                ", usuario= " + usuario.getIdUsuarios() +
                '}';
    }
}