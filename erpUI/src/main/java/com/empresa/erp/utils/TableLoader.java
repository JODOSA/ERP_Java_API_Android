package com.empresa.erp.utils;

import com.empresa.erp.dialogs.*;
import com.empresa.erp.service.StockUpdaterHandler;
import empresa.dao.GenericDAO;
import empresa.models.CabFacturas;
import empresa.models.CabPedidos;
import empresa.models.Proveedores;
import empresa.services.ExportFacturaPDF;
import empresa.services.ExportPedidoPDF;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import com.empresa.erp.utils.FriendlyNamesHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TableLoader {

    public static <T> void load(TableView<T> tableView, List<T> data, Class<T> clazz,
                                GenericActionHandler<T> onDelete, GenericActionHandler<T> onUpdate){
        tableView.getColumns().clear();

        // Define el orden fijo de las propiedades que quieres mostrar
        List<String> propertyOrder = getPropertyOrder(clazz);

        // Busca todos lo métodos getters públicos
        for(String propertyName : propertyOrder){
            try{
                // Construimos el nombre del getter esperado: getXyz
                String methodName = "get" + propertyName;
                Method method = clazz.getMethod(methodName);

                //TableColumn<T, Object> column = new TableColumn<>(propertyName);
                String header = FriendlyNamesHelper
                        .getFriendlyFieldNames(clazz)
                        .getOrDefault(propertyName,propertyName);
                TableColumn<T, Object> column = new TableColumn<>(header);

                if(propertyName.equals("Producto") || propertyName.equals("EmailCli")){
                    column.setPrefWidth(250);
                }

                column.setCellValueFactory(cellData -> {
                    try{
                        Object value = method.invoke(cellData.getValue());
                        return new SimpleObjectProperty<>(value);
                    }catch (Exception e){
                        e.printStackTrace();
                        return new SimpleObjectProperty<>("Error");
                    }
                });
                column.setMinWidth(100);
                tableView.getColumns().add(column);
            }catch (NoSuchMethodException e){
                System.out.println("No existe getter para: " + propertyName);
            }
        }

        // Columna de los botones
        TableColumn<T, Void> actionCol = new TableColumn<>("");
        actionCol.setCellFactory(col -> new TableCell<>(){

            // Botón Edit con Icono
            private final Button btnEdit;
            {
                ImageView editIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/edit_24dp_000000.png"))));
                editIcon.setFitHeight(20);
                editIcon.setFitWidth(20);
                btnEdit = new Button("", editIcon);
            }

            // Botón Borrar con Icono
            private final Button btnDelete;
            {
                ImageView deleteIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/delete_24dp.png"))));
                deleteIcon.setFitHeight(20);
                deleteIcon.setFitWidth(20);
                btnDelete = new Button("", deleteIcon);
            }

            // Botón Ver con Icono
            private final Button btnVer;
            {
                ImageView verIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/preview_24dp.png"))));
                verIcon.setFitHeight(20);
                verIcon.setFitWidth(20);
                btnVer = new Button("", verIcon);
            }

            // Botón Confirmar con Icono
            private final Button btnConfirm;
            {
                ImageView confirmIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/confirm_24dp.png"))));
                confirmIcon.setFitHeight(20);
                confirmIcon.setFitWidth(20);
                btnConfirm = new Button("", confirmIcon);
            }

            // Botón Confirmar con Icono
            private final Button btnImprimir;
            {
                ImageView printIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/print_24dp.png"))));
                printIcon.setFitHeight(20);
                printIcon.setFitWidth(20);
                btnImprimir = new Button("", printIcon);
            }


            {
                // Base para los dos botones
                String baseStyle = """
                    -fx-font-size: 13px;
                    -fx-background-radius: 10;
                    -fx-padding: 4 12;
                    -fx-cursor: hand;
                    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 4, 0, 0, 9);
                    -fx-background-color: transparent;
                    -fx-text-fill: #2c3e50;
                """;


                btnDelete.setStyle(baseStyle);
                btnEdit.setStyle(baseStyle);
                btnConfirm.setStyle(baseStyle);
                btnVer.setStyle(baseStyle);
                btnImprimir.setStyle(baseStyle);

                btnDelete.setOnAction(e ->{
                    T obj = getTableView().getItems().get(getIndex());

                    // Caso especial para CabPedidos
                    if(obj instanceof CabPedidos pedido){
                        if(!pedido.getEstadoPed().equalsIgnoreCase("Pendiente")){
                            AlertaUtils.mostrar("No se puede borrar un pedido confirmado");
                            return;
                        }
                    }else if(obj instanceof CabFacturas factura) {
                        if (!factura.getEstadoFact().equalsIgnoreCase("Emitida")) {
                            AlertaUtils.mostrar("La factura ya está confirmada y no puede borrarse");
                            return;
                        }
                    }
                    // Para todos los demás
                    if(ConfirmationDialog.mostrar() && onDelete != null){
                        onDelete.handle(obj);
                    }
                });

                btnEdit.setOnAction(e -> {
                    T item = getTableView().getItems().get(getIndex());

                    if(item instanceof CabPedidos pedido) {
                        if (!pedido.getEstadoPed().equalsIgnoreCase("Pendiente")) {
                            AlertaUtils.mostrar("El pedido está confirmado y no puede modificarse");
                            return;
                        }
                        // Abre el editor de pedidos
                        new PedidoEditDialog().show(pedido, getTableView());

                    }else if(item instanceof CabFacturas factura){
                        if(!factura.getEstadoFact().equalsIgnoreCase("Emitida")){
                            AlertaUtils.mostrar("La factura ya está confirmada y no puede editarse");
                            return;
                        }
                        new FacturaEditDialog().show(factura, tableView);

                    }else{
                        // Para todo lo demás, sigue usando el editor genérico
                        new GenericEditDialog<T>().show(item, clazz, false, getTableView()::refresh);
                    }
                });


                btnConfirm.setOnAction(e -> {
                    T item = getTableView().getItems().get(getIndex());

                    if(item instanceof CabPedidos pedido){
                        if(!pedido.getEstadoPed().equalsIgnoreCase("Pendiente")){
                            AlertaUtils.mostrar("El pedido ya está confirmado");
                            return;
                        }
                    }else if(item instanceof CabFacturas factura) {
                        if (!factura.getEstadoFact().equalsIgnoreCase("Emitida")) {
                            AlertaUtils.mostrar("La factura ya está confirmada");
                            return;
                        }
                    }
                    StockUpdaterHandler.actualizarStock(item, getTableView());
                });

                btnImprimir.setOnAction(e -> {
                    T item = getTableView().getItems().get(getIndex());
                    if(item instanceof CabFacturas factura){
                        ExportFacturaPDF.exportarFactura(factura);
                    }else if(item instanceof CabPedidos pedido){
                        ExportPedidoPDF.exportarPedido(pedido);
                    }
                });

            }

            // Este bloque controla los botones que se muestran, según el tipo de entidad
            private final HBox buttons = new HBox();
            {

                // buttons.getChildren().add(btnEdit);
                if(!clazz.getSimpleName().equals("ProductosAlmacen")){
                    buttons.getChildren().add(btnEdit);
                    buttons.getChildren().add(btnDelete);
                }
                if (clazz.getSimpleName().equals("CabPedidos")) {
                    btnVer.setOnAction(e -> {
                        T obj = getTableView().getItems().get(getIndex());
                        if(obj instanceof CabPedidos pedido){
                            new ConsultaPedidoDialog().show(pedido);
                        }
                    });
                    buttons.getChildren().add(btnVer);
                    buttons.getChildren().add(btnConfirm);
                    buttons.getChildren().add(btnImprimir);
                } else if (clazz.getSimpleName().equals("CabFacturas")) {
                    btnVer.setOnAction(e -> {
                        T obj = getTableView().getItems().get(getIndex());
                        if (obj instanceof CabFacturas factura) {
                            new ConsultaFacturaDialog().show(factura);
                        }
                    });
                    buttons.getChildren().add(btnVer);
                    buttons.getChildren().add(btnConfirm);
                    buttons.getChildren().add(btnImprimir);
                }
            }

            @Override
            protected void updateItem(Void item, boolean empty){
                super.updateItem(item, empty);
                if(empty){
                    setGraphic(null);
                }else{
                    setGraphic(buttons);
                }
            }
        });

        tableView.getColumns().add(actionCol);
        ObservableList<T> observableData = FXCollections.observableArrayList(data);
        tableView.setItems(observableData);
    }

    public static List<String> getPropertyOrder(Class<?> clazz){
        if(clazz.equals(empresa.models.Cliente.class)){
            return List.of("IdCliente", "NombreCli", "NifCli", "DireccionCli", "PoblacionCli", "TelefonoCli", "EmailCli");
        } else if (clazz.equals(empresa.models.Usuarios.class)) {
            return List.of("IdUsuario", "NombreUs", "TelefonoUs", "EmailUs", "Password");
        }else if (clazz.equals(empresa.models.Proveedores.class)) {
            return List.of("IdProveedor", "NombreProv", "NifProv", "DireccionProv", "PoblacionProv", "TelefonoProv", "EmailProv");
        }else if(clazz.equals(empresa.models.Productos.class)){
            return List.of("Id_Prod", "NombreProd", "ProveedorProd", "PrecioCompra", "PrecioVenta");
        }else if(clazz.equals(empresa.models.Almacen.class)){
            return List.of("Id_Almacen", "NombreAlm", "UbicacionAlm");
        }else if (clazz.equals(empresa.models.CabPedidos.class)){
            return List.of("NumPed", "FechaPed", "EstadoPed", "IvaPed", "TotalPed", "Proveedor", "Usuario");
        }else if (clazz.equals(empresa.models.CabFacturas.class)){
            return List.of("NumFact", "FechaFact", "EstadoFact", "IvaFact", "TotalFact", "Cliente", "Usuario");
        } else if (clazz.equals(empresa.models.ProductosAlmacen.class)) {
            return List.of("IdProducto", "Producto", "Stock", "Almacen");
        }

        return new ArrayList<>();
    }
}
