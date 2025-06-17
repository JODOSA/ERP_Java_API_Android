package empresa.models;

import jakarta.persistence.*;

@Entity
@Table(name = "LINEAS_FACT")

public class LineasFact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Linea_Fact")
    private Integer idLineaFact;

    @Column(name = "Cantidad_Prod_Fact", nullable = false)
    private Integer cantidadProdFact;

    @Column(name = "Precio_Prod_Fact", nullable = false)
    private Double precioProdFact;

    @Column(name = "Subtotal_Fact", nullable = false)
    private Double subtotalFact;

    @ManyToOne
    @JoinColumn(name = "Num_Fact", nullable = false)
    private CabFacturas factura;

    @ManyToOne
    @JoinColumn(name = "Id_Producto", nullable = false)
    private Productos producto;

    @ManyToOne
    @JoinColumn(name = "Id_Almacen", nullable = false)
    private Almacen almacen;

    // Constructor vacio
    public LineasFact(){}

    public LineasFact(CabFacturas factura, Productos producto, Almacen almacen, Integer cantidadProdFact, Double precioProdFact){
        this.factura = factura;
        this.producto = producto;
        this.almacen = almacen;
        this.cantidadProdFact = cantidadProdFact;
        this.precioProdFact = precioProdFact;
        this.subtotalFact = cantidadProdFact * precioProdFact;
    }

    // Getters y Setters
    public Integer getIdLineaFact(){return idLineaFact;}
    public Integer getCantidadProdFact(){return cantidadProdFact;}
    public Double getPrecioProdFact(){return precioProdFact;}
    public Double getSubtotalFact(){return subtotalFact;}
    public CabFacturas getFactura(){return factura;}
    public Productos getProducto(){return producto;}
    public Almacen getAlmacen(){return almacen;}

    public void setCantidadProdFact(Integer cantidadProdFact){
        this.cantidadProdFact = cantidadProdFact;
        this.subtotalFact = cantidadProdFact * this.precioProdFact;
    }
    public void setPrecioProdFact(Double precioProdFact){this.precioProdFact = precioProdFact;}
    public void setSubtotalFact(Double subtotalFact){this.subtotalFact = subtotalFact;}
    public void setFactura(CabFacturas factura){this.factura = factura;}
    public void setProducto(Productos producto){this.producto = producto;}
    public void setAlmacen(Almacen almacen){this.almacen = almacen;}

    @Override
    public String toString(){
        return "Línea Factura: " +
                "ID= " + idLineaFact +
                ", Producto= " + producto.getNombreProd() +
                ", Cantidad= " + cantidadProdFact +
                ", Precio= " + precioProdFact +
                ", Subtotal= " + subtotalFact;
    }
}
