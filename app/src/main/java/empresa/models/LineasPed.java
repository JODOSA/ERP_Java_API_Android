package empresa.models;

import jakarta.persistence.*;

@Entity
@Table(name = "LINEAS_PED")
public class LineasPed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Linea_Ped")
    private Integer idLineaPed;

    @Column(name = "Cantidad_Prod_Ped", nullable = false)
    private Integer cantidadProdPed;

    @Column(name = "Precio_Prod_Ped", nullable = false)
    private Double precioProdPed;

    @Column(name = "Subtotal_Ped", nullable = false)
    private Double subtotalPed;

    @ManyToOne
    @JoinColumn(name = "Num_Ped", nullable = false)
    private CabPedidos cabPedidos;

    @ManyToOne
    @JoinColumn(name = "Id_Producto", nullable = false)
    private Productos producto;

    @ManyToOne
    @JoinColumn(name = "Id_Almacen", nullable = false)
    private Almacen almacen;

    public LineasPed(){}

    public LineasPed(Integer idLineaPed, Integer cantidadProdPed, Double precioProdPed, Double subtotalPed, CabPedidos cabPedidos, Productos producto, Almacen almacen){
        this.idLineaPed = idLineaPed;
        this.cantidadProdPed = cantidadProdPed;
        this.precioProdPed = precioProdPed;
        this.subtotalPed = cantidadProdPed * precioProdPed;
        this.cabPedidos = cabPedidos;
        this.producto = producto;
        this.almacen = almacen;
    }

    public Integer getIdLineaPed(){return idLineaPed;}
    public Integer getCantidadProdPed(){return cantidadProdPed;}
    public Double getPrecioProdPed(){return precioProdPed;}
    public Double getSubtotalPed(){return subtotalPed;}
    public CabPedidos getCabPedidos(){return cabPedidos;}
    public Productos getProducto(){return producto;}
    public Almacen getAlmacen(){return almacen;}

    public void setCantidadProdPed(Integer cantidadProdPed){this.cantidadProdPed = cantidadProdPed;}
    public void setPrecioProdPed(Double precioProdPed){this.precioProdPed = precioProdPed;}
    public void setSubtotalPed(Double subtotalPed){this.subtotalPed = subtotalPed;}
    public void setCabPedidos(CabPedidos cabPedidos){this.cabPedidos = cabPedidos;}
    public void setProducto(Productos producto){this.producto = producto;}
    public void setAlmacen(Almacen almacen){this.almacen = almacen;}

    @Override
    public String toString(){
        return "LineasPed{" +
                "idLineaPed= " + idLineaPed +
                ", cantidadProdPed= " + cantidadProdPed +
                ", precioProdPed= " + precioProdPed +
                ", subtotalPed= " + subtotalPed +
                ", numPed= " + cabPedidos.getNumPed() +
                ", producto= " + producto.getId_Prod() +
                ", almacen= " + almacen.getId_Almacen() +
                '}';
    }

}