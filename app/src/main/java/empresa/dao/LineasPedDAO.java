package empresa.dao;

import empresa.models.*;
import java.util.List;

public class LineasPedDAO extends GenericDAO<LineasPed> {

    public LineasPedDAO(){
        super(LineasPed.class);
    }

    public LineasPed readById(Integer id){
        return super.readById(id);
    }

    public List<LineasPed> readAll(){
        return super.readAll();
    }

    public void update(LineasPed lineasPed){
        super.update(lineasPed);
    }

    public void deleteById(Integer id){
        super.delete(id);
    }

    // Verifica y actualiza el stock al recibir un pedido
    public boolean verificarYActualizarStock(Productos producto, Almacen almacen, int cantidad){
        ProductosAlmacenDAO productosAlmacenDAO = new ProductosAlmacenDAO();
        ProductosAlmacenId prodAlmId = new ProductosAlmacenId(
                producto.getId_Prod().intValue(),
                almacen.getId_Almacen().intValue()
        );
        ProductosAlmacen pa = productosAlmacenDAO.readById(prodAlmId);

        if(pa != null){
            pa.setStock(pa.getStock() + cantidad);
            productosAlmacenDAO.update(pa);
            return true;
        }
        return false;
    }

    // Calcula y actualiza el total del pedido en la cabecera
    public void actualizarTotalPedido(CabPedidos pedido){
        List<LineasPed> lineas = super.readAll();
        double totalPedido = lineas.stream()
                .filter(l -> l.getCabPedidos().equals(pedido))
                .mapToDouble(LineasPed::getSubtotalPed)
                .sum();
        pedido.setTotalPed(totalPedido);
        CabPedidosDAO cabPedidosDAO = new CabPedidosDAO();
        cabPedidosDAO.update(pedido);
    }
}
