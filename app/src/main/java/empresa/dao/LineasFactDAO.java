package empresa.dao;

import empresa.models.*;

import java.util.List;

public class LineasFactDAO extends GenericDAO<LineasFact>{

    public LineasFactDAO(){
        super(LineasFact.class);
    }

    public LineasFact readById(Integer id){
        return super.readById(id);
    }

    public List<LineasFact> readAll(){
        return super.readAll();
    }

    public void update(LineasFact lineaFact){
        super.update(lineaFact);
    }

    public void deleteById(Integer id){
        super.delete(id);
    }

    // Verifica el stock del producto
    public boolean verificarStockDisponible(Productos producto, Almacen almacen, int cantidad){
        ProductosAlmacenDAO productosAlmacenDAO = new ProductosAlmacenDAO();
        ProductosAlmacenId prodAlmId = new ProductosAlmacenId(
                producto.getId_Prod().intValue(),
                almacen.getId_Almacen().intValue()
        );
        ProductosAlmacen pa =productosAlmacenDAO.readById(prodAlmId);
        if(pa != null){
            pa.setStock(pa.getStock() - cantidad);
            productosAlmacenDAO.update(pa);
            return true;
        }
        return false;
    }

    // Calcula el total de la factura y lo actualiza
    public void actualizarTotalFactura(CabFacturas factura){
        List<LineasFact> lineas = super.readAll();
        double totalFactura = lineas.stream()
                .filter(l -> l.getFactura().equals(factura))
                .mapToDouble(LineasFact :: getSubtotalFact)
                .sum();

        CabFacturasDAO cabFacturasDAO = new CabFacturasDAO();
        cabFacturasDAO.update(factura);

        /*factura.setTotalFact(totalFactura);
        super.update(factura);*/

    }
}
