package empresa.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "CAB_PEDIDOS")
public class CabPedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Num_Ped")
    private Integer numPed;

    @Column(name = "Fecha_Ped", nullable = false)
    @Temporal(TemporalType.DATE)
    private String fechaPed;

    @Column(name = "Estado_Ped", nullable = false, length = 50)
    private String estadoPed;

    @Column(name = "IVA_Ped", nullable = false)
    private Double ivaPed;

    @Column(name = "Total_Ped", nullable = false)
    private Double totalPed;

    @ManyToOne
    @JoinColumn(name = "Id_Proveedor", nullable = false)
    private Proveedores proveedor;

    @ManyToOne
    @JoinColumn(name = "Id_Usuario", nullable = false)
    private Usuarios usuario;

    public CabPedidos() {
    }

    public CabPedidos(String fechaPed, String estadoPed, Double ivaPed, Double totalPed, Proveedores proveedor, Usuarios usuario) {

        this.fechaPed = fechaPed;
        this.estadoPed = estadoPed;
        this.ivaPed = ivaPed;
        this.totalPed = totalPed;
        this.proveedor = proveedor;
        this.usuario = usuario;
    }

    //Método para convertir Date a String en formato yyyy-MM-dd
    private String convertirFechaAString(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }

    public Integer getNumPed(){return numPed;}
    public String getFechaPed(){return fechaPed;}
    public String getEstadoPed(){return estadoPed;}
    public Double getIvaPed(){return ivaPed;}
    public Double getTotalPed(){return totalPed;}
    public Proveedores getProveedor(){return proveedor;}
    public Usuarios getUsuario(){return usuario;}

    public void setFechaPed(Date fechaPed){this.fechaPed = convertirFechaAString(fechaPed);}
    public void setEstadoPed(String estadoPed){this.estadoPed = estadoPed;}
    public void setIvaPed(Double ivaPed){this.ivaPed = ivaPed;}
    public void setTotalPed(Double totalPed){this.totalPed = totalPed;}
    public void setProveedor(Proveedores proveedor){this.proveedor = proveedor;}
    public void setUsuario(Usuarios usuario){this.usuario = usuario;}

    /*@Override
    public String toString(){
        return "CabPedidos{" +
                "numPed= " + numPed +
                ", fechaPed= " + fechaPed +
                ", estadoPed= " + estadoPed +
                ", ivaPed= " + ivaPed +
                ", totalPed= " + totalPed +
                ", proveedor= " + proveedor.getIdProveedor() +
                ", usuario= " + usuario.getIdUsuarios() +
                '}';
    }*/

}



