package com.empresa.erp.dialogs;

import empresa.dao.*;
import empresa.models.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.w3c.dom.Text;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PedidoEditDialog {

    private Stage dialog;
    private ObservableList<LineasPed> lineasPedido;
    private TableView<LineasPed> tablaLineas;

    public PedidoEditDialog(){
        // Constructor vacío
    }

    public void show(CabPedidos pedido, TableView<?> tableView) {

        String lblStyle = ("-fx-text-fill: white;-fx-font-weight: bold;");

        String layoutStyle = ("""
            -fx-background-color: #2c3e50;
            -fx-border-color: #98cddd;
            -fx-border-radius: 10;
            -fx-background-radius: 10;
            -fx-padding: 20;
        """);

        String comboStyle = ("-fx-background-color: #98cddd; -fx-border-radius: 4px; -fx-background-radius: 4px;");

        String botonStyle = ("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
        """);

        // Crear los campos para editar la cabecera del pedido
        Label lblNumPedido = new Label("Número de Pedido: " + pedido.getNumPed());
        lblNumPedido.setStyle(lblStyle);

        // Proveedor
        ComboBox<Proveedores> comboProveedores = new ComboBox<>();
        comboProveedores.setStyle(comboStyle);
        comboProveedores.getItems().addAll(new ProveedoresDAO().readAll());
        comboProveedores.setValue(pedido.getProveedor());
        comboProveedores.setPrefWidth(300);
        comboProveedores.setPromptText("Seleccione un proveedor");
        comboProveedores.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(Proveedores prov, boolean empty) {
                super.updateItem(prov, empty);
                setText(empty || prov == null ? null : prov.getNombreProv());
            }
        });

        comboProveedores.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(Proveedores prov, boolean empty) {
                super.updateItem(prov, empty);
                setText(empty || prov == null ? null : prov.getNombreProv());
            }
        });

        // Fecha
        TextField txtFecha = new TextField(pedido.getFechaPed());
        txtFecha.setPromptText("Fecha (yyyy-MM-dd)");
        txtFecha.setPrefWidth(150);

        // IVA
        TextField txtIVA = new TextField(String.valueOf(pedido.getIvaPed()));
        txtIVA.setPromptText("IVA (%)");
        txtIVA.setPrefWidth(80);

        // Estado (NO editable)
        TextField txtEstado = new TextField(pedido.getEstadoPed());
        txtEstado.setDisable(true);
        txtEstado.setPrefWidth(120);

        // Organizar el layout
        Label lblProveedor = new Label("Proveedor");
        lblProveedor.setStyle(lblStyle);
        HBox cabeceraBox1 = new HBox(15, lblNumPedido, lblProveedor, comboProveedores);
        HBox cabeceraBox2 = new HBox(15, new Label("Fecha:"), txtFecha, new Label("IVA:"), txtIVA, new Label("Estado:"), txtEstado);
        cabeceraBox1.setAlignment(Pos.CENTER_LEFT);
        cabeceraBox2.setAlignment(Pos.CENTER_LEFT);

        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Editar Pedido");
        dialog.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/corporate_logo_white_50_53.png"))));

        VBox layout = new VBox(10);
        layout.setStyle(layoutStyle);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);

        tablaLineas = new TableView<>();
        tablaLineas.setEditable(true);
        LineasPedDAO dao = new LineasPedDAO();
        List<LineasPed> lineas = dao.readAll().stream()
                .filter(l -> l.getCabPedidos().getNumPed().equals(pedido.getNumPed()))
                .toList();
        lineasPedido = FXCollections.observableArrayList(lineas);
        tablaLineas.setItems(lineasPedido);

        // Bloque para añadir líneas manualmente

        HBox miniForm = new HBox(10);
        miniForm.setAlignment(Pos.CENTER_LEFT);

        // ComboBox de productos
        ComboBox<Productos> comboProductos = new ComboBox<>();
        comboProductos.setStyle(comboStyle);
        comboProductos.getItems().addAll(new ProductosDAO().readAll());
        comboProductos.setPrefWidth(250);
        comboProductos.setPromptText("Seleccione un producto");
        comboProductos.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(Productos prod, boolean empty) {
                super.updateItem(prod, empty);
                setText(empty || prod == null ? null : prod.getNombreProd());
            }
        });
        comboProductos.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(Productos prod, boolean empty) {
                super.updateItem(prod, empty);
                setText(empty || prod == null ? null : prod.getNombreProd());
            }
        });

        // Campo cantidad
        TextField txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad");
        txtCantidad.setPrefWidth(80);

        // Campo precio
        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");
        txtPrecio.setPrefWidth(80);

        // ComboBox de Almacenes
        ComboBox<Almacen> comboAlmacenes = new ComboBox<>();
        comboAlmacenes.setStyle(comboStyle);
        comboAlmacenes.getItems().addAll(new AlmacenDAO().readAll());
        comboAlmacenes.setPrefWidth(200);
        comboAlmacenes.setPromptText("Seleccione un almacen");
        comboAlmacenes.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(Almacen alm, boolean empty) {
                super.updateItem(alm, empty);
                setText(empty || alm == null ? null : alm.getNombreAlm());
            }
        });
        comboAlmacenes.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(Almacen alm, boolean empty) {
                super.updateItem(alm, empty);
                setText(empty || alm == null ? null : alm.getNombreAlm());
            }
        });

        // Botón añadir producto

        Button btnAddLinea = new Button("Añadir Producto");
        btnAddLinea.setStyle(botonStyle);

        comboProductos.setOnAction(e -> {
            Productos productoSeleccionado = comboProductos.getValue();
            if(productoSeleccionado != null) {
                txtPrecio.setText(String.valueOf(productoSeleccionado.getPrecioCompra()));
            }
        });

        // Evento al pulsar Añadir Producto
        btnAddLinea.setOnAction(e -> {
            Productos producto = comboProductos.getValue();
            Almacen almacen = comboAlmacenes.getValue();
            String cantidadText = txtCantidad.getText();
            String precioText = txtPrecio.getText();

            if(producto == null || cantidadText.isEmpty() || precioText.isEmpty() || almacen == null) {
                com.empresa.erp.utils.AlertaUtils.mostrar("Debe seleccionar producto, almacen, cantidad y precio");
                return;
            }

            try{
                int cantidad = Integer.parseInt(cantidadText);
                double precio = Double.parseDouble(precioText);
                double subtotal = cantidad * precio;

                LineasPed nuevaLinea = new LineasPed();
                nuevaLinea.setCabPedidos(pedido);
                nuevaLinea.setProducto(producto);
                nuevaLinea.setCantidadProdPed(cantidad);
                nuevaLinea.setPrecioProdPed(precio);
                nuevaLinea.setSubtotalPed(subtotal);
                nuevaLinea.setAlmacen(almacen);

                lineasPedido.add(nuevaLinea);
                tablaLineas.refresh();

                // Limpiar campos al salir
                comboProductos.getSelectionModel().clearSelection();
                comboAlmacenes.getSelectionModel().clearSelection();
                txtCantidad.clear();
                txtPrecio.clear();
            }catch (NumberFormatException ex){
                com.empresa.erp.utils.AlertaUtils.mostrar("Cantidad y precio deben ser numéricos");
            }
        });

        miniForm.getChildren().addAll(comboProductos, txtCantidad, txtPrecio, comboAlmacenes, btnAddLinea);

        // Configurar columnas
        configurarColumnas();

        Button btnGuardar = new Button("Guardar Cambios");
        btnGuardar.setStyle(botonStyle);
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle(botonStyle);

        btnGuardar.setOnAction(e -> {
            guardarCambios(pedido, comboProveedores, txtFecha, txtIVA);
            dialog.close();
        });

        btnCancelar.setOnAction(e -> {
            dialog.close();
        });

        //layout.getChildren().addAll(tablaLineas, btnGuardar);
        layout.getChildren().addAll(cabeceraBox1, cabeceraBox2, miniForm, tablaLineas, btnGuardar, btnCancelar);

        Scene scene = new Scene(layout, 800, 600);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
    
    private void configurarColumnas() {
        // Columna Producto (editable)
        TableColumn<LineasPed, String> colProducto = new TableColumn<>("Producto");
        colProducto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProducto().getNombreProd()));
        colProducto.setEditable(false);

        // Columna Cantidad (editable)
        TableColumn<LineasPed, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("cantidadProdPed"));
        colCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.IntegerStringConverter()));
        colCantidad.setOnEditCommit(event -> {
            LineasPed linea = event.getRowValue();
            linea.setCantidadProdPed(event.getNewValue());
            recalcularSubtotal(linea);
            tablaLineas.refresh();
        });

        // Columna Precio (editable)
        TableColumn<LineasPed, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("precioProdPed"));
        //colPrecio.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        colPrecio.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPrecio.setOnEditCommit(event -> {
            LineasPed linea = event.getRowValue();
            linea.setPrecioProdPed(event.getNewValue());
            recalcularSubtotal(linea);
            tablaLineas.refresh();
        });

        // Columna Subtotal (NO editable)
        TableColumn<LineasPed, Double> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("subtotalPed"));
        colSubtotal.setEditable(false);

        // Columna Borrar
        TableColumn<LineasPed, Void> colBorrar = new TableColumn<>("Acciones");
        colBorrar.setCellFactory(param -> new TableCell<>(){

            // Base para los botones de las líneas
            String botonBorrar = ("""
                    -fx-font-size: 13px;
                    -fx-background-radius: 10;
                    -fx-padding: 4 12;
                    -fx-cursor: hand;
                    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 4, 0, 0, 9);
                    -fx-background-color: transparent;
                    -fx-text-fill: #2c3e50;
                """);

            // Botón Borrar con Icono
            private final Button btnBorrar;
            {
                ImageView borrarIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/delete_24dp.png"))));
                borrarIcon.setFitHeight(20);
                borrarIcon.setFitWidth(20);
                btnBorrar = new Button("", borrarIcon);
                btnBorrar.setStyle(botonBorrar);

                
                btnBorrar.setOnAction(e -> {
                    LineasPed linea = getTableView().getItems().get(getIndex());
                    try{
                        // Pedir confirmación
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmar Borrado");
                        alert.setHeaderText(null);
                        alert.setContentText("¿Estás seguro de que deseas borrar este producto del pedido?");

                        // Esperar respuesta
                        alert.showAndWait().ifPresent(response -> {
                            if(response == ButtonType.OK){
                                // Primero borra de la base de datos
                                LineasPedDAO dao = new LineasPedDAO();
                                dao.delete(linea.getIdLineaPed());

                                // Segundo borra de la tabla gráfica
                                lineasPedido.remove(linea);

                                com.empresa.erp.utils.AlertaUtils.mostrar("Producto eliminado correctamente");
                            }
                        });
                    }catch(Exception ex){
                        ex.printStackTrace();
                        com.empresa.erp.utils.AlertaUtils.mostrar("Error al borrar el producto" + ex.getMessage());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                }else{
                    setGraphic(btnBorrar);
                }
            }
        });

        tablaLineas.getColumns().clear();
        tablaLineas.getColumns().addAll(colProducto, colCantidad, colPrecio, colSubtotal, colBorrar);

        colCantidad.setEditable(true);
        colPrecio.setEditable(true);
        tablaLineas.setEditable(true);
    }

    private void recalcularSubtotal(LineasPed linea) {
        double nuevoSubtotal = linea.getCantidadProdPed() * linea.getPrecioProdPed();
        linea.setSubtotalPed(nuevoSubtotal);
    }

    private void guardarCambios(CabPedidos pedido, ComboBox<Proveedores> comboProveedores, TextField txtFecha, TextField txtIVA) {
        try{
            // Guardar cambios en las líneas
            LineasPedDAO dao = new LineasPedDAO();
            //for(LineasPed linea : lineasPedido) {
            for(LineasPed linea : tablaLineas.getItems()) {
                recalcularSubtotal(linea);
                dao.update(linea);
            }

            double nuevoTotal = 0.0;
            for(LineasPed linea : tablaLineas.getItems()) {
                nuevoTotal += linea.getSubtotalPed();

            }

            // Parsear el texto de fecha a un objeto date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaDate = sdf.parse(txtFecha.getText().trim());

            // Actualizar datos de la cabecera
            pedido.setProveedor(comboProveedores.getValue());
            pedido.setFechaPed(fechaDate);
            pedido.setIvaPed(Double.parseDouble(txtIVA.getText().trim()));

            double totalConIVA = (nuevoTotal * Double.parseDouble(txtIVA.getText().trim())) / 100 + nuevoTotal;
            totalConIVA = Math.round(totalConIVA * 100) / 100.0;
            pedido.setTotalPed(totalConIVA);

            // Guardar cambios de la cabecera
            CabPedidosDAO daoCabPedidos = new CabPedidosDAO();
            daoCabPedidos.update(pedido);



            com.empresa.erp.utils.AlertaUtils.mostrar("Pedido actualizado correctamente");
        }catch (Exception e){
            e.printStackTrace();
            com.empresa.erp.utils.AlertaUtils.mostrar("Error al guardar los cambios: " + e.getMessage());
        }
    }
}

