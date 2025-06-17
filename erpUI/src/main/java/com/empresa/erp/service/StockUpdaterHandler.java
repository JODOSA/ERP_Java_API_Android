package com.empresa.erp.service;

import com.empresa.erp.utils.AlertaUtils;
import empresa.dao.*;
import empresa.models.*;
import javafx.scene.control.TableView;

import java.util.List;

public class StockUpdaterHandler {

    public static void actualizarStock(Object item, TableView<?> tableView) {
        try{
            if(item instanceof CabPedidos pedido){
                LineasPedDAO lineasPedDAO = new LineasPedDAO();
                List<LineasPed> lineas = lineasPedDAO.readAll();

                // Filtrar solo las líneas correspondientes al pedido actual
                List<LineasPed> lineasPedido = lineas.stream()
                        .filter(l -> l.getCabPedidos().getNumPed().equals(pedido.getNumPed()))
                        .toList();

                for(LineasPed linea : lineasPedido){
                    boolean actualizado = lineasPedDAO.verificarYActualizarStock(
                            linea.getProducto(),
                            linea.getAlmacen(),
                            linea.getCantidadProdPed()
                    );
                    if(!actualizado){
                        ProductosAlmacenId id = new ProductosAlmacenId(
                                linea.getProducto().getId_Prod().intValue(),
                                linea.getAlmacen().getId_Almacen().intValue());
                        ProductosAlmacen nuevo = new ProductosAlmacen(id, linea.getCantidadProdPed());
                        new ProductosAlmacenDAO().create(nuevo);
                    }
                }
                pedido.setEstadoPed("Confirmado");
                new CabPedidosDAO().update(pedido);

                AlertaUtils.mostrar("Stock actualizado correctamente para el pedido " + pedido.getNumPed());
                tableView.refresh();
            }else if(item instanceof CabFacturas factura){
                LineasFactDAO lineasFactDAO = new LineasFactDAO();
                List<LineasFact> lineas = lineasFactDAO.readAll();

                // Filtrar solo las líneas correspondientes a la factura actual
                List<LineasFact> lineasFactura = lineas.stream()
                        .filter(l -> l.getFactura().getNumFact().equals(factura.getNumFact()))
                        .toList();
                for(LineasFact linea : lineasFactura){
                    boolean resultado = lineasFactDAO.verificarStockDisponible(
                            linea.getProducto(),
                            linea.getAlmacen(),
                            linea.getCantidadProdFact());
                    if(!resultado){
                        AlertaUtils.mostrar("No hay suficiente stock para el producto: " + linea.getProducto().getNombreProd());
                        return;
                    }
                }
                factura.setEstadoFact("Confirmado");
                new CabFacturasDAO().update(factura);

                AlertaUtils.mostrar("Stock descontado correctamente para la factura " + factura.getNumFact());
                tableView.refresh();
            }else{
                AlertaUtils.mostrar("Entidad no compatible para actualizar stock");
            }
        }catch (Exception e){
            e.printStackTrace();
            AlertaUtils.mostrar("Error al actualizar stock: " + e.getMessage());
        }

    }
}
